package com.projects.sai.zomatoapp.model.realm;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

import io.realm.RealmObject;

import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;
import static net.simonvt.schematic.annotation.DataType.Type.TEXT;

public class Restaurant extends RealmObject {


        private String restaurant_id;
        private String name;
        private String address;
        private String featureImage;

        public String getRestaurant_id() {
            return restaurant_id;
        }

        public void setRestaurant_id(String restaurant_id) {
            this.restaurant_id = restaurant_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getFeatureImage() {
            return featureImage;
        }

        public void setFeatureImage(String featureImage) {
            this.featureImage = featureImage;
        }
    }

