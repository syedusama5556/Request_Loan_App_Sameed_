package com.infusiblecoder.loanappsameed.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.infusiblecoder.loanappsameed.Helpers.Comman;
import com.infusiblecoder.loanappsameed.Helpers.VollySingltonClass;
import com.infusiblecoder.loanappsameed.ModelClasses.UserRequestModel;
import com.infusiblecoder.loanappsameed.R;
import com.infusiblecoder.loanappsameed.adapter.NotificationsRequestListAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NotificationsListUserRequests extends AppCompatActivity {


    ArrayList<UserRequestModel> userRequestModelArrayList;
    private RecyclerView recyclerView;
    private NotificationsRequestListAdapter notificationsRequestListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_list_user_requests);

        recyclerView = findViewById(R.id.rec_view_request_list1);

        userRequestModelArrayList = new ArrayList<>();

        notificationsRequestListAdapter = new NotificationsRequestListAdapter(NotificationsListUserRequests.this, userRequestModelArrayList);

        recyclerView.setLayoutManager(new LinearLayoutManager(NotificationsListUserRequests.this));
        recyclerView.setAdapter(notificationsRequestListAdapter);


        getAllData();
    }


    private void getAllData() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Comman.GET_ALL_NOTIFICATIONS_REQUESTS_TABLE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {

                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);


                        Gson gson = new Gson();
                        UserRequestModel userTableData = gson.fromJson(jsonObject.toString(), UserRequestModel.class);

                        System.out.println("mydata is " + userTableData.request_time_stamp);

                        userRequestModelArrayList.add(userTableData);
                        notificationsRequestListAdapter.notifyDataSetChanged();

                    }


                } catch (Exception e) {
                    System.out.println("i ah error " + e.getMessage());
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Comman.showErrorToast(getApplicationContext(), "Error check your internet connection");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                SharedPreferences prefs = getSharedPreferences(Comman.SHAREDPREF_USERDATA, MODE_PRIVATE);
                String uid = prefs.getString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[0], "no value");//"No name defined" is the default value.


                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", uid);

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VollySingltonClass.getmInstance(getApplicationContext()).addToRequsetque(stringRequest);


        // requestListShowAdapter.notifyDataSetChanged();

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), HomeActivityDrawar.class));
        finish();
    }
}
