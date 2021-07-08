package com.project.game.datamanager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.project.game.entity.Achievement;
import com.project.game.entity.Question;
import com.project.game.entity.Score;
import com.project.game.entity.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiProvider {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create();

    ApiProvider _apiProvider = new Retrofit.Builder()
            .baseUrl("http://192.168.1.8:10101/Api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(ApiProvider.class);

    @GET("GetScore")
    Call<List<Score>> GetScore();

    @GET("GetAchievement")
    Call<List<Achievement>> GetAchievement(@Query("lastId") int lastId);

    @GET("GetQuestion")
    Call<List<Question>> GetQuestion(@Query("lastId") int lastId);

    @POST("LoginUser")
    Call<User> LoginUser(@Query("Token") String Token, @Query("name") String name);

    @POST("UpdateScore")
    Call<Boolean> UpdateScore(@Body List<Score> scores);

    @POST("UpdateScore")
    Call<Boolean> AddQuestion(@Body Question question);

    @POST("UpdateScore")
    Call<Boolean> AddAchievement(@Query("userId") int userId, @Body List<Integer> achievements);
}
