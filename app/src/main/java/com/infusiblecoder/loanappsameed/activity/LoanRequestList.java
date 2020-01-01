package com.infusiblecoder.loanappsameed.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.infusiblecoder.loanappsameed.ModelClasses.RequestLoanModel;
import com.infusiblecoder.loanappsameed.R;
import com.infusiblecoder.loanappsameed.adapter.RequestListShowAdapter;

import java.util.ArrayList;

public class LoanRequestList extends AppCompatActivity {

    RequestListShowAdapter requestListShowAdapter;

    RecyclerView recyclerView;

    ArrayList<RequestLoanModel> requestLoanModelArrayList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_request_list);
        recyclerView = findViewById(R.id.rec_view_request_list);

        requestLoanModelArrayList = new ArrayList<RequestLoanModel>();

        requestListShowAdapter = new RequestListShowAdapter(LoanRequestList.this,requestLoanModelArrayList);

        recyclerView.setLayoutManager(new LinearLayoutManager(LoanRequestList.this));
        recyclerView.setAdapter(requestListShowAdapter);
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), HomeActivityDrawar.class));
        finish();
    }
}
