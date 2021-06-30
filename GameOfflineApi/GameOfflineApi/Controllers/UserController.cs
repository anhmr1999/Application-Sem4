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
        private DataContext context;

        public UserController()
        {
            context = new DataContext();
        }

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
    }
}