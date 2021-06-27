using GameOfflineApi.Models.EntityManagers.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GameOfflineApi.Models.ViewModel
{
    public class Question : BaseEntity
    {
        public Question(EntityManagers.Entities.Question question)
        {
            id = question.Id;
            content = question.Content;
            subject = question.Subject;
            Answers = question.Answers.Select(x => new Answer(x)).ToList();
        }

        public string content { get; set; }
        public string subject { get; set; }
        public ICollection<Answer> Answers { get; set; }
    }

    public class Answer : BaseEntity
    {
        public Answer(EntityManagers.Entities.Answer answer)
        {
            id = answer.Id;
            content = answer.Content;
            correct = answer.Correct;
        }

        public string content { get; set; }
        public bool correct { get; set; }
    }
}