package com.infusiblecoder.loanappsameed.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.infusiblecoder.loanappsameed.Helpers.Comman;
import com.infusiblecoder.loanappsameed.Helpers.VollySingltonClass;
import com.infusiblecoder.loanappsameed.ModelClasses.RequestLoanModel;
import com.infusiblecoder.loanappsameed.R;
import com.infusiblecoder.loanappsameed.adapter.RequestListShowAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoanRequestList extends AppCompatActivity {

    RequestListShowAdapter requestListShowAdapter;

    RecyclerView recyclerView;

    ArrayList<RequestLoanModel> requestLoanModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_request_list);
        recyclerView = findViewById(R.id.rec_view_request_list);

        requestLoanModelArrayList = new ArrayList<RequestLoanModel>();

        requestListShowAdapter = new RequestListShowAdapter(LoanRequestList.this, requestLoanModelArrayList);

        recyclerView.setLayoutManager(new LinearLayoutManager(LoanRequestList.this));
        recyclerView.setAdapter(requestListShowAdapter);


        getAllData();
    }

    private void getAllData() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Comman.GET_ALL_REQUEST_TABLE_DATA_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {

                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);


//                        String id = " ", user_id = "",phone="", title = "", description = "", timebefore = "", date = "", time = "", pendingIntentId = "", calanderid2 = "";
//
//                        id = jsonObject.getString("id");
//                        user_id = jsonObject.getString("user_id");
//                        title = jsonObject.getString("title");
//                        description = jsonObject.getString("description");
//                        timebefore = jsonObject.getString("timebefore");
//                        pendingIntentId = jsonObject.getString("pendingintentid");
//                        calanderid2 = jsonObject.getString("calanderid");
//                        phone=jsonObject.getString("phone");


                        Gson gson = new Gson();
                        RequestLoanModel userTableData = gson.fromJson(jsonObject.toString(), RequestLoanModel.class);

                        requestLoanModelArrayList.add(userTableData);
                        requestListShowAdapter.notifyDataSetChanged();

                    }


                } catch (Exception e) {
                    System.out.println("i ah error " + e.getMessage());
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();


                return params;
            }
        };
        VollySingltonClass.getmInstance(getApplicationContext()).addToRequsetque(stringRequest);


        requestListShowAdapter.notifyDataSetChanged();

    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), HomeActivityDrawar.class));
        finish();
    }
}
