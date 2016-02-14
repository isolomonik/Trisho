package com.isolomonik.trisho.models;

import com.google.gson.annotations.SerializedName;


public class LoginModel {
    public LoginModel() {
    }

    @SerializedName("telephone")
    private   String telephone="";

    @SerializedName("password")
    private String passw="";


    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPassw() {
        return passw;
    }

    public void setPassw(String passw) {
        this.passw = passw;
    }
}
