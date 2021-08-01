package com.project.game.common;

import com.project.game.entity.Achievement;
import com.project.game.entity.Question;
import com.project.game.entity.Score;
import com.project.game.entity.User;
import com.project.game.entity.UserAchievement;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiProvider {
    @GET("GetScore")
    Call<List<Score>> GetScore();

    @GET("GetScoreForUser")
    Call<List<Score>> GetScoreForUser(@Query("id") int id);

    @GET("GetScore")
    Call<User> GetUser(@Query("id") int id);

    @GET("GetAchievement")
    Call<List<Achievement>> GetAchievement(@Query("lastId") int lastId);

    @GET("GetAchievement")
    Call<List<UserAchievement>> GetAchievementForUser(@Query("id") int id);

    @GET("GetQuestion")
    Call<List<Question>> GetQuestion(@Query("lastId") int lastId);

    @POST("LoginUser")
    Call<User> LoginUser(@Query("Token") String Token, @Query("name") String name, @Query("avatar") int avatar);

    @POST("UpdateScores")
    Call<Boolean> UpdateScores(@Body List<Score> scores);

    @POST("UpdateScore")
    Call<Boolean> UpdateScore(@Body Object score);

    @POST("AddQuestion")
    Call<Boolean> AddQuestion(@Body Object question);

    @POST("AddAchievements")
    Call<Boolean> AddAchievements(@Query("userId") int userId, @Body List<Integer> achievements);

    @POST("AddAchievement")
    Call<Boolean> AddAchievement(@Query("userId") int userId, @Query("userId") int achievementId);
}
