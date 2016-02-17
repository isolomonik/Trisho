package com.isolomonik.trisho.models;

import com.google.gson.annotations.SerializedName;


public class LoginModel {
    public LoginModel(String telephone, String passw) {
        this.telephone=telephone;
        this.password=passw;
    }

  //  @SerializedName("telephone")
    String telephone="";

 //   @SerializedName("password")
    String password="";


//    public String getTelephone() {
//        return telephone;
//    }
//
//    public void setTelephone(String telephone) {
//        this.telephone = telephone;
//    }
//
//    public String getPassw() {
//        return passw;
//    }
//
//    public void setPassw(String passw) {
//        this.passw = passw;
//    }
}
