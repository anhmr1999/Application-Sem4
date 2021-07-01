using GameOfflineApi.Models.EntityManagers;
using GameOfflineApi.Models.EntityManagers.Entities;
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

        // GET: Question
        public JsonResult Download(int lastId)
        {
            IQueryable<Question> questions = context.Questions.Where(x => x.Id > lastId).OrderBy(x=> x.Id).Take(50);
            IEnumerable<Models.ViewModel.Question> result = questions.Select(x => new Models.ViewModel.Question() { 
                id = x.Id,
                content = x.Content,
                subject =x.Subject,
                Answers = x.Answers.Select(a=> new Models.ViewModel.Answer() { 
                    id = a.Id,
                    content = a.Content,
                    correct = a.Correct,
                    QuestionId = x.Id
                }).ToList()
            });
            return Json(result, JsonRequestBehavior.AllowGet);
        }

        [HttpPost]
        public JsonResult Upload(Question question)
        {
            try
            {
                context.Questions.Add(question);
                context.SaveChanges();
                return Json(true, JsonRequestBehavior.AllowGet);
            }
            catch{}
            return Json(false, JsonRequestBehavior.AllowGet);
        }

        public ActionResult Index(int questionPage = 1, int wattingPage = 1)
        {
            ViewBag.questions = context.Questions.Where(x => x.Active).OrderBy(x => x.Id).Skip((questionPage - 1) * 15).Take(15);
            ViewBag.wattings = context.Questions.Where(x => !x.Active).OrderBy(x => x.Id).Skip((questionPage - 1) * 15).Take(15);
            return View();
        }

        public ActionResult ViewQuestion(int Id)
        {
            Question question = context.Questions.Find(Id);
            if(question == null)
            {
                return RedirectToAction("Index");
            }
            return View();
        }

        [HttpPost]
        public ActionResult SaveQuestion(Question question)
        {
            try
            {
                context.Entry(question).State = EntityState.Modified;
                context.SaveChanges();
                return RedirectToAction("Index");
            }
            catch (Exception)
            {
                return RedirectToAction("ViewQuestion");
            }
        }

        public ActionResult Delete(int Id)
        {
            try
            {
                Question question = context.Questions.Find(Id);
                if(question == null)
                {
                    return RedirectToAction("Index");
                }
                context.Questions.Remove(question);
                context.SaveChanges();
            }
            catch (Exception)
            {
            }
            return RedirectToAction("Index");
        }
    }
}