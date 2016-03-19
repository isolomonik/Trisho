package com.isolomonik.trisho.models;

import com.google.gson.annotations.SerializedName;

import java.util.LinkedList;
import java.util.List;

import io.realm.RealmObject;


public class NewPurchaseItemsModel
        //extends RealmObject
{
    public String getPurchaseGuid() {
        return purchaseGuid;
    }

    @SerializedName("PurchaseGuid")
    public String purchaseGuid="";

    @SerializedName("CustomProducts")
    private  List<CustomProducts> customProducts=new LinkedList<>();

    @SerializedName("RecommendedProducts")
    private  List<NewRecommendedItemModel> recomendedProductses=new LinkedList<>();

    public NewPurchaseItemsModel() { }

    public NewPurchaseItemsModel(String purchaseGuid)
    {
        this.purchaseGuid = purchaseGuid;
    }



    public void setPurchaseGuid(String purchaseGuid) {
        this.purchaseGuid = purchaseGuid;
    }

    public List<CustomProducts> getCustomProductses() {
        return customProducts;
    }

    public void setCustomProductses(List<CustomProducts> customProductses) {
        this.customProducts = customProductses;
    }

    public List<NewRecommendedItemModel> getRecomendedProductses() {
        return recomendedProductses;
    }

    public void setRecomendedProductses(List<NewRecommendedItemModel> recomendedProductses) {
        this.recomendedProductses = recomendedProductses;
    }


}
