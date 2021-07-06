using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace GameOfflineApi.Models.EntityManagers.Entities
{
    public class Score
    {
        [Key,Column(Order =0)]
        public int UserId { get; set; }
        [Key, Column(Order = 1)]
        public int GameId { get; set; }
        [Key, Column(Order = 2)]
        public int LevelId { get; set; }
        public int Point { get; set; }
        public User User { get; set; }
        public Game Game { get; set; }
        [ForeignKey("LevelId")]
        public LevelHard LevelHard { get; set; }
    }
}