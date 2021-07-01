using GameOfflineApi.Models.EntityManagers;
using System;
using System.Collections.Generic;
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
    }
}