package com.isolomonik.trisho.models;

import com.google.gson.annotations.SerializedName;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class LoginModel
        extends RealmObject
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

    @SerializedName("Token")
    @PrimaryKey
    private String token = "";

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserGuid() {
        return userGuid;
    }

    public void setUserGuid(String userGuid) {
        this.userGuid = userGuid;
    }

    @SerializedName("UserName")
    private String userName = "";

    @SerializedName("UserGuid")
    private String userGuid = "";

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }


}
