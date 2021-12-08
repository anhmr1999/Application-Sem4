using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GameOfflineApi.App_Start
{
    public static class AuthenConfig
    {
        public static bool IsLogined { get; set; }
        private static ICollection<MenuItem> Menus { get; set; }
        public static ICollection<MenuItem> GetMenus()
        {
            if(IsLogined)
                return Menus;

            return null;
        }
    }

    public class MenuItem {
    
    }
}