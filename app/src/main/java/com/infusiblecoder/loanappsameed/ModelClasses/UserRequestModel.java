package com.infusiblecoder.loanappsameed.ModelClasses;

import java.io.Serializable;

public class UserRequestModel implements Serializable {

    public String request_id,
            loan_request_code,
            request_sender_user_name,
            request_reciver_user_name,
            request_sender_user_id,
            request_reciver_user_id,
            request_time_stamp,
            request_is_seen,
            req_status;


    public UserRequestModel(String request_id, String loan_request_code, String request_sender_user_name, String request_reciver_user_name, String request_sender_user_id, String request_reciver_user_id, String request_time_stamp, String request_is_seen, String req_status) {
        this.request_id = request_id;
        this.loan_request_code = loan_request_code;
        this.request_sender_user_name = request_sender_user_name;
        this.request_reciver_user_name = request_reciver_user_name;
        this.request_sender_user_id = request_sender_user_id;
        this.request_reciver_user_id = request_reciver_user_id;
        this.request_time_stamp = request_time_stamp;
        this.request_is_seen = request_is_seen;
        this.req_status = req_status;
    }
}