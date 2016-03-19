package com.isolomonik.trisho.models;

import com.google.gson.annotations.SerializedName;


public class EditablePurchaseModel  {


    public EditablePurchaseModel(PurchaseModel purchaseModel) {
        this.guid=purchaseModel.getGuid();
        this.name=purchaseModel.getName();
        this.isOwn=purchaseModel.getIsOwn();
        this.createdDateTime=purchaseModel.getCreatedDateTime();
        this.status=purchaseModel.getStatus();
    }


    @SerializedName("Guid")

    private String guid = "";

    @SerializedName("Name")
    private String name = "";

    @SerializedName("CreatedDateTime")
    private String createdDateTime = "";

    @SerializedName("isOwn")
    private boolean isOwn ;

    @SerializedName("Status")
    private String status ;



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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
