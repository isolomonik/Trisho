package com.isolomonik.trisho.models;


import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class PurchaseModel extends RealmObject {
    @SerializedName("Guid")
    @PrimaryKey
    private String guid = "";

    @SerializedName("Name")
    private String name = "";

    @SerializedName("CreatedDateTime")
    private String createdDateTime = "";

    @SerializedName("isOwn")
    private boolean isOwn ;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @SerializedName("Status")
    private String status ;

    public String getGuid() {return guid;}

    public void setGuid(String guid) {this.guid = guid; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedDateTime() {return createdDateTime; }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }
    public boolean getIsOwn() {
        return isOwn;
    }

    public void setIsOwn(boolean isOwn) {
        this.isOwn = isOwn;
    }
}
