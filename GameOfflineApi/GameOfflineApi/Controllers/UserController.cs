    using GameOfflineApi.Models.EntityManagers;
using GameOfflineApi.Models.EntityManagers.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace GameOfflineApi.Controllers
{
    public class UserController : Controller
    {
        private DataContext context = new DataContext();

        // GET: User
        public ActionResult Index()
        {
            return View();
        }

        public JsonResult AddNew(User user)
        {
            context.Users.Add(user);
            context.SaveChanges();
            return Json(user, JsonRequestBehavior.AllowGet);
        }

        public JsonResult AddAchievement(List<UserAchievement> achievements)
        {
            try
            {
                context.UserAchievements.AddRange(achievements);
                return Json(true, JsonRequestBehavior.AllowGet);
            }
            catch (Exception)
            {
                return Json(false, JsonRequestBehavior.AllowGet);
            }
        }

        public JsonResult LoginToGame(string Token)
        {
            var loginUser = context.Users.FirstOrDefault(x => x.AccessToken == Token);
            if(loginUser != null)
            {
                Models.ViewModel.User user = new Models.ViewModel.User() {
                    id = loginUser.Id,
                    name = loginUser.Name,
                    avatar = loginUser.Avatar,
                    accessToken = loginUser.AccessToken,
                    Achievements = loginUser.Achievements.Select(x => new Models.ViewModel.Achievement() { id = x.Id }).ToList(),
                    Scores = loginUser.Scores.Select(x => new Models.ViewModel.Score() { gameId = x.GameId, levelId = x.LevelId, userId = x.UserId, score = x.Point }).ToList()
                };
                return Json(user, JsonRequestBehavior.AllowGet);
            }
            return Json(null, JsonRequestBehavior.AllowGet);
        }
    }
}