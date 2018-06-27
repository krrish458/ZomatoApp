package com.projects.sai.zomatoapp.ui;

import com.projects.sai.zomatoapp.model.Restaurant;

import java.util.List;

/**
 * Created by sai on 28/06/2018.
 */

public interface RestaurantListContract {

    interface view{
        void showRestaurants(List<Restaurant> RestaurantsList);
        void showProgressBar();
    }
    interface presenter{
        void loadRestaurants();
        void setView(RestaurantListContract.view view);
    }
}
