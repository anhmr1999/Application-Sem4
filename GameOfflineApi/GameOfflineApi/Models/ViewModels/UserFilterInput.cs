﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GameOfflineApi.Models.ViewModels
{
    public class UserFilterInput : PagedInput
    {
        public string Filter { get; set; }
        public int OrderBy { get; set; }
        public bool Desc { get; set; } = true;
    }
}