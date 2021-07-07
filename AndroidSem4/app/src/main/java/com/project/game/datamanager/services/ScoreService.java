package com.project.game.datamanager.services;

import android.util.Log;

import com.project.game.datamanager.ApiProvider;
import com.project.game.entity.Score;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScoreService {

    public static boolean UpdateScore(){

        ApiProvider._apiProvider.GetListHighScore().enqueue(new Callback<List<Score>>() {
            @Override
            public void onResponse(Call<List<Score>> call, Response<List<Score>> response) {
                Log.i("Service", response.body().size()+" Score Download");
            }

            @Override
            public void onFailure(Call<List<Score>> call, Throwable t) {
                Log.i("Service", " Score Update Error");
                Log.e("Service", t.getMessage());
            }
        });

        return true;
    }

}
