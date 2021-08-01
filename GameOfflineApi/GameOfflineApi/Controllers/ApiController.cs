using GameOfflineApi.Models.EntityManagers;
using GameOfflineApi.Models.EntityManagers.Entities;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Data.SqlClient;
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
            var result = context.Scores.Select(x => new Models.ViewModel.Score() { 
                gameId = x.GameId, 
                levelId = x.LevelId, 
                userId = x.UserId, 
                score = x.Point,
                user = new Models.ViewModel.User()
                {
                    id = x.User.Id,
                    name = x.User.Name
                }
            });
            return Json(result, JsonRequestBehavior.AllowGet);
        }

        public JsonResult GetScoreForUser(int id)
        {
            var result = context.Scores.Where(x=> x.UserId == id)?.Select(x => new Models.ViewModel.Score()
            {
                gameId = x.GameId,
                levelId = x.LevelId,
                userId = x.UserId,
                score = x.Point
            });
            return Json(result, JsonRequestBehavior.AllowGet);
        }

        public JsonResult GetUser(int id)
        {
            var userSeach = context.Users.Find(id);
            if(userSeach == null)
            {
                return null;
            }
            var result = new Models.ViewModel.User()
            {
                id = userSeach.Id,
                name = userSeach.Name,
                accessToken = "",
                avatar = userSeach.Avatar,
                achievements = userSeach.Achievements?.Select(x => new Models.ViewModel.Achievement() { id = x.Id }).ToList(),
                scores = userSeach.Scores?.Select(x => new Models.ViewModel.Score() { gameId = x.GameId, levelId = x.LevelId, score = x.Point, userId = x.UserId }).ToList()
            };
            return Json(result, JsonRequestBehavior.AllowGet);
        }

        public JsonResult GetAchievementForUser(int id)
        {
            var result = context.Users.FirstOrDefault(x => x.Id == id)?.Achievements;

            return Json(result, JsonRequestBehavior.AllowGet);
        }

        [HttpPost]
        public JsonResult UpdateScores(ICollection<Models.ViewModel.Score> scores)
        {
            if(scores == null || scores.Count == 0)
            {
                return Json(false, JsonRequestBehavior.AllowGet);
            }
            try
            {
                foreach (var score in scores)
                {
                    CreateOrUpdateScore(score);
                }
            }
            catch (Exception)
            {
                return Json(false, JsonRequestBehavior.AllowGet);
            }
            return Json(true, JsonRequestBehavior.AllowGet);
        }

        [HttpPost]
        public JsonResult UpdateScore(Models.ViewModel.Score score)
        {
            try
            {
                CreateOrUpdateScore(score);
            }
            catch (Exception)
            {
                return Json(false, JsonRequestBehavior.AllowGet);
            }
            return Json(true, JsonRequestBehavior.AllowGet);
        }

        [HttpPost]
        public JsonResult LoginUser(string Token="", string name="", int avatar = 1)
        {
            var loginUser = !string.IsNullOrEmpty(Token) ? context.Users.FirstOrDefault(x => x.AccessToken == Token) : null;
            if (loginUser == null)
            {
                loginUser = new User()
                {
                    Name = string.IsNullOrEmpty(name) ? "G" + DateTime.Now.ToLongDateString() : name,
                    AccessToken = Token,
                    Avatar = avatar
                };
                context.Users.Add(loginUser);
                context.SaveChanges();
            }
            Models.ViewModel.User user = new Models.ViewModel.User()
            {
                id = loginUser.Id,
                name = loginUser.Name,
                avatar = loginUser.Avatar,
                accessToken = loginUser.AccessToken,
                achievements = loginUser.Achievements?.Select(x=> new Models.ViewModel.Achievement() { 
                    id = x.Id
                }).ToList(),
                scores = loginUser.Scores?.Select(x=> new Models.ViewModel.Score(){
                    gameId = x.GameId,
                    levelId = x.LevelId,
                    score = x.Point,
                    userId = x.UserId
                }).ToList()
            };
            return Json(user, JsonRequestBehavior.AllowGet);
        }

        [HttpPost]
        public JsonResult AddAchievements(int userId, ICollection<int> achievements)
        {
            if (achievements == null || achievements.Count == 0)
            {
                return Json(false, JsonRequestBehavior.AllowGet);
            }
            try
            {
                foreach (int item in achievements)
                {
                    AddAchievementForUser(userId, item);
                }
                context.SaveChanges();
            }
            catch (Exception)
            {
                return Json(false, JsonRequestBehavior.AllowGet);
            }
            return Json(true, JsonRequestBehavior.AllowGet);
        }

        [HttpPost]
        public JsonResult AddAchievement(int userId, int achievementId) {
            try
            {
                AddAchievementForUser(userId, achievementId);
                context.SaveChanges();
            }
            catch (Exception ex)
            {
                return Json(false, JsonRequestBehavior.AllowGet);
            }
            return Json(true, JsonRequestBehavior.AllowGet);
        }

        public JsonResult GetAchievement(int lastId)
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

        [HttpPost]
        public JsonResult AddQuestion(Models.ViewModel.Question question)
        {
            try
            {
                context.Questions.Add(new Question()
                {
                    Active = false,
                    Answers = question.answers.Select(x => new Answer()
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

        public JsonResult GetQuestion(int lastId)
        {
            var result = context.Questions.Where(x => x.Id > lastId)?.Select(x =>
                new Models.ViewModel.Question()
                {
                    id = x.Id,
                    answers = x.Answers.Select(a=> new Models.ViewModel.Answer() { 
                        id = a.Id,
                        content = a.Content,
                        correct = a.Correct,
                        questionId = a.QuestionId
                    }).ToList(),
                    content = x.Content,
                    subject = x.Subject,
                    userId = x.UserId
                }
            );
            return Json(result, JsonRequestBehavior.AllowGet);
        }

        private void AddAchievementForUser(int userId, int achievementId)
        {
            context.Database.ExecuteSqlCommand("insert into UserAchievements values (@userId,@achievementId)", new SqlParameter("@userId",userId), new SqlParameter("@achievementId", achievementId));
        }

        private void CreateOrUpdateScore(Models.ViewModel.Score score)
        {
            Score scoreUpdate = context.Scores.FirstOrDefault(x => x.GameId == score.gameId && x.LevelId == score.levelId && x.UserId == score.userId);
            if (scoreUpdate != null)
            {
                scoreUpdate.Point = score.score;
                context.Entry(scoreUpdate).State = EntityState.Modified;
                context.SaveChanges();
            }
            else
            {
                scoreUpdate = new Score()
                {
                    GameId = score.gameId,
                    LevelId = score.levelId,
                    UserId = score.userId,
                    Point = score.score
                };
                context.Scores.Add(scoreUpdate);
                context.SaveChanges();
            }
        }
    }
}