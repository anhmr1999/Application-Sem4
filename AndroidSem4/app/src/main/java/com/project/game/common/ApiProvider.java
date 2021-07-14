package com.project.game.common;

import com.project.game.entity.Achievement;
import com.project.game.entity.Question;
import com.project.game.entity.Score;
import com.project.game.entity.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiProvider {
    @GET("GetScore")
    Call<List<Score>> GetScore();

    @GET("GetAchievement")
    Call<List<Achievement>> GetAchievement(@Query("lastId") int lastId);

    @GET("GetQuestion")
    Call<List<Question>> GetQuestion(@Query("lastId") int lastId);

    @POST("LoginUser")
    Call<User> LoginUser(@Query("Token") String Token, @Query("name") String name, @Query("avatar") int avatar);

    @POST("UpdateScore")
    Call<Boolean> UpdateScore(@Body List<Score> scores);

    @POST("AddQuestion")
    Call<Boolean> AddQuestion(@Body Question question);

    @POST("AddAchievement")
    Call<Boolean> AddAchievement(@Query("userId") int userId, @Body List<Integer> achievements);
}
