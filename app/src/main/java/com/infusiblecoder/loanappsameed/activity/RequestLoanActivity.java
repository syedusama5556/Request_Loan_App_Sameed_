package com.infusiblecoder.loanappsameed.activity;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class RequestLoanActivity extends AppCompatActivity {

    LinearLayout uploadbtnButton;
    Button postarequestButton;
    TextView userNameTextView;
    TextView loanRequestCodeTextView;
    TextView textViewTextView;
    EditText enterAmountEditText;
    View line1ConstraintLayout;
    EditText purposeEditText;
    View line1TwoConstraintLayout;
    EditText collateralEditText;
    View line1ThreeConstraintLayout;
    EditText marketValueEditText;
    View line1FourConstraintLayout;
    View line1ImageView;
    RadioGroup daysradiogroup;
    TextView loan_type_txt;

    RequestQueue rQueue;
    ArrayList<HashMap<String, String>> arraylist;
    String url = "https://www.google.com";
    ArrayList<String> displayName = new ArrayList<>();
    ArrayList<Uri> pdffile = new ArrayList<>();
    private EditText edtVehicleID;
    private EditText edtOwnerID;
    private EditText edtInsurance;
    private CatLoadingView catLoadingView;
    private String loantypeIntent = "car";
    private ListView listview_request;
    ArrayAdapter<String> listview_request_adapter;
    private EditText marketLoanratioEditText;
    private TextView marketBorrowingrateText;

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
        daysradiogroup = findViewById(R.id.days_radio_button_view);
        listview_request = findViewById(R.id.listview_loan_request);

        listview_request_adapter = new ArrayAdapter<>(RequestLoanActivity.this, R.layout.listview_item_black, displayName);

        listview_request.setAdapter(listview_request_adapter);

        listview_request.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {

                String value = (String) adapter.getItemAtPosition(position);


                Object item = ((ArrayAdapter) listview_request.getAdapter()).getItem(position);
                ((ArrayAdapter) listview_request.getAdapter()).remove(item);

                System.out.println("myitemposis " + position);
                try {
                    if (displayName.size() > 0 && pdffile.size() > 0) {

                        displayName.remove(position);
                        pdffile.remove(position);
                    }
                    listview_request_adapter.notifyDataSetChanged();
                    System.out.println("myitemposis2 " + position);
                } catch (Exception e) {
                    System.out.println("Error removing data " + e.getMessage() + displayName.size());
                }
            }
        });

        loan_type_txt = findViewById(R.id.loan_type_txt);

        if (getIntent() != null) {

            loantypeIntent = getIntent().getStringExtra("loan_type");
            loan_type_txt.setText(loantypeIntent);
        }

        userNameTextView = findViewById(R.id.user_name_text_view);
        loanRequestCodeTextView = findViewById(R.id.loan_request_code_text_view);
        textViewTextView = findViewById(R.id.text_view_text_view);
        enterAmountEditText = findViewById(R.id.enter_amount_edit_text);
        line1ConstraintLayout = findViewById(R.id.line1_constraint_layout);
        purposeEditText = findViewById(R.id.purpose_edit_text);
        line1TwoConstraintLayout = findViewById(R.id.line1_two_constraint_layout);
        collateralEditText = findViewById(R.id.collateral_edit_text);
        line1ThreeConstraintLayout = findViewById(R.id.line1_three_constraint_layout);
        marketValueEditText = findViewById(R.id.market_value_edit_text);
        line1FourConstraintLayout = findViewById(R.id.line1_four_constraint_layout);

        line1ImageView = findViewById(R.id.line1_image_view);


        marketLoanratioEditText = (EditText) findViewById(R.id.market_loanratio_edit_text);

        marketBorrowingrateText = (TextView) findViewById(R.id.marketBorrowingrate_text);


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

        if (!name1.equals("no value") && !name1.equals("") && !name2.equals("no value") && !name2.equals("")) {


            userNameTextView.setText(name1 + " " + name2);

        } else {

            Comman.showErrorToast(RequestLoanActivity.this, "Error getting user name");

        }

    }

    public void onSelectDatePressed() {

//        DialogFragment newFragment = new DatePickerFragment();
//        newFragment.show(getSupportFragmentManager(), "datePicker");

    }

    public void onUploadBtnPressed() {


        //   showDialogSelectFiles();


        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // intent.setType("application/pdf");
        intent.setType("*/*");

        startActivityForResult(intent, 1);

    }

    public void onPostARequestPressed() {

        uploadPDF(displayName, pdffile);
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), HomeActivityDrawar.class));
        finish();
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
                                displayName.add("" + cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)));

                                //    edtOwnerID.setText("" + cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)));

                                pdffile.add(uri);

                                System.out.println("errorname" + displayName.get(0));

                                listview_request_adapter.notifyDataSetChanged();
                            }
                        } catch (Exception e) {

                            System.out.println("error" + e.getMessage());
                        } finally {
                            cursor.close();
                        }
                    } else if (uriString.startsWith("file://")) {

                        displayName.add(myFile.getName());
                        //   edtOwnerID.setText("" + myFile.getName());
                        System.out.println("errorname1" + displayName.get(0));

                        listview_request_adapter.notifyDataSetChanged();

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
                                edtVehicleID.setText("" + cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)));
                                pdffile.add(1, uri);
                            }
                        } catch (Exception e) {

                            System.out.println("" + e.getMessage());
                        } finally {
                            cursor.close();
                        }
                    } else if (uriString.startsWith("file://")) {
                        displayName.add(1, myFile.getName());
                        edtVehicleID.setText("" + myFile.getName());
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

        if (pdfname != null && pdfname.size() >= 1) {
            if (!TextUtils.isEmpty(marketValueEditText.getText()) && !TextUtils.isEmpty(purposeEditText.getText()) && !TextUtils.isEmpty(enterAmountEditText.getText()) && !TextUtils.isEmpty(collateralEditText.getText()) && !TextUtils.isEmpty(userNameTextView.getText()) && !TextUtils.isEmpty(loanRequestCodeTextView.getText())) {


                catLoadingView.setText("Please Wait ..");
                catLoadingView.show(getSupportFragmentManager(), "");

                try {


                    VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Comman.UPLOAD_MULTIPLE_DOC_WITH_DATA_URL2nd,
                            new Response.Listener<NetworkResponse>() {
                                @Override
                                public void onResponse(NetworkResponse response) {
                                    Log.d("ressssssoo", new String(response.data));

                                    System.out.println("done data is error " + response);


                                    rQueue.getCache().clear();
                                    try {
                                        catLoadingView.dismiss();

                                        JSONObject jsonObject = new JSONObject(new String(response.data));

                                        jsonObject.toString().replace("\\\\", "");

                                        if (jsonObject.getString("status").equals("false")) {

                                            Comman.showErrorToast(RequestLoanActivity.this, jsonObject.getString("message"));


                                        } else {

                                            startActivity(new Intent(RequestLoanActivity.this, HomeActivityDrawar.class));
                                            Comman.showDefaultToast(RequestLoanActivity.this, jsonObject.getString("message"));
                                            finish();
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
                                    Comman.showErrorToast(RequestLoanActivity.this, "error is 2 " + error);

                                    System.out.println("done data is error " + "error is 6 " + error.getMessage());
                                    error.printStackTrace();
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
                            String userid = prefs.getString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[0], "no value");//"No name defined" is the default value.

                            String userimgurl = prefs.getString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[11], "no value");//"No name defined" is the default value.

                            if (!email.equals("no value") && !email.equals("") && !userid.equals("no value") && !userid.equals("") && !userimgurl.equals("no value") && !userimgurl.equals("")) {


                            } else {

                                Comman.showErrorToast(RequestLoanActivity.this, "Error getting email");

                            }


                            Map<String, String> params = new HashMap<>();
                            params.put(Comman.TABLE_LOAN_REQUEST_ATTRIBUTES[0], textViewTextView.getText().toString());
                            params.put(Comman.TABLE_LOAN_REQUEST_ATTRIBUTES[1], userNameTextView.getText().toString());
                            params.put(Comman.TABLE_LOAN_REQUEST_ATTRIBUTES[2], userimgurl);
                            params.put(Comman.TABLE_LOAN_REQUEST_ATTRIBUTES[3], enterAmountEditText.getText().toString());
                            params.put(Comman.TABLE_LOAN_REQUEST_ATTRIBUTES[4], purposeEditText.getText().toString());
                            params.put(Comman.TABLE_LOAN_REQUEST_ATTRIBUTES[5], collateralEditText.getText().toString());
                            params.put(Comman.TABLE_LOAN_REQUEST_ATTRIBUTES[6], marketValueEditText.getText().toString());

                            params.put("loan_borrowing_rate", marketValueEditText.getText().toString());
                            params.put("loan_loan_ratio", marketValueEditText.getText().toString());


                            int radioButtonID = daysradiogroup.getCheckedRadioButtonId();
                            View radioButton = daysradiogroup.findViewById(radioButtonID);
                            int idx = daysradiogroup.indexOfChild(radioButton);
                            RadioButton r = (RadioButton) daysradiogroup.getChildAt(idx);
                            String selectedtext = r.getText().toString();
                            selectedtext = selectedtext.replaceAll("\\D+", "");


                            int days = Integer.parseInt(selectedtext);
                            long daysmilisec = 1296000000L;
                            switch (days) {

                                case 15: {
                                    daysmilisec = 1296000000L + System.currentTimeMillis();

                                    break;
                                }
                                case 30: {
                                    daysmilisec = 2592000000L + System.currentTimeMillis();
                                    break;
                                }
                                case 60: {
                                    daysmilisec = 5184000000L + System.currentTimeMillis();
                                    break;
                                }
                                case 90: {
                                    daysmilisec = 7776000000L + System.currentTimeMillis();
                                    break;
                                }
                                default: {
                                    daysmilisec = 1296000000L + System.currentTimeMillis();

                                    break;
                                }
                            }

                            Date date = new Date(daysmilisec);


                            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");


                            params.put(Comman.TABLE_LOAN_REQUEST_ATTRIBUTES[8], formatter.format(date));


                            params.put("loan_status", Comman.LOAN_Status[0]);

                            params.put(Comman.TABLE_USERS_ATTRIBUTES[6], email);
                            params.put(Comman.TABLE_LOAN_REQUEST_ATTRIBUTES[7], loantypeIntent);
                            params.put("user_id", userid);
                            params.put("countoffiles", pdffile.size() + "");
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
                            InputStream iStream = null;
                            //InputStream iStream1 = null;
                            //InputStream iStream2 = null;


                            byte[] inputData = null;


//                            if (loan_type_txt.getText().toString().equals(Comman.LOAN_TYPES[1])) {
//                                try {
//                                    iStream = getContentResolver().openInputStream(pdffile.get(0));
//                                    inputData = BitmapConversion.getBytes(iStream);
//
////
////                                    iStream1 = getContentResolver().openInputStream(pdffile.get(1));
////                                    inputData1 = BitmapConversion.getBytes(iStream1);
////
////                                    iStream2 = getContentResolver().openInputStream(pdffile.get(2));
////                                    inputData2 = BitmapConversion.getBytes(iStream2);
//
//
//                                    params.put("filename0", new DataPart(pdfname.get(0), inputData));
//
//
////                                    params.put("filename1", new DataPart(pdfname.get(1), inputData1));
////
////
////                                    params.put("filename2", new DataPart(pdfname.get(2), inputData2));
//
//
//                                } catch (Exception e) {
//
//                                }
//                            } else {


                            try {
                                for (int i = 0; i < pdffile.size(); i++) {

                                    iStream = getContentResolver().openInputStream(pdffile.get(i));
                                    inputData = BitmapConversion.getBytes(iStream);


                                    params.put("filename" + i, new DataPart(pdfname.get(i), inputData));
                                    System.out.println("done data is " + " filename" + i);

                                }

                            } catch (Exception e) {


                            }
//                            }


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
                    System.out.println("error is3 " + e.getMessage());
                    Comman.showErrorToast(RequestLoanActivity.this, "error is3 " + e.getMessage());
                    catLoadingView.dismiss();
                }


            } else {

                Comman.showErrorToast(RequestLoanActivity.this, "Enter Missing Fields");
            }
        } else {
            Comman.showErrorToast(RequestLoanActivity.this, "Select all Files First");

        }
    }


    private void showDialogSelectFiles() {


        Dialog dialog = new Dialog(RequestLoanActivity.this);

        dialog.setContentView(R.layout.layout_upload_collateral_files);


        ImageView attach_Vehicle_ID;
        ImageView attach_Owner_ID;
        ImageView attach_Insurance;


        Button btn_dialog_update_profile;


        edtVehicleID = dialog.findViewById(R.id.edtVehicleID);
        edtOwnerID = dialog.findViewById(R.id.edtOwnerID);
        edtInsurance = dialog.findViewById(R.id.edtInsurance);

        attach_Vehicle_ID = dialog.findViewById(R.id.attach_Vehicle_ID);
        attach_Owner_ID = dialog.findViewById(R.id.attach_Owner_ID);
        attach_Insurance = dialog.findViewById(R.id.attach_Insurance);


        btn_dialog_update_profile = dialog.findViewById(R.id.btn_dialog_update_profile);


        attach_Vehicle_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(intent, 2);
            }
        });

        attach_Owner_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(intent, 1);


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
                if (loan_type_txt.getText().toString().equals(Comman.LOAN_TYPES[1])) {

                    if (!TextUtils.isEmpty(edtInsurance.getText().toString()) && !TextUtils.isEmpty(edtVehicleID.getText().toString()) && !TextUtils.isEmpty(edtOwnerID.getText().toString())) {

                        dialog.dismiss();


                    } else {
                        Comman.showErrorToast(RequestLoanActivity.this, "Enter Missing Fields");
                    }


                } else {
                    if (!TextUtils.isEmpty(edtOwnerID.getText().toString())) {

                        dialog.dismiss();


                    } else {
                        Comman.showErrorToast(RequestLoanActivity.this, "Enter Missing Fields");
                    }

                }
            }
        });

        dialog.show();


    }


//    public static class DatePickerFragment extends DialogFragment
//            implements DatePickerDialog.OnDateSetListener {
//
//        @Override
//        public Dialog onCreateDialog(Bundle savedInstanceState) {
//            final Calendar c = Calendar.getInstance();
//            int year = c.get(Calendar.YEAR);
//            int month = c.get(Calendar.MONTH);
//            int day = c.get(Calendar.DAY_OF_MONTH);
//            DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
//            dialog.getDatePicker().setMinDate(c.getTimeInMillis());
//            return dialog;
//        }
//
//        public void onDateSet(DatePicker view, int year, int month, int day) {
//
//
//            dueDateTextView.setText(day + "-" + (month + 1) + "-" + year);
//
//
//        }
//    }
}
