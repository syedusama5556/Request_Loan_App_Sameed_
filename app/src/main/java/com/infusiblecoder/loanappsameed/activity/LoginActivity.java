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
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.infusiblecoder.loanappsameed.Helpers.Comman;
import com.infusiblecoder.loanappsameed.Helpers.VollySingltonClass;
import com.infusiblecoder.loanappsameed.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

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
    }

    public void onLoginPressed() {


        String pass = password_edit_text.getText().toString();

        String email = emailEdittext.getText().toString();


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
                            Comman.showSucdessToast(LoginActivity.this, "Login Successful!!");


                            SharedPreferences.Editor editor = getSharedPreferences("saveid", MODE_PRIVATE).edit();
                            //     editor.putString("userid", userId_forIntent);
                            editor.putString("type", "normal");
                            editor.apply();

                            Intent intent = new Intent(getApplicationContext(), HomeActivityActivity.class);

                            startActivity(intent);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<String, String>();
                    //  params.put("username", username);
                    //   params.put("password", password);
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
