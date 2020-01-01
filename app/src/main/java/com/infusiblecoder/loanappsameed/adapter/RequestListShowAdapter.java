package com.infusiblecoder.loanappsameed.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.infusiblecoder.loanappsameed.Helpers.Comman;
import com.infusiblecoder.loanappsameed.ModelClasses.RequestLoanModel;
import com.infusiblecoder.loanappsameed.R;
import com.infusiblecoder.loanappsameed.activity.HomeActivityDrawar;
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
        return new MyRequestViewHolder(LayoutInflater.from(context).inflate(R.layout.rec_requestlist_item_layout,null));
    }

    @Override
    public void onBindViewHolder(@NonNull MyRequestViewHolder holder, int position) {



        Glide.with(context).load(Comman.START_URL+requestLoanModelArrayList.get(position).user_img_url_request).placeholder(R.mipmap.ic_launcher).into(holder.recImgProfile);

        holder.recUsername.setText(requestLoanModelArrayList.get(position).user_full_name);

        holder.recLoanAmount.setText(requestLoanModelArrayList.get(position).loan_amount);

        holder.recImgLoanType.setImageResource(R.drawable.car);

        holder.recCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "clicked "+position, Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return 6;
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

            recCardBtn = (CardView) view.findViewById(R.id.rec_card_btn);
            recImgProfile = (CircularImageView) view.findViewById(R.id.rec_img_profile);
            recUsername = (TextView) view.findViewById(R.id.rec_username);
            recLoanAmount = (TextView) view.findViewById(R.id.rec_loan_amount);
            recBtnShowmore = (TextView) view.findViewById(R.id.rec_btn_showmore);
            recImgLoanType = (ImageView) view.findViewById(R.id.rec_img_loan_type);

        }
    }
}
