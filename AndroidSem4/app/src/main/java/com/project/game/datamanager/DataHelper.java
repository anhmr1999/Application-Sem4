package com.project.game.datamanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.project.game.entity.Achievement;
import com.project.game.entity.Answer;
import com.project.game.entity.Game;
import com.project.game.entity.LevelHard;
import com.project.game.entity.Question;
import com.project.game.entity.Score;
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
        db.execSQL(InitTable.Achievement);
        for (int i = 0; i < InitData.Achievement().size(); i++){
            ContentValues content = new ContentValues();
            content.put("id",InitData.Achievement().get(i).getId());
            content.put("name",InitData.Achievement().get(i).getName());
            content.put("tutorial",InitData.Achievement().get(i).getTutorial());
            content.put("checkScore",InitData.Achievement().get(i).isCheckScore());
            content.put("scoreOrNumber",InitData.Achievement().get(i).getScoreOrNumber());
            content.put("gameId",InitData.Achievement().get(i).getGameId());
            content.put("level",InitData.Achievement().get(i).getLevelName());
            db.insert("achievement", null, content);
        }

        db.execSQL(InitTable.Game);
        for (int i = 0; i < InitData.Game().size(); i++){
            ContentValues content = new ContentValues();
            content.put("id",InitData.Game().get(i).getId());
            content.put("name",InitData.Game().get(i).getName());
            db.insert("game", null, content);
        }

        db.execSQL(InitTable.LevelHard);
        for (int i = 0; i < InitData.LevelHard().size(); i++){
            ContentValues content = new ContentValues();
            content.put("id",InitData.LevelHard().get(i).getId());
            content.put("name",InitData.LevelHard().get(i).getName());
            content.put("description",InitData.LevelHard().get(i).getDescription());
            db.insert("levelHard", null, content);
        }

        db.execSQL(InitTable.User);
        for (int i = 0; i < InitData.User().size(); i++){
            ContentValues content = new ContentValues();
            content.put("id",InitData.User().get(i).getId());
            content.put("name",InitData.User().get(i).getName());
            content.put("accessToken",InitData.User().get(i).getAccessToken());
            db.insert("user", null, content);
        }

        db.execSQL(InitTable.Question);
        for (int i = 0; i < InitData.Question().size(); i++){
            ContentValues content = new ContentValues();
            content.put("id",InitData.Question().get(i).getId());
            content.put("content",InitData.Question().get(i).getContent());
            content.put("subject",InitData.Question().get(i).getSubject());
            content.put("userId",InitData.Question().get(i).getUserId());
            db.insert("question", null, content);
        }

        db.execSQL(InitTable.Answer);
        for (int i = 0; i < InitData.Answer().size(); i++){
            ContentValues content = new ContentValues();
            content.put("id",InitData.Answer().get(i).getId());
            content.put("content",InitData.Answer().get(i).getContent());
            content.put("correct",InitData.Answer().get(i).isCorrect());
            content.put("questionId",InitData.Answer().get(i).getQuestionId());
            db.insert("answer", null, content);
        }

        db.execSQL(InitTable.UserAchievement);
        db.execSQL(InitTable.Score);
        for (int i = 0; i < InitData.Score().size(); i++){
            ContentValues content = new ContentValues();
            content.put("gameId",InitData.Score().get(i).getGameId());
            content.put("userId",InitData.Score().get(i).getUserId());
            content.put("levelId",InitData.Score().get(i).getLevelHardId());
            content.put("score",InitData.Score().get(i).getScore());
            content.put("isUpload",InitData.Score().get(i).isUpload() ? 1 : 0);
            db.insert("score", null, content);
        }
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
            "\tuserId INTEGER," +
            " FOREIGN KEY(userId) REFERENCES user(id)\n"+
            ");";

    public static String Answer = "CREATE TABLE answer (\n" +
            "\tid INTEGER NOT NULL PRIMARY KEY,\n" +
            "\tcontent nvarchar(250) NOT NULL,\n" +
            "\tcorrect boolean default 0,\n" +
            "\tquestionId INTEGER, " +
            "FOREIGN KEY(questionId) REFERENCES user(id)\n"+
            ");";

    public static String LevelHard = "CREATE TABLE levelHard (\n" +
            "\tid INTEGER NOT NULL PRIMARY KEY,\n" +
            "\tname nvarchar(250) NOT NULL,\n" +
            "\tdescription nvarchar(500)\n" +
            ");";

    public static String Game = "CREATE TABLE game (\n" +
            "\tid INTEGER NOT NULL PRIMARY KEY,\n" +
            "\tname nvarchar(250) NOT NULL\n" +
            ");";

    public static String User = "CREATE TABLE user (\n" +
            "\tid INTEGER NOT NULL PRIMARY KEY,\n" +
            "\tname nvarchar(250) NOT NULL,\n" +
            "\taccessToken nvarchar(250),\n" +
            "\tavatar INTEGER\n" +
            ");";

    public static String Score = "CREATE TABLE score (\n" +
            "\tlevelId INTEGER NOT NULL,\n" +
            "\tuserId INTEGER NOT NULL,\n" +
            "\tgameId INTEGER NOT NULL,\n" +
            "\tscore INTEGER NOT NULL,\n" +
            "\tisUpload bit NOT NULL default 0,\n" +
            "\tPRIMARY KEY(levelId,userId,gameId),\n" +
            "\tFOREIGN KEY(levelId) REFERENCES levelHard(id),\n" +
            "\tFOREIGN KEY(userId) REFERENCES user(id),\n" +
            "\tFOREIGN KEY(gameId) REFERENCES game(id)\n" +
            ");";

    public static String Achievement = "CREATE TABLE achievement (\n" +
            "\tid INTEGER NOT NULL PRIMARY KEY,\n" +
            "\tname nvarchar(250) NOT NULL,\n" +
            "\ttutorial nvarchar(500),\n" +
            "\tcheckScore bit,\n" +
            "\tscoreOrNumber int,\n" +
            "\tgameId int default 1,\n" +
            "\tlevel nvarchar(250) default 'easy'\n" +
            ");";

    public static String UserAchievement = "CREATE TABLE userachievement (\n" +
            "\tuserId INTEGER NOT NULL,\n" +
            "\tachievementId INTEGER, \n" +
            "\tisUpload bit NOT NULL default 0,\n" +
            "FOREIGN KEY(achievementId) REFERENCES achievement(id),\n" +
            "FOREIGN KEY(userId) REFERENCES user(id)" +
            ");";
}

class InitData{
    public static List<Game> Game(){
        List<Game> Games = new ArrayList<>();
        Games.add(new Game(1, "Game FlappyBird"));
        Games.add(new Game(2, "Game 2048"));
        Games.add(new Game(3, "Game KnowLedge"));
        return Games;
    }

    public static List<LevelHard> LevelHard(){
        List<LevelHard> LevelHards = new ArrayList<>();
        LevelHards.add(new LevelHard(1, "easy",""));
        LevelHards.add(new LevelHard(2, "normal",""));
        LevelHards.add(new LevelHard(3, "difficult",""));
        return LevelHards;
    }

    public static List<User> User(){
        List<User> Users = new ArrayList<>();
        Users.add(new User(1, "Tom Hiddleston","",1));
        Users.add(new User(2, "Alethea","",2));
        Users.add(new User(3, "Eudora","",3));
        Users.add(new User(4, "Griselda","",4));
        return Users;
    }

    public static List<Achievement> Achievement(){
        List<Achievement> Achievements = new ArrayList<>();
        Achievements.add(new Achievement(1, "new Player","", true, 0,"", 0));
        Achievements.add(new Achievement(2, "Obstacle specialist!","", true, 10,"easy",1));
        Achievements.add(new Achievement(3, "Perfect number!","", false, 2048,"easy",2));
        Achievements.add(new Achievement(4, "Smart player!","", true, 10,"easy",3));
        return Achievements;
    }

    public static List<Question> Question(){
        List<Question> Questions = new ArrayList<>();
        Questions.add(new Question(1, "V?? sao s??ng bi???n l???i c?? m??u tr???ng?","",0));
        Questions.add(new Question(2, "V?? sao b???u tr???i ban ????m c?? m??u ??en?","",0));
        Questions.add(new Question(3, "T???i sao ng??? l???i m???","",0));
        Questions.add(new Question(4, "T???i sao ?????ng tr??n cao nh??n xu???ng l???i th???y ch??ng m???t?","",0));
        Questions.add(new Question(5, "V?? sao b???u tr???i l???i c?? m??u xanh?","",0));
        Questions.add(new Question(6, "V?? sao l???i c?? s????ng m???","",0));
        Questions.add(new Question(7, "T???i sao tai bi???t ti???ng ?????ng t??? ????u d???i t???i?","",0));
        Questions.add(new Question(8, "T???i sao khi ch??ng m???t l???i c?? c???m gi??c m???i th??? xoay tr??n?","",0));
        Questions.add(new Question(9, "C???u v???ng l?? g???","",0));
        Questions.add(new Question(10, "V?? sao l?? c??y c?? m??u xanh l???c m?? khi v??? gi?? l???i c?? m??u v??ng ho???c ??????","",0));
        return Questions;
    }

    public static List<Answer> Answer(){
        List<Answer> Answers = new ArrayList<>();
        Answers.add(new Answer(1, "V?? s??ng bi???n l?? d???ng c??c h???t th???y tinh v??? v???n",true,1));
        Answers.add(new Answer(2, "V?? n?????c bi???n c?? nhi???u mu???i tr???ng",false,1));
        Answers.add(new Answer(3, "V?? bi???n ???? gi?? m?? s??ng l?? t??c n??n m??u tr???ng",false,1));
        Answers.add(new Answer(4, "V?? n?????c bi???n ?????p v??o ???? v?? c??t n??n t???o ra m??u tr???ng c???a s??ng",false,1));

        Answers.add(new Answer(5, "V?? ban ????m kh??ng c?? m???t tr???i",false,2));
        Answers.add(new Answer(6, "V?? m??y ban ????m m??y c?? m??u ??en",false,2));
        Answers.add(new Answer(7, "V?? s??? sao tr??n tr???i ????m kh??ng ????? th???p s??ng b???u tr???i",true,2));
        Answers.add(new Answer(8, "Tr???i t???i th?? t???t nhi??n ph???i ??en r???i",false,2));

        Answers.add(new Answer(9, "Gi???c m?? truy???n t???i c??c suy ngh?? s??u th???m trong t??m tr??",false,3));
        Answers.add(new Answer(10, "V?? c?? nh???ng c?? quan trong c?? th??? v???n ho???t ?????ng khi ng???",false,3));
        Answers.add(new Answer(11, "Ng??y ngh?? g?? ????m m?? ?????y",false,3));
        Answers.add(new Answer(12, "T???t c??? ?????u ????ng",true,3));

        Answers.add(new Answer(13, "V?? ????? cao l??m b???n s???, l??m b???n cho??ng",false,4));
        Answers.add(new Answer(14, "V?? khi nh??n th???ng xu???ng, ??p l???c kh??ng kh?? v?? gi?? l??m ???nh h?????ng ?????n c???u tr??c c??n b???ng trong tai",true,4));
        Answers.add(new Answer(15, "V?? khi nh??n t??? cao xu???ng, m???i v???t ?????u b?? h??n l??m b???n cho??ng v??ng",false,4));
        Answers.add(new Answer(16, "T???i khi nh??n t??? cao xu???ng t???o c???m gi??c k??ch th??ch m???nh l??m b???n m???t c??n b???ng",false,4));

        Answers.add(new Answer(17, "V?? da tr???i v???n d?? c?? m??u xanh",false,5));
        Answers.add(new Answer(18, "??nh s??ng xanh c?? b?????c s??ng ng???n, c??c ph??n t??? kh??ng kh?? ph??n t??n ??i xung quanh v?? nhu???m xanh c??? b???u tr???i",true,5));
        Answers.add(new Answer(19, "V?? ??nh s??ng m???t tr???i c?? m??u xanh, khi???n b???u tr???i c?? m??u xanh",false,5));
        Answers.add(new Answer(20, "T???ng ozon ch???n c??c ??nh s??ng kh??c v?? ch??? ????? cho ??nh s??ng m??u xanh ??i qua l??m da tr???i c?? m??u xanh",false,5));

        Answers.add(new Answer(21, "C??y c???i nh??? ra kh?? tr???ng l?? s????ng m??",false,6));
        Answers.add(new Answer(22, "Nh???ng ????m b??ng g??n m???ng v?? ???m ?????c bay ra ngo??i t???o ra s????ng m??",false,6));
        Answers.add(new Answer(23, "S????ng m?? l?? h??i n?????c trong kh?? quy???n ng??ng k???t l???i th??nh nh???ng h???t n?????c r???t nh??? l?? l???ng trong kh??ng kh?? v?? l??m gi???m t???m nh??n c???a ch??ng ta",true,6));
        Answers.add(new Answer(24, "S????ng m?? l?? m??y xu???ng ?????t d???o ch??i",false,6));

        Answers.add(new Answer(25, "Do s??? ch??nh l???ch gi???a th???i gian nghe th???y c???a 2 b??n tai v?? ??p l???c kh??ng kh?? v??o 2 m??ng nh?? c???a 2 tai",true,7));
        Answers.add(new Answer(26, "Do ??m thanh truy???n t???i t??? b??n tai n??o th?? ch??? tai ???? nghe th???y",false,7));
        Answers.add(new Answer(27, "??p l???c kh??ng kh?? c???a 2 tai c???m nh???n kh??c nhau n??n nh???n bi???t ???????c",false,7));
        Answers.add(new Answer(28, "Do ng?????i ??m thanh c??ng g??y ??p l???c kh??ng kh?? v??o da l??m b???n c???m nh???n ???????c ??m thanh t??? ????u ?????n",false,7));

        Answers.add(new Answer(29, "Do n??o ??ang b??? cho??ng n??n g??y c???m gi??c quay tr??n",false,8));
        Answers.add(new Answer(30, "L??c b??? ch??ng m???t, m???t li??n t???c di chuy???n t???o ra c???m gi??c quay tr??n",false,8));
        Answers.add(new Answer(31, "Do h??? th???n kinh trung ????ng kh??ng ?????ng b??? ???????c t??n hi???u t??? tai, m???t, c?? th???, c?? v?? x????ng",true,8));
        Answers.add(new Answer(32, "Ch??ng m???t l??m m???t c???m gi??c gi???a kh??ng gian n??n g??y ra c???m gi??c quay tr??n",false,8));

        Answers.add(new Answer(33, "L?? chi???c c???u n???i t???i ch??n tr???i",false,9));
        Answers.add(new Answer(34, "L?? ??nh s??ng m???t tr???i b??? ph???n x??? khi chi???u v??o c??c gi???t n?????c l?? l???ng trong kh??ng kh??",true,9));
        Answers.add(new Answer(35, "L?? con ???????ng do c??c ng?????i l??n t???o ra ????? ??i t???i nh?? c???a nhau",false,9));
        Answers.add(new Answer(36, "L?? chi???c c???u nhi???u m??u s???c ???????c m??y m??a mang ?????n",false,9));

        Answers.add(new Answer(37, "Do l?? c??y c?? di???p l???c, di???p l???c khi gi?? s??? bi???n th??nh m??u ?????, v??ng",false,10));
        Answers.add(new Answer(38, "Do l??c gi??, l?? c??y ch??? c?? th??? ph???n x??? m??u v??ng v?? ?????",false,10));
        Answers.add(new Answer(39, "L?? c??y gi???ng nh?? t??c, khi gi?? s??? chuy???n sang m??u kh??c m?? ??? ????y l?? v??ng v?? ?????",false,10));
        Answers.add(new Answer(40, "Do l?? c??y v???n d?? c?? nhi???u s???c t??? m??u s???c kh??c nhau, khi l?? gi?? ??i, di???p l???c trong l?? kh??ng c??n ????? nhi???u ????? l???n ??p ??i c??c m??u s???c kh??c n??n ta th???y l?? c??y c?? m??u v??ng v?? ?????",true,10));

        return Answers;
    }

    public static List<Score> Score(){
        List<Score> Scores = new ArrayList<>();
        Scores.add(new Score(1, 1, 1,50, true));
        Scores.add(new Score(1, 1, 2,40, true));
        Scores.add(new Score(1, 1, 3,70, true));

        Scores.add(new Score(2, 2, 1,16000, true));
        Scores.add(new Score(2, 2, 2,20000, true));
        Scores.add(new Score(2, 2, 3,16000, true));

        Scores.add(new Score(3, 3, 1,100, true));
        Scores.add(new Score(3, 3, 2,90, true));
        Scores.add(new Score(3, 3, 3,70, true));
        return Scores;
    }
}
