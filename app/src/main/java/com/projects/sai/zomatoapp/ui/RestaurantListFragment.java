package com.projects.sai.zomatoapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.projects.sai.zomatoapp.R;
import com.projects.sai.zomatoapp.model.NearByRestaurants;
import com.projects.sai.zomatoapp.utilities.Utilities;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sai on 28/06/2018.
 */

public class RestaurantListFragment extends Fragment implements RestaurantListContract.view, RestaurantListAdapter.OnItemClickListener {
    private static String TAG = RestaurantListFragment.class.getSimpleName();
    private Boolean isOnline = true;
    @BindView(R.id.recyclerview_homescreen)
    RecyclerView mRecylerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private RestaurantListContract.presenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new RestaurantListPresenter();
        mPresenter.attachView(((RestaurantListContract.view)this));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homescreen, container, false);
        ButterKnife.bind(this, view);
        // Give the recycler view a default layout manager.
        mRecylerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        showProgressBar();
        loadData();
        return view;
    }

    private void loadData() {
        if (Utilities.isNetworkConnected(getActivity())) {
            mPresenter.loadRestaurants();
        } else
            mPresenter.loadDataLocally();
    }


    @Override
    public void showRestaurants(ArrayList<NearByRestaurants> nearByRestaurantsList) {
        // Create an adapter and supply the data to be displayed.
        RestaurantListAdapter rAdapter = new RestaurantListAdapter(nearByRestaurantsList);
      //  rAdapter.setClickListener(this);
        // Connect the adapter with the recycler view.
        mRecylerView.setAdapter(rAdapter);
        hideProgressBar();
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //assign null to the presenter  when view is no longer available
        mPresenter.detachView();
    }

    //click handlers for favorite buttons
    @Override
    public void onClick(View view, int position) {
            mPresenter.onfavoritesclicked( position);
    }

}
