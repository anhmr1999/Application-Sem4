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
    }
}