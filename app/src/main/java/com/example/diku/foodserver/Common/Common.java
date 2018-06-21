package com.example.diku.foodserver.Common;

import com.example.diku.foodserver.Model.Request;
import com.example.diku.foodserver.Model.User;
import com.example.diku.foodserver.Model.User2;

/**
 * Created by Diku on 24-05-2018.
 */

public class Common {
    public static User currentUser;
    public static Request currentRequest;
    public static final String UPDATE="Update";
    public static final String DELETE="Delete";
    public static final int PICK_IMAGE_REQUEST=71;

    public static String convertCodeToStatus(String code){

        if (code.equals("0"))
            return "Placed";
        else if(code.equals("1"))
            return "On my way";


        else

            return "Shipped";

    }


}