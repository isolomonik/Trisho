package com.isolomonik.trisho.models;


import com.google.gson.annotations.SerializedName;

public class CustomProducts {


@SerializedName("ProductName")
    private String productName;

    @SerializedName("Count")
    private double count;

    public CustomProducts()  {
    }


    public CustomProducts(String name, double count )  {
        productName=name;
        this.count=count;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }
}
