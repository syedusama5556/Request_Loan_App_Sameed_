package com.infusiblecoder.loanappsameed.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.google.gson.Gson;
import com.infusiblecoder.loanappsameed.Helpers.Comman;
import com.infusiblecoder.loanappsameed.Helpers.VollySingltonClass;
import com.infusiblecoder.loanappsameed.ModelClasses.GetAllMultipleTableDataOnClickRequestMODEL;
import com.infusiblecoder.loanappsameed.ModelClasses.RequestLoanModel;
import com.infusiblecoder.loanappsameed.ModelClasses.UserRequestModel;
import com.infusiblecoder.loanappsameed.R;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.roger.catloadinglibrary.CatLoadingView;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ShowDetailsOfserRequestsOnClick extends AppCompatActivity {

    String Usermail = "recipient@example.com";
    private UserRequestModel userRequestModel;
    private CatLoadingView catLoadingView;
    private CircularImageView userImgViewOnclick;
    private TextView reviewRequestRequestSenderUserNameTxt;
    private ImageView verificationStatusImageView;
    private TextView loanRequestCodeOnclick;
    private TextView phoneOnclick;
    private TextView emailOnclick;
    private TextView timestampOnclick;
    private ImageView btnCallUser;
    private ImageView btnSendMailUser;

    private String issented;
    private LinearLayout linlayoutDelete;
    private ImageView btnDeleteRequest;
    private LinearLayout linallbtn;
    private String senderuser_id = "11";
    private RequestLoanModel requestLoanModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details_ofser_requests_on_click);

        linlayoutDelete = findViewById(R.id.linlayout_Delete);
        linallbtn = findViewById(R.id.linallbtn);

        btnDeleteRequest = findViewById(R.id.btn_delete_request);

        userImgViewOnclick = findViewById(R.id.user_img_view_onclick);
        reviewRequestRequestSenderUserNameTxt = findViewById(R.id.review_request_request_sender_user_name_txt);
        verificationStatusImageView = findViewById(R.id.path_image_view);
        loanRequestCodeOnclick = findViewById(R.id.loan_request_code_onclick);
        phoneOnclick = findViewById(R.id.phone_onclick);
        emailOnclick = findViewById(R.id.email_onclick);
        timestampOnclick = findViewById(R.id.timestamp_onclick);
        btnCallUser = findViewById(R.id.btn_call_user);
        btnSendMailUser = findViewById(R.id.btn_send_mail_user);

        try {
            userRequestModel = (UserRequestModel) getIntent().getSerializableExtra("mynotificationsdata");

            issented = getIntent().getStringExtra("issented");

            if (issented.equals("true")) {
                linlayoutDelete.setVisibility(View.VISIBLE);
                linallbtn.setVisibility(View.GONE);

            } else if (issented.equals("false")) {
                linlayoutDelete.setVisibility(View.GONE);
                linallbtn.setVisibility(View.VISIBLE);

            }
        } catch (Exception e) {
            Toast.makeText(this, "error " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        catLoadingView = new CatLoadingView();


        btnSendMailUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userRequestModel.req_status.equals("rejected")) {
                    Comman.showDefaultToast(ShowDetailsOfserRequestsOnClick.this, "Your Request Has Been Rejected, Contact The Admin For Further Details");
                } else {
                    if (!userRequestModel.req_status.equals("awaiting money") && !userRequestModel.req_status.equals("approved") && !userRequestModel.req_status.equals("completed")) {

                        // Create Alert using Builder
                        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ShowDetailsOfserRequestsOnClick.this)
                                .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                                .setTitle("Delete Request")
                                .setMessage("Are You Sure You Want To Reject This Request?")
                                .addButton("Yes", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (dialog, which) -> {


                                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Comman.DELETE_SENT_REQUESTS_for_reciver_URL, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            System.out.println("mysre0 +" + response);
                                            try {
                                                JSONArray jsonArray = new JSONArray(response);

                                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                                String code = jsonObject.getString("code");

                                                System.out.println("mysre1 +" + jsonObject);

                                                if (code.equals("failed")) {
                                                    dialog.dismiss();

                                                    Comman.showErrorToast(ShowDetailsOfserRequestsOnClick.this, "Failed " + jsonObject.getString("message"));


                                                } else {

                                                    dialog.dismiss();
                                                    Comman.showSucdessToast(ShowDetailsOfserRequestsOnClick.this, "" + jsonObject.getString("message"));

                                                    startActivity(new Intent(getApplicationContext(), HomeActivityDrawar.class));
                                                    finish();

                                                }

                                            } catch (JSONException e) {
                                                dialog.dismiss();
                                                Comman.showErrorToast(ShowDetailsOfserRequestsOnClick.this, "error is " + e.getMessage());

                                            }


                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            dialog.dismiss();
                                            Comman.showErrorToast(ShowDetailsOfserRequestsOnClick.this, "error is " + error);


                                        }
                                    }) {
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {


                                            Map<String, String> params = new HashMap<String, String>();
                                            params.put("request_id", userRequestModel.request_id);


                                            System.out.println("mydatais " + userRequestModel.request_id);

                                            return params;

                                        }
                                    };

                                    VollySingltonClass.getmInstance(ShowDetailsOfserRequestsOnClick.this).addToRequsetque(stringRequest);


                                }).addButton("No", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (dialog, which) -> {
                                    dialog.dismiss();

                                });

// Show the alert
                        builder.show();

                    } else {
                        Comman.showDefaultToast(ShowDetailsOfserRequestsOnClick.this, "Your Request Has Been Accepted By The Admin, Contact The Admin For Further Details");
                    }
                }
            }
        });

        btnCallUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (userRequestModel.req_status.equals("rejected")) {
                    Comman.showDefaultToast(ShowDetailsOfserRequestsOnClick.this, "Your Request Has Been Rejected, Contact The Admin For Further Details");


                } else {
                    if (!userRequestModel.req_status.equals("awaiting money") && !userRequestModel.req_status.equals("approved") && !userRequestModel.req_status.equals("completed")) {

                        Dialog view = new Dialog(ShowDetailsOfserRequestsOnClick.this);
                        view.setContentView(R.layout.dialog_comfirm_loan_with_loanratio);

                        TextView dialogLenderTextView = (TextView) view.findViewById(R.id.dialog_lender_text_view);
                        TextView dialogLoanRequestIdTextView = (TextView) view.findViewById(R.id.dialog_loan_request_id_text_view);
                        TextView dialogDueDateTextView = (TextView) view.findViewById(R.id.dialog_due_date_text_view);
                        TextView dialogBorrowingRateTextView = (TextView) view.findViewById(R.id.dialog_borrowing_rate_text_view);
                        TextView dialogLoanRatioTextView = (TextView) view.findViewById(R.id.dialog_loan_ratio_text_view);
                        Button donBtnDialog = (Button) view.findViewById(R.id.don_btn_dialog);


                        System.out.println("mydatais1 " + requestLoanModel);
                        dialogLenderTextView.setText(reviewRequestRequestSenderUserNameTxt.getText().toString());
                        dialogLoanRequestIdTextView.setText(userRequestModel.loan_request_code);

                        dialogDueDateTextView.setText(requestLoanModel.loan_due_date);
                        dialogBorrowingRateTextView.setText(requestLoanModel.loan_borrowing_rate);
                        dialogLoanRatioTextView.setText(requestLoanModel.loan_loan_ratio);


                        donBtnDialog.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                StringRequest stringRequest = new StringRequest(Request.Method.POST, Comman.UPDATE_updatenotificationstatustoaccepted_URL, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {


                                        try {
                                            JSONArray jsonArray = new JSONArray(response);

                                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                                            String code = jsonObject.getString("code");

                                            System.out.println("mysrec +" + jsonObject);

                                            if (code.equals("failed")) {


                                                Comman.showErrorToast(ShowDetailsOfserRequestsOnClick.this, "Failed " + jsonObject.getString("message"));


                                            } else {


                                                Comman.showSucdessToast(ShowDetailsOfserRequestsOnClick.this, "" + jsonObject.getString("message"));

                                                startActivity(new Intent(getApplicationContext(), HomeActivityDrawar.class));
                                                finish();
                                            }

                                        } catch (JSONException e) {
                                            Comman.showErrorToast(ShowDetailsOfserRequestsOnClick.this, "error is " + e.getMessage());

                                        }


                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                        Comman.showErrorToast(ShowDetailsOfserRequestsOnClick.this, "error is " + error);


                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {


                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put("request_id", userRequestModel.request_id);

                                        return params;

                                    }
                                };

                                VollySingltonClass.getmInstance(ShowDetailsOfserRequestsOnClick.this).addToRequsetque(stringRequest);


                            }
                        });

                        view.show();
                    } else {
                        Comman.showDefaultToast(ShowDetailsOfserRequestsOnClick.this, "Your Request Has Been Accepted By The Admin, Contact The Admin For Further Details");
                    }
                }

            }
        });


        btnDeleteRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (issented.equals("true")) {


                    // Create Alert using Builder
                    CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ShowDetailsOfserRequestsOnClick.this)
                            .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                            .setTitle("Delete Request")
                            .setMessage("Are You Sure You Want To Delete This Request, After This The Request Will No Longer Be Visible To The Sent User?")
                            .addButton("Yes", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (dialog, which) -> {


                                StringRequest stringRequest = new StringRequest(Request.Method.POST, Comman.DELETE_SENT_REQUESTS_URL, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {


                                        try {
                                            JSONArray jsonArray = new JSONArray(response);

                                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                                            String code = jsonObject.getString("code");

                                            System.out.println("mysred +" + jsonObject);

                                            if (code.equals("failed")) {
                                                dialog.dismiss();

                                                Comman.showErrorToast(ShowDetailsOfserRequestsOnClick.this, "Failed " + jsonObject.getString("message"));


                                            } else {

                                                dialog.dismiss();
                                                Comman.showSucdessToast(ShowDetailsOfserRequestsOnClick.this, "" + jsonObject.getString("message"));

                                                startActivity(new Intent(getApplicationContext(), HomeActivityDrawar.class));
                                                finish();


                                            }

                                        } catch (JSONException e) {
                                            dialog.dismiss();
                                            Comman.showErrorToast(ShowDetailsOfserRequestsOnClick.this, "error is " + e.getMessage());

                                        }


                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        dialog.dismiss();
                                        Comman.showErrorToast(ShowDetailsOfserRequestsOnClick.this, "error is " + error);


                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {

                                        SharedPreferences prefs = getSharedPreferences(Comman.SHAREDPREF_USERDATA, MODE_PRIVATE);
                                        String id = prefs.getString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[0], "no value");//"No name defined" is the default value.

                                        if (!id.equals("no value") && !id.equals("")) {


                                        } else {


                                            Comman.showErrorToast(ShowDetailsOfserRequestsOnClick.this, "error is getting id");

                                        }

                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put("request_id", userRequestModel.request_id);
                                        params.put("user_id", id);

                                        System.out.println("mydatais " + id + userRequestModel.request_id);

                                        return params;

                                    }
                                };

                                VollySingltonClass.getmInstance(ShowDetailsOfserRequestsOnClick.this).addToRequsetque(stringRequest);


                            }).addButton("No", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (dialog, which) -> {
                                dialog.dismiss();

                            });

// Show the alert
                    builder.show();


                }


            }
        });

        getAllDataFormMultipleTable();
    }


    public void getAllDataFormMultipleTable() {

        if (issented.equals("true")) {
            senderuser_id = userRequestModel.request_reciver_user_id;
        } else {

            senderuser_id = userRequestModel.request_sender_user_id;


        }
        if (!senderuser_id.equals("no value") && !senderuser_id.equals("")) {


            catLoadingView.setText("Please Wait ..");
            catLoadingView.show(getSupportFragmentManager(), "");


            StringRequest stringRequest = new StringRequest(Request.Method.POST, Comman.GET_ALL_DATA_FROM_MULTIPLE_TABLE_REQUESTS_TABLE_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    System.out.println("myjson is" + response);

                    try {


                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);

                        System.out.println("myjson is" + response);
                        String code = jsonObject.getString("code");
                        if (code.equals("success")) {


                            Gson gson = new Gson();
                            GetAllMultipleTableDataOnClickRequestMODEL getAllMultipleTableDataOnClickRequestMODEL = gson.fromJson(jsonObject.toString(), GetAllMultipleTableDataOnClickRequestMODEL.class);


                            System.out.println("mydatais " + getAllMultipleTableDataOnClickRequestMODEL.email);

                            Glide.with(ShowDetailsOfserRequestsOnClick.this).load(Comman.START_URL + getAllMultipleTableDataOnClickRequestMODEL.user_img_url).placeholder(R.mipmap.ic_launcher).into(userImgViewOnclick);

                            reviewRequestRequestSenderUserNameTxt.setText(userRequestModel.request_sender_user_id);

                            if (issented.equals("true")) {

                                reviewRequestRequestSenderUserNameTxt.setText(userRequestModel.request_reciver_user_id);

                            }

                            loanRequestCodeOnclick.setText(userRequestModel.loan_request_code);
                            phoneOnclick.setText(getAllMultipleTableDataOnClickRequestMODEL.phone);
                            emailOnclick.setText(getAllMultipleTableDataOnClickRequestMODEL.email);
                            timestampOnclick.setText(userRequestModel.request_time_stamp);

                            catLoadingView.dismiss();

                            getAllData();

                            // FancyToast.makeText(ShowDetailsOfserRequestsOnClick.this, "" + jsonObject.getString("message"), FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();


                        } else if (code.equals("failed")) {
                            catLoadingView.dismiss();
                            FancyToast.makeText(ShowDetailsOfserRequestsOnClick.this, "Error gettting data", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();


                        }
                    } catch (JSONException e) {
                        catLoadingView.dismiss();

                        System.out.println("myjson erroris is" + e.getMessage());
                        FancyToast.makeText(ShowDetailsOfserRequestsOnClick.this, "" + e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();


                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    catLoadingView.dismiss();

                    FancyToast.makeText(ShowDetailsOfserRequestsOnClick.this, "Error! is " + error, FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();


                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {


                    Map<String, String> params = new HashMap<String, String>();

                    params.put("user_id", senderuser_id);


                    if (issented.equals("true")) {

                        params.put("issented", "true1");


                    } else {
                        params.put("issented", "false1");


                    }

                    System.out.println("dtaais" + issented + senderuser_id);

                    return params;
                }
            };

            VollySingltonClass.getmInstance(getApplicationContext()).addToRequsetque(stringRequest);


        } else {

            FancyToast.makeText(ShowDetailsOfserRequestsOnClick.this, "Complete all the fields!!!", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

        }


    }


    private void getAllData() {

        catLoadingView.setText("Please Wait ..");
        catLoadingView.show(getSupportFragmentManager(), "");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Comman.getdataforsingleuserwithloanreqcode_REQUESTS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                System.out.println("mydatais1 " + response);

                try {

                    JSONArray jsonArray = new JSONArray(response);


                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);


                        if (jsonObject.getString("code").equals("success")) {

                            Gson gson = new Gson();

                            requestLoanModel = gson.fromJson(jsonObject.toString(), RequestLoanModel.class);

                            catLoadingView.dismiss();
                        } else {
                            catLoadingView.dismiss();

                            Comman.showErrorToast(ShowDetailsOfserRequestsOnClick.this, "No Data Found ");
                        }
                    }

                } catch (Exception e) {
                    catLoadingView.dismiss();
                    System.out.println("i ah error " + e.getMessage());
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Comman.showErrorToast(ShowDetailsOfserRequestsOnClick.this, "Error check your internet connection");
                catLoadingView.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String, String> params = new HashMap<String, String>();
                params.put("loan_request_code", userRequestModel.loan_request_code);

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VollySingltonClass.getmInstance(ShowDetailsOfserRequestsOnClick.this).addToRequsetque(stringRequest);


    }


}
