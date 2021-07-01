namespace GameOfflineApi.Migrations
{
    using GameOfflineApi.Models.EntityManagers;
    using System;
    using System.Data.Entity;
    using System.Data.Entity.Migrations;
    using System.Linq;

    internal sealed class Configuration : DbMigrationsConfiguration<DataContext>
    {
        public Configuration()
        {
            AutomaticMigrationsEnabled = false;
        }

        protected override void Seed(DataContext context)
        {
            context.Games.AddRange(InitialData.Games);
            context.LevelHards.AddRange(InitialData.LevelHards);
            context.Users.AddRange(InitialData.Users);
            context.Achievements.AddRange(InitialData.Achievements);
            context.Questions.AddRange(InitialData.Questions);
            context.Answers.AddRange(InitialData.Answers);
            context.Scores.AddRange(InitialData.Scores);

            context.SaveChanges();
        }
    }
}
