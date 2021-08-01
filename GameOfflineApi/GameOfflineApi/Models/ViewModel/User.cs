using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GameOfflineApi.Models.ViewModel
{
    public class User : BaseEntity
    {
        public string name { get; set; }
        public string accessToken { get; set; }
        public int avatar { get; set; }
        public ICollection<Achievement> achievements { get; set; }
        public ICollection<Score> scores { get; set; }
    }
}