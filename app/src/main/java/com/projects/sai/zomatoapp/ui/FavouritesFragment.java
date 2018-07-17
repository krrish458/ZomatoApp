package com.projects.sai.zomatoapp.ui;

import android.database.Cursor;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.view.menu.MenuAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projects.sai.zomatoapp.R;
import com.projects.sai.zomatoapp.utilities.Constatnts;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sai on 30/06/2018.
 */

public class FavouritesFragment extends Fragment  {

    private FavouritesListAdapter mAdapter;
    @BindView(R.id.recyclerview_homescreen)
    RecyclerView mRecyclerView;

    public void notifyAdapter() {
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_homescreen, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new FavouritesListAdapter(getActivity());
        // Connect the adapter with the recycler view.
        mRecyclerView.setAdapter(mAdapter);
        // Give the recycler view a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }


}
