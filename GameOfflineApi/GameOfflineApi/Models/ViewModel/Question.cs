using GameOfflineApi.Models.EntityManagers.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GameOfflineApi.Models.ViewModel
{
    public class Question : BaseEntity
    {
        public string content { get; set; }
        public string subject { get; set; }
        public int? userId { get; set; }
        public ICollection<Answer> answers { get; set; }
    }

    public class Answer : BaseEntity
    {
        public string content { get; set; }
        public bool correct { get; set; }
        public int questionId { get; set; }
    }
}