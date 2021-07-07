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
    public class ScoreController : Controller
    {

        private DataContext context = new DataContext();

        // GET: Score
        public ActionResult Index()
        {
            return View();
        }

        public JsonResult GetListHighScore()
        {
            var result = context.Scores.Select(x=> new Models.ViewModel.Score() { gameId = x.GameId, levelId = x.LevelId, userId = x.UserId, score = x.Point });
            return Json(result, JsonRequestBehavior.AllowGet);
        }

        public JsonResult UpdateScore(ICollection<Models.ViewModel.Score> scores)
        {
            foreach(var score in scores)
            {
                if(context.Scores.Any(x=> x.GameId == score.gameId && x.LevelId == score.levelId && x.UserId == score.userId))
                {
                    Score scoreUpdate = context.Scores.FirstOrDefault(x=> x.GameId == score.gameId && x.LevelId == score.levelId && x.UserId == score.userId);
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
                } else
                {
                    try
                    {
                        Score scoreUpdate = new Score() {
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
            return Json(true , JsonRequestBehavior.AllowGet);
        }
    }
}