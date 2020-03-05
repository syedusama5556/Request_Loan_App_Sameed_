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
import com.infusiblecoder.loanappsameed.R;
import com.infusiblecoder.loanappsameed.adapter.RequestListShowAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoanRequestListFragment extends Fragment {

    RequestListShowAdapter requestListShowAdapter;

    RecyclerView recyclerView;

    ArrayList<RequestLoanModel> requestLoanModelArrayList;
    private LinearLayout no_item_layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loan_request_list, container, false);
        recyclerView = view.findViewById(R.id.rec_view_request_list21);
        no_item_layout = view.findViewById(R.id.no_item_layout);

        requestLoanModelArrayList = new ArrayList<RequestLoanModel>();

        requestListShowAdapter = new RequestListShowAdapter(getActivity(), requestLoanModelArrayList, "false");

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(requestListShowAdapter);


        getAllData();


        return view;
    }


    private void getAllData() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Comman.GET_ALL_REQUEST_TABLE_DATA_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                System.out.println("i ah error " + response);
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

                            Comman.showErrorToast(getActivity(), "No Data Found");
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


}
