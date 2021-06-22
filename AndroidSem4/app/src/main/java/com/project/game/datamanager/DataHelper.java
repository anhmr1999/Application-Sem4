package com.project.game.datamanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.project.game.entity.Achievement;
import com.project.game.entity.Answer;
import com.project.game.entity.Game;
import com.project.game.entity.LevelHard;
import com.project.game.entity.Question;
import com.project.game.entity.User;

import java.util.ArrayList;
import java.util.List;

public class DataHelper extends SQLiteOpenHelper {
    private static final String Database_Name = "StudentManager";
    private static final int db_version = 1;

    public DataHelper(@Nullable Context context) {
        super(context, Database_Name, null, db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL(student);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

class InitTable{
    public static String Question = "CREATE TABLE question (\n" +
            "\tid INTEGER NOT NULL PRIMARY KEY,\n" +
            "\tcontent nvarchar(250) NOT NULL,\n" +
            "\tsubject nvarchar(250),\n" +
            "\tuserId INTEGER NULL FOREIGN KEY REFERENCES user(id)\n"+
            ");";

    public static String Answer = "CREATE TABLE answer (\n" +
            "\tid INTEGER NOT NULL PRIMARY KEY,\n" +
            "\tcontent nvarchar(250) NOT NULL,\n" +
            "\tcorrect boolean default 0,\n" +
            "\tquestionId INTEGER FOREIGN KEY REFERENCES user(id)\n"+
            ");";

    public static String LevelHard = "CREATE TABLE levelHard (\n" +
            "\tid INTEGER NOT NULL PRIMARY KEY,\n" +
            "\tname nvarchar(250) NOT NULL,\n" +
            "\tdescription nvarchar(500),\n" +
            "\tgameId INTEGER FOREIGN KEY REFERENCES game(id)\n"+
            ");";

    public static String Game = "CREATE TABLE game (\n" +
            "\tid INTEGER NOT NULL PRIMARY KEY,\n" +
            "\tname nvarchar(250) NOT NULL,\n" +
            ");";

    public static String User = "CREATE TABLE user (\n" +
            "\tid INTEGER NOT NULL PRIMARY KEY,\n" +
            "\tname nvarchar(250) NOT NULL,\n" +
            "\taccessToken nvarchar(250),\n" +
            ");";

    public static String Score = "CREATE TABLE score (\n" +
            "\tlevelId INTEGER NOT NULL FOREIGN KEY REFERENCES levelHard(id),\n" +
            "\tuserId INTEGER NOT NULL FOREIGN KEY REFERENCES user(id),\n" +
            "\tgameId INTEGER FOREIGN KEY REFERENCES game(id),\n" +
            "\tscore INTEGER NOT NULL,\n" +
            "\tPRIMARY KEY(levelId,userId,gameId)\n" +
            ");";

    public static String Achievement = "CREATE TABLE achievement (\n" +
            "\tid INTEGER NOT NULL PRIMARY KEY,\n" +
            "\tname nvarchar(250) NOT NULL,\n" +
            "\ttutorial nvarchar(500),\n" +
            ");";

    public static String UserAchievement = "CREATE TABLE userachievement (\n" +
            "\tuserId INTEGER NOT NULL FOREIGN KEY REFERENCES user(id),\n" +
            "\tachievementId INTEGER FOREIGN KEY REFERENCES achievement(id),\n" +
            ");";
}

class InitData{
    public static List<Game> Game(){
        List<Game> Games = new ArrayList<>();
        Games.add(new Game(0, "Game FlappyBird"));
        Games.add(new Game(0, "Game 2048"));
        Games.add(new Game(0, "Game KnowLedge"));
        return Games;
    }

    public static List<LevelHard> LevelHard(){
        List<LevelHard> LevelHards = new ArrayList<>();
        LevelHards.add(new LevelHard(0, "easy","",1));
        LevelHards.add(new LevelHard(0, "normal","",1));
        LevelHards.add(new LevelHard(0, "difficult","",1));

        LevelHards.add(new LevelHard(0, "easy","",2));
        LevelHards.add(new LevelHard(0, "normal","",2));
        LevelHards.add(new LevelHard(0, "difficult","",2));

        LevelHards.add(new LevelHard(0, "easy","",3));
        LevelHards.add(new LevelHard(0, "normal","",3));
        LevelHards.add(new LevelHard(0, "difficult","",3));
        return LevelHards;
    }

    public static List<User> User(){
        List<User> Users = new ArrayList<>();
        Users.add(new User(0, "Tom Hiddleston",""));
        Users.add(new User(0, "Alethea",""));
        Users.add(new User(0, "Eudora",""));
        Users.add(new User(0, "Griselda",""));
        Users.add(new User(0, "Magnus",""));
        Users.add(new User(0, "Calantha",""));
        Users.add(new User(0, "Jocasta ",""));
        return Users;
    }

    public static List<Achievement> Achievement(){
        List<Achievement> Achievements = new ArrayList<>();
        Achievements.add(new Achievement(0, "Obstacle specialist!",""));
        Achievements.add(new Achievement(0, "Number matching expert!",""));
        Achievements.add(new Achievement(0, "Smart player!",""));
        return Achievements;
    }

    public static List<Question> Question(){
        List<Question> Questions = new ArrayList<>();
        Questions.add(new Question(0, "Vì sao sóng biển lại có màu trắng?","a",0));
        Questions.add(new Question(0, "Vì sao bầu trời ban đêm có màu đen?","a",0));
        Questions.add(new Question(0, "Tại sao ngủ lại mơ?","a",0));
        Questions.add(new Question(0, "Tại sao đứng trên cao nhìn xuống lại thấy chóng mặt?","a",0));
        Questions.add(new Question(0, "Vì sao bầu trời lại có màu xanh?","a",0));
        Questions.add(new Question(0, "Vì sao lại có sương mù?","a",0));
        Questions.add(new Question(0, "Tại sao tai biết tiếng động từ đâu dội tới?","a",0));
        Questions.add(new Question(0, "Mây được hình thành như thế nào?","a",0));
        Questions.add(new Question(0, "Tại sao khi chóng mặt lại có cảm giác mọi thứ xoay tròn?","a",0));
        Questions.add(new Question(0, "Vì sao lại có cầu vồng?","a",0));
        Questions.add(new Question(0, "Tại sao con người ngủ hay nghiến răng?","a",0));
        Questions.add(new Question(0, "Vì sao tai cụp xuống khi máy bay cất, hạ cánh?","a",0));
        Questions.add(new Question(0, "Vì sao lá cây có màu xanh lục?","a",0));
        return Questions;
    }

    public static List<Answer> Answer(){
        List<Answer> Answers = new ArrayList<>();
        Answers.add(new Answer(0, "Vì sóng biển là dạng các hạt thủy tinh vỡ vụn",true,1));
        Answers.add(new Answer(0, "Vì nước biển có nhiều muối trắng",false,1));
        Answers.add(new Answer(0, "Vì biển đã già mà sóng là tóc nên màu trắng",false,1));
        Answers.add(new Answer(0, "Vì nước biển đập vào đá và cát nên tạo ra màu trắng của sóng",false,1));

        Answers.add(new Answer(0, "Vì ban đêm không có mặt trời",false,2));
        Answers.add(new Answer(0, "Vì mây ban đêm mây có màu đen",false,2));
        Answers.add(new Answer(0, "Vì số sao trên trời đêm không đủ thắp sáng bầu trời",true,2));
        Answers.add(new Answer(0, "Trời tối thì tất nhiên phải đen rồi",false,2));

        Answers.add(new Answer(0, "Giấc mơ truyền tải các suy nghĩ sâu thẳm trong tâm trí",false,3));
        Answers.add(new Answer(0, "Vì có những cơ quan trong cơ thể vẫn hoạt động khi ngủ",false,3));
        Answers.add(new Answer(0, "Ngày nghĩ gì đêm mơ đấy",false,3));
        Answers.add(new Answer(0, "Tất cả đều đúng",true,3));

        Answers.add(new Answer(0, "Vì độ cao làm bạn sợ, làm bạn choáng",false,4));
        Answers.add(new Answer(0, "Vì khi nhìn thẳng xuống, áp lực không khí và gió làm ảnh hưởng đến cấu trúc cân bằng trong tai",false,4));
        Answers.add(new Answer(0, "Vì khi nhìn từ cao xuống, mọi vật đều bé hơn làm bạn choáng váng",false,4));
        Answers.add(new Answer(0, "Tại khi nhìn từ cao xuống tạo cảm giác kích thích mạnh làm bạn mất cân bằng",true,4));

        Answers.add(new Answer(0, "Vì da trời vốn dĩ có màu xanh",false,5));
        Answers.add(new Answer(0, "Ánh sáng đi qua tầng khí quyển bị tán xạ, ánh sáng xanh có bước sóng ngắn, các phân tử không khí phân tán đi xung quanh và nhuộm xanh cả bầu trời",true,5));
        Answers.add(new Answer(0, "Vì ánh sáng mặt trời có màu xanh, khiến bầu trời có màu xanh",false,5));
        Answers.add(new Answer(0, "Tầng ozon chặn các ánh sáng khác và chỉ để cho ánh sáng màu xanh đi qua làm da trời có màu xanh",false,5));

        Answers.add(new Answer(0, "Cây cối nhả ra khí trắng là sương mù",false,6));
        Answers.add(new Answer(0, "Những đám bông gòn mỏng và ẩm ước bay ra ngoài tạo ra sương mù",false,6));
        Answers.add(new Answer(0, "Sương mù là hiện tượng hơi nước trong khí quyển ngưng kết lại thành những hạt nước rất nhỏ lơ lửng trong không khí và làm giảm tầm nhìn của chúng ta",true,6));
        Answers.add(new Answer(0, "Sương mù là mây xuống đất dạo chơi",false,6));

        Answers.add(new Answer(0, "Do sự chênh lệch giữa thời gian nghe thấy của 2 bên tai và áp lực không khí vào 2 màng nhĩ của 2 tai",true,7));
        Answers.add(new Answer(0, "Do âm thanh truyền tới từ bên tai nào thì chỉ tai đó nghe thấy",false,7));
        Answers.add(new Answer(0, "Áp lực không khí của 2 tai cảm nhận khác nhau nên nhận biết được",false,7));
        Answers.add(new Answer(0, "Do người âm thanh cũng gây áp lực không khí vào da làm bạn cảm nhận được âm thanh từ đâu đến",false,7));

        Answers.add(new Answer(0, "Do não đang bị choáng nên gây cảm giác quay tròn",false,8));
        Answers.add(new Answer(0, "Lúc bị chóng mặt, mắt liên tục di chuyển tạo ra cảm giác quay tròn",false,8));
        Answers.add(new Answer(0, "Do hệ thần kinh trung ương không đồng bộ được tín hiệu từ tai, mắt, cơ thể, cơ và xương",true,8));
        Answers.add(new Answer(0, "Chóng mặt làm mất cảm giác giữa không gian nên gây ra cảm giác quay tròn",false,8));
        return Answers;
    }
}
