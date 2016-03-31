package com.isolomonik.trisho.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.isolomonik.trisho.R;
import com.isolomonik.trisho.RestAPI.APIFactory;
import com.isolomonik.trisho.RestAPI.RetrofitAPIInterface;
import com.isolomonik.trisho.models.EditablePurchaseModel;
import com.isolomonik.trisho.models.PurchaseModel;
import com.isolomonik.trisho.recycler_helper.ItemTouchHelperAdapter;
import com.isolomonik.trisho.recycler_helper.ItemTouchHelperViewHolder;
import com.isolomonik.trisho.utils.AdapterCallBackInterface;
import com.isolomonik.trisho.utils.GlobalVar;

import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;


public class PurchaseListAdapter extends RecyclerView.Adapter<PurchaseListAdapter.PurchaseHolder>
        implements ItemTouchHelperAdapter, Callback {

    private Fragment context;
    // final RealmResults<PurchaseModel> purchases;
    private final RealmList<PurchaseModel> purchases;
    private Realm realm= Realm.getDefaultInstance();




    // final ArrayList<PurchaseModel> purchases;

    class PurchaseHolder extends RecyclerView.ViewHolder
            implements ItemTouchHelperViewHolder
    {
        TextView purchaseName;
        TextView createdDateTime;
        CheckBox chkBox;
        public PurchaseHolder(View itemView) {
            super(itemView);
            this.purchaseName = (TextView) itemView.findViewById(R.id.tvPurchaseName);
            this.createdDateTime = (TextView) itemView.findViewById(R.id.tvPurchaseDate);
            chkBox= (CheckBox) itemView.findViewById(R.id.cbPurchaseDone);
            chkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v(GlobalVar.MY_LOG, "Checked change " + purchases.get(getAdapterPosition()).getName());
                    realm.beginTransaction();
                    if (((CheckBox)v).isChecked()) {
                        purchases.get(getAdapterPosition()).setStatus(GlobalVar.STATUS_DONE);
                    } else {
                        purchases.get(getAdapterPosition()).setStatus(GlobalVar.STATUS_ADD);
                    }
                    realm.commitTransaction();
                    changedModelToAPI(purchases.get(getAdapterPosition()));
                }
            } );
        }
        @Override
        public void onItemSelected() {
            Log.v(GlobalVar.MY_LOG, "selected"+itemView.toString());
                     itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }



    /// public PurchaseListAdapter(Fragment context, RealmResults<PurchaseModel> purchases) {
    public PurchaseListAdapter(Fragment context, RealmList<PurchaseModel> purchases) {
        this.context = context;
        this.purchases = purchases;
    }
    @Override
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
    @Override
    public void onBindViewHolder(PurchaseHolder holder, final int position) {
        PurchaseModel purchase = purchases.get(position);
        if (purchase != null) {

           holder.purchaseName.setText(purchase.getName());
           holder.createdDateTime.setText(purchase.getCreatedDateTime().substring(0, 10));
           holder.chkBox.setChecked(purchase.getStatus().equals(GlobalVar.STATUS_DONE));
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
public void onItemDismiss(int position) {
    realm.beginTransaction();
    deletedModelToAPI(purchases.get(position));
    purchases.remove(position);
    realm.commitTransaction();
    notifyItemRemoved(position);
}



    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        PurchaseModel prev = purchases.remove(fromPosition);
        purchases.add(toPosition > fromPosition ? toPosition - 1 : toPosition, prev);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    private void changedModelToAPI(PurchaseModel purchaseModel) {

        EditablePurchaseModel model=new EditablePurchaseModel(purchaseModel);
        try {
            Gson gson = new GsonBuilder().create();
            String json = gson.toJson(model);
            OkHttpClient client = new OkHttpClient();
            MediaType type = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(type, json);
            Request request = new Request.Builder()
                    .url(GlobalVar.URL_API + "api/Purchase?token=" + GlobalVar.API_TOKEN)
                    .post(body)
                    .build();

            client.newCall(request).enqueue(this);
            Log.v(GlobalVar.MY_LOG,json );
        } catch (Exception e) {
            e.printStackTrace();
        }

//        Intent editService = new Intent(context.getContext(), PurchaseEditService.class);
//        editService.putExtra("purchaseGuid", purchaseModel.getGuid());
//        context.getContext().startService(editService);

}

    private void deletedModelToAPI(PurchaseModel purchaseModel) {
          try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(GlobalVar.URL_API + "api/Purchase?token=" + GlobalVar.API_TOKEN+"&purchaseGuid="+purchaseModel.getGuid())
                    .delete()
                    .build();

            client.newCall(request).enqueue(this);
            Log.v(GlobalVar.MY_LOG,"deleted "+ GlobalVar.URL_API + "api/Purchase?token=" + GlobalVar.API_TOKEN+"&purchaseGuid="+purchaseModel.getGuid());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(okhttp3.Call call, IOException e) {

    }

    @Override
    public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {

    }

}
