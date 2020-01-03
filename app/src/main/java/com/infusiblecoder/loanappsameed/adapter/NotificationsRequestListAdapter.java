package com.infusiblecoder.loanappsameed.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.infusiblecoder.loanappsameed.ModelClasses.UserRequestModel;
import com.infusiblecoder.loanappsameed.R;
import com.infusiblecoder.loanappsameed.activity.ShowDetailsOfRequestSelected;

import java.util.ArrayList;


public class NotificationsRequestListAdapter extends RecyclerView.Adapter<NotificationsRequestListAdapter.MyRequestViewHolder> {


    Context context;
    ArrayList<UserRequestModel> userRequestModelArrayList;

    public NotificationsRequestListAdapter(Context context, ArrayList<UserRequestModel> userRequestModelArrayList) {
        this.context = context;
        this.userRequestModelArrayList = userRequestModelArrayList;
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


        if (userRequestModelArrayList.get(position).request_is_seen.equals("false")) {
            holder.rec_img_message.setImageResource(R.drawable.ic_message_closed_envelope);
        } else if (userRequestModelArrayList.get(position).request_is_seen.equals("true")) {
            holder.rec_img_message.setImageResource(R.drawable.ic_messageopen);
        }

        holder.recBtnShowmoreNotificationRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, ShowDetailsOfRequestSelected.class);
                UserRequestModel userRequestModel = userRequestModelArrayList.get(position);
                i.putExtra("mynotificationsdata", userRequestModel);
                context.startActivity(i);


            }
        });


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

            recCardBtn = (CardView) view.findViewById(R.id.rec_card_btn);
            rec_img_message = (ImageView) view.findViewById(R.id.rec_img_message);
            recSenderUsername = (TextView) view.findViewById(R.id.rec_sender_username);
            recLoanRequestCode = (TextView) view.findViewById(R.id.rec_loan_request_code);
            recTimestampNotificationRequests = (TextView) view.findViewById(R.id.rec_timestamp_notification_requests);
            recBtnShowmoreNotificationRequests = (TextView) view.findViewById(R.id.rec_btn_showmore_notification_requests);

        }
    }
}
