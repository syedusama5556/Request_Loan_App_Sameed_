package com.infusiblecoder.loanappsameed.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.infusiblecoder.loanappsameed.Helpers.Comman;
import com.infusiblecoder.loanappsameed.Helpers.VollySingltonClass;
import com.infusiblecoder.loanappsameed.ModelClasses.UserRequestModel;
import com.infusiblecoder.loanappsameed.R;
import com.infusiblecoder.loanappsameed.activity.ShowDetailsOfserRequestsOnClick;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class NotificationsRequestListAdapter extends RecyclerView.Adapter<NotificationsRequestListAdapter.MyRequestViewHolder> {


    Context context;
    ArrayList<UserRequestModel> userRequestModelArrayList;
    String isNotification;

    public NotificationsRequestListAdapter(Context context, ArrayList<UserRequestModel> userRequestModelArrayList, String isNotification) {
        this.context = context;
        this.userRequestModelArrayList = userRequestModelArrayList;
        this.isNotification = isNotification;
    }

    @NonNull
    @Override
    public MyRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyRequestViewHolder(LayoutInflater.from(context).inflate(R.layout.rec_notification_requestlist_item_layout, null));
    }

    @Override
    public void onBindViewHolder(@NonNull MyRequestViewHolder holder, int position) {


        holder.recSenderUsername.setText("Name: " + userRequestModelArrayList.get(position).request_sender_user_name);

        holder.recLoanRequestCode.setText("Loan Code: " + userRequestModelArrayList.get(position).loan_request_code);

        holder.recTimestampNotificationRequests.setText("" + userRequestModelArrayList.get(position).request_time_stamp);


        if (isNotification.equals("true")) {

            if (userRequestModelArrayList.get(position).request_is_seen.equals("false")) {
                holder.rec_img_message.setImageResource(R.drawable.ic_message_closed_envelope);
            } else if (userRequestModelArrayList.get(position).request_is_seen.equals("true")) {
                holder.rec_img_message.setImageResource(R.drawable.ic_messageopen);
            }

            holder.recCardBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (!TextUtils.isEmpty(userRequestModelArrayList.get(position).request_reciver_user_id)) {


                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Comman.UPDATE_updatenotificationseentotrue_URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {


                                try {
                                    JSONArray jsonArray = new JSONArray(response);

                                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                                    String code = jsonObject.getString("code");

                                    System.out.println("mysre +" + jsonObject);

                                    if (code.equals("failed")) {


                                        //  Comman.showErrorToast(context, "Failed " + jsonObject.getString("message"));


                                        Intent i = new Intent(context, ShowDetailsOfserRequestsOnClick.class);
                                        UserRequestModel userRequestModel = userRequestModelArrayList.get(position);
                                        i.putExtra("mynotificationsdata", userRequestModel);
                                        i.putExtra("issented", "false");
                                        context.startActivity(i);

                                    } else {


                                        // Comman.showSucdessToast(context, "" + jsonObject.getString("message"));


                                        Intent i = new Intent(context, ShowDetailsOfserRequestsOnClick.class);
                                        UserRequestModel userRequestModel = userRequestModelArrayList.get(position);
                                        i.putExtra("mynotificationsdata", userRequestModel);
                                        i.putExtra("issented", "false");
                                        context.startActivity(i);


                                    }

                                } catch (JSONException e) {
                                    Comman.showErrorToast(context, "error is " + e.getMessage());

                                }


                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Comman.showErrorToast(context, "error is " + error);


                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {


                                String id = userRequestModelArrayList.get(position).request_id;//"No name defined" is the default value.

                                Map<String, String> params = new HashMap<String, String>();
                                params.put("request_id", id);

                                return params;

                            }
                        };

                        VollySingltonClass.getmInstance(context).addToRequsetque(stringRequest);


                    } else {
                        Comman.showErrorToast(context, "Enter Missing Fields");
                    }


                }
            });

        } else if (isNotification.equals("sent")) {

            holder.recSenderUsername.setText("Name: " + userRequestModelArrayList.get(position).request_reciver_user_name);


            holder.rec_img_message.setImageResource(R.drawable.ic_reply_black_24dp);


            holder.recCardBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (!TextUtils.isEmpty(userRequestModelArrayList.get(position).request_reciver_user_id)) {


                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Comman.UPDATE_updatenotificationseentotrue_URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {


                                try {
                                    JSONArray jsonArray = new JSONArray(response);

                                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                                    String code = jsonObject.getString("code");

                                    System.out.println("mysre +" + jsonObject);

                                    if (code.equals("failed")) {


                                        //  Comman.showErrorToast(context, "Failed " + jsonObject.getString("message"));


                                        Intent i = new Intent(context, ShowDetailsOfserRequestsOnClick.class);
                                        UserRequestModel userRequestModel = userRequestModelArrayList.get(position);
                                        i.putExtra("mynotificationsdata", userRequestModel);
                                        i.putExtra("issented", "true");
                                        context.startActivity(i);

                                    } else {


                                        // Comman.showSucdessToast(context, "" + jsonObject.getString("message"));


                                        Intent i = new Intent(context, ShowDetailsOfserRequestsOnClick.class);
                                        UserRequestModel userRequestModel = userRequestModelArrayList.get(position);
                                        i.putExtra("mynotificationsdata", userRequestModel);
                                        i.putExtra("issented", "true");
                                        context.startActivity(i);


                                    }

                                } catch (JSONException e) {
                                    Comman.showErrorToast(context, "error is " + e.getMessage());

                                }


                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Comman.showErrorToast(context, "error is " + error);


                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {


                                String id = userRequestModelArrayList.get(position).request_id;//"No name defined" is the default value.

                                Map<String, String> params = new HashMap<String, String>();
                                params.put("request_id", id);

                                return params;

                            }
                        };

                        VollySingltonClass.getmInstance(context).addToRequsetque(stringRequest);


                    } else {
                        Comman.showErrorToast(context, "Enter Missing Fields");
                    }


                }
            });

        } else if (isNotification.equals("recived")) {
            if (userRequestModelArrayList.get(position).request_is_seen.equals("false")) {
                holder.rec_img_message.setImageResource(R.drawable.ic_message_closed_envelope);
            } else if (userRequestModelArrayList.get(position).request_is_seen.equals("true")) {
                holder.rec_img_message.setImageResource(R.drawable.ic_messageopen);
            }

            holder.recCardBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (!TextUtils.isEmpty(userRequestModelArrayList.get(position).request_reciver_user_id)) {


                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Comman.UPDATE_updatenotificationseentotrue_URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {


                                try {
                                    JSONArray jsonArray = new JSONArray(response);

                                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                                    String code = jsonObject.getString("code");

                                    System.out.println("mysre +" + jsonObject);

                                    if (code.equals("failed")) {


                                        //  Comman.showErrorToast(context, "Failed " + jsonObject.getString("message"));


                                        Intent i = new Intent(context, ShowDetailsOfserRequestsOnClick.class);
                                        UserRequestModel userRequestModel = userRequestModelArrayList.get(position);
                                        i.putExtra("mynotificationsdata", userRequestModel);
                                        i.putExtra("issented", "false");
                                        context.startActivity(i);

                                    } else {


                                        // Comman.showSucdessToast(context, "" + jsonObject.getString("message"));


                                        Intent i = new Intent(context, ShowDetailsOfserRequestsOnClick.class);
                                        UserRequestModel userRequestModel = userRequestModelArrayList.get(position);
                                        i.putExtra("mynotificationsdata", userRequestModel);
                                        i.putExtra("issented", "false");
                                        context.startActivity(i);


                                    }

                                } catch (JSONException e) {
                                    Comman.showErrorToast(context, "error is " + e.getMessage());

                                }


                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Comman.showErrorToast(context, "error is " + error);


                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {


                                String id = userRequestModelArrayList.get(position).request_id;//"No name defined" is the default value.

                                Map<String, String> params = new HashMap<String, String>();
                                params.put("request_id", id);

                                return params;

                            }
                        };

                        VollySingltonClass.getmInstance(context).addToRequsetque(stringRequest);


                    } else {
                        Comman.showErrorToast(context, "Enter Missing Fields");
                    }


                }
            });

        }


    }

    @Override
    public int getItemCount() {
        return userRequestModelArrayList.size();
    }

    class MyRequestViewHolder extends RecyclerView.ViewHolder {


        private CardView recCardBtn;
        private ImageView rec_img_message;
        private TextView recSenderUsername;
        private TextView recLoanRequestCode;
        private TextView recTimestampNotificationRequests;
        private TextView recBtnShowmoreNotificationRequests;


        public MyRequestViewHolder(@NonNull View view) {
            super(view);

            recCardBtn = view.findViewById(R.id.rec_card_btn);
            rec_img_message = view.findViewById(R.id.rec_img_message);
            recSenderUsername = view.findViewById(R.id.rec_sender_username);
            recLoanRequestCode = view.findViewById(R.id.rec_loan_request_code);
            recTimestampNotificationRequests = view.findViewById(R.id.rec_timestamp_notification_requests);
            recBtnShowmoreNotificationRequests = view.findViewById(R.id.rec_btn_showmore_notification_requests);

        }
    }
}
