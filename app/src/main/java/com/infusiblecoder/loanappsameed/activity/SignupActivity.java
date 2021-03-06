/**
 * Created by Usama.
 */

package com.infusiblecoder.loanappsameed.activity;

import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.animation.PathInterpolatorCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.infusiblecoder.loanappsameed.Helpers.BitmapConversion;
import com.infusiblecoder.loanappsameed.Helpers.Comman;
import com.infusiblecoder.loanappsameed.Helpers.GenericFileProvider;
import com.infusiblecoder.loanappsameed.Helpers.VollySingltonClass;
import com.infusiblecoder.loanappsameed.R;
import com.roger.catloadinglibrary.CatLoadingView;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class SignupActivity extends AppCompatActivity {

    private static final int PICK_FROM_CAMERA = 1000;
    private static final int PICK_FROM_GALLARY = 1001;
    CatLoadingView catLoadingView;
    // The request code used in ActivityCompat.requestPermissions()
// and returned in the Activity's onRequestPermissionsResult()
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.CALL_PHONE,
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
    };
    private Button alreadyAmemberLoginButton;
    private Button signupbtnButton;
    private ImageView backImageView, image_view_profile;
    private TextView welcomeSignupTextView, select_img_txt;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText addressEditText;
    private Spinner whatyoupretendEditText;
    private EditText fieldOfActivityEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Bitmap user_img_url;

    public static Intent newIntent(Context context) {

        // Fill the created intent with the data you want to be passed to this Activity when it's opened.
        return new Intent(context, SignupActivity.class);
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

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.signup_activity);

        catLoadingView = new CatLoadingView();
        this.init();

        startAnimationOne();
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

    }

    private void init() {

        image_view_profile = findViewById(R.id.image_view_profile);
        select_img_txt = findViewById(R.id.select_img_txt);

        backImageView = findViewById(R.id.back_image_view);
        welcomeSignupTextView = findViewById(R.id.welcome_signup_text_view);
        firstNameEditText = findViewById(R.id.first_name_edit_text);
        lastNameEditText = findViewById(R.id.last_name_edit_text);
        addressEditText = findViewById(R.id.address_edit_text);
        whatyoupretendEditText = findViewById(R.id.whatyoupretend_edit_text);
        fieldOfActivityEditText = findViewById(R.id.field_of_activity_edit_text);
        phoneEditText = findViewById(R.id.phone_edit_text);
        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text);

        String[] aa = new String[]{"Borrower", "Investor"};
        ArrayAdapter arrayAda = new ArrayAdapter(this, R.layout.spinner_custom_layout, aa);
        whatyoupretendEditText.setAdapter(arrayAda);

        // Configure Already a member? Login component
        alreadyAmemberLoginButton = this.findViewById(R.id.already_amember_login_button);
        alreadyAmemberLoginButton.setOnClickListener((view) -> {
            this.onAlreadyAMemberLoginPressed();
        });

        // Configure SignUpBtn component
        signupbtnButton = this.findViewById(R.id.signupbtn_button);
        signupbtnButton.setOnClickListener((view) -> {
            onSignUpBtnPressed();
        });

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                finish();
            }
        });

        select_img_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_gallery_image();
            }
        });


    }

    public void onAlreadyAMemberLoginPressed() {

        this.startLoginActivity();
    }

    public void onSignUpBtnPressed() {
        //   FancyToast.makeText(SignupActivity.this,"work",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();

        //   Toast.makeText(this, "work", Toast.LENGTH_SHORT).show();

        String fname = firstNameEditText.getText().toString();
        String lname = lastNameEditText.getText().toString();
        String email = emailEditText.getText().toString();

        String address = addressEditText.getText().toString();
        String whatyoupretend = whatyoupretendEditText.getSelectedItem().toString();
        String fieldofactivity = firstNameEditText.getText().toString();
        String phone = phoneEditText.getText().toString();

        String password = passwordEditText.getText().toString();
        String status = "true";
        String confirmpassword = confirmPasswordEditText.getText().toString();

        if (user_img_url == null) {
            FancyToast.makeText(SignupActivity.this, "Select Image First", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

            return;
        }
        if (!fname.equals("") && !lname.equals("") && !address.equals("") && !email.equals("") && !whatyoupretend.equals("") && !phone.equals("") && !status.equals("") && !fieldofactivity.equals("")) {


            if (user_img_url == null) {
                FancyToast.makeText(SignupActivity.this, "Select Image First", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                return;
            }

            if (!email.contains("@")) {
                FancyToast.makeText(SignupActivity.this, "Incorrect Email", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                return;
            }

            if (!password.equals(confirmpassword)) {
                FancyToast.makeText(SignupActivity.this, "Password Not Matching", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                return;
            }

            if (password.length() < 6) {
                FancyToast.makeText(SignupActivity.this, "Password should be 6 characters or more", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                return;
            }


            catLoadingView.setText("Please Wait ..");
            catLoadingView.show(getSupportFragmentManager(), "");


            StringRequest stringRequest = new StringRequest(Request.Method.POST, Comman.REGISTER_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {


                    try {


                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String code = jsonObject.getString("code");
                        if (code.equals("reg_success")) {

                            catLoadingView.dismiss();
                            FancyToast.makeText(SignupActivity.this, "Registered!", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

                            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                            finish();

                        } else if (code.equals("reg_failed")) {
                            catLoadingView.dismiss();
                            FancyToast.makeText(SignupActivity.this, "User Exists!!!", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    catLoadingView.dismiss();

                    FancyToast.makeText(SignupActivity.this, "Error! is " + error, FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();


                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    //       user_img_url = tvUrl.getText().toString();

                    String image = BitmapConversion.getStringImage(BitmapConversion.getbitmapfromimageview(image_view_profile));


                    System.out.println("img is " + image);


                    Map<String, String> params = new HashMap<String, String>();

                    params.put(Comman.TABLE_USERS_ATTRIBUTES[0], fname);
                    params.put(Comman.TABLE_USERS_ATTRIBUTES[1], lname);
                    params.put(Comman.TABLE_USERS_ATTRIBUTES[2], address);
                    params.put(Comman.TABLE_USERS_ATTRIBUTES[3], whatyoupretend);
                    params.put(Comman.TABLE_USERS_ATTRIBUTES[4], fieldofactivity);
                    params.put(Comman.TABLE_USERS_ATTRIBUTES[5], phone);
                    params.put(Comman.TABLE_USERS_ATTRIBUTES[6], email);
                    params.put(Comman.TABLE_USERS_ATTRIBUTES[7], password);
                    params.put(Comman.TABLE_USERS_ATTRIBUTES[8], status);
                    params.put(Comman.TABLE_USERS_ATTRIBUTES[9], image);


                    return params;
                }
            };

            VollySingltonClass.getmInstance(getApplicationContext()).addToRequsetque(stringRequest);


        } else {

            FancyToast.makeText(SignupActivity.this, "Complete all the fields!!!", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

        }


    }


    public void startAnimationOne() {

        ObjectAnimator animator1 = ObjectAnimator.ofPropertyValuesHolder(signupbtnButton, PropertyValuesHolder.ofKeyframe(View.SCALE_X, Keyframe.ofFloat(0f, 0.3f), Keyframe.ofFloat(0.2f, 1.1f), Keyframe.ofFloat(0.4f, 0.9f), Keyframe.ofFloat(0.6f, 1.03f), Keyframe.ofFloat(0.8f, 0.97f), Keyframe.ofFloat(1f, 1f)), PropertyValuesHolder.ofKeyframe(View.SCALE_Y, Keyframe.ofFloat(0f, 0.3f), Keyframe.ofFloat(0.2f, 1.1f), Keyframe.ofFloat(0.4f, 0.9f), Keyframe.ofFloat(0.6f, 1.03f), Keyframe.ofFloat(0.8f, 0.97f), Keyframe.ofFloat(1f, 1f)));
        animator1.setDuration(2000);
        animator1.setInterpolator(PathInterpolatorCompat.create(0.22f, 0.61f, 0.36f, 1f));

        ObjectAnimator animator2 = ObjectAnimator.ofPropertyValuesHolder(signupbtnButton, PropertyValuesHolder.ofKeyframe(View.ALPHA, Keyframe.ofFloat(0f, 0f), Keyframe.ofFloat(0.8f, 1f), Keyframe.ofFloat(1f, 1f)));
        animator2.setDuration(2000);
        animator2.setInterpolator(PathInterpolatorCompat.create(0.22f, 0.61f, 0.36f, 1f));

        AnimatorSet animatorSet1 = new AnimatorSet();
        animatorSet1.playTogether(animator1, animator2);
        animatorSet1.setTarget(signupbtnButton);

    }


    public void get_gallery_image() {
        Intent sdintent = new Intent(Intent.ACTION_PICK);
        sdintent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        sdintent.setType("image/*");
        startActivityForResult(sdintent, PICK_FROM_GALLARY);
    }


    //open camera
    public void openCameraAndGetImage() {

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, PICK_FROM_CAMERA);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case PICK_FROM_CAMERA: {
                if (resultCode == Activity.RESULT_OK) {
                    Bitmap bitmapImage = (Bitmap) data.getExtras().get("data");

                    user_img_url = bitmapImage;
                    image_view_profile.setImageBitmap(user_img_url);

                }
                break;


            }
            case PICK_FROM_GALLARY: {
                if (requestCode == PICK_FROM_GALLARY) {

                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    System.out.println("myuri is1 " + selectedImage);
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
                            System.out.println("myuri is2 " + newuriis);

                            //   image_view_profile.setImageBitmap(BitmapConversion.uriToBitmap(uri,SignupActivity.this));
                            user_img_url = BitmapConversion.uriToBitmap(uri, SignupActivity.this);
                            image_view_profile.setImageURI(uri);


                            System.out.println("myuri is3 " + user_img_url);

                            cursor.close();
                        } else {


                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            String filePath = cursor.getString(columnIndex);


                            user_img_url = BitmapFactory.decodeFile(filePath);


                            image_view_profile.setImageBitmap(user_img_url);


                            cursor.close();
                        }
                    }

                    break;
                }
            }


        }
    }


    private void startLoginActivity() {

        this.startActivity(LoginActivity.newIntent(this));
    }
}
