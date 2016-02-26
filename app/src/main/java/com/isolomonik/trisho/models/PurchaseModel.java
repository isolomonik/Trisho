package com.isolomonik.trisho.models;


import io.realm.RealmObject;

public class PurchaseModel extends RealmObject {
    private String guid = "";
    private String name = "";
    private String createdDateTime = "";
    private boolean isOwn = true;

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
