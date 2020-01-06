package com.infusiblecoder.loanappsameed.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.infusiblecoder.loanappsameed.Helpers.Comman;
import com.infusiblecoder.loanappsameed.Helpers.PDFTools;
import com.infusiblecoder.loanappsameed.Helpers.VollySingltonClass;
import com.infusiblecoder.loanappsameed.ModelClasses.RequestLoanModel;
import com.infusiblecoder.loanappsameed.R;
import com.roger.catloadinglibrary.CatLoadingView;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ShowDetailsOfRequestSelected extends AppCompatActivity {

    RequestLoanModel requestLoanModeldata;

    ImageView verificationStatusImageView;

    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.CALL_PHONE,
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
    };
    TextView borrower_text_view, loanAmountTextView, purposeOfLoanTextView, collateralTextView, marketValueTextView, loanRequestIdTextView, dueDateTextView;
    private ImageView btnSendrequest;
    private ImageView btnCancel;
    private LinearLayout btnSeevehicalDoc;
    private LinearLayout btnSeeownerIdDoc;
    private LinearLayout btnSeeinsuranceDoc;
    private CatLoadingView catLoadingView;


    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details_of_request_selected);

        try {
            requestLoanModeldata = (RequestLoanModel) getIntent().getSerializableExtra("myrequestdata");
        } catch (Exception e) {
            Toast.makeText(this, "error " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        catLoadingView = new CatLoadingView();


        btnSendrequest = findViewById(R.id.btn_sendrequest);
        btnCancel = findViewById(R.id.btn_cancel);

        borrower_text_view = findViewById(R.id.borrower_text_view);
        loanAmountTextView = findViewById(R.id.usd_text_view);
        purposeOfLoanTextView = findViewById(R.id.purpose_of_loan_text_view);
        collateralTextView = findViewById(R.id.collateral_text_view);
        marketValueTextView = findViewById(R.id.market_value_text_view);
        loanRequestIdTextView = findViewById(R.id.loan_request_id_text_view);
        dueDateTextView = findViewById(R.id.due_date_text_view);

        verificationStatusImageView = findViewById(R.id.path_image_view);

        btnSeevehicalDoc = findViewById(R.id.btn_seevehical_doc);
        btnSeeownerIdDoc = findViewById(R.id.btn_seeowner_id_doc);
        btnSeeinsuranceDoc = findViewById(R.id.btn_seeinsurance_doc);

        borrower_text_view.setText(requestLoanModeldata.user_full_name);
        loanAmountTextView.setText("$" + requestLoanModeldata.loan_amount + " USD");
        purposeOfLoanTextView.setText(requestLoanModeldata.loan_purpose);
        collateralTextView.setText(requestLoanModeldata.loan_collateral);

        marketValueTextView.setText(requestLoanModeldata.loan_market_value);
        loanRequestIdTextView.setText(requestLoanModeldata.loan_request_code);
        dueDateTextView.setText(requestLoanModeldata.loan_due_date);


        btnSeevehicalDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDoc(requestLoanModeldata.loan_doc_vehicle_id_url);

            }
        });
        btnSeeownerIdDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDoc(requestLoanModeldata.loan_doc_owner_id_url);

            }
        });
        btnSeeinsuranceDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDoc(requestLoanModeldata.loan_doc_insurance_url);

            }
        });


        btnSendrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onbtnSendrequestPressed();


            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ShowDetailsOfRequestSelected.super.onBackPressed();


            }
        });


    }

    public void onbtnSendrequestPressed() {


        SharedPreferences prefs = getSharedPreferences(Comman.SHAREDPREF_USERDATA, MODE_PRIVATE);
        String fname = prefs.getString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[1], "no value");
        String lname = prefs.getString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[2], "no value");

        String fullname = fname + " " + lname;

        String senderuser_id = prefs.getString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[0], "no value");//"No name defined" is the default value.


        String loan_request_code = requestLoanModeldata.loan_request_code;
        String request_sender_user_name = fullname;
        String request_reciver_user_name = requestLoanModeldata.user_full_name;
        String request_sender_user_id = senderuser_id;

        String request_reciver_user_id = requestLoanModeldata.user_id;


        Date currentTime = Calendar.getInstance().getTime();

        String request_time_stamp = String.valueOf(currentTime);

        System.out.println("error is 12" + fullname + senderuser_id + request_reciver_user_id + loan_request_code);
        String request_is_seen = "false";


        if (!loan_request_code.equals("") && !request_reciver_user_name.equals("") && !request_sender_user_name.equals("") && !request_sender_user_id.equals("") && !request_time_stamp.equals("") && !request_reciver_user_id.equals("")) {


            catLoadingView.setText("Please Wait ..");
            catLoadingView.show(getSupportFragmentManager(), "");


            StringRequest stringRequest = new StringRequest(Request.Method.POST, Comman.SEND_REQUEST_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {


                    try {


                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String code = jsonObject.getString("code");
                        if (code.equals("success")) {

                            catLoadingView.dismiss();
                            FancyToast.makeText(ShowDetailsOfRequestSelected.this, "" + jsonObject.getString("message"), FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

                            startActivity(new Intent(ShowDetailsOfRequestSelected.this,LoanRequestList.class));
                            finish();

                        } else if (code.equals("failed")) {
                            catLoadingView.dismiss();
                            FancyToast.makeText(ShowDetailsOfRequestSelected.this, "" + jsonObject.getString("message"), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();


                        }
                    } catch (JSONException e) {
                        FancyToast.makeText(ShowDetailsOfRequestSelected.this, "" + e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();


                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    catLoadingView.dismiss();

                    FancyToast.makeText(ShowDetailsOfRequestSelected.this, "Error! is " + error, FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();


                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    //       user_img_url = tvUrl.getText().toString();


//                    request_id
//                            loan_request_code
//                    request_sender_user_name
//                            request_reciver_user_name
//                    request_sender_user_id
//                            request_reciver_user_id
//                    request_time_stamp
//                            request_is_seen

                    Map<String, String> params = new HashMap<String, String>();


                    params.put(Comman.TABLE_Requests_ATTRIBUTES[0], loan_request_code);
                    params.put(Comman.TABLE_Requests_ATTRIBUTES[1], request_sender_user_name);
                    params.put(Comman.TABLE_Requests_ATTRIBUTES[2], request_reciver_user_name);
                    params.put(Comman.TABLE_Requests_ATTRIBUTES[3], request_sender_user_id);
                    params.put(Comman.TABLE_Requests_ATTRIBUTES[4], request_reciver_user_id);
                    params.put(Comman.TABLE_Requests_ATTRIBUTES[5], request_time_stamp);
                    params.put(Comman.TABLE_Requests_ATTRIBUTES[6], request_is_seen);


                    return params;
                }
            };

            VollySingltonClass.getmInstance(getApplicationContext()).addToRequsetque(stringRequest);


        } else {

            FancyToast.makeText(ShowDetailsOfRequestSelected.this, "Complete all the fields!!!", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

        }


    }

    public void showDoc(String url) {

        String url1 = Comman.START_URL + url;
        new PDFTools(ShowDetailsOfRequestSelected.this, url1);

    }
}
