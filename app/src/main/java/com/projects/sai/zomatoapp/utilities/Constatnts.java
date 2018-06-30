package com.projects.sai.zomatoapp.utilities;

import android.net.Uri;

import com.projects.sai.zomatoapp.model.localdatabase.RestaurantFields;
import com.projects.sai.zomatoapp.model.localdatabase.RestaurantProvider;

import java.net.URI;

/**
 * Created by sai on 30/06/2018.
 */

public class Constatnts {

    public static String[] PROJECTION = {
            RestaurantFields.Column_name,    // Contract class constant for restaurant name in the database table
            RestaurantFields.Column_address,    // Contract class constant for restaurant address in the database table
            RestaurantFields.Column_featureImage,    // Contract class constant restaurant feature image in the database table
    };

    public static Uri RESTAURANTS_URI= RestaurantProvider.Resturants.CONTENT_URI;
    /*public static Uri FAVORITES_URI= RestaurantProvider.Favorites.CONTENT_URI;*/
}
