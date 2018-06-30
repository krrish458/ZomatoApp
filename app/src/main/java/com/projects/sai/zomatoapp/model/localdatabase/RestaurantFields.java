package com.projects.sai.zomatoapp.model.localdatabase;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;
import static net.simonvt.schematic.annotation.DataType.Type.TEXT;

/**
 * Created by sai on 29/06/2018.
 */

public interface RestaurantFields {


    //Fields for the table restaurant are declared here
    @DataType(INTEGER)
    @PrimaryKey
    @AutoIncrement
    String Column_Id = "_id";
    @DataType(TEXT)
    String Column_restaurantId = "restaurantID";

    @DataType(TEXT)
    @NotNull
    String Column_name = "name";

    @DataType(TEXT)
    @NotNull
    String Column_address = "address";

    @DataType(TEXT)
    @NotNull
    String Column_featureImage = "featureImage";


}
