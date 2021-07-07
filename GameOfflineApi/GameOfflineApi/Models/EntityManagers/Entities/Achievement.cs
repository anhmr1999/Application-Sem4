using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GameOfflineApi.Models.EntityManagers.Entities
{
    public class Achievement : Entity
    {
        public string Name { get; set; }
        public string Tutorial { get; set; }
        public bool CheckScore { get; set; }
        public int ScoreOrNumber { get; set; }
        public int GameId { get; set; }
        public string LevelName { get; set; }
        public ICollection<User> Users{ get; set; }
    }
}