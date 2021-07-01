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
    }
}