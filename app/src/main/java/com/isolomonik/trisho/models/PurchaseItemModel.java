package com.isolomonik.trisho.models;


import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class PurchaseItemModel  extends RealmObject {

 @SerializedName("ProductGuid")
@PrimaryKey
private String productGuid="";

    @SerializedName("ProductName")
    private String productName="";

    @SerializedName("Description")
    private String description="";

    @SerializedName("Count")
    private double count;

    @SerializedName("Status")
    private int status;

    public String getProductGuid() {
        return productGuid;
    }

    public void setProductGuid(String productGuid) {
        this.productGuid = productGuid;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }



}
