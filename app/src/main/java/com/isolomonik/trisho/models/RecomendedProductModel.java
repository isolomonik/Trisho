package com.isolomonik.trisho.models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class RecomendedProductModel extends RealmObject {

    @SerializedName("ProductGuid")
    @PrimaryKey
    private String productGuid;

    @SerializedName("ProductName")
    private String productName;


    @SerializedName("IsFeaturedProducts")
    private boolean isFeaturedProducts;

    @SerializedName("Matches")
    private int matches;

    @SerializedName("PrevMatches")
    private int prevMatches;


    public int getFeaturedMatches() {

        return featuredMatches;
    }

    public void setFeaturedMatches(int featuredMatches) {
        this.featuredMatches = featuredMatches;
    }

    public int getPrevMatches() {
        return prevMatches;
    }

    public void setPrevMatches(int prevMatches) {
        this.prevMatches = prevMatches;
    }

    @SerializedName("FeaturedMatches")
    private int featuredMatches;

    @SerializedName("Count")
    private double count;

    private int status=0;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }



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

    public boolean getIsFeaturedProducts() {return isFeaturedProducts; }

    public void setIsFeaturedProducts(boolean isFeaturedProducts) {
        this.isFeaturedProducts = isFeaturedProducts;
    }

    public int getMatches() {
        return matches;
    }

    public void setMatches(int matches) {
        this.matches = matches;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }
}
