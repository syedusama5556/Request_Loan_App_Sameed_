/**
 * Created by Usama.
 */

package com.infusiblecoder.loanappsameed.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.animation.PathInterpolatorCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.infusiblecoder.loanappsameed.Helpers.Comman;
import com.infusiblecoder.loanappsameed.R;


public class HomeActivityActivity extends AppCompatActivity {

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

    public static Intent newIntent(Context context) {

        // Fill the created intent with the data you want to be passed to this Activity when it's opened.
        return new Intent(context, HomeActivityActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.home_activity_activity);
        this.init();
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

    }

    public void onInstantLoanForANPressed() {

    }

    public void onInstantLoanForCoPressed() {

    }

    public void onInstantLoanForTraPressed() {

    }

    public void onInstantLoanForANTwoPressed() {

    }

    public void onInstantLoanForTaxPressed() {

    }

    public void onInstantLoanForOthPressed() {

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

    void showLogoutDialog(){
        // Create Alert using Builder
        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(this)
                .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                .setTitle("Log out")
                .setMessage("Are You Sure You Want To Logout?")
                .addButton("Yes", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (dialog, which) -> {
                    Comman.showSucdessToast(HomeActivityActivity.this, "Logged Out");
                    dialog.dismiss();

                    SharedPreferences.Editor editor = getSharedPreferences(Comman.SHAREDPREF_USERDATA, MODE_PRIVATE).edit();
                    editor.putString(Comman.SHAREDPREF_USERDATA_ATTRIBUTES[0],"" );
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

                    startActivity(new Intent(HomeActivityActivity.this,LoginActivity.class));
                    finish();
                }).addButton("No", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (dialog, which) -> {
                    dialog.dismiss();

                });

// Show the alert
        builder.show();
    }
}
