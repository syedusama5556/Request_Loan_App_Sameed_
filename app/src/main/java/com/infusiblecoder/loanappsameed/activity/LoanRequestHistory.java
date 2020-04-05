package com.infusiblecoder.loanappsameed.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.infusiblecoder.loanappsameed.Helpers.Comman;
import com.infusiblecoder.loanappsameed.R;
import com.infusiblecoder.loanappsameed.fragment.MyAppliedLoans;
import com.infusiblecoder.loanappsameed.fragment.RecivedLoanRequests;

public class LoanRequestHistory extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_request_history);

        prefs = getSharedPreferences(Comman.SHAREDPREF_USERDATA, MODE_PRIVATE);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new LoanRequestHistory.SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), HomeActivityDrawar.class);

        intent.putExtra("nameofact", "request");
        startActivity(intent);
        finish();
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            String islender = prefs.getString("islender", "no");
            Fragment fragment = null;
            switch (position) {
                case 0: {
                    fragment = new RecivedLoanRequests();
                    break;
                }
                case 1: {


//                    if (!islender.equals("no") && islender.equals("true")) {
//
//
//                        fragment = new EmptyFragmentView();
//
//                    } else if (!islender.equals("no") && islender.equals("false")) {
//
//
//
//                    }

                    fragment = new MyAppliedLoans();

                    break;
                }
                default: {
                    Comman.showErrorToast(LoanRequestHistory.this, "Stop!");
                }

            }
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }
    }
}
