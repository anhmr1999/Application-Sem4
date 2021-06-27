using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace GameOfflineApi.Models.EntityManagers.Entities
{
    public class Entity
    {
        [Key]
        public int Id { get; set; }
    }
}