/**
 * Created by Usama.
 */

package com.infusiblecoder.loanappsameed.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.infusiblecoder.loanappsameed.Helpers.BitmapConversion;
import com.infusiblecoder.loanappsameed.Helpers.Comman;
import com.infusiblecoder.loanappsameed.Helpers.GenericFileProvider;
import com.infusiblecoder.loanappsameed.Helpers.VollySingltonClass;
import com.infusiblecoder.loanappsameed.R;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.roger.catloadinglibrary.CatLoadingView;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class ProfileActivity extends AppCompatActivity {
    private static final int PICK_FROM_CAMERA = 1000;
    private static final int PICK_FROM_GALLARY = 1001;
    CircularImageView ricardo_image_view;
    private TextView ricardoJosephTextView;
    private TextView ricardojosephGmailTextView;
    private ImageButton editButton;
    private TextView profileTextView;
    private TextView generalTextView;
    private TextView updateAndModifyYoTextView;
    private Button profileSettingsButton;
    private TextView changeYourPasswordTextView;
    private Button privacyButton;
    private TextView changeYourNotificaTextView;
    private Button notificationsButton;
    CatLoadingView catLoadingView;
    private Bitmap user_img_url;
    private String imageinstring;

    public static Intent newIntent(Context context) {

        // Fill the created intent with the data you want to be passed to this Activity when it's opened.
        return new Intent(context, ProfileActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.profile_activity);
        this.init();
    }

    private void init() {
        catLoadingView = new CatLoadingView();
        // Configure Ricardo Joseph component
        ricardoJosephTextView = this.findViewById(R.id.ricardo_joseph_text_view);
        ricardo_image_view = findViewById(R.id.ricardo_image_view);
        // Configure ricardojoseph@gmail. component
        ricardojosephGmailTextView = this.findViewById(R.id.ricardojoseph_gmail_text_view);

        // Configure Edit component
        editButton = this.findViewById(R.id.edit_button);
        editButton.setOnClickListener((view) -> {
            this.onEditPressed();
        });

        // Configure Profile component
        profileTextView = this.findViewById(R.id.profile_text_view);

        // Configure GENERAL component
        generalTextView = this.findViewById(R.id.general_text_view);

        // Configure Update and modify yo component
        updateAndModifyYoTextView = this.findViewById(R.id.update_and_modify_yo_text_view);

        profileSettingsButton = this.findViewById(R.id.profile_settings_button);


        // Configure Change your password component
        changeYourPasswordTextView = this.findViewById(R.id.change_your_password_text_view);

        // Configure Privacy component
        privacyButton = this.findViewById(R.id.privacy_button);


        // Configure Change your password component
        changeYourPasswordTextView = this.findViewById(R.id.change_your_password_text_view);


        loadAllData();

    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), HomeActivityDrawar.class));
        finish();
    }

    public void loadAllData() {


        SharedPreferences prefs = getSharedPreferences(Comman.SHAREDPREF_USERDATA, MODE_PRIVATE);
        String user_id = prefs.getString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[0], "no value");//"No name defined" is the default value.


        String fname = prefs.getString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[1], "no value");
        String lname = prefs.getString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[2], "no value");

        String fullname = fname + " " + lname;
        String email = prefs.getString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[7], "no value");

        String img_url = prefs.getString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[10], "no value");


        if (!user_id.equals("") && !user_id.equals("no value")) {

            ricardojosephGmailTextView.setText(email);
            ricardoJosephTextView.setText(fullname);

            Glide.with(ProfileActivity.this).load(img_url)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true).placeholder(R.mipmap.ic_launcher).into(ricardo_image_view);


        } else {

            Comman.showErrorToast(ProfileActivity.this, "Error");
        }


    }

    public void onEditPressed() {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update Image");
        builder.setMessage("Are You Sure You want To Update Image ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                get_gallery_image();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();



    }


    public void get_gallery_image() {
        Intent sdintent = new Intent(Intent.ACTION_PICK);
        sdintent.setType("image/*");
        sdintent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(sdintent, PICK_FROM_GALLARY);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case PICK_FROM_CAMERA: {
                if (resultCode == Activity.RESULT_OK) {
                    Bitmap bitmapImage = (Bitmap) data.getExtras().get("data");


                }
                break;


            }
            case PICK_FROM_GALLARY: {
                if (requestCode == PICK_FROM_GALLARY) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

//                    Cursor cursor = getContentResolver().query(selectedImage,
//                            filePathColumn, null, null, null);

                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);

                    cursor.moveToFirst();


                    if (cursor.moveToFirst()) {
                        //   final ImageView imageView = (ImageView) findViewById(R.id.pictureView);


                        if (Build.VERSION.SDK_INT >= 29) {
                            // You can replace '0' by 'cursor.getColumnIndex(MediaStore.Images.ImageColumns._ID)'
                            // Note that now, you read the column '_ID' and not the column 'DATA'

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            String filePath = cursor.getString(columnIndex);
                            File imageFile = new File(filePath);
                            Uri uri = GenericFileProvider.getUriForFile(this, "com.infusiblecoder.loanappsameed.provider", imageFile);
                            File newuriis = new File(uri.toString());
                            System.out.println("myuri is " + newuriis);
                            ricardo_image_view.setImageURI(uri);
                            user_img_url = BitmapFactory.decodeFile(newuriis.toString());

                            cursor.close();
                        } else {

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            String filePath = cursor.getString(columnIndex);


                            user_img_url = BitmapFactory.decodeFile(filePath);

                            cursor.close();


                        }

                    }

                    try {


                        imageinstring = BitmapConversion.getStringImage(user_img_url);

                    } catch (Exception e) {

                        imageinstring = "/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDABALDA4MChAODQ4SERATGCgaGBYWGDEjJR0oOjM9PDkz";
                        Toast.makeText(this, "Android 10 Error ", Toast.LENGTH_SHORT).show();
                    }

                    catLoadingView.setText("Please Wait ..");
                    catLoadingView.show(getSupportFragmentManager(), "");
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Comman.UPDATE_PROFILE_image_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            System.out.println("myresponseisto " + response);
                            try {


                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                String code = jsonObject.getString("code");
                                if (code.equals("reg_success")) {

                                    String image_url = jsonObject.getString("image_url");

                                    SharedPreferences.Editor editor = getSharedPreferences(Comman.SHAREDPREF_USERDATA, MODE_PRIVATE).edit();

                                    editor.putString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[10], Comman.START_URL + image_url);


                                    editor.apply();


                                    catLoadingView.dismiss();
                                    FancyToast.makeText(ProfileActivity.this, "Updated!", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();


                                } else if (code.equals("reg_failed")) {
                                    catLoadingView.dismiss();
                                    FancyToast.makeText(ProfileActivity.this, "Failed!!!", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();


                                }
                            } catch (JSONException e) {
                                e.printStackTrace();

                                catLoadingView.dismiss();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            catLoadingView.dismiss();

                            FancyToast.makeText(ProfileActivity.this, "Error! is " + error, FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();


                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            //       user_img_url = tvUrl.getText().toString();


                            SharedPreferences prefs = getSharedPreferences(Comman.SHAREDPREF_USERDATA, MODE_PRIVATE);
                            String id = prefs.getString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[0], "no value");//"No name defined" is the default value.
                            String email = prefs.getString("email", "no value");
                            if (!id.equals("no value") && !id.equals("")) {

                                System.out.println("error gettin id");
                                //  Toast.makeText(ProfileActivity.this, "Error getting id", Toast.LENGTH_SHORT).show();
                            }


                            Map<String, String> params = new HashMap<String, String>();
                            params.put("user_id", id);
                            params.put("email", email);
                            params.put("user_img_url", imageinstring);


                            return params;
                        }
                    };

                    VollySingltonClass.getmInstance(getApplicationContext()).addToRequsetque(stringRequest);


                    break;
                }
            }


        }
    }






    public void onProfileSettingsPressed(View view) {

//        $firstname_db = "firstname";
//        $lastname_db = "lastname";
//        $address_db = "address";
//        $whatyoupretend_db = "whatyoupretend";
//        $fieldofactivity_db = "fieldofactivity";
//        $phone_db = "phone";
//        $email_db = "email";

        showDialogUpdateProfile();

    }

    private void showDialogUpdateProfile() {


        Dialog dialog = new Dialog(ProfileActivity.this);

        dialog.setContentView(R.layout.layout_update_profile);

        EditText edtPassword;
        EditText edtFirstname;
        EditText edtLastname;
        EditText edtAddress;
        EditText edtWhatyoupretend;
        EditText edtFieldofactivity;
        EditText edtPhone;
        EditText edtEmail;
        Button btnDialogUpdateProfile;


        edtPassword = dialog.findViewById(R.id.edtPassword);
        edtFirstname = dialog.findViewById(R.id.edtFirstname);
        edtLastname = dialog.findViewById(R.id.edtLastname);
        edtAddress = dialog.findViewById(R.id.edtAddress);
        edtWhatyoupretend = dialog.findViewById(R.id.edtWhatyoupretend);
        edtFieldofactivity = dialog.findViewById(R.id.edtFieldofactivity);
        edtPhone = dialog.findViewById(R.id.edtPhone);

        btnDialogUpdateProfile = dialog.findViewById(R.id.btn_dialog_update_profile);

        SharedPreferences prefs = getSharedPreferences(Comman.SHAREDPREF_USERDATA, MODE_PRIVATE);


        edtFirstname.setText(prefs.getString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[1], "no value"));//"No name defined" is the default value.
        edtLastname.setText(prefs.getString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[2], "no value"));//"No name defined" is the default value.
        edtAddress.setText(prefs.getString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[3], "no value"));//"No name defined" is the default value.
        edtWhatyoupretend.setText(prefs.getString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[4], "no value"));//"No name defined" is the default value.
        edtFieldofactivity.setText(prefs.getString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[5], "no value"));//"No name defined" is the default value.
        edtPhone.setText(prefs.getString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[6], "no value"));//"No name defined" is the default value.


        btnDialogUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!TextUtils.isEmpty(edtPhone.getText().toString()) && !TextUtils.isEmpty(edtPassword.getText().toString())) {


                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Comman.UPDATE_PROFILE_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            try {
                                JSONArray jsonArray = new JSONArray(response);

                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                String code = jsonObject.getString("code");

                                System.out.println("mysre +" + jsonObject);

                                if (code.equals("failed")) {


                                    Comman.showErrorToast(ProfileActivity.this, "Failed " + jsonObject.getString("message"));


                                } else {

                                    Comman.showSucdessToast(ProfileActivity.this, "" + jsonObject.getString("message"));


                                    SharedPreferences.Editor editor = getSharedPreferences(Comman.SHAREDPREF_USERDATA, MODE_PRIVATE).edit();

                                    editor.putString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[1], edtFirstname.getText().toString());
                                    editor.putString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[2], edtLastname.getText().toString());
                                    editor.putString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[3], edtAddress.getText().toString());
                                    editor.putString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[4], edtWhatyoupretend.getText().toString());
                                    editor.putString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[5], edtFieldofactivity.getText().toString());
                                    editor.putString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[6], edtPhone.getText().toString());


                                    editor.apply();


                                    dialog.dismiss();

                                }

                            } catch (JSONException e) {
                                Comman.showErrorToast(ProfileActivity.this, "error is " + e.getMessage());

                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Comman.showErrorToast(ProfileActivity.this, "error is " + error);


                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {


                            SharedPreferences prefs = getSharedPreferences(Comman.SHAREDPREF_USERDATA, MODE_PRIVATE);
                            String id = prefs.getString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[0], "no value");//"No name defined" is the default value.

                            if (id.equals("no value")) {

                                Comman.showErrorToast(ProfileActivity.this, "error ");

                            }


                            Map<String, String> params = new HashMap<String, String>();
                            params.put("user_id", id);

                            params.put(Comman.TABLE_USERS_ATTRIBUTES[0], edtFirstname.getText().toString());
                            params.put(Comman.TABLE_USERS_ATTRIBUTES[1], edtLastname.getText().toString());
                            params.put(Comman.TABLE_USERS_ATTRIBUTES[2], edtAddress.getText().toString());
                            params.put(Comman.TABLE_USERS_ATTRIBUTES[3], edtWhatyoupretend.getText().toString());
                            params.put(Comman.TABLE_USERS_ATTRIBUTES[4], edtFieldofactivity.getText().toString());
                            params.put(Comman.TABLE_USERS_ATTRIBUTES[5], edtPhone.getText().toString());

                            params.put("oldpass", edtPassword.getText().toString());


                            return params;

                        }
                    };

                    VollySingltonClass.getmInstance(getApplicationContext()).addToRequsetque(stringRequest);


                } else {
                    Comman.showErrorToast(ProfileActivity.this, "Enter Missing Fields");
                }

            }
        });


        dialog.show();


    }

    public void onPrivacyPressed(View view) {


        showDialogPassword();


    }

    public void showDialogPassword() {

        Dialog dialog = new Dialog(ProfileActivity.this);

        dialog.setContentView(R.layout.layout_change_password);

        EditText edtPassword = dialog.findViewById(R.id.edtPassword);
        EditText edtNewPassword = dialog.findViewById(R.id.edtNewPassword);
        EditText edtNewConfirmPassword = dialog.findViewById(R.id.edtNewConfirmPassword);
        Button btnDialogPassChange = dialog.findViewById(R.id.btn_dialog_pass_change);

        btnDialogPassChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!TextUtils.isEmpty(edtNewConfirmPassword.getText().toString()) && !TextUtils.isEmpty(edtNewPassword.getText().toString()) && !TextUtils.isEmpty(edtPassword.getText().toString())) {

                    if (edtNewConfirmPassword.getText().toString().equals(edtNewPassword.getText().toString())) {


                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Comman.CHANGE_PASSWORD_URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {


                                try {
                                    JSONArray jsonArray = new JSONArray(response);

                                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                                    String code = jsonObject.getString("code");
                                    if (code.equals("failed")) {


                                        Comman.showErrorToast(ProfileActivity.this, "Failed " + jsonObject.getString("message"));


                                    } else {

                                        Comman.showSucdessToast(ProfileActivity.this, "" + jsonObject.getString("message"));

                                        dialog.dismiss();

                                    }
                                } catch (JSONException e) {
                                    Comman.showErrorToast(ProfileActivity.this, "error is " + e.getMessage());

                                }


                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Comman.showErrorToast(ProfileActivity.this, "error is " + error);


                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {


                                SharedPreferences prefs = getSharedPreferences(Comman.SHAREDPREF_USERDATA, MODE_PRIVATE);
                                String id = prefs.getString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[0], "no value");//"No name defined" is the default value.

                                if (id.equals("no value")) {

                                    Comman.showErrorToast(ProfileActivity.this, "error ");


                                }


                                Map<String, String> params = new HashMap<String, String>();
                                params.put("user_id", id);
                                params.put("oldpass", edtPassword.getText().toString());
                                params.put("newpass", edtNewPassword.getText().toString());
                                return params;
                            }
                        };
                        VollySingltonClass.getmInstance(getApplicationContext()).addToRequsetque(stringRequest);


                    } else {
                        Comman.showErrorToast(ProfileActivity.this, "Password not matching");
                    }


                } else {
                    Comman.showErrorToast(ProfileActivity.this, "Enter Missing Fields");
                }

            }
        });


        dialog.show();

    }
}
