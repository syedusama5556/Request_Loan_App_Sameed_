package com.infusiblecoder.loanappsameed.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.infusiblecoder.loanappsameed.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SentedLoanRequests extends Fragment {


    public SentedLoanRequests() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sented_loan_requests, container, false);
    }

}
