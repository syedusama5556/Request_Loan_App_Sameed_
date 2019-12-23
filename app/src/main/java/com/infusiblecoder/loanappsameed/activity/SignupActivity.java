/**
 * Created by Usama.
 */

package com.infusiblecoder.loanappsameed.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.infusiblecoder.loanappsameed.Helpers.Comman;
import com.infusiblecoder.loanappsameed.Helpers.VollySingltonClass;
import com.infusiblecoder.loanappsameed.R;
import com.roger.catloadinglibrary.CatLoadingView;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class SignupActivity extends AppCompatActivity {

    CatLoadingView catLoadingView;
    private Button alreadyAmemberLoginButton;
    private Button signupbtnButton;
    private ImageView backImageView;
    private TextView welcomeSignupTextView;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText addressEditText;
    private EditText whatyoupretendEditText;
    private EditText fieldOfActivityEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;

    public static Intent newIntent(Context context) {

        // Fill the created intent with the data you want to be passed to this Activity when it's opened.
        return new Intent(context, SignupActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.signup_activity);

        catLoadingView = new CatLoadingView();
        this.init();
    }

    private void init() {


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
        String whatyoupretend = whatyoupretendEditText.getText().toString();
        String fieldofactivity = firstNameEditText.getText().toString();
        String phone = phoneEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String status = "true";
        String confirmpassword = confirmPasswordEditText.getText().toString();

        if (!password.equals(confirmpassword)) {
            FancyToast.makeText(SignupActivity.this, "Password Not Matching", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

            return;
        }

        if (!fname.equals("") && !lname.equals("") && !address.equals("") && !email.equals("") && !whatyoupretend.equals("") && !phone.equals("") && !status.equals("") && !fieldofactivity.equals("")) {


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


                    return params;
                }
            };

            VollySingltonClass.getmInstance(getApplicationContext()).addToRequsetque(stringRequest);


        } else {

            FancyToast.makeText(SignupActivity.this, "Complete all the fields!!!", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

        }


    }

    private void startLoginActivity() {

        this.startActivity(LoginActivity.newIntent(this));
    }
}
