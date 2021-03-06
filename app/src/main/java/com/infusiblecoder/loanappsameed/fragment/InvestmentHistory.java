package com.infusiblecoder.loanappsameed.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
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
import com.infusiblecoder.loanappsameed.ModelClasses.RequestLoanModel;
import com.infusiblecoder.loanappsameed.ModelClasses.UserRequestModel;
import com.infusiblecoder.loanappsameed.R;
import com.infusiblecoder.loanappsameed.adapter.NotificationsRequestListAdapter;
import com.infusiblecoder.loanappsameed.adapter.RequestListShowAdapter_for_my_investments;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class InvestmentHistory extends Fragment {


    ArrayList<UserRequestModel> userRequestModelArrayList;
    private RecyclerView recyclerView;
    private NotificationsRequestListAdapter notificationsRequestListAdapter;
    private LinearLayout no_item_layout;
    private LinearLayout linlayoutHide;
    private RecyclerView recViewRequestListForMyloans;
    private RequestListShowAdapter_for_my_investments requestListShowAdapter;
    private ArrayList<RequestLoanModel> requestLoanModelArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_investment_history, container, false);

        linlayoutHide = view.findViewById(R.id.linlayout_hide);
        recViewRequestListForMyloans = view.findViewById(R.id.rec_view_request_list_for_myloans);

        no_item_layout = view.findViewById(R.id.no_item_layout);
        recyclerView = view.findViewById(R.id.rec_view_request_list);

        userRequestModelArrayList = new ArrayList<>();

        notificationsRequestListAdapter = new NotificationsRequestListAdapter(getActivity(), userRequestModelArrayList, "recived");

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(notificationsRequestListAdapter);
        recyclerView.setNestedScrollingEnabled(false);


        requestLoanModelArrayList = new ArrayList<RequestLoanModel>();

        requestListShowAdapter = new RequestListShowAdapter_for_my_investments(getActivity(), requestLoanModelArrayList, "false");

        recViewRequestListForMyloans.setLayoutManager(new LinearLayoutManager(getActivity()));
        recViewRequestListForMyloans.setAdapter(requestListShowAdapter);
        recViewRequestListForMyloans.setNestedScrollingEnabled(false);

        getAllData();
        getAllDatamyloan();

        return view;
    }


    private void getAllData() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Comman.GET_ALL_REQUEST_TABLE_Single_User_DATA_URL_lender, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("rescccccccc " + response);

                try {

                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        if (jsonObject.getString("code").equals("success")) {

                            Gson gson = new Gson();
                            RequestLoanModel userTableData = gson.fromJson(jsonObject.toString(), RequestLoanModel.class);


                            requestLoanModelArrayList.add(userTableData);
                            requestListShowAdapter.notifyDataSetChanged();

                            no_item_layout.setVisibility(View.GONE);

                        } else {
                            no_item_layout.setVisibility(View.VISIBLE);

                            Comman.showErrorToast(getActivity(), "No Data Found for applied loans");
                        }

                    }


                } catch (Exception e) {
                    no_item_layout.setVisibility(View.VISIBLE);
                    System.out.println("i ah error " + e.getMessage());
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                no_item_layout.setVisibility(View.VISIBLE);
                Comman.showErrorToast(getActivity(), "Error check your internet connection");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                SharedPreferences prefs = getActivity().getSharedPreferences(Comman.SHAREDPREF_USERDATA, MODE_PRIVATE);
                String id = prefs.getString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[0], "no value");//"No name defined" is the default value.

                if (!id.equals("no value") && !id.equals("")) {


                } else {


                    Comman.showErrorToast(getActivity(), "error is getting id");

                }

                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", id);

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VollySingltonClass.getmInstance(getActivity()).addToRequsetque(stringRequest);


        requestListShowAdapter.notifyDataSetChanged();

    }

    private void getAllDatamyloan() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Comman.GET_ALL_Recived_REQUESTS_TABLE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {

                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        if (jsonObject.getString("code").equals("data_success")) {

                            Gson gson = new Gson();
                            UserRequestModel userTableData = gson.fromJson(jsonObject.toString(), UserRequestModel.class);

                            System.out.println("mydata is " + userTableData.request_time_stamp);
                            if (!userTableData.req_status.equals("pending") && !userTableData.req_status.equals("rejected")) {
                                userRequestModelArrayList.add(userTableData);
                                notificationsRequestListAdapter.notifyDataSetChanged();
                                no_item_layout.setVisibility(View.GONE);

                            } else {

                                no_item_layout.setVisibility(View.VISIBLE);
                            }
                        } else {
                            no_item_layout.setVisibility(View.VISIBLE);
                            Comman.showErrorToast(getActivity(), "No Data Found For Recived Requested");
                        }
                    }


                } catch (Exception e) {
                    no_item_layout.setVisibility(View.VISIBLE);
                    System.out.println("i ah error " + e.getMessage());
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                no_item_layout.setVisibility(View.VISIBLE);
                Comman.showErrorToast(getActivity(), "Error check your internet connection");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                SharedPreferences prefs = getActivity().getSharedPreferences(Comman.SHAREDPREF_USERDATA, MODE_PRIVATE);
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
        VollySingltonClass.getmInstance(getActivity()).addToRequsetque(stringRequest);


        // requestListShowAdapter.notifyDataSetChanged();

    }


}
