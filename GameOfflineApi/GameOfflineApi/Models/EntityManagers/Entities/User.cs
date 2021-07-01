using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GameOfflineApi.Models.EntityManagers.Entities
{
    public class User : Entity
    {
        public string Name { get; set; }
        public string AccessToken { get; set; }
        public int Avatar { get; set; }
        public ICollection<Achievement> Achievements { get; set; }
        public ICollection<Question> Questions { get; set; }
        public ICollection<Score> Scores { get; set; }
    }
}