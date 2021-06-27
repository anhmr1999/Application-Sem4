﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GameOfflineApi.Models.EntityManagers.Entities
{
    public class LevelHard : Entity
    {
        public string Name { get; set; }
        public string Description { get; set; }
        public int GameId { get; set; }
        public Game Game { get; set; }
        public ICollection<Score> Scores { get; set; }
    }
}