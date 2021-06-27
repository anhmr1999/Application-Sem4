using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GameOfflineApi.Models.EntityManagers.Entities
{
    public class Answer : Entity
    {
        public string Content { get; set; }
        public bool Correct { get; set; }
        public Question Question { get; set; }
    }
}