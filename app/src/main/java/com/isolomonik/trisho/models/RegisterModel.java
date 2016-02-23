package com.isolomonik.trisho.models;


import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RegisterModel  extends RealmObject {

    public RegisterModel() {
    }

    @SerializedName("Name")
    private String name = "";

    @SerializedName("Telephone")
    private String telephone = "";

    @SerializedName("Email")
    private String email = "";

    @SerializedName("Password")
    private String password = "";


    @PrimaryKey
    private String token = "";

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() { return telephone; }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }


}
