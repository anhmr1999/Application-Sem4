using GameOfflineApi.Models.EntityManagers;
using GameOfflineApi.Models.EntityManagers.Entities;
using GameOfflineApi.Models.ViewModels;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Web.Mvc;

namespace GameOfflineApi.Controllers
{
    public class QuestionController : Controller
    {
        private DataContext context = new DataContext();

        public ActionResult Index(QuestionFilterInput input)
        {
            var list = context.Questions.Include(x => x.User);
            if (input.Active != null)
            {
                list = list.Where(x=> x.Active == input.Active.Value);
            }
            if (!string.IsNullOrEmpty(input.Filter))
            {
                list = list.Where(x => x.Content.ToLower().Contains(input.Filter.ToLower()));
            }
            var count = list.Count();
            ViewBag.pages = Math.Ceiling((decimal)count / input.TakeCount);
            ViewBag.current = input;
            var models = list.OrderByDescending(x=> x.Id).Skip((input.PageNumber - 1) * input.TakeCount).Take(input.TakeCount).ToList();
            return View(models);
        }

        public ActionResult Edit(int? id)
        {
            if (id == null)
            {
                return RedirectToAction("Index");
            }
            var model = context.Questions.Include(x=> x.Answers).FirstOrDefault(x=> x.Id == id);
            return View(model);
        }

        public JsonResult Edit(Question question)
        {
            try
            {
                context.Entry(question).State = EntityState.Modified;
                context.SaveChanges();
                return Json(new { success = true }, JsonRequestBehavior.AllowGet);
            }
            catch (Exception)
            {
                return Json(new { success = false }, JsonRequestBehavior.AllowGet);
            }
        }
    }
}