package com.example.giveandtake.model;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUtilities {

    private static Retrofit retrofit = null;

    public static newsAPI getNewsApiInterface()
    {
        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder().baseUrl(newsAPI.BASE_URL).addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(newsAPI.class);
    }



}
