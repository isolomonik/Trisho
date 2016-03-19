package com.isolomonik.trisho.adapters;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.isolomonik.trisho.R;
import com.isolomonik.trisho.models.PurchaseItemModel;
import com.isolomonik.trisho.recycler_helper.ItemTouchHelperAdapter;
import com.isolomonik.trisho.recycler_helper.ItemTouchHelperViewHolder;
import com.isolomonik.trisho.services.PurchaseItemsEditService;
import com.isolomonik.trisho.utils.GlobalVar;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class PurchaseItemsAdapter extends RecyclerView.Adapter<PurchaseItemsAdapter.ItemHolder>
        implements ItemTouchHelperAdapter
{


    private Fragment context;

    private Realm realm= Realm.getDefaultInstance();
    private RealmList<PurchaseItemModel> items;

    class ItemHolder extends RecyclerView.ViewHolder
            implements ItemTouchHelperViewHolder
    {
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
            this.isDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v(GlobalVar.MY_LOG, "Checked change " + items.get(getAdapterPosition()).getProductName() );
                    realm.beginTransaction();
                    if (((CheckBox)v).isChecked()) {
                        items.get(getAdapterPosition()).setStatus(GlobalVar.STATUS_DONE);
                    } else {
                        items.get(getAdapterPosition()).setStatus(GlobalVar.STATUS_ADD);
                    }
                    realm.commitTransaction();
                    //  changedModelToAPI(items.get(getAdapterPosition()));
                }
            });
        }

        @Override
        public void onItemSelected() {
            Log.v(GlobalVar.MY_LOG, "selected" + itemView.toString());
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }


    public PurchaseItemsAdapter(Fragment context, RealmList<PurchaseItemModel> items) {
        this.context = context;
        this.items = items;

        //this.items.sort("status");
    }

    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rowView = inflater.inflate(R.layout.row_items, parent, false);

        final ItemHolder itemsHolder = new ItemHolder(rowView);
        return itemsHolder;
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

    @Override
    public void onItemDismiss(int position) {
        //  realm.beginTransaction();
        realm.beginTransaction();
        items.get(position).setStatus(GlobalVar.STATUS_IGNOR);
        PurchaseItemModel prev = items.remove(position);
        items.add(items.size(), prev);
        realm.commitTransaction();
        notifyItemRemoved(position);
        notifyDataSetChanged();
        //  realm.commitTransaction();
    }
    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        PurchaseItemModel prev = items.remove(fromPosition);
        items.add(toPosition > fromPosition ? toPosition - 1 : toPosition, prev);
        notifyItemMoved(fromPosition, toPosition);
    }

    private void changedModelToAPI(PurchaseItemModel purchaseItemModel) {
Log.v(GlobalVar.MY_LOG, "change item "+purchaseItemModel.toString());

        Intent editService = new Intent(context.getActivity(), PurchaseItemsEditService.class);
        context.getActivity().startService(editService.putExtra("model", purchaseItemModel));
    }

}
