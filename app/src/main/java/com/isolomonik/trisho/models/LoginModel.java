package com.isolomonik.trisho.models;

import com.google.gson.annotations.SerializedName;

import io.realm.Realm;


public class LoginModel
        // extends Realm
{

    @SerializedName("Password")
    private String password = "";

    @SerializedName("Telephone")
    private String telephone = "";

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }


}
