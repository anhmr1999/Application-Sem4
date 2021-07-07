package com.project.game.datamanager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.project.game.entity.Score;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface ApiProvider {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create();

    ApiProvider _apiProvider = new Retrofit.Builder()
            .baseUrl("https://192.168.1.8:44397/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(ApiProvider.class);

    @GET("Score/GetListHighScore")
    Call<List<Score>> GetListHighScore();
}
