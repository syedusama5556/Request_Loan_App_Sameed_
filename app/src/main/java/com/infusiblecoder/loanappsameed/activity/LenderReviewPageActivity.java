/**
 *  Created by [Author].
 */

package com.infusiblecoder.loanappsameed.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.infusiblecoder.loanappsameed.R;

public class LenderReviewPageActivity extends AppCompatActivity {
	
	public static Intent newIntent(Context context) {
	
		// Fill the created intent with the data you want to be passed to this Activity when it's opened.
		return new Intent(context, LenderReviewPageActivity.class);
	}
	
	private TextView usdTextView;
	private Button rejectButton;
	private ImageButton acceptButton;
	private ImageButton reviewButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.lender_review_page_activity);
		this.init();
	}
	
	private void init() {
	
		// Configure $30,000 USD component
		usdTextView = this.findViewById(R.id.usd_text_view);
		SpannableString usdTextViewText = new SpannableString(this.getString(R.string.lender_review_page_activity_usd_text_view_text));
		usdTextViewText.setSpan(new RelativeSizeSpan(0.56f), 7, 11, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		usdTextView.setText(usdTextViewText);
		
		// Configure reject component
		rejectButton = this.findViewById(R.id.reject_button);
		rejectButton.setOnClickListener((view) -> {
	this.onGroup45Pressed();
});
		
		// Configure accept component
		acceptButton = this.findViewById(R.id.accept_button);
		acceptButton.setOnClickListener((view) -> {
	this.onGroup44Pressed();
});
		
		// Configure review component
		reviewButton = this.findViewById(R.id.review_button);
		reviewButton.setOnClickListener((view) -> {
	this.onGroup46Pressed();
});
	}
	
	public void onGroup45Pressed() {
	
	}
	
	public void onGroup44Pressed() {
	
	}
	
	public void onGroup46Pressed() {
	
	}
}
