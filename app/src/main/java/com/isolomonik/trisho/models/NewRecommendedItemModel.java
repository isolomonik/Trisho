package com.isolomonik.trisho.models;


import com.google.gson.annotations.SerializedName;

public class NewRecommendedItemModel {

    public String getProductGuid() {
        return productGuid;
    }

    public void setProductGuid(String productGuid) {
        this.productGuid = productGuid;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }
    @SerializedName("ProductGuid")
    private String productGuid;

    @SerializedName("Count")
    private double count;
}
