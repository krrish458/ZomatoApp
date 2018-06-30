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

import com.projects.sai.zomatoapp.R;
import com.projects.sai.zomatoapp.model.localdatabase.RestaurantFields;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;

/**
 * Created by sai on 30/06/2018.
 */

public class FavouritesListAdapter extends RecyclerView.Adapter<FavouritesListAdapter.FavouritesViewHolder>  {

    private final LayoutInflater mInflater;
    private Context mContext;
    private Cursor mCursor=null;

    public FavouritesListAdapter(Context mContext) {
        this.mInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
    }

    public void setData(Cursor cursor) {
        mCursor = cursor;
        notifyDataSetChanged();
    }
    @Override
    public FavouritesListAdapter.FavouritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(mContext).
                inflate(R.layout.restaurant_listitem, parent, false);
        return new FavouritesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FavouritesListAdapter.FavouritesViewHolder holder, int position) {


        if (mCursor != null) {
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
        }
    }

    @Override
    public int getItemCount() {
        if (mCursor != null) {
            return mCursor.getCount();
        } else {
            return -1;
        }
    }


    class FavouritesViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.image_background)
        public ImageView image_background;
        @BindView(R.id.text_name)
        public TextView name;
        @BindView(R.id.text_address)
        public TextView address;
        public FavouritesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        void  bindData(String name, String address, String featureImage){
           this.name.setText(name);
           this.address.setText(address);
            Picasso.with(mContext)
                    .load(featureImage)
                    .into(image_background);
        }
    }
}
