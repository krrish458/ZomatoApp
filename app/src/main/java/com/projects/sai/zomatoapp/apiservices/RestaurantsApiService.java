package com.projects.sai.zomatoapp.apiservices;


import com.projects.sai.zomatoapp.model.RestaurantCollection;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by sai on 28/06/2018.
 */

public interface RestaurantsApiService {
    public static String API_KEY="02955979658ebb82f077b0bdc9a78206";

    @GET("/api/v2.1/geocode")
    Call<RestaurantCollection> getRestaurants(
            @Header("user-key") String api_key,
            @Query("lat") double latitude,
            @Query("lon") double longitude);
}
