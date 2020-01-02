package com.infusiblecoder.loanappsameed.ModelClasses;

public class UserTableData {


    public String user_id, firstname, lastname, address, whatyoupretend, fieldofactivity, phone, email, password, status, user_img_url;

    public UserTableData(String user_id, String firstname, String lastname, String address, String whatyoupretend, String fieldofactivity, String phone, String email, String password, String status, String user_img_url) {
        this.user_id = user_id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.whatyoupretend = whatyoupretend;
        this.fieldofactivity = fieldofactivity;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.status = status;
        this.user_img_url = user_img_url;
    }
}