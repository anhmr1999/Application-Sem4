using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GameOfflineApi.Models.EntityManagers.Entities
{
    public class Game : Entity
    {
        public string Name { get; set; }
        public ICollection<Score> Scores { get; set; }
    }
}