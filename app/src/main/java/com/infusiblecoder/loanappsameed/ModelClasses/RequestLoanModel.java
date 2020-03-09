package com.infusiblecoder.loanappsameed.ModelClasses;

import java.io.Serializable;

public class RequestLoanModel implements Serializable {

    public String loan_id, user_id, loan_request_code, user_full_name, user_img_url_request, loan_amount, loan_purpose, loan_collateral, loan_market_value, loan_borrowing_rate, loan_loan_ratio, loan_type, loan_duration, loan_paid_out_date, loan_due_date, loan_doc_urls, loan_status;

    public RequestLoanModel(String loan_id, String user_id, String loan_request_code, String user_full_name, String user_img_url_request, String loan_amount, String loan_purpose, String loan_collateral, String loan_market_value, String loan_borrowing_rate, String loan_loan_ratio, String loan_type, String loan_duration, String loan_paid_out_date, String loan_due_date, String loan_doc_urls, String loan_status) {
        this.loan_id = loan_id;
        this.user_id = user_id;
        this.loan_request_code = loan_request_code;
        this.user_full_name = user_full_name;
        this.user_img_url_request = user_img_url_request;
        this.loan_amount = loan_amount;
        this.loan_purpose = loan_purpose;
        this.loan_collateral = loan_collateral;
        this.loan_market_value = loan_market_value;
        this.loan_borrowing_rate = loan_borrowing_rate;
        this.loan_loan_ratio = loan_loan_ratio;
        this.loan_type = loan_type;
        this.loan_duration = loan_duration;
        this.loan_paid_out_date = loan_paid_out_date;
        this.loan_due_date = loan_due_date;
        this.loan_doc_urls = loan_doc_urls;
        this.loan_status = loan_status;
    }
}
