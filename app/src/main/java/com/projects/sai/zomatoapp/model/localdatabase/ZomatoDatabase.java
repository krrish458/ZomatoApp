package com.projects.sai.zomatoapp.model.localdatabase;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by sai on 29/06/2018.
 */

@Database(version = ZomatoDatabase.VERSION)
public class ZomatoDatabase {


    public static final int VERSION = 1;

    @Table(RestaurantFields.class)
    public static final String Restaurants = "restaurants";

}
