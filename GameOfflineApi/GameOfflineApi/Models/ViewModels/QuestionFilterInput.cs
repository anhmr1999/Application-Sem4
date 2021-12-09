using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GameOfflineApi.Models.ViewModels
{
    public class QuestionFilterInput : PagedInput
    {
        public bool? Active { get; set; }
        public string Filter { get; set; }
    }
}