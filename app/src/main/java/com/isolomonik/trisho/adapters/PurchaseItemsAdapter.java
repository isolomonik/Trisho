package com.isolomonik.trisho.adapters;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
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
        EditText count;
        CheckBox isDone;
        public ItemHolder(View itemView) {
            super(itemView);
            this.productName = (TextView) itemView.findViewById(R.id.tvItemName);
            this.count = (EditText) itemView.findViewById(R.id.etCount);
            this.isDone = (CheckBox) itemView.findViewById(R.id.chbDone);
        }
    }

    public PurchaseItemsAdapter(Fragment context, RealmResults<PurchaseItemModel> items) {
        this.context = context;
        this.items = items;
    }

    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rowView = inflater.inflate(R.layout.row_items, parent, false);

        final ItemHolder purchaseHolder = new ItemHolder(rowView);
//        rowView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                AdapterCallBackInterface myInterface = (AdapterCallBackInterface) context;
//                myInterface.showItems(purchases.get(purchaseHolder.getAdapterPosition()).getGuid());
//            }
//        });
        return purchaseHolder;
    }

    public void onBindViewHolder(ItemHolder holder, int position) {
        PurchaseItemModel product = items.get(position);
        if (product != null) {

            holder.productName.setText(product.getProductName());
            holder.count.setText(String.valueOf(product.getCount()));
            holder.isDone.setText(product.getStatus());
            holder.isDone.setChecked(product.getStatus().equals(GlobalVar.STATUS_DONE));

        }
    }

    @Override
    public int getItemCount() {

        return items.size();
    }
}
