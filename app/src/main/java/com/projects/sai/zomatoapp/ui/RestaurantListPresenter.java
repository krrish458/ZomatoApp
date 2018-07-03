package com.projects.sai.zomatoapp.ui;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.projects.sai.zomatoapp.apiservices.RestaurantsApiService;
import com.projects.sai.zomatoapp.apiservices.ServiceGenerator;
import com.projects.sai.zomatoapp.model.NearByRestaurants;
import com.projects.sai.zomatoapp.model.RestaurantCollection;
import com.projects.sai.zomatoapp.model.localdatabase.FavoriteFields;
import com.projects.sai.zomatoapp.model.localdatabase.RestaurantFields;
import com.projects.sai.zomatoapp.model.localdatabase.RestaurantProvider;
import com.projects.sai.zomatoapp.utilities.Constatnts;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by sai on 28/06/2018.
 */

public class RestaurantListPresenter implements RestaurantListContract.presenter {
    private RestaurantsApiService mService;
    private RestaurantListContract.view mView;
    private ArrayList<NearByRestaurants> mRestaurantList;


    //method to consume nearbyrestaurant api services
    @Override
    public void loadRestaurants() {
        mService = ServiceGenerator.createService(RestaurantsApiService.class);
        Call<RestaurantCollection> call = mService.getRestaurants(RestaurantsApiService.API_KEY, -37.803, 145.002);
        call.enqueue(new Callback<RestaurantCollection>() {
            @Override
            public void onResponse(Call<RestaurantCollection> call, Response<RestaurantCollection> response) {
                if (response.code() == 200 && response.body() != null) {
                    mRestaurantList = response.body().getRestaurantArrayList();
                    mView.showRestaurants(mRestaurantList);
                    //if database dosen't contain any data then save the consumed api data locally
                    if (!localDataExists())
                        SaveDataLocally();
                }
            }

            @Override
            public void onFailure(Call<RestaurantCollection> call, Throwable t) {
                Log.d("failure", "failure");
            }
        });
    }

    private void SaveDataLocally() {
        ContentValues values = new ContentValues();
        for (NearByRestaurants nearByRestaurants : mRestaurantList) {
            //restaurant object res to fetch the properties of restaurant object and inserted to content values.
            NearByRestaurants.Restaurant res = nearByRestaurants.getRestaurant();
            values.put(RestaurantFields.Column_restaurantId, res.getId());
            values.put(RestaurantFields.Column_name, res.getName());
            values.put(RestaurantFields.Column_address, res.getLocation().getAddress());
            values.put(RestaurantFields.Column_featureImage, res.getFeatured_image());
            ((RestaurantListFragment) mView).getActivity().getContentResolver().insert(RestaurantProvider.Resturants.CONTENT_URI, values);
        }
    }

    private Boolean localDataExists() {

        Cursor cursor = ((RestaurantListFragment) mView).getActivity().getContentResolver().query(Constatnts.RESTAURANTS_URI, Constatnts.PROJECTION, null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;

        } else {
            cursor.close();
            return false;
        }
    }


    public void loadDataLocally() {
        ArrayList<NearByRestaurants> nearByRestaurantslist = new ArrayList<>();
        Cursor cursor = ((RestaurantListFragment) mView).getActivity().getContentResolver()
                .query(Constatnts.RESTAURANTS_URI,
                        Constatnts.PROJECTION,
                        null,
                        null,
                        null,
                        null);

        if (cursor == null) {
        }
        //if data is present in database then  populate an array list of type nearbyrestaurants and populate the recyclerview.
        else {
            while (cursor.moveToNext()) {
                NearByRestaurants nearByRestaurants = new NearByRestaurants();
                NearByRestaurants.Restaurant restaurant = new NearByRestaurants.Restaurant();
                NearByRestaurants.Location location = new NearByRestaurants.Location();
                String id = cursor.getString((cursor.getColumnIndex(RestaurantFields.Column_restaurantId)));
                String name = cursor.getString(cursor.getColumnIndex(RestaurantFields.Column_name));
                String address = cursor.getString(cursor.getColumnIndex(RestaurantFields.Column_address));
                String featureimage = cursor.getString(cursor.getColumnIndex(RestaurantFields.Column_featureImage));
                restaurant.setFeatured_image(featureimage);
                restaurant.setName(name);
                location.setAddress(address);
                restaurant.setLocation(location);
                restaurant.setId(id);
                nearByRestaurants.setRestaurant(restaurant);
                nearByRestaurantslist.add(nearByRestaurants);
            }
            mView.showRestaurants(nearByRestaurantslist);
            cursor.close();
        }
    }


    @Override
    public void attachView(RestaurantListContract.view view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void onAddFavorites(int position) {
        ContentValues values = new ContentValues();
        NearByRestaurants.Restaurant res = mRestaurantList.get(position).getRestaurant();
        values.put(RestaurantFields.Column_restaurantId, res.getId());
        values.put(RestaurantFields.Column_name, res.getName());
        values.put(RestaurantFields.Column_address, res.getLocation().getAddress());
        values.put(RestaurantFields.Column_featureImage, res.getFeatured_image());
        ((RestaurantListFragment) mView).getActivity().getContentResolver().insert(Constatnts.FAVORITES_URI, values);

    }

    @Override
    public void onRemoveFavorites(int position) {
        String selection = FavoriteFields.Column_restaurantId + "=?";
        String[] selectionArgs = new String[]{mRestaurantList.get(position).getRestaurant().getId()};
        ((RestaurantListFragment) mView).getActivity().getContentResolver().delete(Constatnts.FAVORITES_URI, selection, selectionArgs);
    }
}
