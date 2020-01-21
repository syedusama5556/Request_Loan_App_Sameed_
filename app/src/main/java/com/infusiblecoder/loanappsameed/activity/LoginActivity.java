/**
 * Created by Usama.
 */

package com.infusiblecoder.loanappsameed.activity;

import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.animation.PathInterpolatorCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.infusiblecoder.loanappsameed.Helpers.Comman;
import com.infusiblecoder.loanappsameed.Helpers.VollySingltonClass;
import com.infusiblecoder.loanappsameed.ModelClasses.UserTableData;
import com.infusiblecoder.loanappsameed.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    ImageView backimg;
    private TextView welcomeLoginTextView;
    private EditText emailEdittext, password_edit_text;
    private Button loginButton;
    private Button forgotPasswordButton;
    private Button donThaveAaccountSignUpButton;
    private ConstraintLayout usernameConstraintLayout;

    private ConstraintLayout passwordCopy8ConstraintLayout;

    public static Intent newIntent(Context context) {

        // Fill the created intent with the data you want to be passed to this Activity when it's opened.
        return new Intent(context, LoginActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.login_activity);
        this.init();

        this.startAnimationOne();
    }

    private void init() {


        usernameConstraintLayout = this.findViewById(R.id.username_constraint_layout);
        backimg = this.findViewById(R.id.back_image_view);


        passwordCopy8ConstraintLayout = this.findViewById(R.id.password_copy8_constraint_layout);


        // Configure Username component
        emailEdittext = this.findViewById(R.id.username_edit_text);
        password_edit_text = this.findViewById(R.id.password_edit_text);
        // Configure Password Copy 8 component

        // Configure Login component
        loginButton = this.findViewById(R.id.login_button);
        loginButton.setOnClickListener((view) -> {
            this.onLoginPressed();
        });

        // Configure Forgot password? component
        forgotPasswordButton = this.findViewById(R.id.forgot_password_button);
        forgotPasswordButton.setOnClickListener((view) -> {
            this.onForgotPasswordPressed();
        });

        // Configure Don’t have a account? Sign up component
        donThaveAaccountSignUpButton = this.findViewById(R.id.don_thave_aaccount_sign_up_button);
        donThaveAaccountSignUpButton.setOnClickListener((view) -> {
            this.onDonTHaveAAccountSignUpPressed();
        });


        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void onLoginPressed() {


        String email = emailEdittext.getText().toString();
        String pass = password_edit_text.getText().toString();


        if (!pass.equals("") && !email.equals("")) {


            StringRequest stringRequest = new StringRequest(Request.Method.POST, Comman.LOGIN_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {


                    try {
                        JSONArray jsonArray = new JSONArray(response);

                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String code = jsonObject.getString("code");
                        if (code.equals("login_failed")) {


                            Comman.showErrorToast(LoginActivity.this, "Login Failed " + jsonObject.getString("message"));


                        } else {

                            Comman.showSucdessToast(LoginActivity.this, "Login Successful\n Welcome " + jsonObject.getString("address"));


//                            $firstname = "firstname";
//                            $lastname = "lastname";
//                            $address = "address";
//                            $whatyoupretend = "whatyoupretend";
//                            $fieldofactivity = "fieldofactivity";
//
//                            $phone = "phone";
//                            $email = "email5600";
//                            $password = "password";
//                            $status = "status";
//                            $img_in_base64 = "/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDABALDA4MChAODQ4SERATGCgaGBYWGDEjJR0oOjM9PDkz";
//


//                            UserTableData userTableData = new UserTableData(jsonObject.getString("user_id"), jsonObject.getString("firstname")
//                                    , jsonObject.getString("lastname"), jsonObject.getString("address"),
//                                    jsonObject.getString("whatyoupretend"), jsonObject.getString("fieldofactivity"), jsonObject.getString("phone")
//                                    , jsonObject.getString("email"), jsonObject.getString("password"), jsonObject.getString("status"),
//                                    jsonObject.getString("user_img_url"));


                            JsonParser parser = new JsonParser();
                            JsonElement mJson = parser.parse(response);
                            Gson gson = new Gson();
                            UserTableData userTableData = gson.fromJson(jsonObject.toString(), UserTableData.class);


//                            ArrayList<UserTableData> userTableData1 = new ArrayList<>();
//
//                            Type type = new TypeToken<ArrayList<UserTableData>>(){}.getType();
//                            userTableData1 = gson.fromJson(response, type);
//
//                            System.out.println("qwerty data is "+userTableData1.get(0).email);
//                            System.out.println("qwerty data is "+userTableData1.get(0).address);

//                            "user_id","firstname", "lastname", "address", "whatyoupretend",
//                                    "fieldofactivity", "phone", "email", "password", "status","user_img_url"};


                            SharedPreferences.Editor editor = getSharedPreferences(Comman.SHAREDPREF_USERDATA, MODE_PRIVATE).edit();
                            editor.putString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[0], userTableData.user_id);
                            editor.putString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[1], userTableData.firstname);
                            editor.putString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[2], userTableData.lastname);
                            editor.putString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[3], userTableData.address);
                            editor.putString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[4], userTableData.whatyoupretend);
                            editor.putString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[5], userTableData.fieldofactivity);
                            editor.putString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[6], userTableData.phone);
                            editor.putString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[7], userTableData.email);
                            editor.putString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[8], userTableData.password);
                            editor.putString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[9], userTableData.status);
                            editor.putString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[10], Comman.START_URL + userTableData.user_img_url);
                            editor.putString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[11], userTableData.user_img_url);


                            editor.apply();

                            Intent intent = new Intent(getApplicationContext(), HomeActivityDrawar.class);
                            startActivity(intent);
                            finish();
                        }
                    } catch (JSONException e) {
                        Comman.showErrorToast(LoginActivity.this, "error is  json" + e.getMessage());

                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Comman.showErrorToast(LoginActivity.this, "error is " + error);


                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("email", emailEdittext.getText().toString());
                    params.put("password", password_edit_text.getText().toString());
                    return params;
                }
            };
            VollySingltonClass.getmInstance(getApplicationContext()).addToRequsetque(stringRequest);


        } else {

            Comman.showErrorToast(LoginActivity.this, "Enter Missing Fields");
        }


    }

    public void onForgotPasswordPressed() {

    }

    public void onDonTHaveAAccountSignUpPressed() {

        this.startSignupActivity();
    }

    private void startSignupActivity() {

        this.startActivity(SignupActivity.newIntent(this));
    }

    public void startAnimationOne() {

        ObjectAnimator animator1 = ObjectAnimator.ofPropertyValuesHolder(loginButton, PropertyValuesHolder.ofKeyframe(View.SCALE_X, Keyframe.ofFloat(0f, 0.3f), Keyframe.ofFloat(0.2f, 1.1f), Keyframe.ofFloat(0.4f, 0.9f), Keyframe.ofFloat(0.6f, 1.03f), Keyframe.ofFloat(0.8f, 0.97f), Keyframe.ofFloat(1f, 1f)), PropertyValuesHolder.ofKeyframe(View.SCALE_Y, Keyframe.ofFloat(0f, 0.3f), Keyframe.ofFloat(0.2f, 1.1f), Keyframe.ofFloat(0.4f, 0.9f), Keyframe.ofFloat(0.6f, 1.03f), Keyframe.ofFloat(0.8f, 0.97f), Keyframe.ofFloat(1f, 1f)));
        animator1.setDuration(2000);
        animator1.setInterpolator(PathInterpolatorCompat.create(0.22f, 0.61f, 0.36f, 1f));

        ObjectAnimator animator2 = ObjectAnimator.ofPropertyValuesHolder(loginButton, PropertyValuesHolder.ofKeyframe(View.ALPHA, Keyframe.ofFloat(0f, 0f), Keyframe.ofFloat(0.8f, 1f), Keyframe.ofFloat(1f, 1f)));
        animator2.setDuration(2000);
        animator2.setInterpolator(PathInterpolatorCompat.create(0.22f, 0.61f, 0.36f, 1f));

        AnimatorSet animatorSet1 = new AnimatorSet();
        animatorSet1.playTogether(animator1, animator2);
        animatorSet1.setTarget(loginButton);

        ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(usernameConstraintLayout, PropertyValuesHolder.ofFloat(View.TRANSLATION_X, -327f, 0f));
        animator3.setDuration(1200);
        animator3.setInterpolator(PathInterpolatorCompat.create(0f, 0f, 1f, 1f));

        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(animator3);
        animatorSet2.setTarget(usernameConstraintLayout);

        ObjectAnimator animator4 = ObjectAnimator.ofPropertyValuesHolder(passwordCopy8ConstraintLayout, PropertyValuesHolder.ofFloat(View.TRANSLATION_X, -327f, 0f));
        animator4.setDuration(1700);
        animator4.setInterpolator(PathInterpolatorCompat.create(0f, 0f, 1f, 1f));

        AnimatorSet animatorSet3 = new AnimatorSet();
        animatorSet3.playTogether(animator4);
        animatorSet3.setTarget(passwordCopy8ConstraintLayout);

        AnimatorSet animatorSet4 = new AnimatorSet();
        animatorSet4.playTogether(animatorSet1, animatorSet2, animatorSet3);
        animatorSet4.start();
    }
}
