using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GameOfflineApi.Models.ViewModel
{
    public class Score
    {
        public Score(EntityManagers.Entities.Score score)
        {
            userId = score.UserId;
            gameId = score.GameId;
            levelId = score.LevelId;
            this.score = score.Point;
        }

        public int userId { get; set; }
        public int gameId { get; set; }
        public int levelId { get; set; }
        public int score { get; set; }
    }
}