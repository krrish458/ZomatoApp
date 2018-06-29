package com.projects.sai.zomatoapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projects.sai.zomatoapp.R;
import com.projects.sai.zomatoapp.model.Restaurants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sai on 28/06/2018.
 */

public class RestaurantListFragment  extends Fragment implements RestaurantListContract.view{
    public static String TAG= RestaurantListFragment.class.getSimpleName();
    @BindView(R.id.recyclerview_homescreen)
    RecyclerView mRecylerView;
    private RestaurantListContract.presenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mPresenter= new RestaurantListPresenter();
        mPresenter.setView(this);
        mPresenter.loadRestaurants();
        View view= inflater.inflate(R.layout.fragment_homescreen,container,false);
        ButterKnife.bind(this,view);
        mRecylerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void showRestaurants(ArrayList<Restaurants> RestaurantsList) {
        Log.d(TAG, "populating vies");
        RestaurantListAdapter rAdapter=new RestaurantListAdapter(RestaurantsList);
        mRecylerView.setAdapter(rAdapter);

    }

    @Override
    public void showProgressBar() {

    }
}
