using GameOfflineApi.Models.EntityManagers.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GameOfflineApi.Models.EntityManagers
{
    public static class InitialData
    {
        public static IEnumerable<Game> Games = new List<Game>()
        {
            new Game(){Id = 1, Name = "Game FlappyBird"},
            new Game(){Id = 2, Name = "Game 2048"},
            new Game(){Id = 3, Name = "Game KnowLedge"}
        };

        public static IEnumerable<LevelHard> LevelHards = new List<LevelHard>()
        {
            new LevelHard(){Id = 1, Name = "easy", Description = ""},
            new LevelHard(){Id = 2, Name = "normal", Description = ""},
            new LevelHard(){Id = 3, Name = "difficult", Description = ""}
        };

        public static IEnumerable<Achievement> Achievements = new List<Achievement>() { 
            new Achievement(){Id = 1, Name = "new Player", Tutorial = ""},
            new Achievement(){Id = 2, Name = "Obstacle specialist!", Tutorial = ""},
            new Achievement(){Id = 3, Name = "Number matching expert!", Tutorial = ""},
            new Achievement(){Id = 4, Name = "Smart player!", Tutorial = ""}
        };

        public static IEnumerable<User> Users = new List<User>() {
            new User(){Id = 1, Name = "Tom Hiddleston", Avatar = 1},
            new User(){Id = 2, Name = "Alethea", Avatar = 1},
            new User(){Id = 3, Name = "Eudora", Avatar = 1},
            new User(){Id = 4, Name = "Griselda", Avatar = 1}
        };

        public static IEnumerable<Question> Questions = new List<Question>() {
            new Question(){Id = 1, Content = "Vì sao sóng biển lại có màu trắng?", Subject = "", UserId = null, Active = true},
            new Question(){Id = 2, Content = "Vì sao bầu trời ban đêm có màu đen?", Subject = "", UserId = null, Active = true},
            new Question(){Id = 3, Content = "Tại sao ngủ lại mơ?", Subject = "", UserId = null, Active = true},
            new Question(){Id = 4, Content = "Tại sao đứng trên cao nhìn xuống lại thấy chóng mặt?", Subject = "", UserId = null, Active = true},
            new Question(){Id = 5, Content = "Vì sao bầu trời lại có màu xanh?", Subject = "", UserId = null, Active = true},
            new Question(){Id = 6, Content = "Vì sao lại có sương mù?", Subject = "", UserId = null, Active = true},
            new Question(){Id = 7, Content = "Tại sao tai biết tiếng động từ đâu dội tới?", Subject = "", UserId = null, Active = true},
            new Question(){Id = 8, Content = "Tại sao khi chóng mặt lại có cảm giác mọi thứ xoay tròn?", Subject = "", UserId = null, Active = true},
            new Question(){Id = 9, Content = "Cầu vồng là gì?", Subject = "", UserId = null, Active = true},
            new Question(){Id = 10, Content = "Vì sao lá cây có màu xanh lục mà khi về già lại có màu vàng hoặc đỏ?", Subject = "", UserId = null, Active = true}
        };

        public static IEnumerable<Answer> Answers = new List<Answer>() { 
            new Answer(){Id = 1, Content = "Vì sóng biển là dạng các hạt thủy tinh vỡ vụn", Correct = true, QuestionId = 1},
            new Answer(){Id = 2, Content = "Vì nước biển có nhiều muối trắng", Correct = false, QuestionId = 1},
            new Answer(){Id = 3, Content = "Vì biển đã già mà sóng là tóc nên màu trắng", Correct = false, QuestionId = 1},
            new Answer(){Id = 4, Content = "Vì nước biển đập vào đá và cát nên tạo ra màu trắng của sóng", Correct = false, QuestionId = 1},

            new Answer(){Id = 5, Content = "Vì ban đêm không có mặt trời", Correct = false, QuestionId = 2},
            new Answer(){Id = 6, Content = "Vì mây ban đêm mây có màu đen", Correct = false, QuestionId = 2},
            new Answer(){Id = 7, Content = "Vì số sao trên trời đêm không đủ thắp sáng bầu trời", Correct = true, QuestionId = 2},
            new Answer(){Id = 8, Content = "Trời tối thì tất nhiên phải đen rồi", Correct = false, QuestionId = 2},

            new Answer(){Id = 9, Content = "Giấc mơ truyền tải các suy nghĩ sâu thẳm trong tâm trí", Correct = false, QuestionId = 3},
            new Answer(){Id = 10, Content = "Vì có những cơ quan trong cơ thể vẫn hoạt động khi ngủ", Correct = false, QuestionId = 3},
            new Answer(){Id = 11, Content = "Ngày nghĩ gì đêm mơ đấy", Correct = false, QuestionId = 3},
            new Answer(){Id = 12, Content = "Tất cả đều đúng", Correct = true, QuestionId = 3},

            new Answer(){Id = 13, Content = "Vì độ cao làm bạn sợ, làm bạn choáng", Correct = false, QuestionId = 4},
            new Answer(){Id = 14, Content = "Vì khi nhìn thẳng xuống, áp lực không khí và gió làm ảnh hưởng đến cấu trúc cân bằng trong tai", Correct = true, QuestionId = 4},
            new Answer(){Id = 15, Content = "Vì khi nhìn từ cao xuống, mọi vật đều bé hơn làm bạn choáng váng", Correct = false, QuestionId = 4},
            new Answer(){Id = 16, Content = "Tại khi nhìn từ cao xuống tạo cảm giác kích thích mạnh làm bạn mất cân bằng", Correct = false, QuestionId = 4},

            new Answer(){Id = 17, Content = "Vì da trời vốn dĩ có màu xanh", Correct = false, QuestionId = 5},
            new Answer(){Id = 18, Content = "Ánh sáng xanh bị các phân tử không khí phân tán đi xung quanh và nhuộm xanh cả bầu trời", Correct = true, QuestionId = 5},
            new Answer(){Id = 19, Content = "Vì ánh sáng mặt trời có màu xanh, khiến bầu trời có màu xanh", Correct = false, QuestionId = 5},
            new Answer(){Id = 20, Content = "Tầng ozon chặn các ánh sáng khác và chỉ để cho ánh sáng màu xanh đi qua làm da trời có màu xanh", Correct = false, QuestionId = 5},

            new Answer(){Id = 21, Content = "Cây cối nhả ra khí trắng là sương mù", Correct = false, QuestionId = 6},
            new Answer(){Id = 22, Content = "Những đám bông gòn mỏng và ẩm ước bay ra ngoài tạo ra sương mù", Correct = false, QuestionId = 6},
            new Answer(){Id = 23, Content = "Sương mù là hơi nước trong khí quyển ngưng kết lại thành những hạt nước rất nhỏ lơ lửng trong không khí và làm giảm tầm nhìn của chúng ta", Correct = true, QuestionId = 6},
            new Answer(){Id = 24, Content = "Sương mù là mây xuống đất dạo chơi", Correct = true, QuestionId = 6},

            new Answer(){Id = 25, Content = "Do sự chênh lệch giữa thời gian nghe thấy của 2 bên tai và áp lực không khí vào 2 màng nhĩ của 2 tai", Correct = true, QuestionId = 7},
            new Answer(){Id = 26, Content = "Do âm thanh truyền tới từ bên tai nào thì chỉ tai đó nghe thấy", Correct = false, QuestionId = 7},
            new Answer(){Id = 27, Content = "Áp lực không khí của 2 tai cảm nhận khác nhau nên nhận biết được", Correct = false, QuestionId = 7},
            new Answer(){Id = 28, Content = "Do người âm thanh cũng gây áp lực không khí vào da làm bạn cảm nhận được âm thanh từ đâu đến", Correct = false, QuestionId = 7},

            new Answer(){Id = 29, Content = "Do não đang bị choáng nên gây cảm giác quay tròn", Correct = false, QuestionId = 8},
            new Answer(){Id = 30, Content = "Lúc bị chóng mặt, mắt liên tục di chuyển tạo ra cảm giác quay tròn", Correct = false, QuestionId = 8},
            new Answer(){Id = 31, Content = "Do hệ thần kinh trung ương không đồng bộ được tín hiệu từ tai, mắt, cơ thể, cơ và xương", Correct = true, QuestionId = 8},
            new Answer(){Id = 32, Content = "Chóng mặt làm mất cảm giác giữa không gian nên gây ra cảm giác quay tròn", Correct = false, QuestionId = 8},

            new Answer(){Id = 33, Content = "Là chiếc cầu nối tới chân trời", Correct = false, QuestionId = 9},
            new Answer(){Id = 34, Content = "Là ánh sáng mặt trời bị phản xạ khi chiếu vào các giọt nước lơ lửng trong không khí", Correct = true, QuestionId = 9},
            new Answer(){Id = 35, Content = "Là con đường do các người lùn tạo ra để đi tới nhà của nhau", Correct = false, QuestionId = 9},
            new Answer(){Id = 36, Content = "Là chiếc cầu nhiều màu sắc được mây mưa mang đến", Correct = false, QuestionId = 9},

            new Answer(){Id = 37, Content = "Do lá cây có diệp lục, diệp lục khi già sẽ biến thành màu đỏ, vàng", Correct = false, QuestionId = 10},
            new Answer(){Id = 38, Content = "Do lúc già, lá cây chỉ có thể phản xạ màu vàng và đỏ", Correct = false, QuestionId = 10},
            new Answer(){Id = 39, Content = "Lá cây giống như tóc, khi già sẽ chuyển sang màu khác mà ở đây là vàng và đỏ", Correct = false, QuestionId = 10},
            new Answer(){Id = 40, Content = "Do khi lá già đi, diệp lục trong lá không còn đủ nhiều để lấn áp đi các màu sắc khác nên ta thấy lá cây có màu vàng và đỏ", Correct = true, QuestionId = 10}
        };

        public static IEnumerable<Score> Scores = new List<Score>() {
            new Score(){GameId = 1, LevelId = 1, UserId = 1, Point = 50},
            new Score(){GameId = 1, LevelId = 2, UserId = 1, Point = 40},
            new Score(){GameId = 1, LevelId = 3, UserId = 1, Point = 70},

            new Score(){GameId = 1, LevelId = 1, UserId = 2, Point = 60},
            new Score(){GameId = 1, LevelId = 2, UserId = 2, Point = 70},
            new Score(){GameId = 1, LevelId = 3, UserId = 2, Point = 80},

            new Score(){GameId = 1, LevelId = 1, UserId = 3, Point = 102},
            new Score(){GameId = 1, LevelId = 2, UserId = 3, Point = 99},
            new Score(){GameId = 1, LevelId = 3, UserId = 3, Point = 41},

            new Score(){GameId = 1, LevelId = 1, UserId = 4, Point = 32},
            new Score(){GameId = 1, LevelId = 2, UserId = 4, Point = 101},
            new Score(){GameId = 1, LevelId = 3, UserId = 4, Point = 91},

            new Score(){GameId = 2, LevelId = 1, UserId = 1, Point = 24058},
            new Score(){GameId = 2, LevelId = 2, UserId = 1, Point = 18204},
            new Score(){GameId = 2, LevelId = 3, UserId = 1, Point = 36054},

            new Score(){GameId = 2, LevelId = 1, UserId = 2, Point = 16000},
            new Score(){GameId = 2, LevelId = 2, UserId = 2, Point = 20000},
            new Score(){GameId = 2, LevelId = 3, UserId = 2, Point = 16000},

            new Score(){GameId = 2, LevelId = 1, UserId = 3, Point = 18024},
            new Score(){GameId = 2, LevelId = 2, UserId = 3, Point = 26048},
            new Score(){GameId = 2, LevelId = 3, UserId = 3, Point = 31024},

            new Score(){GameId = 2, LevelId = 1, UserId = 4, Point = 27024},
            new Score(){GameId = 2, LevelId = 2, UserId = 4, Point = 18484},
            new Score(){GameId = 2, LevelId = 3, UserId = 4, Point = 30674},

            new Score(){GameId = 3, LevelId = 1, UserId = 1, Point = 120},
            new Score(){GameId = 3, LevelId = 2, UserId = 1, Point = 95},
            new Score(){GameId = 3, LevelId = 3, UserId = 1, Point = 75},

            new Score(){GameId = 3, LevelId = 1, UserId = 2, Point = 85},
            new Score(){GameId = 3, LevelId = 2, UserId = 2, Point = 95},
            new Score(){GameId = 3, LevelId = 3, UserId = 2, Point = 65},

            new Score(){GameId = 3, LevelId = 1, UserId = 3, Point = 100},
            new Score(){GameId = 3, LevelId = 2, UserId = 3, Point = 90},
            new Score(){GameId = 3, LevelId = 3, UserId = 3, Point = 70},

            new Score(){GameId = 3, LevelId = 1, UserId = 4, Point = 150},
            new Score(){GameId = 3, LevelId = 2, UserId = 4, Point = 120},
            new Score(){GameId = 3, LevelId = 3, UserId = 4, Point = 90},
        };
    }
}