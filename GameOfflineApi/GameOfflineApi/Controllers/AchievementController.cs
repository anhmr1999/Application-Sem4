using GameOfflineApi.Models.EntityManagers;
using GameOfflineApi.Models.EntityManagers.Entities;
using GameOfflineApi.Models.ViewModels;
using System;
using System.Linq;
using System.Web.Mvc;

namespace GameOfflineApi.Controllers
{
    public class AchievementController : Controller
    {

        private DataContext context = new DataContext();

        // GET: Score
        public ActionResult Index(AchievementFilterInput input)
        {
            var list = context.Achievements.AsQueryable();
            if (!string.IsNullOrEmpty(input.Filter))
                list = list.Where(x => x.Name.ToLower().Contains(input.Filter.ToLower()));
            if (!string.IsNullOrEmpty(input.LevelName))
                list = list.Where(x => x.LevelName == input.LevelName);

            var count = list.Count();
            ViewBag.pages = Math.Ceiling((decimal)count / input.TakeCount);
            ViewBag.current = input.PageNumber;
            var model = list.OrderByDescending(x => x.Id).Skip((input.PageNumber - 1) * input.TakeCount).Take(input.TakeCount).ToList();
            ViewBag.games = context.Games;
            return View(model);
        }
    }
}