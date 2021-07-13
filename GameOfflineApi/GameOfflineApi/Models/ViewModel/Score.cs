using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GameOfflineApi.Models.ViewModel
{
    public class Score
    {
        public int userId { get; set; }
        public int gameId { get; set; }
        public int levelId { get; set; }
        public int score { get; set; }
        public User User { get; set; }
    }
}