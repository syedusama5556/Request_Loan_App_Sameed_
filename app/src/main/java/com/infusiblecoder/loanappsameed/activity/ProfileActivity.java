/**
 * Created by Usama.
 */

package com.infusiblecoder.loanappsameed.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.infusiblecoder.loanappsameed.Helpers.Comman;
import com.infusiblecoder.loanappsameed.R;


public class ProfileActivity extends AppCompatActivity {

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

        // Configure Ricardo Joseph component
        ricardoJosephTextView = this.findViewById(R.id.ricardo_joseph_text_view);

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

        // Configure Profile Settings component
        profileSettingsButton = this.findViewById(R.id.profile_settings_button);
        profileSettingsButton.setOnClickListener((view) -> {
            this.onProfileSettingsPressed();
        });

        // Configure Change your password component
        changeYourPasswordTextView = this.findViewById(R.id.change_your_password_text_view);

        // Configure Privacy component
        privacyButton = this.findViewById(R.id.privacy_button);
        privacyButton.setOnClickListener((view) -> {
            this.onPrivacyPressed();
        });

        // Configure Change your notifica component
        changeYourNotificaTextView = this.findViewById(R.id.change_your_notifica_text_view);

        // Configure Notifications component
        notificationsButton = this.findViewById(R.id.notifications_button);
        notificationsButton.setOnClickListener((view) -> {
            this.onNotificationsPressed();
        });

        // Configure Home component

    }
public void loadAllData(){



    SharedPreferences prefs = getSharedPreferences(Comman.SHAREDPREF_USERDATA, MODE_PRIVATE);
    String user_id = prefs.getString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[0], "no value");//"No name defined" is the default value.


    String fname = prefs.getString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[1], "no value");
    String lname = prefs.getString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[2], "no value");
    String email = prefs.getString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[7], "no value");

    String img_url = prefs.getString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[10], "no value");



    if (!user_id.equals("") && !user_id.equals("no value")) {








    } else {

        Comman.showErrorToast(ProfileActivity.this, "Error");
    }



}
    public void onEditPressed() {

    }

    public void onProfileSettingsPressed() {

    }

    public void onPrivacyPressed() {

    }

    public void onNotificationsPressed() {

    }
}
