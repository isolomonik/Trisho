package com.isolomonik.trisho.models;

import io.realm.RealmObject;


public class NewPurchaseItemsModel
//        extends RealmObject
{
    private String purchaseGuid="";
    private CustomProducts[] customProductses;

    public RecomendedProducts[] getRecomendedProductses() {
        return recomendedProductses;
    }

    public void setRecomendedProductses(RecomendedProducts[] recomendedProductses) {
        this.recomendedProductses = recomendedProductses;
    }

    public CustomProducts[] getCustomProductses() {
        return customProductses;
    }

    public void setCustomProductses(CustomProducts[] customProductses) {
        this.customProductses = customProductses;
    }

    private RecomendedProducts[] recomendedProductses;


    public String getPurchaseGuid() {
        return purchaseGuid;
    }

    public void setPurchaseGuid(String purchaseGuid) {
        this.purchaseGuid = purchaseGuid;
    }



    private class CustomProducts{
        String productName;
        double count;
    }
    private  class RecomendedProducts{
        String productGuid;
        double count;
    }
}
