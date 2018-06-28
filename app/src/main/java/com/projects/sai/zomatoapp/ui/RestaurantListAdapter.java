package com.projects.sai.zomatoapp.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.projects.sai.zomatoapp.R;
import com.projects.sai.zomatoapp.model.Restaurants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sai on 28/06/2018.
 */

public class RestaurantListAdapter extends RecyclerView.Adapter <RestaurantListAdapter.RestaurantListHolder> {
    private static String TAG =RestaurantListAdapter.class.getSimpleName();
    private Context mContext;
    private List<Restaurants> mRestaurantsList;

    public RestaurantListAdapter(List<Restaurants> mRestaurantsList) {
        this.mRestaurantsList = mRestaurantsList;
    }

    @Override
    public RestaurantListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext==null)
            mContext=parent.getContext();
        View itemView = LayoutInflater.
                from(mContext).
                inflate(R.layout.restaurant_listitem, parent, false);

        return new RestaurantListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RestaurantListHolder holder, int position) {
        Restaurants restaurant=mRestaurantsList.get(position);
        holder.name.setText(restaurant.getRestaurant().getName());
        Log.d("setting tname",restaurant.getRestaurant().getName());
        holder.address.setText(restaurant.getRestaurant().getLocation().getAddress());
    }

    @Override
    public int getItemCount() {
        Log.d(TAG +"size", String.valueOf(mRestaurantsList.size()));
        return mRestaurantsList.size();

    }

    public static class RestaurantListHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.image_background)
        public ImageView image_background;
        @BindView(R.id.text_name)
        public TextView name;
        @BindView(R.id.text_address)
        public TextView address;
        public RestaurantListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
