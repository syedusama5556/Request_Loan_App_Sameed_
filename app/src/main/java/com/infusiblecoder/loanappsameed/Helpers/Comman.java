package com.infusiblecoder.loanappsameed.Helpers;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.infusiblecoder.loanappsameed.ModelClasses.UserTableData;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Comman {

    //public static String START_URL = "http://peer.infusiblecoder.com/android/loanapp/";
    public static String START_URL = "http://192.168.0.108/android/loanapp/";


    public static String DB_URL = START_URL + "db.php";

    public static String REGISTER_URL = START_URL + "register.php";

    public static String UPLOAD_MULTIPLE_DOC_WITH_DATA_URL = START_URL +"uploadmultipledoc.php";



    public static String LOGIN_URL = START_URL + "login.php";
    public static String PROFILE_PAGE_URL = START_URL + "getdataforsingleuser.php";
    public static String CHANGE_PASSWORD_URL = START_URL + "changepassword.php";
    public static String UPDATE_PROFILE_URL = START_URL + "updateprofile.php";

    public static String TABLE_USERS_ATTRIBUTES[] = {"firstname", "lastname", "address", "whatyoupretend", "fieldofactivity", "phone", "email", "password", "status","user_img_url"};
    public static String TABLE_LOAN_REQUEST_ATTRIBUTES[] = {"loan_request_code", "user_full_name","user_img_url_request", "loan_amount", "loan_purpose", "loan_collateral", "loan_market_value","loan_type", "loan_due_date", "loan_doc_vehicle_id_url", "loan_doc_owner_id_url","loan_doc_insurance_url"};





    public static String SHAREDPREF_USERDATA = "saveuserinfo";
    public static String SHAREDPREF_USERDATA_ATTRIBUTES[] = {"user_id","firstname", "lastname", "address", "whatyoupretend", "fieldofactivity", "phone", "email", "password", "status","user_img_url","user_img_url_without_start"};




//    ArrayList<UserTableData> userTableData1 = new ArrayList<>();
//
//    Type type = new TypeToken<ArrayList<UserTableData>>(){}.getType();
//    userTableData1 = gson.fromJson(response, type);
//
//                            System.out.println("qwerty data is "+userTableData1.get(0).email);
//                            System.out.println("qwerty data is "+userTableData1.get(0).address);





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
