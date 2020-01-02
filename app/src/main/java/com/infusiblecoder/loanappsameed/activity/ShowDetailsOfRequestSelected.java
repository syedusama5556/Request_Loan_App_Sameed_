package com.infusiblecoder.loanappsameed.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import com.infusiblecoder.loanappsameed.Helpers.Comman;
import com.infusiblecoder.loanappsameed.Helpers.PDFTools;
import com.infusiblecoder.loanappsameed.ModelClasses.RequestLoanModel;
import com.infusiblecoder.loanappsameed.R;

public class ShowDetailsOfRequestSelected extends AppCompatActivity {

    RequestLoanModel requestLoanModeldata;

    ImageView verificationStatusImageView;

    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_PHONE_STATE

    };
     TextView loanAmountTextView,purposeOfLoanTextView,collateralTextView,marketValueTextView,loanRequestIdTextView,dueDateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details_of_request_selected);

        try {
            requestLoanModeldata = (RequestLoanModel) getIntent().getSerializableExtra("myrequestdata");
        } catch (Exception e) {
            Toast.makeText(this, "error " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }




        loanAmountTextView = (TextView) findViewById(R.id.usd_text_view);
        purposeOfLoanTextView = (TextView)findViewById(R.id.purpose_of_loan_text_view);
        collateralTextView = (TextView) findViewById(R.id.collateral_text_view);
        marketValueTextView = (TextView) findViewById(R.id.market_value_text_view);
        loanRequestIdTextView = (TextView) findViewById(R.id.loan_request_id_text_view);
        dueDateTextView = (TextView)findViewById(R.id.due_date_text_view);

        verificationStatusImageView = (ImageView) findViewById(R.id.path_image_view);



        loanAmountTextView.setText(requestLoanModeldata.loan_amount);
        purposeOfLoanTextView.setText(requestLoanModeldata.loan_purpose);
        collateralTextView.setText(requestLoanModeldata.loan_collateral);

        marketValueTextView.setText(requestLoanModeldata.loan_market_value);
        loanRequestIdTextView.setText(requestLoanModeldata.loan_request_code);
        dueDateTextView.setText(requestLoanModeldata.loan_due_date);



    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public void show(View view) {

        String url = Comman.START_URL + requestLoanModeldata.loan_doc_owner_id_url;
        Toast.makeText(this, "" + url, Toast.LENGTH_SHORT).show();
        new PDFTools(ShowDetailsOfRequestSelected.this, url);

    }
}
