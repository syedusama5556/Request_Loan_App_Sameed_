package com.infusiblecoder.loanappsameed.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.infusiblecoder.loanappsameed.BuildConfig;
import com.infusiblecoder.loanappsameed.Helpers.Comman;
import com.infusiblecoder.loanappsameed.Helpers.GenericFileProvider;
import com.infusiblecoder.loanappsameed.Helpers.InputStreamReader;
import com.infusiblecoder.loanappsameed.ModelClasses.RequestLoanModel;
import com.infusiblecoder.loanappsameed.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;

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
    private TextView recTotalPayable, rec_loan_duration;
    private RequestLoanModel requestLoanModeldata;
    private ImageView btn_share_layout;
    private TextView txtx_nameplace_holder, tptalpayabletdxt;

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_loan_details_and_share_them);

        tptalpayabletdxt = (TextView) findViewById(R.id.tptalpayabletdxt);
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
        rec_loan_duration = (TextView) findViewById(R.id.rec_loan_duration);
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

            rec_loan_duration.setText(requestLoanModeldata.loan_duration + "");

            System.out.println("intrest isccccccc " + requestLoanModeldata.loan_borrowing_rate);
            recAmount.setText(Comman.getFormatedNumber(requestLoanModeldata.loan_amount));

            double a = Double.parseDouble(requestLoanModeldata.loan_borrowing_rate);

            System.out.println("intrest is " + a + " " + requestLoanModeldata.loan_amount);
            double b = Double.parseDouble(requestLoanModeldata.loan_amount);

            double intrest = (a * b) / 100;

            System.out.println("intrest is " + intrest);


            double servicefee = intrest * 0.05;


            recInterest.setText("" + Comman.getFormatedNumber("" + round(intrest, 1) + ""));

//Todo
//            recServiceFees.setText("" + round(servicefee, 2));
//            double total1 = (Double.parseDouble(requestLoanModeldata.loan_amount) + intrest) + servicefee;
//            recTotalPayable.setText(total1 + "");


            if (getIntent().getStringExtra("ismyloan").equals("false")) {


                recServiceFees.setText("-" + Comman.getFormatedNumber("" + round(servicefee, 2)));

                double total2 = (Double.parseDouble(requestLoanModeldata.loan_amount) + intrest) - servicefee;
                recTotalPayable.setText(Comman.getFormatedNumber(total2 + ""));
                tptalpayabletdxt.setText("Total receivable $");
            } else {

                recServiceFees.setText("" + Comman.getFormatedNumber("" + round(servicefee, 2)));

                double total1 = (Double.parseDouble(requestLoanModeldata.loan_amount) + intrest) + servicefee;
                recTotalPayable.setText(Comman.getFormatedNumber(total1 + ""));


            }

        }


        btn_share_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  takeScreenshot();
                createPdf();
            }
        });


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


    private void createPdf() {
        recLoanAmount.setText(Comman.getFormatedNumber(requestLoanModeldata.loan_amount));
        recPurpose.setText(requestLoanModeldata.loan_purpose);
        recBorrower.setText(requestLoanModeldata.loan_borrowing_rate);
        recCollateral.setText(requestLoanModeldata.loan_collateral);
        recMarketValue.setText(Comman.getFormatedNumber(requestLoanModeldata.loan_market_value));
        recLoanRatio.setText(requestLoanModeldata.loan_loan_ratio);


        String sometext = "" + txtx_nameplace_holder.getText() + "      " + recName.getText() + "\n"
                + "Amount $ :-" + "      " + recLoanAmount.getText() + "\n"
                + "Purpose :-" + "      " + recPurpose.getText() + "\n"
                + "Borrowing Rate % :-" + "      " + recBorrower.getText() + "\n"
                + "Collateral :-" + "      " + recCollateral.getText() + "\n"
                + "Market value $ :-" + "      " + recMarketValue.getText() + "\n"
                + "Asset/loan ratio % :-" + "      " + recLoanRatio.getText() + "\n\n\n"

                + "Amount $ :-" + "      " + recLoanAmount.getText() + "\n"
                + "Interest $ :-" + "      " + recInterest.getText() + "\n"
                + "Service fees (5%) $ :-" + "      " + recServiceFees.getText() + "\n"
                + tptalpayabletdxt.getText() + "      " + recTotalPayable.getText();

        System.out.println("prtdatais " + sometext);

//String textis = "fhjdsjkfjksfhsdhfk jksdfhdjksfhjkds<br> fdjksfhdjkshfk fjkdshfjkdsh <br>fjkdshfjkds fkdshfkdsn<br> fkjdshfkds fdksfhkdjsf <br>djksfhjksdf fdjshfjkds ";
        //    System.out.println("prtdatais "+ Html.toHtml(textis.,Html.FROM_HTML_MODE_LEGACY));


        // create a new document
        PdfDocument document = new PdfDocument();
        // crate a page description
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        // start a page
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        //  canvas.drawCircle(50, 50, 30, paint);
        //paint.setColor(Color.BLACK);
        canvas.drawText(txtx_nameplace_holder.getText() + " :", 20, 20, paint);
        canvas.drawText("" + recName.getText(), 150, 20, paint);

        canvas.drawText("Amount $ :", 20, 40, paint);
        canvas.drawText("" + recLoanAmount.getText(), 150, 40, paint);

        canvas.drawText("Purpose :", 20, 60, paint);
        canvas.drawText("" + recPurpose.getText(), 150, 60, paint);

        canvas.drawText("Loan Duration (days) :", 20, 80, paint);
        canvas.drawText("" + rec_loan_duration.getText(), 150, 80, paint);

        canvas.drawText("Borrowing Rate % :", 20, 100, paint);
        canvas.drawText("" + recBorrower.getText(), 150, 100, paint);

        canvas.drawText("Collateral :", 20, 120, paint);
        canvas.drawText("" + recCollateral.getText(), 150, 120, paint);

        canvas.drawText("Market value $ :", 20, 140, paint);
        canvas.drawText("" + recMarketValue.getText(), 150, 140, paint);

        canvas.drawText("Asset/loan ratio % :", 20, 160, paint);
        canvas.drawText("" + recLoanRatio.getText(), 150, 160, paint);


        canvas.drawText("Amount $ :", 20, 180, paint);
        canvas.drawText("" + recLoanAmount.getText(), 150, 180, paint);

        canvas.drawText("Interest $ :", 20, 200, paint);
        canvas.drawText("" + recInterest.getText(), 150, 200, paint);

        canvas.drawText("Service fees (5%) $ :", 20, 220, paint);
        canvas.drawText("" + recServiceFees.getText(), 150, 220, paint);

        canvas.drawText(tptalpayabletdxt.getText() + ":", 20, 240, paint);
        canvas.drawText("" + recTotalPayable.getText() + "", 150, 240, paint);


        //canvas.drawt
        // finish the page
        document.finishPage(page);
// draw text on the graphics object of the page

        // write the document content
        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/loanapp/";
        File file = new File(directory_path);
        if (!file.exists()) {
            file.mkdirs();
        }

        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
        String targetPdf = directory_path + now + ".pdf";
        File filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));
            Toast.makeText(this, "Done", Toast.LENGTH_LONG).show();

            viewPdf(filePath.toString());
        } catch (IOException e) {
            Log.e("main", "error " + e.toString());
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }
        // close the document
        document.close();


    }


    public void ConvertHTMLStringToPDF() {
        // note: be sure to copy the helper function ConvertHTMLStringToPDF() from this webpage
        String apiKey = "02d050d6-5f35-4dd2-a7c7-10fa90adc8b4";
        String value = "fhjdsjkfjksfhsdhfk jksdfhdjksfhjkds<br> fdjksfhdjkshfk fjkdshfjkdsh <br>fjkdshfjkds fkdshfkdsn<br> fkjdshfkds fdksfhkdjsf <br>djksfhjksdf fdjshfjkds ";

        String apiURL = "http://api.html2pdfrocket.com/pdf";
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("apiKey", apiKey);
        params.put("value", value);

        // Call the API convert to a PDF
        new InputStreamReader(Request.Method.POST, apiURL,
                new Response.Listener<byte[]>() {
                    @Override
                    public void onResponse(byte[] response) {
                        try {
                            if (response != null) {
                                String directory_path = Environment.getExternalStorageDirectory().getPath() + "/loanapp/";
                                File file = new File(directory_path);
                                if (!file.exists()) {
                                    file.mkdirs();
                                }

                                // Write stream output to local file
                                File pdfFile = new File(directory_path, "MySamplePDFFile.pdf");
                                OutputStream opStream = new FileOutputStream(pdfFile);
                                pdfFile.setWritable(true);
                                opStream.write(response);
                                opStream.flush();
                                opStream.close();
                            }
                        } catch (Exception ex) {
                            Toast.makeText(getBaseContext(), "Error while generating PDF file!!", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, params);

    }


    // Method for opening a pdf file
    private void viewPdf(String file) {

        File pdfFile = new File(file);  // -> filename = maven.pdf


        Uri path = FileProvider.getUriForFile(ShowLoanDetailsAndShareThem.this, BuildConfig.APPLICATION_ID + ".provider", pdfFile);
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pdfIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try {
            startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(ShowLoanDetailsAndShareThem.this, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
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
