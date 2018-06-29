package com.projects.sai.zomatoapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.projects.sai.zomatoapp.apiservices.RestaurantsApiService;
import com.projects.sai.zomatoapp.apiservices.ServiceGenerator;
import com.projects.sai.zomatoapp.model.RestaurantCollection;
import com.projects.sai.zomatoapp.ui.RestaurantListFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragment_container, new RestaurantListFragment());
        transaction.commit();
    }


}
