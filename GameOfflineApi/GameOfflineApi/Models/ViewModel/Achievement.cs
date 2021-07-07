using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GameOfflineApi.Models.ViewModel
{
    public class Achievement : BaseEntity
    {
        public string name { get; set; }
        public string tutorial { get; set; }
        public bool checkScore { get; set; }
        public int scoreOrNumber { get; set; }
        public int gameId { get; set; }
        public string levelName { get; set; }
    }
}