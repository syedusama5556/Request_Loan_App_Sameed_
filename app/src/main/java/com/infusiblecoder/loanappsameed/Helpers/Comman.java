package com.infusiblecoder.loanappsameed.Helpers;

import android.content.Context;

import com.shashank.sony.fancytoastlib.FancyToast;

public class Comman {

    public static String START_URL = " http://192.168.10.11/android/loanapp";


    public static String DB_URL = START_URL + "/db.php";

    public static String REGISTER_URL = START_URL + "/register.php";

    public static String LOGIN_URL = START_URL + "/login.php";

//        firstname = "firstname";
//        lastname = "lastname";
//        address = "address";
//        whatyoupretend = "whatyoupretend";
//        fieldofactivity = "fieldofactivity";
//
//        phone = "phone";
//        email = "email";
//        password = "password";
//        status = "status";


    public static String TABLE_USERS_ATTRIBUTES[] = {"firstname", "lastname", "address", "whatyoupretend", "fieldofactivity", "phone", "email", "password", "status"};


    public static void showErrorToast(Context context, String txt) {

        FancyToast.makeText(context, txt, FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

    }

    public static void showSucdessToast(Context context, String txt) {

        FancyToast.makeText(context, txt, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

    }


    public static void showDefaultToast(Context context, String txt) {

        FancyToast.makeText(context, txt, FancyToast.LENGTH_LONG, FancyToast.DEFAULT, false).show();

    }

}
