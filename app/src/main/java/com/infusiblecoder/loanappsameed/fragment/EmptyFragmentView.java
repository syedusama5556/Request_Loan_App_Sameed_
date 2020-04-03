package com.infusiblecoder.loanappsameed.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.infusiblecoder.loanappsameed.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmptyFragmentView extends Fragment {

    public EmptyFragmentView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_empty_view, container, false);
    }
}
