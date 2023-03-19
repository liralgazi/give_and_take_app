package com.example.giveandtake.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlaceModel {
    final public static PlaceModel instance = new PlaceModel();

    final String BASE_URL = "https://api.globalgiving.org/api/public/orgservice/all/organizations";
    Retrofit retrofit;
    PlaceAPI placeAPI;

    private PlaceModel(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        placeAPI = retrofit.create(PlaceAPI.class);
    }

    public LiveData<List<Place>> searchPlaceByName(String name){
        MutableLiveData<List<Place>> data = new MutableLiveData<>();
        Call<PlaceSearchResults> call = placeAPI.searchPlaceByName(name);
        call.enqueue(new Callback<PlaceSearchResults>() {
            @Override
            public void onResponse(Call<PlaceSearchResults> call, Response<PlaceSearchResults> response) {
                if (response.isSuccessful()){
                    PlaceSearchResults res = response.body();
                    data.setValue(res.getSearch());
                }else{
                    Log.d("TAG","----- getPlaceByName response error");
                }
            }

            @Override
            public void onFailure(Call<PlaceSearchResults> call, Throwable t) {
                Log.d("TAG","----- getPlaceByName fail");
            }
        });
        return data;
    }
}
