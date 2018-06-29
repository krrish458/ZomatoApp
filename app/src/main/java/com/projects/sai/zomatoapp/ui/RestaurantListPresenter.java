package com.projects.sai.zomatoapp.ui;

import android.util.Log;

import com.projects.sai.zomatoapp.apiservices.RestaurantsApiService;
import com.projects.sai.zomatoapp.apiservices.ServiceGenerator;
import com.projects.sai.zomatoapp.model.RestaurantCollection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sai on 28/06/2018.
 */

public class RestaurantListPresenter implements RestaurantListContract.presenter {
    private RestaurantsApiService mService;
    private RestaurantListContract.view mView;


    @Override
    public void loadRestaurants() {
        mService = ServiceGenerator.createService(RestaurantsApiService.class);
        Call<RestaurantCollection> call = mService.getRestaurants(RestaurantsApiService.API_KEY, -37.803, 145.002);

        call.enqueue(new Callback<RestaurantCollection>() {
            @Override
            public void onResponse(Call<RestaurantCollection> call, Response<RestaurantCollection> response) {
                if(response.code()==200) {
                    mView.showRestaurants(response.body().getRestaurantArrayList());
                }
                else {
                    Log.d("message", "couldnt connect to server");
                }
            }

            @Override
            public void onFailure(Call<RestaurantCollection> call, Throwable t) {
                Log.d("failure", "failure");
            }
        });
    }

    @Override
    public void setView(RestaurantListContract.view view) {
            mView=view;
    }
}
