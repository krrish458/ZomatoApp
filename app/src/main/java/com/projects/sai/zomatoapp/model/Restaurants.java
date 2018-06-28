package com.projects.sai.zomatoapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sai on 28/06/2018.
 */

public class Restaurants {
    //restaurants list containing objects of type restaurant and location
    private Restaurant restaurant;


    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    //class for location object
    public static class Location {

        private String address;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    //class for restaurant object
    public static class Restaurant {
        @SerializedName("id")
        private String id;
        @SerializedName("name")
        private String name;
        private Location location;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

    }
}
