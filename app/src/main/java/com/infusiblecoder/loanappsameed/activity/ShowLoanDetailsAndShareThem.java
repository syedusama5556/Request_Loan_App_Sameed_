package com.infusiblecoder.loanappsameed.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.infusiblecoder.loanappsameed.Helpers.Comman;
import com.infusiblecoder.loanappsameed.Helpers.GenericFileProvider;
import com.infusiblecoder.loanappsameed.ModelClasses.RequestLoanModel;
import com.infusiblecoder.loanappsameed.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class ShowLoanDetailsAndShareThem extends AppCompatActivity {
    private TextView recName;
    private TextView recLoanAmount;
    private TextView recPurpose;
    private TextView recBorrower;
    private TextView recCollateral;
    private TextView recMarketValue;
    private TextView recLoanRatio;
    private TextView recAmount;
    private TextView recInterest;
    private TextView recServiceFees;
    private TextView recTotalPayable;
    private RequestLoanModel requestLoanModeldata;
    private ImageView btn_share_layout;
    private TextView txtx_nameplace_holder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_loan_details_and_share_them);


        recName = (TextView) findViewById(R.id.rec_name);
        recLoanAmount = (TextView) findViewById(R.id.rec_loan_amount);
        recPurpose = (TextView) findViewById(R.id.rec_purpose);
        recBorrower = (TextView) findViewById(R.id.rec_borrower);
        recCollateral = (TextView) findViewById(R.id.rec_collateral);
        recMarketValue = (TextView) findViewById(R.id.rec_market_value);
        recLoanRatio = (TextView) findViewById(R.id.rec_loan_ratio);
        recAmount = (TextView) findViewById(R.id.rec_amount);
        recInterest = (TextView) findViewById(R.id.rec_interest);
        recServiceFees = (TextView) findViewById(R.id.rec_service_fees);
        recTotalPayable = (TextView) findViewById(R.id.rec_total_payable);
        btn_share_layout = (ImageView) findViewById(R.id.btn_share_layout);
        txtx_nameplace_holder = (TextView) findViewById(R.id.txtx_nameplace_holder);

        if (getIntent().getSerializableExtra("myrequestdata") != null) {

            requestLoanModeldata = (RequestLoanModel) getIntent().getSerializableExtra("myrequestdata");

            if (getIntent().getStringExtra("ismyloan").equals("false")) {
                txtx_nameplace_holder.setText("User Id");
                recName.setText(requestLoanModeldata.user_id);
            } else {
                recName.setText(requestLoanModeldata.user_full_name);
            }
            recLoanAmount.setText(Comman.getFormatedNumber(requestLoanModeldata.loan_amount));
            recPurpose.setText(requestLoanModeldata.loan_purpose);
            recBorrower.setText(requestLoanModeldata.loan_borrowing_rate);
            recCollateral.setText(requestLoanModeldata.loan_collateral);
            recMarketValue.setText(Comman.getFormatedNumber(requestLoanModeldata.loan_market_value));
            recLoanRatio.setText(requestLoanModeldata.loan_loan_ratio);


            recAmount.setText(Comman.getFormatedNumber(requestLoanModeldata.loan_amount));

            double a = Double.parseDouble(requestLoanModeldata.loan_borrowing_rate);

            System.out.println("intrest is " + a + " " + requestLoanModeldata.loan_amount);
            double b = Double.parseDouble(requestLoanModeldata.loan_amount);


            double intrest = (a * b) / 100;

            System.out.println("intrest is " + intrest);

            recInterest.setText(Comman.getFormatedNumber(round(intrest, 2) + ""));

            double servicefee = intrest * 0.05;
//Todo
//            recServiceFees.setText("" + round(servicefee, 2));
//            double total1 = (Double.parseDouble(requestLoanModeldata.loan_amount) + intrest) + servicefee;
//            recTotalPayable.setText(total1 + "");


            if (getIntent().getStringExtra("ismyloan").equals("false")) {


                recServiceFees.setText("-" + Comman.getFormatedNumber("" + round(servicefee, 2)));

                double total2 = (Double.parseDouble(requestLoanModeldata.loan_amount) + intrest) - servicefee;
                recTotalPayable.setText(Comman.getFormatedNumber(total2 + ""));
            } else {

                recServiceFees.setText("" + Comman.getFormatedNumber("" + round(servicefee, 2)));

                double total1 = (Double.parseDouble(requestLoanModeldata.loan_amount) + intrest) + servicefee;
                recTotalPayable.setText(Comman.getFormatedNumber(total1 + ""));

            }

        }


        btn_share_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeScreenshot();
            }
        });


    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    private void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            openScreenshot(imageFile);
        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void openScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Uri uri = GenericFileProvider.getUriForFile(this, "com.infusiblecoder.loanappsameed.provider", imageFile);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
    }

}
