package com.isolomonik.trisho.models;

import io.realm.RealmObject;


public class RecomendedProductModel extends RealmObject {
private String productGuid;
    private String productName;
    private boolean isFeaturedProducts;
    private int matches;
    private double count;

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

    public boolean isFeaturedProducts() {
        return isFeaturedProducts;
    }

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
