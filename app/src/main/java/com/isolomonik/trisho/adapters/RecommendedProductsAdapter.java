package com.isolomonik.trisho.adapters;


import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.isolomonik.trisho.R;
import com.isolomonik.trisho.models.RecomendedProductModel;
import com.isolomonik.trisho.utils.GlobalVar;

import io.realm.RealmResults;

public class RecommendedProductsAdapter extends RecyclerView.Adapter<RecommendedProductsAdapter.ItemHolder> {


    private Fragment context;
    private RealmResults<RecomendedProductModel> items;

    class ItemHolder extends RecyclerView.ViewHolder {
        TextView productName;
        CheckBox add;
        public ItemHolder(View itemView) {
            super(itemView);
            this.productName = (TextView) itemView.findViewById(R.id.tvRecommendedProduct);
            this.add = (CheckBox) itemView.findViewById(R.id.cbAddRecommended);
        }
    }

    public RecommendedProductsAdapter(Fragment context, RealmResults<RecomendedProductModel> items) {
        this.context = context;
        this.items = items;
        this.items.sort("isFeaturedProducts");
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
            if(product.getIsFeaturedProducts()){
                holder.productName.setBackgroundColor(Color.YELLOW);
            }

        }
    }

    @Override
    public int getItemCount() {

        return items.size();
    }
}
