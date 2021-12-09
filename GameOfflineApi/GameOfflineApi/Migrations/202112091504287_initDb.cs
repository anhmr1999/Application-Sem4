namespace GameOfflineApi.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class initDb : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.Achievements",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Name = c.String(),
                        Tutorial = c.String(),
                        CheckScore = c.Boolean(nullable: false),
                        ScoreOrNumber = c.Int(nullable: false),
                        GameId = c.Int(nullable: false),
                        LevelName = c.String(),
                    })
                .PrimaryKey(t => t.Id);
            
            CreateTable(
                "dbo.Users",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Name = c.String(),
                        AccessToken = c.String(),
                        Avatar = c.Int(nullable: false),
                        CreationTime = c.DateTime(nullable: false),
                    })
                .PrimaryKey(t => t.Id);
            
            CreateTable(
                "dbo.Questions",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Content = c.String(),
                        Active = c.Boolean(nullable: false),
                        Subject = c.String(),
                        UserId = c.Int(),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Users", t => t.UserId)
                .Index(t => t.UserId);
            
            CreateTable(
                "dbo.Answers",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Content = c.String(),
                        Correct = c.Boolean(nullable: false),
                        QuestionId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Questions", t => t.QuestionId, cascadeDelete: true)
                .Index(t => t.QuestionId);
            
            CreateTable(
                "dbo.Scores",
                c => new
                    {
                        UserId = c.Int(nullable: false),
                        GameId = c.Int(nullable: false),
                        LevelId = c.Int(nullable: false),
                        Point = c.Int(nullable: false),
                    })
                .PrimaryKey(t => new { t.UserId, t.GameId, t.LevelId })
                .ForeignKey("dbo.Games", t => t.GameId, cascadeDelete: true)
                .ForeignKey("dbo.LevelHards", t => t.LevelId, cascadeDelete: true)
                .ForeignKey("dbo.Users", t => t.UserId, cascadeDelete: true)
                .Index(t => t.UserId)
                .Index(t => t.GameId)
                .Index(t => t.LevelId);
            
            CreateTable(
                "dbo.Games",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Name = c.String(),
                    })
                .PrimaryKey(t => t.Id);
            
            CreateTable(
                "dbo.LevelHards",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Name = c.String(),
                        Description = c.String(),
                    })
                .PrimaryKey(t => t.Id);
            
            CreateTable(
                "dbo.UserAchievements",
                c => new
                    {
                        User_Id = c.Int(nullable: false),
                        Achievement_Id = c.Int(nullable: false),
                    })
                .PrimaryKey(t => new { t.User_Id, t.Achievement_Id })
                .ForeignKey("dbo.Users", t => t.User_Id, cascadeDelete: true)
                .ForeignKey("dbo.Achievements", t => t.Achievement_Id, cascadeDelete: true)
                .Index(t => t.User_Id)
                .Index(t => t.Achievement_Id);
            
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.Scores", "UserId", "dbo.Users");
            DropForeignKey("dbo.Scores", "LevelId", "dbo.LevelHards");
            DropForeignKey("dbo.Scores", "GameId", "dbo.Games");
            DropForeignKey("dbo.Questions", "UserId", "dbo.Users");
            DropForeignKey("dbo.Answers", "QuestionId", "dbo.Questions");
            DropForeignKey("dbo.UserAchievements", "Achievement_Id", "dbo.Achievements");
            DropForeignKey("dbo.UserAchievements", "User_Id", "dbo.Users");
            DropIndex("dbo.UserAchievements", new[] { "Achievement_Id" });
            DropIndex("dbo.UserAchievements", new[] { "User_Id" });
            DropIndex("dbo.Scores", new[] { "LevelId" });
            DropIndex("dbo.Scores", new[] { "GameId" });
            DropIndex("dbo.Scores", new[] { "UserId" });
            DropIndex("dbo.Answers", new[] { "QuestionId" });
            DropIndex("dbo.Questions", new[] { "UserId" });
            DropTable("dbo.UserAchievements");
            DropTable("dbo.LevelHards");
            DropTable("dbo.Games");
            DropTable("dbo.Scores");
            DropTable("dbo.Answers");
            DropTable("dbo.Questions");
            DropTable("dbo.Users");
            DropTable("dbo.Achievements");
        }
    }
}
