package com.infusiblecoder.loanappsameed.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.MenuItemCompat;
import androidx.core.view.animation.PathInterpolatorCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.google.android.material.navigation.NavigationView;
import com.infusiblecoder.loanappsameed.BuildConfig;
import com.infusiblecoder.loanappsameed.Helpers.Comman;
import com.infusiblecoder.loanappsameed.Helpers.VollySingltonClass;
import com.infusiblecoder.loanappsameed.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HomeActivityDrawar extends AppCompatActivity {


    ImageView choose_aloan_type_text_view;
    int rowsCount = 0;
    private ConstraintLayout personalloanConstraintLayout;
    private Button instantLoanForPerButton;
    private ConstraintLayout carloanConstraintLayout;
    private Button instantLoanForAnbutton;
    private ConstraintLayout commercialConstraintLayout;
    private Button instantLoanForCoButton;
    private ConstraintLayout travelloanConstraintLayout;
    private Button instantLoanForTraButton;
    private ConstraintLayout houseloanConstraintLayout;
    private Button instantLoanForAntwoButton;
    private ConstraintLayout taxloanConstraintLayout;
    private Button instantLoanForTaxButton;
    private ConstraintLayout otherloanConstraintLayout;
    private Button instantLoanForOthButton;
    private AppBarConfiguration mAppBarConfiguration;
    private TextView username;
    private TextView userEmail;
    private CircularImageView userprofilePicture;
    private TextView notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_drawar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        choose_aloan_type_text_view = findViewById(R.id.choose_aloan_type_text_view);

        choose_aloan_type_text_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(navigationView);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {


                    case R.id.nav_profile: {

                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        finish();
                        break;
                    }
                    case R.id.nav_loan_request_list: {

                        startActivity(new Intent(getApplicationContext(), LoanRequestList.class));
                        finish();
                        break;
                    }
                    case R.id.nav_loan_request_history: {


                        break;
                    }

                    case R.id.nav_notifications_list: {

                        startActivity(new Intent(getApplicationContext(), NotificationsListUserRequests.class));
                        finish();
                        break;
                    }

                    case R.id.nav_share: {

                        try {
                            Intent shareIntent = new Intent(Intent.ACTION_SEND);
                            shareIntent.setType("text/plain");
                            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                            String shareMessage = "\nLet me recommend you this application\n\n";
                            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                            startActivity(Intent.createChooser(shareIntent, "choose one"));
                        } catch (Exception e) {
                            //e.toString();
                        }

                        break;
                    }
                    case R.id.nav_send: {


                        break;
                    }


                }


                return false;
            }
        });


        username = navigationView.getHeaderView(0).findViewById(R.id.user_name_text_view_test);
        userEmail = navigationView.getHeaderView(0).findViewById(R.id.email_test);
        userprofilePicture = navigationView.getHeaderView(0).findViewById(R.id.imageView_test);


//These lines should be added in the OnCreate() of your main activity
        notifications = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.nav_notifications_list));


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_profile, R.id.nav_loan_request_list, R.id.nav_loan_request_history,
                R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();


        this.init();

        loadAllData();

    }

    private void initializeCountDrawer() {
        //Gravity property aligns the text
        notifications.setGravity(Gravity.CENTER_VERTICAL);
        notifications.setTypeface(null, Typeface.BOLD);
        notifications.setTextColor(getResources().getColor(R.color.color_accent));


        notifications.setText("" + rowsCount);

    }


    public void getRowsCountRequests() {

        SharedPreferences prefs = getSharedPreferences(Comman.SHAREDPREF_USERDATA, MODE_PRIVATE);
        String userid = prefs.getString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[0], "no value");//"No name defined" is the default value.


        System.out.println("work herre 1");

        if (!userid.equals("") && !userid.equals("no value")) {

            System.out.println("work herre 2");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Comman.GET_ALL_UNSEEN_REQUESTS_TABLE_DATA_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    System.out.println("work herre 3");
                    try {
                        JSONArray jsonArray = new JSONArray(response);

                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String code = jsonObject.getString("code");

                        System.out.println("work herre 7" + code);
                        if (code.equals("failed")) {
                            System.out.println("work herre 4" +"Failed to get notifications error is " + jsonObject.getString("message"));

                           // Comman.showErrorToast(HomeActivityDrawar.this, "Failed to get notifications error is " + jsonObject.getString("message"));


                        } else {
                            System.out.println("work herre 5");
                            //  Toast.makeText(HomeActivityDrawar.this, "res is "+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                            rowsCount = Integer.parseInt(jsonObject.getString("message"));

                            initializeCountDrawer();

                        }
                    } catch (JSONException e) {

                        System.out.println("work herre 6" + e.getMessage());

                        Comman.showErrorToast(HomeActivityDrawar.this, "Error is " + e.getMessage());

                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Comman.showErrorToast(HomeActivityDrawar.this, "error is " + error);


                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    System.out.println("work herre 8");

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user_id", userid);

                    return params;
                }
            };
            VollySingltonClass.getmInstance(getApplicationContext()).addToRequsetque(stringRequest);


        } else {
            System.out.println("work herre 9");

            Comman.showErrorToast(HomeActivityDrawar.this, "Enter Missing Fields");
        }


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

            userEmail.setText(email);
            username.setText(fullname);

            Glide.with(HomeActivityDrawar.this).load(img_url).placeholder(R.mipmap.ic_launcher).into(userprofilePicture);


        } else {

            Comman.showErrorToast(HomeActivityDrawar.this, "Error");
        }

        getRowsCountRequests();

    }


    private void init() {

        // Configure PersonalLoan component
        personalloanConstraintLayout = this.findViewById(R.id.personalloan_constraint_layout);

        // Configure Instant loan for per component
        instantLoanForPerButton = this.findViewById(R.id.instant_loan_for_per_button);
        instantLoanForPerButton.setOnClickListener((view) -> {
            this.onInstantLoanForPerPressed();
        });

        // Configure carLoan component
        carloanConstraintLayout = this.findViewById(R.id.carloan_constraint_layout);

        // Configure Instant loan for a N component
        instantLoanForAnbutton = this.findViewById(R.id.instant_loan_for_anbutton);
        instantLoanForAnbutton.setOnClickListener((view) -> {
            this.onInstantLoanForANPressed();
        });

        // Configure Commercial component
        commercialConstraintLayout = this.findViewById(R.id.commercial_constraint_layout);

        // Configure Instant loan for  co component
        instantLoanForCoButton = this.findViewById(R.id.instant_loan_for_co_button);
        instantLoanForCoButton.setOnClickListener((view) -> {
            this.onInstantLoanForCoPressed();
        });

        // Configure TravelLoan component
        travelloanConstraintLayout = this.findViewById(R.id.travelloan_constraint_layout);

        // Configure Instant loan for tra component
        instantLoanForTraButton = this.findViewById(R.id.instant_loan_for_tra_button);
        instantLoanForTraButton.setOnClickListener((view) -> {
            this.onInstantLoanForTraPressed();
        });

        // Configure HouseLoan component
        houseloanConstraintLayout = this.findViewById(R.id.houseloan_constraint_layout);

        // Configure Instant loan for a n component
        instantLoanForAntwoButton = this.findViewById(R.id.instant_loan_for_antwo_button);
        instantLoanForAntwoButton.setOnClickListener((view) -> {
            this.onInstantLoanForANTwoPressed();
        });

        // Configure TaxLoan component
        taxloanConstraintLayout = this.findViewById(R.id.taxloan_constraint_layout);

        // Configure Instant loan for Tax component
        instantLoanForTaxButton = this.findViewById(R.id.instant_loan_for_tax_button);
        instantLoanForTaxButton.setOnClickListener((view) -> {
            this.onInstantLoanForTaxPressed();
        });

        // Configure OtherLoan component
        otherloanConstraintLayout = this.findViewById(R.id.otherloan_constraint_layout);

        // Configure Instant loan for oth component
        instantLoanForOthButton = this.findViewById(R.id.instant_loan_for_oth_button);
        instantLoanForOthButton.setOnClickListener((view) -> {
            this.onInstantLoanForOthPressed();
        });


        startAnimationOne();

    }

    public void onInstantLoanForPerPressed() {
        Intent intent = new Intent(getApplicationContext(), RequestLoanActivity.class);
        intent.putExtra("loan_type", "personal loan");
        startActivity(intent);
        finish();
    }

    public void onInstantLoanForANPressed() {
        Intent intent = new Intent(getApplicationContext(), RequestLoanActivity.class);
        intent.putExtra("loan_type", "car loan");
        startActivity(intent);
        finish();
    }

    public void onInstantLoanForCoPressed() {
        Intent intent = new Intent(getApplicationContext(), RequestLoanActivity.class);
        intent.putExtra("loan_type", "commercial loan");
        startActivity(intent);
        finish();
    }

    public void onInstantLoanForTraPressed() {
        Intent intent = new Intent(getApplicationContext(), RequestLoanActivity.class);
        intent.putExtra("loan_type", "travel loan");
        startActivity(intent);
        finish();
    }

    public void onInstantLoanForANTwoPressed() {
        Intent intent = new Intent(getApplicationContext(), RequestLoanActivity.class);
        intent.putExtra("loan_type", "house loan");
        startActivity(intent);
        finish();
    }

    public void onInstantLoanForTaxPressed() {
        Intent intent = new Intent(getApplicationContext(), RequestLoanActivity.class);
        intent.putExtra("loan_type", "tax loan");
        startActivity(intent);
        finish();
    }

    public void onInstantLoanForOthPressed() {
        Intent intent = new Intent(getApplicationContext(), RequestLoanActivity.class);
        intent.putExtra("loan_type", "other loan");
        startActivity(intent);
        finish();
    }

    public void startAnimationOne() {

        ObjectAnimator animator1 = ObjectAnimator.ofPropertyValuesHolder(personalloanConstraintLayout, PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, -100f, 0f));
        animator1.setDuration(1200);
        animator1.setInterpolator(PathInterpolatorCompat.create(0f, 0f, 1f, 1f));

        AnimatorSet animatorSet1 = new AnimatorSet();
        animatorSet1.playTogether(animator1);
        animatorSet1.setTarget(personalloanConstraintLayout);

        ObjectAnimator animator2 = ObjectAnimator.ofPropertyValuesHolder(carloanConstraintLayout, PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, -200f, 0f));
        animator2.setDuration(1200);
        animator2.setInterpolator(PathInterpolatorCompat.create(0f, 0f, 1f, 1f));

        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(animator2);
        animatorSet2.setTarget(carloanConstraintLayout);

        ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(commercialConstraintLayout, PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, -150f, 0f));
        animator3.setDuration(1200);
        animator3.setInterpolator(PathInterpolatorCompat.create(0f, 0f, 1f, 1f));

        AnimatorSet animatorSet3 = new AnimatorSet();
        animatorSet3.playTogether(animator3);
        animatorSet3.setTarget(commercialConstraintLayout);

        ObjectAnimator animator4 = ObjectAnimator.ofPropertyValuesHolder(travelloanConstraintLayout, PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, -180f, 0f));
        animator4.setDuration(1200);
        animator4.setInterpolator(PathInterpolatorCompat.create(0f, 0f, 1f, 1f));

        AnimatorSet animatorSet4 = new AnimatorSet();
        animatorSet4.playTogether(animator4);
        animatorSet4.setTarget(travelloanConstraintLayout);

        ObjectAnimator animator5 = ObjectAnimator.ofPropertyValuesHolder(houseloanConstraintLayout, PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, -180f, 0f));
        animator5.setDuration(1200);
        animator5.setInterpolator(PathInterpolatorCompat.create(0f, 0f, 1f, 1f));

        AnimatorSet animatorSet5 = new AnimatorSet();
        animatorSet5.playTogether(animator5);
        animatorSet5.setTarget(houseloanConstraintLayout);

        ObjectAnimator animator6 = ObjectAnimator.ofPropertyValuesHolder(taxloanConstraintLayout, PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, -220f, 0f));
        animator6.setDuration(1200);
        animator6.setInterpolator(PathInterpolatorCompat.create(0f, 0f, 1f, 1f));

        AnimatorSet animatorSet6 = new AnimatorSet();
        animatorSet6.playTogether(animator6);
        animatorSet6.setTarget(taxloanConstraintLayout);

        ObjectAnimator animator7 = ObjectAnimator.ofPropertyValuesHolder(otherloanConstraintLayout, PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, -220f, 0f));
        animator7.setDuration(1200);
        animator7.setInterpolator(PathInterpolatorCompat.create(0f, 0f, 1f, 1f));

        AnimatorSet animatorSet7 = new AnimatorSet();
        animatorSet7.playTogether(animator7);
        animatorSet7.setTarget(otherloanConstraintLayout);

        AnimatorSet animatorSet8 = new AnimatorSet();
        animatorSet8.playTogether(animatorSet1, animatorSet2, animatorSet3, animatorSet4, animatorSet5, animatorSet6, animatorSet7);
        animatorSet8.start();
    }

    public void logout_Task(View view) {


        showLogoutDialog();

    }

    void showLogoutDialog() {
        // Create Alert using Builder
        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(this)
                .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                .setTitle("Log out")
                .setMessage("Are You Sure You Want To Logout?")
                .addButton("Yes", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (dialog, which) -> {
                    Comman.showSucdessToast(HomeActivityDrawar.this, "Logged Out");
                    dialog.dismiss();

                    SharedPreferences.Editor editor = getSharedPreferences(Comman.SHAREDPREF_USERDATA, MODE_PRIVATE).edit();
                    editor.putString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[0], "");
                    editor.putString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[1], "");
                    editor.putString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[2], "");
                    editor.putString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[3], "");
                    editor.putString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[4], "");
                    editor.putString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[5], "");
                    editor.putString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[6], "");
                    editor.putString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[7], "");
                    editor.putString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[8], "");
                    editor.putString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[9], "");
                    editor.putString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[10], "");

                    editor.apply();

                    startActivity(new Intent(HomeActivityDrawar.this, LoginActivity.class));
                    finish();
                }).addButton("No", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (dialog, which) -> {
                    dialog.dismiss();

                });

// Show the alert
        builder.show();
    }

}
