/**
 * Created by Usama.
 */

package com.infusiblecoder.loanappsameed.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.infusiblecoder.loanappsameed.R;


public class OnboardActivity extends AppCompatActivity {

    private TextView inHacHabitassePlaTextView;
    private TextView donecFacilisisTortTextView;
    private Button loginButton;
    private Button signUpButton;

    public static Intent newIntent(Context context) {

        // Fill the created intent with the data you want to be passed to this Activity when it's opened.
        return new Intent(context, OnboardActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.onboard_activity);
        this.init();
    }

    private void init() {

        // Configure In hac habitasse pla component
        inHacHabitassePlaTextView = this.findViewById(R.id.in_hac_habitasse_pla_text_view);

        // Configure Donec facilisis tort component
        donecFacilisisTortTextView = this.findViewById(R.id.donec_facilisis_tort_text_view);

        // Configure Login component
        loginButton = this.findViewById(R.id.login_button);
        loginButton.setOnClickListener((view) -> {
            this.onLoginPressed();
        });

        // Configure Sign up component
        signUpButton = this.findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener((view) -> {
            this.onSignUpPressed();
        });
    }

    public void onLoginPressed() {

        this.startLoginActivity();
    }

    public void onSignUpPressed() {

        this.startSignupActivity();
    }

    private void startSignupActivity() {

        this.startActivity(SignupActivity.newIntent(this));
    }

    private void startLoginActivity() {

        this.startActivity(LoginActivity.newIntent(this));
    }
}
