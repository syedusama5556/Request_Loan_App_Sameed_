package com.infusiblecoder.loanappsameed.ModelClasses;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class RequestLoanModel  implements Serializable {


    public String loan_id, loan_request_code, user_full_name, user_img_url_request, loan_amount, loan_purpose, loan_collateral, loan_market_value, loan_type, loan_due_date, loan_doc_vehicle_id_url, loan_doc_owner_id_url, loan_doc_insurance_url;


    public RequestLoanModel(String loan_id, String loan_request_code, String user_full_name, String user_img_url_request, String loan_amount, String loan_purpose, String loan_collateral, String loan_market_value, String loan_type, String loan_due_date, String loan_doc_vehicle_id_url, String loan_doc_owner_id_url, String loan_doc_insurance_url) {
        this.loan_id = loan_id;
        this.loan_request_code = loan_request_code;
        this.user_full_name = user_full_name;
        this.user_img_url_request = user_img_url_request;
        this.loan_amount = loan_amount;
        this.loan_purpose = loan_purpose;
        this.loan_collateral = loan_collateral;
        this.loan_market_value = loan_market_value;
        this.loan_type = loan_type;
        this.loan_due_date = loan_due_date;
        this.loan_doc_vehicle_id_url = loan_doc_vehicle_id_url;
        this.loan_doc_owner_id_url = loan_doc_owner_id_url;
        this.loan_doc_insurance_url = loan_doc_insurance_url;
    }


}
