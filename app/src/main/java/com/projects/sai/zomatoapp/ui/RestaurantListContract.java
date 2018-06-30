package com.projects.sai.zomatoapp.ui;

import android.content.ContentValues;

import com.projects.sai.zomatoapp.model.NearByRestaurants;

import java.util.ArrayList;

/**
 * Created by sai on 28/06/2018.
 */


public interface RestaurantListContract {

    interface view{
        void showRestaurants(ArrayList<NearByRestaurants> nearByRestaurantsList);
        void showProgressBar();
        void hideProgressBar();
    }
    interface presenter{
        void loadRestaurants();
        void loadDataLocally();
        void attachView(RestaurantListContract.view view);
        void detachView();
    }
}
