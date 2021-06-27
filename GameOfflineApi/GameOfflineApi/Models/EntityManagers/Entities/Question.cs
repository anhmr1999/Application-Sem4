using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GameOfflineApi.Models.EntityManagers.Entities
{
    public class Question : Entity
    {
        public string Content { get; set; }
        public bool Active { get; set; }
        public string Subject { get; set; }
        public int? UserId { get; set; }
        public User User { get; set; }
        public ICollection<Answer> Answers { get; set; }
    }
}