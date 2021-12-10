    using GameOfflineApi.Models.EntityManagers;
using GameOfflineApi.Models.EntityManagers.Entities;
using GameOfflineApi.Models.ViewModels;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Data.Entity;
using System.Web;
using System.Web.Mvc;

namespace GameOfflineApi.Controllers
{
    public class UserController : Controller
    {
        private DataContext context = new DataContext();

        // GET: User
        public ActionResult Index(UserFilterInput input)
        {
            ViewBag.countAch = context.Achievements.Count();
            var ds = context.Users.Include(x=> x.Achievements).Include(x=> x.Scores);
            if (!string.IsNullOrEmpty(input.Filter))
            {
                ds = ds.Where(x=> x.Name.ToLower().Contains(input.Filter.ToLower()));
            }
            switch (input.OrderBy)
            {
                case 1:
                    if (input.Desc)
                        ds = ds.OrderByDescending(x => x.Name);
                    else
                        ds = ds.OrderBy(x=> x.Name);
                    break;
                case 2:
                    if (input.Desc)
                        ds = ds.OrderByDescending(x => x.Achievements.Count);
                    else
                        ds = ds.OrderBy(x => x.Achievements.Count);
                    break;
                default:
                    ds = ds.OrderByDescending(x=> x.Id);
                    break;
            }
            ViewBag.pages = Math.Ceiling((decimal)ds.Count()/input.TakeCount);
            ViewBag.current = input;
            var models = ds.Skip((input.PageNumber - 1) * input.TakeCount).Take(input.TakeCount).ToList();

            return View(models);
        }

        public ActionResult View(int? id)
        {
            if (id == null)
            {
                return RedirectToAction("Index");
            }
            var model = context.Users.Include(x => x.Scores).Include(x => x.Achievements).FirstOrDefault(x=> x.Id == id);
            return View(model);
        }
    }
}