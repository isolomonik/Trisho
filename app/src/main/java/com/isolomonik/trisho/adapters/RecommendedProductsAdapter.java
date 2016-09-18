package com.isolomonik.trisho.adapters;


import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.isolomonik.trisho.R;
import com.isolomonik.trisho.models.RecomendedProductModel;
import com.isolomonik.trisho.utils.AdapterCallBackInterface;

import java.util.List;

import io.realm.Realm;

public class RecommendedProductsAdapter extends RecyclerView.Adapter<RecommendedProductsAdapter.ItemHolder> {


    private Fragment context;
    private Realm realm= Realm.getDefaultInstance();
    //private RealmList<RecomendedProductModel> items;
    private List<RecomendedProductModel> items;

    class ItemHolder extends RecyclerView.ViewHolder {
        TextView productName;
        CheckBox added;
        EditText count;
        public ItemHolder(View itemView) {
            super(itemView);
            productName = (TextView) itemView.findViewById(R.id.tvRecommendedProduct);
            added = (CheckBox) itemView.findViewById(R.id.cbAddRecommended);
            added.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AdapterCallBackInterface myInterface = (AdapterCallBackInterface) context;
                    realm.beginTransaction();
                    if (items.get(getAdapterPosition()).getStatus()==1){
                        items.get(getAdapterPosition()).setStatus(0);}
                    else {items.get(getAdapterPosition()).setStatus(1);}
                    realm.commitTransaction();
                    myInterface.showItems( items.get(getAdapterPosition()).getProductGuid(), String.valueOf(items.get(getAdapterPosition()).getCount()));
                }
            });
            count =(EditText) itemView.findViewById(R.id.etRecommendedCount);

        }
    }

    //public RecommendedProductsAdapter(Fragment context, RealmList<RecomendedProductModel> items) {
    public RecommendedProductsAdapter(Fragment context, List<RecomendedProductModel> items) {
        this.context = context;
        this.items = items;
      //  this.items..sort("isFeaturedProducts");
    }

    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rowView = inflater.inflate(R.layout.row_recommended_product, parent, false);

        final ItemHolder purchaseHolder = new ItemHolder(rowView);
        return purchaseHolder;
    }

    public void onBindViewHolder(ItemHolder holder, int position) {
        RecomendedProductModel product = items.get(position);
        if (product != null) {

            holder.productName.setText(product.getProductName());
            if(!product.getIsFeaturedProducts()){
                holder.productName.setTextColor(Color.RED);
            } else { holder.productName.setTextColor(Color.BLACK);}
            holder.added.setChecked(product.getStatus()==1);
            holder.count.setText(String.valueOf(product.getCount()));
        }
    }

    @Override
    public int getItemCount() {

        return items.size();
    }
}
