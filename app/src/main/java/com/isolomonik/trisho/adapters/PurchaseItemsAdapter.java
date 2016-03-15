package com.isolomonik.trisho.adapters;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.isolomonik.trisho.R;
import com.isolomonik.trisho.models.PurchaseItemModel;
import com.isolomonik.trisho.utils.GlobalVar;

import io.realm.RealmResults;

public class PurchaseItemsAdapter extends RecyclerView.Adapter<PurchaseItemsAdapter.ItemHolder> {


    private Fragment context;
    private RealmResults<PurchaseItemModel> items;

    class ItemHolder extends RecyclerView.ViewHolder {
        TextView productName;
        TextView productDescription;
        EditText count;
        CheckBox isDone;
        public ItemHolder(View itemView) {
            super(itemView);
            this.productName = (TextView) itemView.findViewById(R.id.tvItemName);
            this.productDescription = (TextView) itemView.findViewById(R.id.tvItemDescription);
            this.count = (EditText) itemView.findViewById(R.id.etCount);
            this.isDone = (CheckBox) itemView.findViewById(R.id.chbDone);
        }
    }

    public PurchaseItemsAdapter(Fragment context, RealmResults<PurchaseItemModel> items) {
        this.context = context;
        this.items = items;
        this.items.sort("status");
    }

    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rowView = inflater.inflate(R.layout.row_items, parent, false);

        final ItemHolder purchaseHolder = new ItemHolder(rowView);
        return purchaseHolder;
    }

    public void onBindViewHolder(ItemHolder holder, int position) {
        PurchaseItemModel product = items.get(position);
        if (product != null) {

            holder.productName.setText(product.getProductName());
            holder.productDescription.setText(product.getDescription());
           holder.count.setText(String.valueOf(product.getCount()));
//            holder.isDone.setText(String.valueOf(product.getStatus()));
            holder.isDone.setChecked(product.getStatus().equals(GlobalVar.STATUS_DONE));
            if(product.getStatus().equals("Ignored")){
                holder.productName.setTextColor(context.getResources().getColor(R.color.secondary_text));
            }

        }
    }

    @Override
    public int getItemCount() {

        return items.size();
    }
}
