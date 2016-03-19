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
import com.isolomonik.trisho.models.CustomProducts;

import java.util.List;


public class CustomProductsAdapter extends RecyclerView.Adapter<CustomProductsAdapter.ItemHolder> {


    private Fragment context;
    private List<CustomProducts> items;

    class ItemHolder extends RecyclerView.ViewHolder {
        TextView productName;
        CheckBox add;
        public ItemHolder(View itemView) {
            super(itemView);
            this.productName = (TextView) itemView.findViewById(R.id.tvRecommendedProduct);
            this.add = (CheckBox) itemView.findViewById(R.id.cbAddRecommended);
        }
    }

    public CustomProductsAdapter(Fragment context, List<CustomProducts> items) {
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
        CustomProducts product = items.get(position);
        if (product != null) {

            holder.productName.setText(product.getProductName());
            holder.add.setChecked(true);
        }
    }

    @Override
    public int getItemCount() {

        return items.size();
    }
}
