using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GameOfflineApi.Models.ViewModel
{
    public class User : BaseEntity
    {
        public User(EntityManagers.Entities.User user)
        {
            id = user.Id;
            name = user.Name;
        }

        public string name { get; set; }
        public string accessToken { get; set; }
    }
}