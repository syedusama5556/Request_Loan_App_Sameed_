package com.infusiblecoder.loanappsameed.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.infusiblecoder.loanappsameed.Helpers.Comman;
import com.infusiblecoder.loanappsameed.ModelClasses.RequestLoanModel;
import com.infusiblecoder.loanappsameed.R;
import com.infusiblecoder.loanappsameed.activity.ShowDetailsOfRequestSelected;
import com.infusiblecoder.loanappsameed.activity.SubmitAReviewForLoanInReview;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;


public class RequestListShowAdapter extends RecyclerView.Adapter<RequestListShowAdapter.MyRequestViewHolder> {


    Context context;
    ArrayList<RequestLoanModel> requestLoanModelArrayList;
    String ismyappliedloan;

    public RequestListShowAdapter(Context context, ArrayList<RequestLoanModel> requestLoanModelArrayList, String ismyappliedloan) {
        this.context = context;
        this.requestLoanModelArrayList = requestLoanModelArrayList;
        this.ismyappliedloan = ismyappliedloan;
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

        if (ismyappliedloan.equals("true")) {
            holder.rec_status.setText("Status: " + requestLoanModelArrayList.get(position).loan_status);





            switch (requestLoanModelArrayList.get(position).loan_status) {


                case "pending": {
                    holder.rec_relative_color_layout.setBackgroundColor(context.getResources().getColor(R.color.request_load_activity_uploadbtn_button_background_color));
                    break;
                }
                case "review": {
                    holder.rec_relative_color_layout.setBackgroundColor(context.getResources().getColor(R.color.request_load_activity_uploadbtn_button_background_color));
                    break;
                }
                case "rejected": {
                    holder.rec_relative_color_layout.setBackgroundColor(context.getResources().getColor(R.color.lender_review_page_activity_reject_button_text_color));
                    break;
                }
                case "approved": {
                    holder.rec_relative_color_layout.setBackgroundColor(context.getResources().getColor(R.color.borrower_reject_page_activity_repost_button_text_color));
                    break;
                }
                case "completed": {
                    holder.rec_relative_color_layout.setBackgroundColor(context.getResources().getColor(R.color.borrower_reject_page_activity_repost_button_text_color));
                    break;
                }


                default: {

                    break;
                }
            }




            holder.recCardBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, SubmitAReviewForLoanInReview.class);
                    RequestLoanModel requestLoanModel = requestLoanModelArrayList.get(position);
                    i.putExtra("myrequestdata", requestLoanModel);
                    context.startActivity(i);
                }
            });





        } else {
            holder.rec_status.setText("Status: " + requestLoanModelArrayList.get(position).loan_status);

            if (requestLoanModelArrayList.get(position).loan_status.equals(Comman.LOAN_Status[3])) {
                holder.rec_relative_color_layout.setBackgroundColor(context.getResources().getColor(R.color.borrower_reject_page_activity_repost_button_text_color));
            }

            holder.recCardBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, ShowDetailsOfRequestSelected.class);
                    RequestLoanModel requestLoanModel = requestLoanModelArrayList.get(position);
                    i.putExtra("myrequestdata", requestLoanModel);
                    context.startActivity(i);
                }
            });


        }

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
        TextView rec_status;
        ImageView recImgLoanType;
        RelativeLayout rec_relative_color_layout;


        public MyRequestViewHolder(@NonNull View view) {
            super(view);

            rec_relative_color_layout = view.findViewById(R.id.rec_relative_color_layout);
            rec_status = view.findViewById(R.id.rec_status);
            recCardBtn = view.findViewById(R.id.rec_card_btn);
            recImgProfile = view.findViewById(R.id.rec_img_profile);
            recUsername = view.findViewById(R.id.rec_username);
            recLoanAmount = view.findViewById(R.id.rec_loan_amount);
            recBtnShowmore = view.findViewById(R.id.rec_btn_showmore);
            recImgLoanType = view.findViewById(R.id.rec_img_loan_type);

        }
    }
}
