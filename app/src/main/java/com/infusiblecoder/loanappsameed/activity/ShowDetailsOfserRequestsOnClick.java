package com.infusiblecoder.loanappsameed.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.infusiblecoder.loanappsameed.Helpers.Comman;
import com.infusiblecoder.loanappsameed.Helpers.VollySingltonClass;
import com.infusiblecoder.loanappsameed.ModelClasses.GetAllMultipleTableDataOnClickRequestMODEL;
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

    String Usermail = "recipient@example.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details_ofser_requests_on_click);


        try {
            userRequestModel = (UserRequestModel) getIntent().getSerializableExtra("mynotificationsdata");
        } catch (Exception e) {
            Toast.makeText(this, "error " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        catLoadingView = new CatLoadingView();


        userImgViewOnclick = (CircularImageView) findViewById(R.id.user_img_view_onclick);
        reviewRequestRequestSenderUserNameTxt = (TextView) findViewById(R.id.review_request_request_sender_user_name_txt);
        verificationStatusImageView = (ImageView) findViewById(R.id.path_image_view);
        loanRequestCodeOnclick = (TextView) findViewById(R.id.loan_request_code_onclick);
        phoneOnclick = (TextView) findViewById(R.id.phone_onclick);
        emailOnclick = (TextView) findViewById(R.id.email_onclick);
        timestampOnclick = (TextView) findViewById(R.id.timestamp_onclick);
        btnCallUser = (ImageView) findViewById(R.id.btn_call_user);
        btnSendMailUser = (ImageView) findViewById(R.id.btn_send_mail_user);


        btnCallUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_CALL);

                intent.setData(Uri.parse("tel:" + phoneOnclick.getText().toString()));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    Activity#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for Activity#requestPermissions for more details.
                        return;
                    }
                }
                startActivity(intent);

            }
        });

        btnSendMailUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{emailOnclick.getText().toString()});
                i.putExtra(Intent.EXTRA_SUBJECT, "Request");
                i.putExtra(Intent.EXTRA_TEXT   , "You Sent Me A Request ");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(ShowDetailsOfserRequestsOnClick.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }


            }
        });

        getAllDataFormMultipleTable();
    }


    public void getAllDataFormMultipleTable() {


        String senderuser_id = userRequestModel.request_sender_user_id;

        if (!senderuser_id.equals("no value") && !senderuser_id.equals("")) {


            catLoadingView.setText("Please Wait ..");
            catLoadingView.show(getSupportFragmentManager(), "");


            StringRequest stringRequest = new StringRequest(Request.Method.POST, Comman.GET_ALL_DATA_FROM_MULTIPLE_TABLE_REQUESTS_TABLE_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {


                    try {


                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String code = jsonObject.getString("code");
                        if (code.equals("success")) {


                            Gson gson = new Gson();
                            GetAllMultipleTableDataOnClickRequestMODEL getAllMultipleTableDataOnClickRequestMODEL = gson.fromJson(jsonObject.toString(), GetAllMultipleTableDataOnClickRequestMODEL.class);


                            System.out.println("mydatais "+getAllMultipleTableDataOnClickRequestMODEL.request_time_stamp);

                            Glide.with(ShowDetailsOfserRequestsOnClick.this).load(Comman.START_URL+getAllMultipleTableDataOnClickRequestMODEL.user_img_url).placeholder(R.mipmap.ic_launcher).into(userImgViewOnclick);


                            reviewRequestRequestSenderUserNameTxt.setText(userRequestModel.request_sender_user_name);
                            loanRequestCodeOnclick.setText(userRequestModel.loan_request_code);
                            phoneOnclick.setText(getAllMultipleTableDataOnClickRequestMODEL.phone);
                            emailOnclick.setText(getAllMultipleTableDataOnClickRequestMODEL.email);
                            timestampOnclick.setText(userRequestModel.request_time_stamp);

                            catLoadingView.dismiss();

                           // FancyToast.makeText(ShowDetailsOfserRequestsOnClick.this, "" + jsonObject.getString("message"), FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();


                        } else if (code.equals("failed")) {
                            catLoadingView.dismiss();
                            FancyToast.makeText(ShowDetailsOfserRequestsOnClick.this, "Error gettting data" , FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();


                        }
                    } catch (JSONException e) {
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

                    return params;
                }
            };

            VollySingltonClass.getmInstance(getApplicationContext()).addToRequsetque(stringRequest);


        } else {

            FancyToast.makeText(ShowDetailsOfserRequestsOnClick.this, "Complete all the fields!!!", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

        }


    }

}
