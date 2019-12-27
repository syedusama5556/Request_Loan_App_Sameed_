/**
 * Created by Usama.
 */

package com.infusiblecoder.loanappsameed.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.infusiblecoder.loanappsameed.R;

import java.util.Calendar;


public class RequestLoanActivity extends AppCompatActivity {

     ImageButton selectdateButton;
     LinearLayout uploadbtnButton;
     Button postarequestButton;




     ConstraintLayout constraintLayoutConstraintLayout;
     TextView userNameTextView;
     TextView loanRequestCodeTextView;
     TextView textViewTextView;
     ConstraintLayout enteramountConstraintLayout;
     EditText enterAmountEditText;
     View line1ConstraintLayout;
     ConstraintLayout enterPurposeConstraintLayout;
     EditText purposeEditText;
     View line1TwoConstraintLayout;
     ConstraintLayout enterCollateralConstraintLayout;
     EditText collateralEditText;
     View line1ThreeConstraintLayout;
     ConstraintLayout enterMarketValueConstraintLayout;
     EditText marketValueEditText;
     View line1FourConstraintLayout;
     ConstraintLayout deudateviewConstraintLayout;
     ConstraintLayout duedateConstraintLayout;
     static TextView dueDateTextView;
     View line1ImageView;



    public static Intent newIntent(Context context) {

        // Fill the created intent with the data you want to be passed to this Activity when it's opened.
        return new Intent(context, RequestLoanActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.request_loan_activity);
        this.init();
    }

    private void init() {




        constraintLayoutConstraintLayout = (ConstraintLayout)  findViewById(R.id.constraint_layout_constraint_layout);
        userNameTextView = (TextView)  findViewById(R.id.user_name_text_view);
        loanRequestCodeTextView = (TextView)  findViewById(R.id.loan_request_code_text_view);
        textViewTextView = (TextView)  findViewById(R.id.text_view_text_view);
        enteramountConstraintLayout = (ConstraintLayout)  findViewById(R.id.enteramount_constraint_layout);
        enterAmountEditText = (EditText)  findViewById(R.id.enter_amount_edit_text);
        line1ConstraintLayout = (View)  findViewById(R.id.line1_constraint_layout);
        enterPurposeConstraintLayout = (ConstraintLayout)  findViewById(R.id.enter_purpose_constraint_layout);
        purposeEditText = (EditText)  findViewById(R.id.purpose_edit_text);
        line1TwoConstraintLayout = (View)  findViewById(R.id.line1_two_constraint_layout);
        enterCollateralConstraintLayout = (ConstraintLayout)  findViewById(R.id.enter_collateral_constraint_layout);
        collateralEditText = (EditText)  findViewById(R.id.collateral_edit_text);
        line1ThreeConstraintLayout = (View)  findViewById(R.id.line1_three_constraint_layout);
        enterMarketValueConstraintLayout = (ConstraintLayout)  findViewById(R.id.enter_market_value_constraint_layout);
        marketValueEditText = (EditText)  findViewById(R.id.market_value_edit_text);
        line1FourConstraintLayout = (View)  findViewById(R.id.line1_four_constraint_layout);
        deudateviewConstraintLayout = (ConstraintLayout)  findViewById(R.id.deudateview_constraint_layout);
        duedateConstraintLayout = (ConstraintLayout)  findViewById(R.id.duedate_constraint_layout);
        dueDateTextView = (TextView)  findViewById(R.id.due_date_text_view);
        line1ImageView = (View)  findViewById(R.id.line1_image_view);






        // Configure SelectDate component
        selectdateButton = this.findViewById(R.id.selectdate_button);
        selectdateButton.setOnClickListener((view) -> {
            this.onSelectDatePressed();
        });

        // Configure UploadBtn component
        uploadbtnButton = this.findViewById(R.id.uploadbtn_button);
        uploadbtnButton.setOnClickListener((view) -> {
            this.onUploadBtnPressed();
        });

        // Configure PostARequest component
        postarequestButton = this.findViewById(R.id.postarequest_button);
        postarequestButton.setOnClickListener((view) -> {
            this.onPostARequestPressed();
        });
    }

    public void onSelectDatePressed() {

        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");

    }

    public void onUploadBtnPressed() {

    }

    public void onPostARequestPressed() {

    }




    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
            dialog.getDatePicker().setMinDate(c.getTimeInMillis());
            return  dialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {

            dueDateTextView.setText(day+"-"+(month+1)+"-"+ year);



        }
    }
}
