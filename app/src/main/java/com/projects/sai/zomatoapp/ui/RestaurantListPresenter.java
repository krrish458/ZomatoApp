package com.projects.sai.zomatoapp.ui;

import android.content.ContentValues;
import android.database.Cursor;
import android.nfc.Tag;
import android.util.Log;

import com.projects.sai.zomatoapp.apiservices.RestaurantsApiService;
import com.projects.sai.zomatoapp.apiservices.ServiceGenerator;
import com.projects.sai.zomatoapp.model.NearByRestaurants;
import com.projects.sai.zomatoapp.model.RestaurantCollection;
import com.projects.sai.zomatoapp.model.localdatabase.FavoriteFields;
import com.projects.sai.zomatoapp.model.localdatabase.RestaurantFields;
import com.projects.sai.zomatoapp.model.localdatabase.RestaurantProvider;
import com.projects.sai.zomatoapp.model.realm.Favorites;
import com.projects.sai.zomatoapp.model.realm.RealmController;
import com.projects.sai.zomatoapp.model.realm.Restaurant;
import com.projects.sai.zomatoapp.utilities.Constatnts;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by sai on 28/06/2018.
 */

public class RestaurantListPresenter implements RestaurantListContract.presenter  {
    private RestaurantsApiService mService;
    private RestaurantListContract.view mView;
    private ArrayList<NearByRestaurants> mRestaurantList;
    private Realm mRealm;

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

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for(NearByRestaurants nearByRestaurants : mRestaurantList){
                    NearByRestaurants.Restaurant res = nearByRestaurants.getRestaurant();
                    Restaurant r= realm.createObject(Restaurant.class);
                    r.setAddress(res.getLocation().getAddress());
                    r.setName(res.getName());
                    r.setFeatureImage(res.getFeatured_image());
                    r.setRestaurant_id(res.getId());
                }
            }
        });
    }

    private Boolean localDataExists() {
        long count= mRealm.where(Restaurant.class).count();
        if(count>0)
            return true;
        else
            return false;
    }


    public void loadDataLocally() {
        Log.d("loading offline data", "using realm");
        ArrayList<NearByRestaurants> nearByRestaurantslist = new ArrayList<>();
        RealmResults <Restaurant> results= mRealm.where(Restaurant.class).findAllAsync();
        for(Restaurant r: results){
            NearByRestaurants nearByRestaurants = new NearByRestaurants();
            NearByRestaurants.Restaurant restaurant = new NearByRestaurants.Restaurant();
            NearByRestaurants.Location location = new NearByRestaurants.Location();
            restaurant.setName(r.getName());
            restaurant.setFeatured_image(r.getFeatureImage());
            restaurant.setId(r.getRestaurant_id());
            location.setAddress(r.getAddress());
            restaurant.setLocation(location);
            nearByRestaurants.setRestaurant(restaurant);
            nearByRestaurantslist.add(nearByRestaurants);
        }
        mView.showRestaurants(nearByRestaurantslist);

    }


    @Override
    public void attachView(RestaurantListContract.view view) {
        mView = view;
        mRealm= RealmController.with((RestaurantListFragment)mView).getRealm();
    }

    @Override
    public void detachView() {
        mView = null;
        mRealm.close();
    }

    @Override
    public void onAddFavorites(int position) {
        NearByRestaurants.Restaurant res = mRestaurantList.get(position).getRestaurant();
        Favorites favrealm= new Favorites();
        favrealm.setRestaurant_id(res.getId());
        favrealm.setFeatureImage(res.getFeatured_image());
        favrealm.setAddress(res.getLocation().getAddress());
        favrealm.setName(res.getName());
        mRealm.beginTransaction();
        mRealm.copyToRealm(favrealm);
        mRealm.commitTransaction();

    }

    @Override
    public void onRemoveFavorites(int position) {
        NearByRestaurants.Restaurant res = mRestaurantList.get(position).getRestaurant();
        String id=res.getId();
        RealmResults<Favorites> results=mRealm.where(Favorites.class).findAllAsync();
        Favorites fav=results.where().equalTo("restaurant_id",id).findFirst();
        if(!mRealm.isInTransaction())
            mRealm.beginTransaction();
            fav.deleteFromRealm();
        mRealm.commitTransaction();
    }


}
