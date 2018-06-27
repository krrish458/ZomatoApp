package com.projects.sai.zomatoapp.apiservices;

import com.projects.sai.zomatoapp.model.Restaurant;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by sai on 28/06/2018.
 */

public interface RestaurantsApiService {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<Restaurant>> getRestaurants();
}
