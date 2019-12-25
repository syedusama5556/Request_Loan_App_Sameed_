/**
 * Created by Usama.
 */

package com.infusiblecoder.loanappsameed.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.infusiblecoder.loanappsameed.R;


public class RequestLoadActivity extends AppCompatActivity {

    private ImageButton selectdateButton;
    private LinearLayout uploadbtnButton;
    private Button postarequestButton;

    public static Intent newIntent(Context context) {

        // Fill the created intent with the data you want to be passed to this Activity when it's opened.
        return new Intent(context, RequestLoadActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.request_load_activity);
        this.init();
    }

    private void init() {

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
    }

    public void onSelectDatePressed() {

    }

    public void onUploadBtnPressed() {

    }

    public void onPostARequestPressed() {

    }
}
