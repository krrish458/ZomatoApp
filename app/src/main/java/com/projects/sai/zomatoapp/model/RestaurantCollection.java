package com.projects.sai.zomatoapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by sai on 28/06/2018.
 */

public class RestaurantCollection {
    //arralylist to collect all the restaurants in abbotsford
    @SerializedName("nearby_restaurants")
    ArrayList<NearByRestaurants> restaurantArrayList;
    public ArrayList<NearByRestaurants> getRestaurantArrayList() {
        return restaurantArrayList;
    }

    public void setRestaurantArrayList(ArrayList<NearByRestaurants> restaurantArrayList) {
        this.restaurantArrayList = restaurantArrayList;
    }


}
