/**
 * Created by Usama.
 */

package com.infusiblecoder.loanappsameed.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.infusiblecoder.loanappsameed.Helpers.BitmapConversion;
import com.infusiblecoder.loanappsameed.Helpers.Comman;
import com.infusiblecoder.loanappsameed.Helpers.VolleyMultipartRequest;
import com.infusiblecoder.loanappsameed.ModelClasses.DataPart;
import com.infusiblecoder.loanappsameed.R;
import com.roger.catloadinglibrary.CatLoadingView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


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


    RequestQueue rQueue;
    ArrayList<HashMap<String, String>> arraylist;
    String url = "https://www.google.com";
    private EditText edtVehicleID;
    private EditText edtOwnerID;
    private EditText edtInsurance;

    ArrayList<String> displayName = new ArrayList<>();
    ArrayList<Uri> pdffile = new ArrayList<>();
    private CatLoadingView catLoadingView;

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

        catLoadingView = new CatLoadingView();

        constraintLayoutConstraintLayout = (ConstraintLayout) findViewById(R.id.constraint_layout_constraint_layout);
        userNameTextView = (TextView) findViewById(R.id.user_name_text_view);
        loanRequestCodeTextView = (TextView) findViewById(R.id.loan_request_code_text_view);
        textViewTextView = (TextView) findViewById(R.id.text_view_text_view);
        enteramountConstraintLayout = (ConstraintLayout) findViewById(R.id.enteramount_constraint_layout);
        enterAmountEditText = (EditText) findViewById(R.id.enter_amount_edit_text);
        line1ConstraintLayout = (View) findViewById(R.id.line1_constraint_layout);
        enterPurposeConstraintLayout = (ConstraintLayout) findViewById(R.id.enter_purpose_constraint_layout);
        purposeEditText = (EditText) findViewById(R.id.purpose_edit_text);
        line1TwoConstraintLayout = (View) findViewById(R.id.line1_two_constraint_layout);
        enterCollateralConstraintLayout = (ConstraintLayout) findViewById(R.id.enter_collateral_constraint_layout);
        collateralEditText = (EditText) findViewById(R.id.collateral_edit_text);
        line1ThreeConstraintLayout = (View) findViewById(R.id.line1_three_constraint_layout);
        enterMarketValueConstraintLayout = (ConstraintLayout) findViewById(R.id.enter_market_value_constraint_layout);
        marketValueEditText = (EditText) findViewById(R.id.market_value_edit_text);
        line1FourConstraintLayout = (View) findViewById(R.id.line1_four_constraint_layout);
        deudateviewConstraintLayout = (ConstraintLayout) findViewById(R.id.deudateview_constraint_layout);
        duedateConstraintLayout = (ConstraintLayout) findViewById(R.id.duedate_constraint_layout);
        dueDateTextView = (TextView) findViewById(R.id.due_date_text_view);
        line1ImageView = (View) findViewById(R.id.line1_image_view);


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


        String code = String.valueOf(System.currentTimeMillis());

        textViewTextView.setText(code);


        SharedPreferences prefs = getSharedPreferences(Comman.SHAREDPREF_USERDATA, MODE_PRIVATE);
        String name1 = prefs.getString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[1], "no value");//"No name defined" is the default value.
        String name2 = prefs.getString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[2], "no value");//"No name defined" is the default value.

        if (!name1.equals("no value") && !name1.equals("") && !name2.equals("no value") && !name2.equals("")){



            userNameTextView.setText(name1+" "+name2);

        } else {

           Comman.showErrorToast(RequestLoanActivity.this,"Error getting user name");

        }

    }

    public void onSelectDatePressed() {

        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");

    }

    public void onUploadBtnPressed() {


        showDialogSelectFiles();

    }

    public void onPostARequestPressed() {

        uploadPDF(displayName, pdffile);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            if (resultCode == RESULT_OK) {

                if (requestCode == 1) {


                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    String uriString = uri.toString();
                    File myFile = new File(uriString);
                    String path = myFile.getAbsolutePath();

                    System.out.println("mydatais 1 " + path);
                    if (uriString.startsWith("content://")) {
                        Cursor cursor = null;
                        try {
                            cursor = this.getContentResolver().query(uri, null, null, null, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                System.out.println("mydatais 1 " + cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)));
                                displayName.add(0, "" + cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)));

                                edtVehicleID.setText("" + cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)));

                                pdffile.add(0, uri);
                            }
                        } catch (Exception e) {

                            System.out.println("" + e.getMessage());
                        } finally {
                            cursor.close();
                        }
                    } else if (uriString.startsWith("file://")) {
                        displayName.add(0, myFile.getName());
                        edtVehicleID.setText("" + myFile.getName());
                    }

                }

                if (requestCode == 2) {


                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    String uriString = uri.toString();
                    File myFile = new File(uriString);
                    String path = myFile.getAbsolutePath();

                    System.out.println("mydatais 2 " + path);
                    if (uriString.startsWith("content://")) {
                        Cursor cursor = null;
                        try {
                            cursor = this.getContentResolver().query(uri, null, null, null, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                System.out.println("mydatais 2 " + cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)));
                                displayName.add(1, cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)));
                                edtOwnerID.setText("" + cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)));
                                pdffile.add(1, uri);
                            }
                        } catch (Exception e) {

                            System.out.println("" + e.getMessage());
                        } finally {
                            cursor.close();
                        }
                    } else if (uriString.startsWith("file://")) {
                        displayName.add(1, myFile.getName());
                        edtOwnerID.setText("" + myFile.getName());
                    }

                }

                if (requestCode == 3) {


                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    String uriString = uri.toString();
                    File myFile = new File(uriString);
                    String path = myFile.getAbsolutePath();

                    System.out.println("mydatais 3 " + path);

                    if (uriString.startsWith("content://")) {
                        Cursor cursor = null;
                        try {
                            cursor = this.getContentResolver().query(uri, null, null, null, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                System.out.println("mydatais 3 " + cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)));
                                displayName.add(2, cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)));
                                edtInsurance.setText("" + cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)));

                                pdffile.add(2, uri);
                            }
                        } catch (Exception e) {

                            System.out.println("err2 " + e.getMessage());
                        } finally {
                            cursor.close();
                        }
                    } else if (uriString.startsWith("file://")) {
                        displayName.add(2, myFile.getName());
                        edtInsurance.setText("" + myFile.getName());
                    }

                }


            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void uploadPDF(ArrayList<String> pdfname, ArrayList<Uri> pdffile) {

        if (pdfname != null && pdfname.size() == 3) {
            if (!TextUtils.isEmpty(marketValueEditText.getText()) && !TextUtils.isEmpty(purposeEditText.getText()) && !TextUtils.isEmpty(enterAmountEditText.getText()) && !TextUtils.isEmpty(dueDateTextView.getText()) && !TextUtils.isEmpty(collateralEditText.getText()) && !TextUtils.isEmpty(userNameTextView.getText()) && !TextUtils.isEmpty(loanRequestCodeTextView.getText())) {

                InputStream iStream = null;
                InputStream iStream1 = null;
                InputStream iStream2 = null;
                try {

                    iStream = getContentResolver().openInputStream(pdffile.get(0));
                    final byte[] inputData = BitmapConversion.getBytes(iStream);

                    iStream1 = getContentResolver().openInputStream(pdffile.get(1));
                    final byte[] inputData1 = BitmapConversion.getBytes(iStream1);

                    iStream2 = getContentResolver().openInputStream(pdffile.get(2));
                    final byte[] inputData2 = BitmapConversion.getBytes(iStream2);


                    catLoadingView.setText("Please Wait ..");
                    catLoadingView.show(getSupportFragmentManager(), "");

                    VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Comman.UPLOAD_MULTIPLE_DOC_WITH_DATA_URL,
                            new Response.Listener<NetworkResponse>() {
                                @Override
                                public void onResponse(NetworkResponse response) {
                                    Log.d("ressssssoo", new String(response.data));

                                    rQueue.getCache().clear();
                                    try {
                                        JSONObject jsonObject = new JSONObject(new String(response.data));
                                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                                        jsonObject.toString().replace("\\\\", "");

                                        if (jsonObject.getString("status").equals("true")) {
                                            Log.d("come::: >>>  ", "yessssss");
                                            arraylist = new ArrayList<HashMap<String, String>>();
                                            JSONArray dataArray = jsonObject.getJSONArray("data");
                                            catLoadingView.dismiss();

//                                    for (int i = 0; i < dataArray.length(); i++) {
//                                        JSONObject dataobj = dataArray.getJSONObject(i);
//                                        url = dataobj.optString("pathToFile");
//                                        tv.setText(url);
//                                    }


                                        }
                                    } catch (JSONException e) {
                                        Comman.showErrorToast(RequestLoanActivity.this, "error is " + e.getMessage());
                                        catLoadingView.dismiss();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Comman.showErrorToast(RequestLoanActivity.this, "error is2 " + error.getMessage());
                                    catLoadingView.dismiss();
                                }
                            }) {

                        /*
                         * If you want to add more parameters with the image
                         * you can do it here
                         * here we have only one parameter with the image
                         * which is tags
                         * */
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {


                            SharedPreferences prefs = getSharedPreferences(Comman.SHAREDPREF_USERDATA, MODE_PRIVATE);
                            String email = prefs.getString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[7], "no value");//"No name defined" is the default value.

                            if (!email.equals("no value") && !email.equals("") ){




                            } else {

                                Comman.showErrorToast(RequestLoanActivity.this,"Error getting email");

                            }


                            Map<String, String> params = new HashMap<>();
                            params.put(Comman.TABLE_LOAN_REQUEST_ATTRIBUTES[0], textViewTextView.getText().toString());
                            params.put(Comman.TABLE_LOAN_REQUEST_ATTRIBUTES[1], userNameTextView.getText().toString());
                            params.put(Comman.TABLE_LOAN_REQUEST_ATTRIBUTES[2], enterAmountEditText.getText().toString());
                            params.put(Comman.TABLE_LOAN_REQUEST_ATTRIBUTES[3], purposeEditText.getText().toString());
                            params.put(Comman.TABLE_LOAN_REQUEST_ATTRIBUTES[4], collateralEditText.getText().toString());
                            params.put(Comman.TABLE_LOAN_REQUEST_ATTRIBUTES[5], marketValueEditText.getText().toString());
                            params.put(Comman.TABLE_LOAN_REQUEST_ATTRIBUTES[6], dueDateTextView.getText().toString());

                            params.put(Comman.TABLE_USERS_ATTRIBUTES[6], email);
//                    params.put(Comman.TABLE_LOAN_REQUEST_ATTRIBUTES[8], pdfname.get(0));
//                    params.put(Comman.TABLE_LOAN_REQUEST_ATTRIBUTES[9], pdfname.get(0));


                            return params;
                        }

                        /*
                         *pass files using below method
                         * */
                        @Override
                        protected Map<String, DataPart> getByteData() {
                            Map<String, DataPart> params = new HashMap<>();

                            params.put("filename", new DataPart(pdfname.get(0), inputData));
                            params.put("filename1", new DataPart(pdfname.get(1), inputData1));
                            params.put("filename2", new DataPart(pdfname.get(2), inputData2));
                            return params;
                        }
                    };


                    volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                            0,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    rQueue = Volley.newRequestQueue(RequestLoanActivity.this);
                    rQueue.add(volleyMultipartRequest);


                } catch (Exception e) {
                    Comman.showErrorToast(RequestLoanActivity.this, "error is3 " + e.getMessage());
                    catLoadingView.dismiss();
                }


            } else {

                Comman.showErrorToast(RequestLoanActivity.this, "Enter Missing Fields");
            }
        } else {
            Comman.showErrorToast(RequestLoanActivity.this, "Select all 3 Files First");

        }
    }


    private void showDialogSelectFiles() {


        Dialog dialog = new Dialog(RequestLoanActivity.this);

        dialog.setContentView(R.layout.layout_upload_collateral_files);


        ImageView attach_Vehicle_ID;
        ImageView attach_Owner_ID;
        ImageView attach_Insurance;


        Button btn_dialog_update_profile;


        edtVehicleID = (EditText) dialog.findViewById(R.id.edtVehicleID);
        edtOwnerID = (EditText) dialog.findViewById(R.id.edtOwnerID);
        edtInsurance = (EditText) dialog.findViewById(R.id.edtInsurance);

        attach_Vehicle_ID = (ImageView) dialog.findViewById(R.id.attach_Vehicle_ID);
        attach_Owner_ID = (ImageView) dialog.findViewById(R.id.attach_Owner_ID);
        attach_Insurance = (ImageView) dialog.findViewById(R.id.attach_Insurance);


        btn_dialog_update_profile = (Button) dialog.findViewById(R.id.btn_dialog_update_profile);


        attach_Vehicle_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(intent, 1);

            }
        });

        attach_Owner_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(intent, 2);

            }
        });

        attach_Insurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(intent, 3);

            }
        });


        btn_dialog_update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!TextUtils.isEmpty(edtInsurance.getText().toString()) && !TextUtils.isEmpty(edtVehicleID.getText().toString()) && !TextUtils.isEmpty(edtOwnerID.getText().toString())) {

                    dialog.dismiss();


                } else {
                    Comman.showErrorToast(RequestLoanActivity.this, "Enter Missing Fields");
                }

            }
        });

        dialog.show();


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
            return dialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {

            dueDateTextView.setText(day + "-" + (month + 1) + "-" + year);


        }
    }
}
