package com.projects.sai.zomatoapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.projects.sai.zomatoapp.apiservices.RestaurantsApiService;
import com.projects.sai.zomatoapp.apiservices.ServiceGenerator;
import com.projects.sai.zomatoapp.model.RestaurantCollection;
import com.projects.sai.zomatoapp.model.realm.Favorites;
import com.projects.sai.zomatoapp.ui.FavouritesFragment;
import com.projects.sai.zomatoapp.ui.RestaurantListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private MyPagerAdapter adapterViewPager;

    @BindView(R.id.vpPager)
    ViewPager vpPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);

        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);

    }


    public static class MyPagerAdapter extends FragmentPagerAdapter  implements RestaurantListFragment.ondatachangelistener{
        private static int NUM_ITEMS = 2;
        private FavouritesFragment favouritesFragment;
        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);

        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new RestaurantListFragment(this);
                case 1:
                    favouritesFragment=new FavouritesFragment();
                    return favouritesFragment;

                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "RestaurantsNearBy";
                case 1:
                    return "Favourites";

                default:
                    return null;
            }
        }


        @Override
        public void onFavoriteDataChange() {
            favouritesFragment.notifyAdapter();
        }
    }


}
