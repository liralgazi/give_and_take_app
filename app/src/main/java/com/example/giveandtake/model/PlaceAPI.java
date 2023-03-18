package com.example.giveandtake.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlaceAPI {
    @GET("/?apikey=17584e92-5207-4332-a823-45df8a2a1242")
    Call<PlaceSearchResults> searchPlaceByName(@Query("s") String title);
    @GET("/?apikey=17584e92-5207-4332-a823-45df8a2a1242")
    Call<Place> getPlaceByName(@Query("t") String title);
}
