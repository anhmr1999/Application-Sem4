using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GameOfflineApi.Models.ViewModels
{
    public class PagedInput
    {
        public int TakeCount { get; set; } = 10;
        public int PageNumber { get; set; } = 1;
    }
}