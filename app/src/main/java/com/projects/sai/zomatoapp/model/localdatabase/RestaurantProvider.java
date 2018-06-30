package com.projects.sai.zomatoapp.model.localdatabase;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by sai on 29/06/2018.
 */
@ContentProvider(authority = RestaurantProvider.AUTHORITY, database = ZomatoDatabase.class)
public class RestaurantProvider {


    public static final String AUTHORITY =
            "com.projects.sai.zomatoapp.model.localdatabase.RestaurantProvider";
    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);


    //method to build the contentprovider uri
    private static Uri buildUri(String... paths) {
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }

    interface Path {

        //name must match exactly to the table name specified in zomato database class
        String Restaurants = "restaurants";
        String Favorites="favorites";
    }

    @TableEndpoint(table = ZomatoDatabase.Restaurants)
    //BUILDING CONTENTURI'S FOR RESTAURANT TABLE
    public static class Resturants {
        //BUILDING PATH TO BROWSE RESTAURANT TABLE
        @ContentUri(
                path = Path.Restaurants,
                type = "vnd.android.cursor.dir/movies",
                defaultSort = RestaurantFields.Column_restaurantId + " ASC")
        public static final Uri CONTENT_URI = buildUri(Path.Restaurants);

        //BUILDING PATH TO BROWSE FOR A PARTICULAR ROW BASED ON RESTAURANT ID
        @InexactContentUri(
                name = "RESTAURANT_ID",
                path = Path.Restaurants + "/#",
                type = "vnd.android.cursor.item/planet",
                whereColumn = RestaurantFields.Column_restaurantId,
                pathSegment = 1)
        public static Uri withId(long id) {
            return buildUri(Path.Restaurants, String.valueOf(id));
        }
    }

    @TableEndpoint(table = ZomatoDatabase.Favorites)
    //BUILDING THE FAVORITES TABLE
    public static class Favorites {
        //BUILDING PATH TO BROWSE THE WHOLE FAVORITES TABLE.
        @ContentUri(
                path = Path.Favorites,
                type = "vnd.android.cursor.dir/movies",
                defaultSort = RestaurantFields.Column_restaurantId + " ASC")
        public static final Uri CONTENT_URI = buildUri(Path.Favorites);

        //BUILDING PATH TO BROWSE FOR A PARTICULAR ROW BASED ON RESTAURANT ID
        @InexactContentUri(
                name = "RESTAURANT_ID",
                path = Path.Restaurants + "/#",
                type = "vnd.android.cursor.item/planet",
                whereColumn = RestaurantFields.Column_restaurantId,
                pathSegment = 1)
        public static Uri withId(long id) {
            return buildUri(Path.Favorites, String.valueOf(id));
        }
    }
}

