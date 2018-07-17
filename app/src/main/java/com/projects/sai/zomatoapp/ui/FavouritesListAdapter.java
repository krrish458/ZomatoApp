package com.projects.sai.zomatoapp.ui;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.projects.sai.zomatoapp.R;
import com.projects.sai.zomatoapp.model.localdatabase.FavoriteFields;
import com.projects.sai.zomatoapp.model.localdatabase.RestaurantFields;
import com.projects.sai.zomatoapp.model.realm.Favorites;
import com.projects.sai.zomatoapp.model.realm.RealmController;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

import static android.content.ContentValues.TAG;

/**
 * Created by sai on 30/06/2018.
 */

public class FavouritesListAdapter extends RecyclerView.Adapter<FavouritesListAdapter.FavouritesViewHolder>  {


    private Context mContext;
    private Realm mRealm;

    private static final String TAG=FavouritesListAdapter.class.getSimpleName();

    public FavouritesListAdapter(Activity activity) {
        this.mContext = activity;
        mRealm= RealmController.with(activity).getRealm();
    }


    @Override
    public FavouritesListAdapter.FavouritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.restaurant_listitem, parent, false);
        return new FavouritesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FavouritesListAdapter.FavouritesViewHolder holder, int position) {

        if(mRealm!=null){
            RealmResults<Favorites> results=mRealm.where(Favorites.class).findAllAsync();
            Favorites fav=results.get(position);
            Log.d(TAG,fav.getName());
            holder.bindData(fav.getName(),fav.getAddress(),fav.getFeatureImage());

        }

        /*if (mCursor != null) {
            if (mCursor.moveToPosition(position)) {
                String name = mCursor.getString(mCursor.getColumnIndex(RestaurantFields.Column_name));
                String address = mCursor.getString(mCursor.getColumnIndex(RestaurantFields.Column_address));
                String featureimage = mCursor.getString(mCursor.getColumnIndex(RestaurantFields.Column_featureImage));
                holder.bindData(name,address,featureimage);
            } else {
               // holder.wordItemView.setText(R.string.error_no_word);
            }
        } else {
            Log.e (TAG, "onBindViewHolder: Cursor is null.");
        }*/
    }

    @Override
    public int getItemCount() {

            return (int) mRealm.where(Favorites.class).count();

    }


    class FavouritesViewHolder extends RecyclerView.ViewHolder{
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

        public FavouritesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            favorite.setVisibility(View.INVISIBLE);
            favorite_border.setVisibility(View.INVISIBLE);
        }

        void  bindData(String name, String address, String featureImage){
           this.name.setText(name);
           this.address.setText(address);
            Picasso.with(mContext)
                    .load(featureImage)
                    .placeholder(R.drawable.ic_placeholder)
                    .into(image_background);
        }
    }
}
