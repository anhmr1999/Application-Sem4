using GameOfflineApi.Models.EntityManagers;
using GameOfflineApi.Models.EntityManagers.Entities;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace GameOfflineApi.Controllers
{
    public class ApiController : Controller
    {
        private DataContext context = new DataContext();

        public JsonResult GetScore()
        {
            var result = context.Scores.Select(x => new Models.ViewModel.Score() { gameId = x.GameId, levelId = x.LevelId, userId = x.UserId, score = x.Point });
            return Json(result, JsonRequestBehavior.AllowGet);
        }

        public JsonResult UpdateScore(ICollection<Models.ViewModel.Score> scores)
        {
            foreach (var score in scores)
            {
                if (context.Scores.Any(x => x.GameId == score.gameId && x.LevelId == score.levelId && x.UserId == score.userId))
                {
                    Score scoreUpdate = context.Scores.FirstOrDefault(x => x.GameId == score.gameId && x.LevelId == score.levelId && x.UserId == score.userId);
                    scoreUpdate.Point = score.score;
                    try
                    {
                        context.Entry(scoreUpdate).State = EntityState.Modified;
                        context.SaveChanges();
                    }
                    catch (Exception)
                    {
                        return Json(false, JsonRequestBehavior.AllowGet);
                    }
                }
                else
                {
                    try
                    {
                        Score scoreUpdate = new Score()
                        {
                            GameId = score.gameId,
                            LevelId = score.levelId,
                            UserId = score.userId,
                            Point = score.score
                        };
                        context.Scores.Add(scoreUpdate);
                        context.SaveChanges();
                    }
                    catch (Exception)
                    {
                        return Json(false, JsonRequestBehavior.AllowGet);
                    }
                }
            }
            return Json(true, JsonRequestBehavior.AllowGet);
        }

        public JsonResult LoginUser(string Token, string name="")
        {
            var loginUser = context.Users.FirstOrDefault(x => x.AccessToken == Token);
            if (loginUser == null)
            {
                context.Users.Add(new User()
                {
                    Name = string.IsNullOrEmpty(name) ? "New Player" : name,
                    AccessToken = Token,
                    Avatar = 1,
                });
                context.SaveChanges();
                loginUser = context.Users.FirstOrDefault(x => x.AccessToken == Token);
            }
            Models.ViewModel.User user = new Models.ViewModel.User()
            {
                id = loginUser.Id,
                name = loginUser.Name,
                avatar = loginUser.Avatar,
                accessToken = loginUser.AccessToken,
                Achievements = loginUser.Achievements?.Select(x => new Models.ViewModel.Achievement() { id = x.Id }).ToList(),
                Scores = loginUser.Scores?.Select(x => new Models.ViewModel.Score() { gameId = x.GameId, levelId = x.LevelId, userId = x.UserId, score = x.Point }).ToList()
            };
            return Json(user, JsonRequestBehavior.AllowGet);
        }

        public JsonResult AddAchievement(int userId, ICollection<int> achievements)
        {
            foreach (var item in achievements)
            {
                try
                {
                    if (!context.UserAchievements.Any(x => x.AchievementId == item && x.UserId == userId))
                    {
                        context.UserAchievements.Add(new UserAchievement()
                        {
                            UserId = userId,
                            AchievementId = item
                        });
                        context.SaveChanges();
                    }
                }
                catch (Exception)
                {
                    return Json(false, JsonRequestBehavior.AllowGet);
                }
            }
            return Json(true, JsonRequestBehavior.AllowGet);
        }

        public JsonResult getAchievement(int lastId)
        {
            var result = context.Achievements.Where(x => x.Id > lastId)?.Select(x=>
                new Models.ViewModel.Achievement()
                {
                    id = x.Id,
                    checkScore = x.CheckScore,
                    gameId = x.GameId,
                    levelName = x.LevelName,
                    name = x.Name,
                    scoreOrNumber = x.ScoreOrNumber,
                    tutorial = x.Tutorial
                }
            );
            return Json(result, JsonRequestBehavior.AllowGet);
        }

        public JsonResult AddQuestion(Models.ViewModel.Question question)
        {
            try
            {
                context.Questions.Add(new Question()
                {
                    Active = false,
                    Answers = question.Answers.Select(x => new Answer()
                    {
                        Content = x.content,
                        Correct = x.correct
                    }).ToList(),
                    Content = question.content,
                    Subject = question.subject,
                    UserId = question.userId
                });
                context.SaveChanges();
            }
            catch (Exception)
            {
                return Json(false, JsonRequestBehavior.AllowGet);
            }
            return Json(true, JsonRequestBehavior.AllowGet);
        }

        public JsonResult getQuestion(int lastId)
        {
            var result = context.Questions.Where(x => x.Id > lastId)?.Select(x =>
                new Models.ViewModel.Question()
                {
                    id = x.Id,
                    Answers = x.Answers.Select(a=> new Models.ViewModel.Answer() { 
                        id = a.Id,
                        content = a.Content,
                        correct = a.Correct,
                        QuestionId = a.QuestionId
                    }).ToList(),
                    content = x.Content,
                    subject = x.Subject,
                    userId = x.UserId
                }
            );
            return Json(result, JsonRequestBehavior.AllowGet);
        }
    }
}