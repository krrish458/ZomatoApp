package com.projects.sai.zomatoapp.ui;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.projects.sai.zomatoapp.R;
import com.projects.sai.zomatoapp.model.NearByRestaurants;
import com.projects.sai.zomatoapp.model.localdatabase.FavoriteFields;
import com.projects.sai.zomatoapp.model.localdatabase.RestaurantFields;
import com.projects.sai.zomatoapp.model.localdatabase.RestaurantProvider;
import com.projects.sai.zomatoapp.utilities.Constatnts;
import com.projects.sai.zomatoapp.utilities.Utilities;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sai on 28/06/2018.
 */

public class RestaurantListAdapter extends RecyclerView.Adapter <RestaurantListAdapter.RestaurantListHolder> {
    private static String TAG = RestaurantListAdapter.class.getSimpleName();
    private Context mContext;
    private List<NearByRestaurants> mNearByRestaurantsList;
    private OnItemClickListener clickListener;

    public void setClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface OnItemClickListener {
        public void onClick(View view, int position);
    }

    public RestaurantListAdapter(List<NearByRestaurants> mNearByRestaurantsList) {
        this.mNearByRestaurantsList = mNearByRestaurantsList;
    }

    @Override
    public RestaurantListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null)
            mContext = parent.getContext();
        View itemView = LayoutInflater.
                from(mContext).
                inflate(R.layout.restaurant_listitem, parent, false);

        return new RestaurantListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RestaurantListHolder holder, int position) {
        NearByRestaurants.Restaurant restaurant = mNearByRestaurantsList.get(position).getRestaurant();
        holder.bindData(restaurant);
    }

    @Override
    public int getItemCount() {
        Log.d(TAG + "size", String.valueOf(mNearByRestaurantsList.size()));
        return mNearByRestaurantsList.size();

    }

    public class RestaurantListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.image_background)
        public ImageView image_background;
        @BindView(R.id.text_name)
        public TextView name;
        @BindView(R.id.text_address)
        public TextView address;
        @BindView(R.id.image_favorite_border)
        ImageView favorite_border;
        @BindView(R.id.image_favorite)
        ImageView favorite;


        public RestaurantListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            favorite.setOnClickListener(this);
            favorite_border.setOnClickListener(this);

        }


        public void bindData(NearByRestaurants.Restaurant restaurant) {
            //to set favorites button if restaurants already listed as favorite stored in database.
            String selection = FavoriteFields.Column_restaurantId + "=?";
            String[] selectionArgs = new String[]{restaurant.getId()};
            Cursor cursor = mContext.getContentResolver()
                    .query(Constatnts.FAVORITES_URI,
                            Constatnts.PROJECTION,
                            selection,
                            selectionArgs,
                            null,
                            null);
            if (cursor.getCount() > 0) {
                favorite_border.setVisibility(View.INVISIBLE);
                favorite.setVisibility(View.VISIBLE);
            } else {
                favorite.setVisibility(View.INVISIBLE);
                favorite_border.setVisibility(View.VISIBLE);
            }
            name.setText(restaurant.getName());
            Log.d("setting tname", restaurant.getName());
            address.setText(restaurant.getLocation().getAddress());
            Picasso.with(mContext)
                    .load(restaurant.getFeatured_image())
                    .error(R.drawable.ic_placeholder)
                    .into(image_background);

        }


        @Override
        public void onClick(View view) {
            if (Utilities.isNetworkConnected(mContext)) {
                switch (view.getId()) {
                    case R.id.image_favorite:
                        favorite.setVisibility(View.INVISIBLE);
                        favorite_border.setVisibility(View.VISIBLE);
                        break;
                    case R.id.image_favorite_border:
                        favorite_border.setVisibility(View.INVISIBLE);
                        favorite.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
                clickListener.onClick(view, getAdapterPosition());
            } else {
                Toast.makeText(mContext,
                        "Please connect to internet and click to add to favorites",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}
