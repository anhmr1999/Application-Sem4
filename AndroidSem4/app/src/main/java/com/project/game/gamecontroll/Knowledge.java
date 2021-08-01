package com.project.game.gamecontroll;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.project.game.KnowledgeActivity;
import com.project.game.R;
import com.project.game.common.ApiProviderImpl;
import com.project.game.component.AnswerAdapter;
import com.project.game.common.Contants;
import com.project.game.datamanager.repository.AchievementRepository;
import com.project.game.datamanager.repository.QuestionRepository;
import com.project.game.datamanager.repository.ScoreRepository;
import com.project.game.datamanager.repository.UserAchievementRepository;
import com.project.game.entity.Achievement;
import com.project.game.entity.Answer;
import com.project.game.entity.Question;
import com.project.game.entity.Score;
import com.project.game.entity.UserAchievement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Knowledge {
    private QuestionRepository repository;
    private static Knowledge knowledge;
    private List<Question> questionList;
    private List<Answer> answers;
    private int score = 0,timer;;
    private Random random;
    private CountDownTimer countDown;
    private ApiProviderImpl apiProvider;
    private ScoreRepository scoreRepository;
    private AdapterView.OnItemClickListener itemClickListener;
    private AchievementRepository achievementRepository;
    private UserAchievementRepository userAchievementRepository;
    private Context context;

    static {knowledge = new Knowledge();}

    public static Knowledge getDataGame() {
        return knowledge;
    }

    public void init(Context context){
        this.context = context;
        scoreRepository = new ScoreRepository(context);
        achievementRepository = new AchievementRepository(context);
        userAchievementRepository = new UserAchievementRepository(context);
        random = new Random();
        score = 0;
        apiProvider = new ApiProviderImpl(context);
        repository = new QuestionRepository(context);
        if(Contants.knowLevel.getName().toLowerCase().equals("easy")){
            timer = 21000;
        } else if(Contants.knowLevel.getName().toLowerCase().equals("normal")) {
            timer = 16000;
        } else {
            timer = 11000;
        }
        itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                KnowledgeActivity.lst_answer.getChildAt(position).findViewById(R.id.btn_answer).setBackgroundResource(R.drawable.bg_answer_choose);
                stopCountDown();
                KnowledgeActivity.lst_answer.setOnItemClickListener(null);

                new CountDownTimer(2000,100){

                    @Override
                    public void onTick(long millisUntilFinished) {}

                    @Override
                    public void onFinish() {
                        if(((Answer)KnowledgeActivity.lst_answer.getItemAtPosition(position)).isCorrect()){
                            KnowledgeActivity.lst_answer.getChildAt(position).findViewById(R.id.btn_answer).setBackgroundResource(R.drawable.bg_answer_true);
                            new CountDownTimer(1000, 100) {
                                @Override
                                public void onTick(long l) {}

                                @Override
                                public void onFinish() {
                                    CorrectAnswer();
                                    changeQuestion();
                                }
                            }.start();
                        } else {
                            EndGame();
                            KnowledgeActivity.gameOver = true;
                            KnowledgeActivity.isPlayGame = false;
                        }
                    }
                }.start();
            }
        };
        getQuestion();
    }

    public void changeQuestion(){
        Question question;
        if(questionList.size() > 1){
            question = questionList.get(random.nextInt(questionList.size() - 1));
            questionList.remove(question);
        } else {
            question = questionList.get(0);
            getQuestion();
        }
        KnowledgeActivity.txt_Question.setText(question.getContent());
        answers = question.getAnswers();
        KnowledgeActivity.lst_answer.setAdapter(new AnswerAdapter(question.getAnswers()));
        KnowledgeActivity.lst_answer.setOnItemClickListener(itemClickListener);
        setTimer();
    }

    public void setTimer(){
        if(countDown != null){
            countDown.cancel();
        }
        countDown = new CountDownTimer(timer, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                if((millisUntilFinished/1000) >= 10){
                    KnowledgeActivity.txtTimer.setText("00:"+millisUntilFinished/1000);
                } else {
                    KnowledgeActivity.txtTimer.setText("00:0"+millisUntilFinished/1000);
                }
            }

            @Override
            public void onFinish() {
                EndGame();
            }
        };
        countDown.start();
    }

    public void stopCountDown(){
        countDown.cancel();
    }

    public void CorrectAnswer(){
        score += 5;
        KnowledgeActivity.txt_Score.setText(""+score);
    }

    public void EndGame() {
        ListView lst_answer =KnowledgeActivity.lst_answer;
        KnowledgeActivity.txt_EndCore.setText(""+score);
        for (int i = 0; i< lst_answer.getChildCount(); i++){
            if(((Answer)lst_answer.getItemAtPosition(i)).isCorrect()){
                lst_answer.getChildAt(i).findViewById(R.id.btn_answer).setBackgroundResource(R.drawable.bg_answer_true);
                countDown.cancel();
            }
        }
        new CountDownTimer(1000, 100) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                KnowledgeActivity.GameKnowLedgeOver.setVisibility(View.VISIBLE);
                Score currentScore = scoreRepository.getScoreForUpdate(3, Contants.User.getId(), Contants.knowLevel.getId());
                if(currentScore == null){
                    currentScore = new Score(Contants.knowLevel.getId(), 3, Contants.User.getId(), score, false);
                    scoreRepository.add(currentScore);
                    if(Contants.User.getAccessToken() != null && Contants.IsNetworkConnected(context) && Contants.User.getAccessToken() != ""){
                        apiProvider.UpdateScore();
                    }
                } else {
                    if(currentScore.getScore() < score){
                        currentScore.setScore(score);
                        currentScore.setUpload(false);
                        scoreRepository.update(currentScore);
                        if(Contants.User.getAccessToken() != null && Contants.IsNetworkConnected(context) && Contants.User.getAccessToken() != ""){
                            apiProvider.UpdateScore();
                        }
                    }
                }

                List<Achievement> achievements = new ArrayList<>();
                for (Achievement achievement: achievementRepository.getAchievement(2)) {
                    if(achievement.getScoreOrNumber() <= Game2048.getDataGame().getScore() && achievement.getLevelName().equals(Contants._2048Level.getName())){
                        achievements.add(achievement);
                        userAchievementRepository.add(new UserAchievement(Contants.User.getId(), achievement.getId(), false));
                    }
                }
                if(achievements.size() > 0){
                    KnowledgeActivity.dialog.setAchievement(achievements);
                    KnowledgeActivity.dialog.show();
                    if(Contants.User.getAccessToken() != null && Contants.IsNetworkConnected(context) && Contants.User.getAccessToken() != ""){
                        apiProvider.UpdateAchievement();
                    }
                }
            }
        }.start();
    }

    public void use5050(){
        int hide = 2;
        while (hide > 0){
            int position = random.nextInt(answers.size());
            if(!answers.get(position).isCorrect()
                    && KnowledgeActivity.lst_answer.getChildAt(position).getVisibility() != View.INVISIBLE){
                KnowledgeActivity.lst_answer.getChildAt(position).setVisibility(View.INVISIBLE);
                hide--;
            }
        }
    }

    public void useChangeQuestion(){
        changeQuestion();
    }

    public void useSkipQuestion(){
        ListView lst_answer =KnowledgeActivity.lst_answer;
        for (int i = 0; i< lst_answer.getChildCount(); i++){
            if(((Answer)lst_answer.getItemAtPosition(i)).isCorrect()){
                lst_answer.getChildAt(i).findViewById(R.id.btn_answer).setBackgroundResource(R.drawable.bg_answer_true);
                countDown.cancel();
            }
        }
        new CountDownTimer(1000, 100) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                CorrectAnswer();
                changeQuestion();
            }
        }.start();
    }

    private void getQuestion(){
        questionList = repository.get(0, 20);
    }
}
