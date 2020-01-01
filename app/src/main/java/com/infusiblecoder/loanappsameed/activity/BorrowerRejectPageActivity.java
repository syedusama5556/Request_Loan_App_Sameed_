/**
 * Created by [Author].
 */

package com.infusiblecoder.loanappsameed.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.infusiblecoder.loanappsameed.R;

public class BorrowerRejectPageActivity extends AppCompatActivity {

    private Button repostButton;
    private Button counterButton;
    private Button cancelButton;
    private TextView usdTextView;

    public static Intent newIntent(Context context) {

        // Fill the created intent with the data you want to be passed to this Activity when it's opened.
        return new Intent(context, BorrowerRejectPageActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.borrower_reject_page_activity);
        this.init();
    }

    private void init() {

        // Configure repost component
        repostButton = this.findViewById(R.id.repost_button);
        repostButton.setOnClickListener((view) -> {
            this.onViewPressed();
        });

        // Configure counter component
        counterButton = this.findViewById(R.id.counter_button);
        counterButton.setOnClickListener((view) -> {
            this.onViewThreePressed();
        });

        // Configure cancel component
        cancelButton = this.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener((view) -> {
            this.onViewTwoPressed();
        });

        // Configure $30,000,00 USD component
        usdTextView = this.findViewById(R.id.usd_text_view);
        SpannableString usdTextViewText = new SpannableString(this.getString(R.string.borrower_reject_page_activity_usd_text_view_text));
        usdTextViewText.setSpan(new RelativeSizeSpan(0.56f), 10, 14, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        usdTextView.setText(usdTextViewText);
    }

    public void onViewPressed() {

    }

    public void onViewThreePressed() {

    }

    public void onViewTwoPressed() {

    }
}
