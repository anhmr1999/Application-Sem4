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
    }
}