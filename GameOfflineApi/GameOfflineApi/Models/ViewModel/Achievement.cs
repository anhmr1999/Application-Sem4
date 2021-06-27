using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GameOfflineApi.Models.ViewModel
{
    public class Achievement : BaseEntity
    {
        public Achievement(EntityManagers.Entities.Achievement achievement)
        {
            id = achievement.Id;
            name = achievement.Name;
            tutorial = achievement.Tutorial;
        }
        public string name { get; set; }
        public string tutorial { get; set; }
    }
}