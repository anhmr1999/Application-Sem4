package com.project.game.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.project.game.MainActivity;
import com.project.game.datamanager.repository.AchievementRepository;
import com.project.game.datamanager.repository.AnswerRepository;
import com.project.game.datamanager.repository.QuestionRepository;
import com.project.game.datamanager.repository.ScoreRepository;
import com.project.game.datamanager.repository.UserAchievementRepository;
import com.project.game.datamanager.repository.UserRepository;
import com.project.game.entity.Achievement;
import com.project.game.entity.Answer;
import com.project.game.entity.Question;
import com.project.game.entity.Score;
import com.project.game.entity.User;
import com.project.game.entity.UserAchievement;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiProviderImpl {
    private Gson gson;
    private ApiProvider _apiProvider;
    private ScoreRepository _scoreRepository;
    private UserRepository _userRepository;
    private AchievementRepository _achievementRepository;
    private UserAchievementRepository _userAchievementRepository;
    private QuestionRepository _questionRepository;
    private AnswerRepository _answerRepository;
    private SharedPreferences sp;

    public ApiProviderImpl(Context context) {
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create();
        //Laptop
        /*_apiProvider = new Retrofit.Builder()
                .baseUrl("http://192.168.1.8:10101/Api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(ApiProvider.class);*/

        //PC công ty
        _apiProvider = new Retrofit.Builder()
                .baseUrl("http://192.168.1.47:10001/Api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(ApiProvider.class);

        sp = context.getSharedPreferences("CommonSetting", Context.MODE_PRIVATE);
        _scoreRepository = new ScoreRepository(context);
        _userRepository = new UserRepository(context);
        _achievementRepository = new AchievementRepository(context);
        _userAchievementRepository = new UserAchievementRepository(context);
        _questionRepository = new QuestionRepository(context);
        _answerRepository = new AnswerRepository(context);
    }

    public CallApiResult<Score> LoadScore(){
        CallApiResult result = new CallApiResult();
        _apiProvider.GetScore().enqueue(new Callback<List<Score>>() {
            @Override
            public void onResponse(Call<List<Score>> call, Response<List<Score>> response) {
                if(response.isSuccessful()){
                    List<Score> scores = _scoreRepository.GetScore();
                    for (Score score: response.body()) {
                        if(!checkHaveScore(scores, score)){
                            if(_userRepository.getUser(score.getUserId()) == null){
                                GetUser(score.getUserId());
                            }
                            _scoreRepository.add(score);
                        } else {
                            _scoreRepository.update(score);
                        }
                    }
                    result.setStatus(true);
                } else {
                    result.setStatusCode(response.code());
                    result.setStatus(false);
                    result.setMessage("Đã xảy ra lỗi khi xử lý yêu cầu này!");
                }
            }

            @Override
            public void onFailure(Call<List<Score>> call, Throwable t) {
                result.setStatus(false);
                result.setMessage("Lỗi kết nối Internet, vui lòng kiểm tra lại đường truyền!");
            }
        });
        return result;
    }

    private CallApiResult<Score> GetScore(int id){
        CallApiResult result = new CallApiResult();
        _apiProvider.GetScoreForUser(id).enqueue(new Callback<List<Score>>() {
            @Override
            public void onResponse(Call<List<Score>> call, Response<List<Score>> response) {
                if(response.isSuccessful()){
                    for (Score score: response.body()) {
                        _scoreRepository.add(score);
                    }
                    result.setStatus(true);
                } else {
                    result.setStatusCode(response.code());
                    result.setStatus(false);
                    result.setMessage("Đã xảy ra lỗi khi xử lý yêu cầu này!");
                }
            }

            @Override
            public void onFailure(Call<List<Score>> call, Throwable t) {
                result.setStatus(false);
                result.setMessage("Lỗi kết nối Internet, vui lòng kiểm tra lại đường truyền!");
            }
        });
        return result;
    }

    private CallApiResult<Score> GetAchievementForUser(int id){
        CallApiResult result = new CallApiResult();
        _apiProvider.GetAchievementForUser(id).enqueue(new Callback<List<UserAchievement>>() {
            @Override
            public void onResponse(Call<List<UserAchievement>> call, Response<List<UserAchievement>> response) {
                if(response.isSuccessful()){
                    for (UserAchievement userAchievement: response.body()) {
                        _userAchievementRepository.add(userAchievement);
                    }
                    result.setStatus(true);
                } else {
                    result.setStatusCode(response.code());
                    result.setStatus(false);
                    result.setMessage("Đã xảy ra lỗi khi xử lý yêu cầu này!");
                }
            }

            @Override
            public void onFailure(Call<List<UserAchievement>> call, Throwable t) {
                result.setStatus(false);
                result.setMessage("Lỗi kết nối Internet, vui lòng kiểm tra lại đường truyền!");
            }
        });
        return result;
    }

    public CallApiResult<User> Login(String Token, String name, int avatar){
        CallApiResult result = new CallApiResult();
        _apiProvider.LoginUser(Token,name,avatar).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    User user = response.body();
                    if(_userRepository.add(user)){
                        GetScore(user.getId());
                        GetAchievementForUser(user.getId());
                        Contants.User = user;

                        SharedPreferences.Editor editor = sp.edit();
                        editor.putInt("UserId",user.getId());
                        editor.apply();

                        MainActivity.userName.setText(Contants.User.getName());
                        MainActivity.userAvatar.setImageResource(Contants.getAvatarResource());

                        Log.e("API","Success");
                    }
                    result.setStatus(true);
                } else {
                    result.setStatusCode(response.code());
                    result.setStatus(false);
                    result.setMessage("Đã xảy ra lỗi khi xử lý yêu cầu này!");

                    Log.e("API","Failt");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                result.setStatus(false);
                result.setMessage("Lỗi kết nối Internet, vui lòng kiểm tra lại đường truyền!");
                Log.e("API","Die");
            }
        });
        return result;
    }

    private CallApiResult<User> GetUser(int id){
        CallApiResult result = new CallApiResult();
        _apiProvider.GetUser(id).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    _userRepository.add(response.body());
                } else {
                    result.setStatusCode(response.code());
                    result.setStatus(false);
                    result.setMessage("Đã xảy ra lỗi khi xử lý yêu cầu này!");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                result.setStatus(false);
                result.setMessage("Lỗi kết nối Internet, vui lòng kiểm tra lại đường truyền!");
            }
        });
        return result;
    }

    public CallApiResult<Boolean> LoadAchievement(){
        CallApiResult<Boolean> result = new CallApiResult<>();
        _apiProvider.GetAchievement(_achievementRepository.getLastId()).enqueue(new Callback<List<Achievement>>() {
            @Override
            public void onResponse(Call<List<Achievement>> call, Response<List<Achievement>> response) {
                if(response.isSuccessful() && response.body().size() > 0){
                    for (Achievement achievement: response.body()) {
                        _achievementRepository.addAchievement(achievement);
                    }
                    result.setStatus(true);
                } else {
                    result.setStatusCode(response.code());
                    result.setStatus(false);
                    result.setMessage("Đã xảy ra lỗi khi xử lý yêu cầu này!");
                }
            }

            @Override
            public void onFailure(Call<List<Achievement>> call, Throwable t) {
                result.setStatus(false);
                result.setMessage("Lỗi kết nối Internet, vui lòng kiểm tra lại đường truyền!");
            }
        });
        return result;
    }

    public CallApiResult<Boolean> LoadQuestion(){
        CallApiResult<Boolean> result = new CallApiResult<>();
        _apiProvider.GetQuestion(_questionRepository.getLastId()).enqueue(new Callback<List<Question>>() {
            @Override
            public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                if(response.isSuccessful()/* && response.body().size() > 0*/){
                    for (Question question: response.body()) {
                        if(_questionRepository.add(question)){
                            for (Answer answer : question.getAnswers()) {
                                _answerRepository.add(answer);
                            }
                        }
                    }
                    result.setStatus(true);
                } else {
                    result.setStatusCode(response.code());
                    result.setStatus(false);
                    result.setMessage("Đã xảy ra lỗi khi xử lý yêu cầu này!");
                }
            }

            @Override
            public void onFailure(Call<List<Question>> call, Throwable t) {
                result.setStatus(false);
                result.setMessage("Lỗi kết nối Internet, vui lòng kiểm tra lại đường truyền!");
            }
        });
        return result;
    }

    public CallApiResult<Boolean> UpdateScores(){
        CallApiResult<Boolean> result = new CallApiResult<>();
        List<Score> scores = _scoreRepository.getListScoreUpload();
        _apiProvider.UpdateScores(scores).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful() && response.body()){
                    //_scoreRepository.UploadSuccess(Contants.User.getId(), scores);
                    result.setStatus(true);
                } else {
                    result.setStatusCode(response.code());
                    result.setStatus(false);
                    result.setMessage("Đã xảy ra lỗi khi xử lý yêu cầu này!");
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                result.setStatus(false);
                result.setMessage("Lỗi kết nối Internet, vui lòng kiểm tra lại đường truyền!");
            }
        });
        return result;
    }

    public CallApiResult<Boolean> UpdateScore(){
        CallApiResult<Boolean> result = new CallApiResult<>();
        List<Score> scores = _scoreRepository.getListScoreUpload();
        for (Score score : scores) {
            _apiProvider.UpdateScore(new ApiScore(score)).enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if(response.isSuccessful() && response.body()){
                        _scoreRepository.UploadSuccess(Contants.User.getId(), score.getGameId(), score.getLevelHardId());
                        result.setStatus(true);
                    } else {
                        result.setStatusCode(response.code());
                        result.setStatus(false);
                        result.setMessage("Đã xảy ra lỗi khi xử lý yêu cầu này!");
                        return;
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    result.setStatus(false);
                    result.setMessage("Lỗi kết nối Internet, vui lòng kiểm tra lại đường truyền!");
                    return;
                }
            });
        };
        return result;
    }

    public CallApiResult<Boolean> UpdateAchievement(){
        CallApiResult<Boolean> result = new CallApiResult<>();
        List<Integer> achievementIds = _userAchievementRepository.getListScoreForUpload();
        for (int achievementId: achievementIds) {
            _apiProvider.AddAchievement(Contants.User.getId(), achievementId).enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if(response.isSuccessful() && response.body()){
                        _userAchievementRepository.UploadSuccess();
                        result.setStatus(true);
                    } else {
                        result.setStatusCode(response.code());
                        result.setStatus(false);
                        result.setMessage("Đã xảy ra lỗi khi xử lý yêu cầu này!");
                        return;
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    result.setStatus(false);
                    result.setMessage("Lỗi kết nối Internet, vui lòng kiểm tra lại đường truyền!");
                    return;
                }
            });
        }
        return result;
    }

    public CallApiResult<Boolean> UpdateAchievements(){
        CallApiResult<Boolean> result = new CallApiResult<>();
        List<Integer> achievementIds = _userAchievementRepository.getListScoreForUpload();
        _apiProvider.AddAchievements(Contants.User.getId(), achievementIds).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful() && response.body()){
                    _userAchievementRepository.UploadSuccess();
                    result.setStatus(true);
                } else {
                    result.setStatusCode(response.code());
                    result.setStatus(false);
                    result.setMessage("Đã xảy ra lỗi khi xử lý yêu cầu này!");
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                result.setStatus(false);
                result.setMessage("Lỗi kết nối Internet, vui lòng kiểm tra lại đường truyền!");
            }
        });
        return result;
    }

    public CallApiResult<Boolean> AddQuestion(Question question){
        CallApiResult<Boolean> result = new CallApiResult<>();
        _apiProvider.AddQuestion(question).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful() && response.body()){
                    result.setStatus(true);
                } else {
                    result.setStatusCode(response.code());
                    result.setStatus(false);
                    result.setMessage("Đã xảy ra lỗi khi xử lý yêu cầu này!");
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                result.setStatus(false);
                result.setMessage("Lỗi kết nối Internet, vui lòng kiểm tra lại đường truyền!");
            }
        });
        return result;
    }

    private boolean checkHaveScore(List<Score> scores, Score score){
        for (Score s: scores) {
            if(s.getUserId() == score.getUserId() && s.getGameId() == score.getGameId() && s.getLevelHardId() == score.getLevelHardId()){
                return true;
            }
        }
        return false;
    }

}

class ApiScore{
    private Score score;

    public ApiScore(Score score) {
        this.score = score;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }
}

class ApiQuestion{
    private Question question;

    public ApiQuestion(Question question) {
        this.question = question;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}