package com.isolomonik.trisho.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.isolomonik.trisho.R;
import com.isolomonik.trisho.models.PurchaseModel;
import com.isolomonik.trisho.utils.AdapterCallBackInterface;

import java.util.ArrayList;

import io.realm.RealmResults;


public class PurchaseListAdapter extends RecyclerView.Adapter<PurchaseListAdapter.PurchaseHolder>{

    private Fragment context;
    private final RealmResults<PurchaseModel> purchases;

    class PurchaseHolder extends RecyclerView.ViewHolder {
        TextView purchaseName;
        TextView createdDateTime;
        public PurchaseHolder(View itemView) {
            super(itemView);
            this.purchaseName = (TextView) itemView.findViewById(R.id.tvPurchaseName);
            this.createdDateTime = (TextView) itemView.findViewById(R.id.tvPurchaseDate);
        }
    }

    public PurchaseListAdapter(Fragment context, RealmResults<PurchaseModel> purchases) {
        this.context = context;
        this.purchases = purchases;
    }

    public PurchaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rowView = inflater.inflate(R.layout.row_purchase, parent, false);

        final PurchaseHolder purchaseHolder = new PurchaseHolder(rowView);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AdapterCallBackInterface myInterface = (AdapterCallBackInterface) context;
                myInterface.showItems(purchases.get(purchaseHolder.getAdapterPosition()).getGuid(), purchases.get(purchaseHolder.getAdapterPosition()).getName());
            }
        });
        return purchaseHolder;
    }

    public void onBindViewHolder(PurchaseHolder holder, int position) {
        PurchaseModel purchase = purchases.get(position);
        if (purchase != null) {

           holder.purchaseName.setText(purchase.getName());
            holder.createdDateTime.setText(purchase.getCreatedDateTime().substring(0,10));

        }
    }

    @Override
    public int getItemCount() {

        return purchases.size();
    }

//    @Override
//    public long getItemId(int position) {
//        return position;
//    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
