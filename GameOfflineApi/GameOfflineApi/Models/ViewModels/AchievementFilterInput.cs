using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GameOfflineApi.Models.ViewModels
{
    public class AchievementFilterInput : PagedInput
    {
        public string Filter { get; set; }
        public string LevelName { get; set; }
        public int? GameId { get; set; }
    }
}