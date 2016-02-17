package com.isolomonik.trisho.models;

import com.google.gson.annotations.SerializedName;


public class LoginModel {
    public LoginModel(String telephone, String passw) {
        this.telephone=telephone;
        this.password=passw;
    }

  @SerializedName("Telephone")
    String telephone="";

  @SerializedName("Password")
    String password="";


}
