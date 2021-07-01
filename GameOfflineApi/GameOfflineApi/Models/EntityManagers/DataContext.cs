using GameOfflineApi.Models.EntityManagers.Entities;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Web;

namespace GameOfflineApi.Models.EntityManagers
{
    public class DataContext : DbContext
    {
        public DataContext() : base("appConnect")
        {}

        public DbSet<User> Users { get; set; }
        public DbSet<Achievement> Achievements { get; set; }
        public DbSet<Game> Games { get; set; }
        public DbSet<LevelHard> LevelHards { get; set; }
        public DbSet<Question> Questions { get; set; }
        public DbSet<Answer> Answers { get; set; }
        public DbSet<Score> Scores { get; set; }
        public DbSet<UserAchievement> UserAchievements { get; set; }
    }
}