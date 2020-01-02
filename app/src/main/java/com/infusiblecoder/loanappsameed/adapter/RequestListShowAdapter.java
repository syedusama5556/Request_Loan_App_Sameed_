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

import com.bumptech.glide.Glide;
import com.infusiblecoder.loanappsameed.Helpers.Comman;
import com.infusiblecoder.loanappsameed.ModelClasses.RequestLoanModel;
import com.infusiblecoder.loanappsameed.R;
import com.infusiblecoder.loanappsameed.activity.ShowDetailsOfRequestSelected;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;


public class RequestListShowAdapter extends RecyclerView.Adapter<RequestListShowAdapter.MyRequestViewHolder> {


    Context context;
    ArrayList<RequestLoanModel> requestLoanModelArrayList;

    public RequestListShowAdapter(Context context, ArrayList<RequestLoanModel> requestLoanModelArrayList) {
        this.context = context;
        this.requestLoanModelArrayList = requestLoanModelArrayList;
    }

    @NonNull
    @Override
    public MyRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyRequestViewHolder(LayoutInflater.from(context).inflate(R.layout.rec_requestlist_item_layout, null));
    }

    @Override
    public void onBindViewHolder(@NonNull MyRequestViewHolder holder, int position) {


        Glide.with(context).load(Comman.START_URL + requestLoanModelArrayList.get(position).user_img_url_request).placeholder(R.mipmap.ic_launcher).into(holder.recImgProfile);

        holder.recUsername.setText("Name: " + requestLoanModelArrayList.get(position).user_full_name);

        holder.recLoanAmount.setText("Loan Amount: $" + requestLoanModelArrayList.get(position).loan_amount);


        switch (requestLoanModelArrayList.get(position).loan_type) {

            case "personal loan": {
                holder.recImgLoanType.setImageResource(R.drawable.ic_person_rec);
                break;
            }
            case "car loan": {
                holder.recImgLoanType.setImageResource(R.drawable.ic_car_rec);
                break;
            }
            case "commercial loan": {
                holder.recImgLoanType.setImageResource(R.drawable.commercial_buldings);
                break;
            }
            case "travel loan": {
                holder.recImgLoanType.setImageResource(R.drawable.plane);
                break;
            }
            case "house loan": {
                holder.recImgLoanType.setImageResource(R.drawable.home_1);
                break;
            }
            case "tax loan": {
                holder.recImgLoanType.setImageResource(R.drawable.mask_group_1);
                break;
            }
            case "other loan": {
                holder.recImgLoanType.setImageResource(R.drawable.ic_help_other_rec);
                break;
            }
            default: {
                holder.recImgLoanType.setImageResource(R.drawable.ic_help_other_rec);
                break;
            }
        }


        holder.recBtnShowmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, ShowDetailsOfRequestSelected.class);
                RequestLoanModel requestLoanModel = requestLoanModelArrayList.get(position);
                i.putExtra("myrequestdata", requestLoanModel);
                context.startActivity(i);


            }
        });


    }

    @Override
    public int getItemCount() {
        return requestLoanModelArrayList.size();
    }

    class MyRequestViewHolder extends RecyclerView.ViewHolder {
        CardView recCardBtn;
        CircularImageView recImgProfile;
        TextView recUsername;
        TextView recLoanAmount;
        TextView recBtnShowmore;
        ImageView recImgLoanType;


        public MyRequestViewHolder(@NonNull View view) {
            super(view);

            recCardBtn = view.findViewById(R.id.rec_card_btn);
            recImgProfile = view.findViewById(R.id.rec_img_profile);
            recUsername = view.findViewById(R.id.rec_username);
            recLoanAmount = view.findViewById(R.id.rec_loan_amount);
            recBtnShowmore = view.findViewById(R.id.rec_btn_showmore);
            recImgLoanType = view.findViewById(R.id.rec_img_loan_type);

        }
    }
}
