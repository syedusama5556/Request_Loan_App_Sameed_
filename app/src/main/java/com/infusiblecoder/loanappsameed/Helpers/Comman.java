package com.infusiblecoder.loanappsameed.Helpers;

import android.content.Context;

import com.shashank.sony.fancytoastlib.FancyToast;

public class Comman {

    //public static String START_URL = "https://peer.infusiblecoder.com/android/loanapp/";
    public static String START_URL = "http://192.168.0.102/android/loanapp/";


    public static String DB_URL = START_URL + "db.php";
    public static String Admin_Email = "admin@peer2peer.com";

    public static String REGISTER_URL = START_URL + "register.php";

    public static String UPLOAD_MULTIPLE_DOC_WITH_DATA_URL = START_URL + "uploadmultipledoc.php";
    public static String UPLOAD_MULTIPLE_DOC_WITH_DATA_URL2nd = START_URL + "uploadmultipledoc2.php";


    public static String DELETE_SENT_REQUESTS_URL = START_URL + "deletesentrequest.php";
    public static String DELETE_SENT_REQUESTS_for_reciver_URL = START_URL + "deleterequest_for_reciver.php";
    public static String getdataforsingleuserwithloanreqcode_REQUESTS_URL = START_URL + "getdataforsingleuserwithloanreqcode.php";


    public static String GET_ALL_singlerequestdeudatefortimer_URL = START_URL + "getsinglerequestdeudatefortimer.php";
    public static String GET_ALL_REQUEST_TABLE_DATA_URL = START_URL + "getalldatarequesttable.php";
    public static String GET_ALL_REQUEST_TABLE_Single_User_DATA_URL = START_URL + "getalldatarequesttablesingleuser.php";
    public static String GET_ALL_UNSEEN_REQUESTS_TABLE_DATA_URL = START_URL + "getallunseeenuserrequests.php";
    public static String GET_ALL_NOTIFICATIONS_REQUESTS_TABLE_URL = START_URL + "getallallnotificationsfromrequeststable.php";
    public static String GET_ALL_DATA_FROM_MULTIPLE_TABLE_REQUESTS_TABLE_URL = START_URL + "getdatafrommultipletableforonclickrequest.php";
    public static String GET_ALL_Sented_REQUEST_TABLE_DATA_URL = START_URL + "getallsentedrequestshistory.php";
    public static String GET_ALL_Recived_REQUESTS_TABLE_URL = START_URL + "getallallnotificationsfromrequeststable.php";


    public static String SEND_REQUEST_URL = START_URL + "sendrequesttoperson.php";

    public static String LOGIN_URL = START_URL + "login.php";
    public static String PROFILE_PAGE_URL = START_URL + "getdataforsingleuser.php";
    public static String CHANGE_PASSWORD_URL = START_URL + "changepassword.php";

    public static String UPDATE_PROFILE_URL = START_URL + "updateprofile.php";
    public static String UPDATE_updatenotificationseentotrue_URL = START_URL + "updatenotificationseentotrue.php";
    public static String UPDATE_updatenotificationstatustoaccepted_URL = START_URL + "updatenotificationstatustoaccepted.php";
    public static String UPDATE_inreview_requests_loanrequest_table_URL = START_URL + "updateinreviewrequestsloanrequesttable.php";


    public static String[] TABLE_USERS_ATTRIBUTES = {"firstname", "lastname", "address", "whatyoupretend", "fieldofactivity", "phone", "email", "password", "status", "user_img_url"};
    public static String[] TABLE_LOAN_REQUEST_ATTRIBUTES = {"loan_request_code", "user_full_name", "user_img_url_request", "loan_amount", "loan_purpose", "loan_collateral", "loan_market_value", "loan_borrowing_rate", "loan_loan_ratio", "loan_type", "loan_due_date", "loan_doc_urls", "loan_status"};
    public static String[] TABLE_Requests_ATTRIBUTES = {"loan_request_code", "request_sender_user_name", "request_reciver_user_name", "request_sender_user_id", "request_reciver_user_id", "request_time_stamp", "request_is_seen"};


    public static String SHAREDPREF_USERDATA = "saveuserinfo";
    public static String[] SHAREDPREF_USERDATA_ATTRIBUTES = {"user_id", "firstname", "lastname", "address", "whatyoupretend", "fieldofactivity", "phone", "email", "password", "status", "user_img_url", "user_img_url_without_start"};


//    ArrayList<UserTableData> userTableData1 = new ArrayList<>();
//
//    Type type = new TypeToken<ArrayList<UserTableData>>(){}.getType();
//    userTableData1 = gson.fromJson(response, type);
//
//                            System.out.println("qwerty data is "+userTableData1.get(0).email);
//                            System.out.println("qwerty data is "+userTableData1.get(0).address);


    public static String[] LOAN_TYPES = {"personal loan", "car loan", "commercial loan", "travel loan", "house loan", "tax loan", "other loan"};

    public static String[] LOAN_Status = {"pending", "review", "rejected", "approved", "completed"};
    public static String[] REQUEST_Status = {"pending", "rejected", "accepted", "approved", "completed"};


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
