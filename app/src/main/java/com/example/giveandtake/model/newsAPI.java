package com.example.giveandtake.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface newsAPI {

    //String BASE_URL = "GET https://newsapi.org/v2/top-headlines?country=il&apiKey=dd16b736845c4011a2b69b986bbc1a32";
    String BASE_URL = "https://newsapi.org/v2/";


    //get the top headlines from the news apk
    @GET("top-headlines")
    Call<mainNews> getNews(
            @Query("country") String country,
            @Query("pageSize")  int pageSize,
            @Query("apiKey") String apiKey
    );

    //get a specific topic of news
    @GET("top-headlines")
    Call<mainNews> getCategoryNews(
            @Query("country") String country,
            @Query("category")  String category,
            @Query("pageSize")  int pageSize,
            @Query("apiKey") String apiKey
    );

}
