package com.isolomonik.trisho.adapters;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.isolomonik.trisho.R;
import com.isolomonik.trisho.models.EditablePurchaseItemsModel;
import com.isolomonik.trisho.models.PurchaseItemModel;
import com.isolomonik.trisho.recycler_helper.ItemTouchHelperAdapter;
import com.isolomonik.trisho.recycler_helper.ItemTouchHelperViewHolder;
import com.isolomonik.trisho.utils.AdapterCallBackInterface;
import com.isolomonik.trisho.utils.GlobalVar;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PurchaseItemsAdapter extends RecyclerView.Adapter<PurchaseItemsAdapter.ItemHolder>
        implements ItemTouchHelperAdapter, Callback {


    private Fragment context;

    private Realm realm= Realm.getDefaultInstance();
    private RealmList<PurchaseItemModel> items;
    private String purchaseGuid;



    class ItemHolder extends RecyclerView.ViewHolder
            implements ItemTouchHelperViewHolder
        , NumberPicker.OnValueChangeListener
    {
        TextView productName;
        TextView productDescription;
        EditText count;
        CheckBox isDone;
        Button countPicker;
        public ItemHolder(final View itemView) {
            super(itemView);
            this.productName = (TextView) itemView.findViewById(R.id.tvItemName);
            this.productDescription = (TextView) itemView.findViewById(R.id.tvItemDescription);
            this.count = (EditText) itemView.findViewById(R.id.etCount);
//            count.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
//                    final NumberPicker np= new NumberPicker(itemView.getContext());
//                    np.setMinValue(0);
//                    np.setMaxValue(100);
//                    try {
//                       // np.setValue(Integer.parseInt(count.getText().toString()));
//                    } catch (Exception e){
//                        np.setValue(1);
//                    }
//
//                    builder.setCancelable(false)
//                            .setView(np)
//                            .setPositiveButton("SAVE",
//                                    new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int id) {
//                                            PurchaseItemModel item= items.get(getAdapterPosition());
//                                            realm.beginTransaction();
//                                            item.setCount(np.getValue());
//                                            realm.commitTransaction();
//                                            changedModelToAPI(item);
//                                            Log.v(GlobalVar.MY_LOG, "changed item count: "+np.getValue());
//                                            notifyDataSetChanged();
//                                            dialog.dismiss();
//                                        }
//                                    });
//                    AlertDialog alert = builder.create();
//                    alert.show();
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                    PurchaseItemModel item= items.get(getAdapterPosition());
//                    realm.beginTransaction();
//                    try {
//                        item.setCount(Double.parseDouble(s.toString()));
//                    } catch( Exception e) { }
//                    realm.commitTransaction();
//                    changedModelToAPI(item);
//                    Log.v(GlobalVar.MY_LOG, "changed item count: "+s.toString());
//                }
//            });
//            countPicker = (Button) itemView.findViewById(R.id.btnCountPicker);

            count.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                    final NumberPicker np= new NumberPicker(itemView.getContext());
                    np.setMinValue(0);
                    np.setMaxValue(100);
                    try {
                        np.setValue(Integer.parseInt(count.getText().toString()));
                    } catch (Exception e){
                        np.setValue(1);
                    }

                    builder
                            //.setCancelable(false)
                            .setView(np)
                            .setPositiveButton("SAVE",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            PurchaseItemModel item= items.get(getAdapterPosition());
                                            realm.beginTransaction();
                                            item.setCount(np.getValue());
                                            realm.commitTransaction();
                                            changedModelToAPI(item);
                                            Log.v(GlobalVar.MY_LOG, "changed item count: "+np.getValue());
                                            notifyDataSetChanged();
                                            dialog.dismiss();
                                        }
                                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
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
                    changedModelToAPI(items.get(getAdapterPosition()));
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

       // ---------- for NumberPicker
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

        }
    }


    public PurchaseItemsAdapter(Fragment context, RealmList<PurchaseItemModel> items, String purchaseGuid) {
        this.context = context;
        this.items = items;
        this.purchaseGuid=purchaseGuid;

        //this.items.sort("status");
    }

    public ItemHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rowView = inflater.inflate(R.layout.row_items, parent, false);
        final ItemHolder itemsHolder = new ItemHolder(rowView);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
                final EditText description=new EditText(parent.getContext());
                PurchaseItemModel item= items.get(itemsHolder.getAdapterPosition());
                description.setText(item.getDescription());
                builder.setTitle("Add discription:")
                        .setCancelable(false)
                        .setView(description)
                        .setPositiveButton("SAVE",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        PurchaseItemModel item= items.get(itemsHolder.getAdapterPosition());
                                        realm.beginTransaction();
                                        item.setDescription(description.getText().toString());
                                        realm.commitTransaction();
                                        changedModelToAPI(item);
                                        Log.v(GlobalVar.MY_LOG, "added item discription: "+description.getText().toString());
                                        notifyDataSetChanged();
                                        dialog.dismiss();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });


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
                holder.count.setVisibility(View.INVISIBLE);
                holder.isDone.setVisibility(View.INVISIBLE);
             //   holder.countPicker.setVisibility(View.INVISIBLE);
                holder.productName.setTextColor(context.getResources().getColor(R.color.secondary_text));
            }else {holder.productName.setTextColor(context.getResources().getColor(R.color.primary_text));
                holder.count.setVisibility(View.VISIBLE);
                holder.isDone.setVisibility(View.VISIBLE);
             //   holder.countPicker.setVisibility(View.VISIBLE);
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
        changedModelToAPI(items.get(position));
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
        notifyDataSetChanged();
    }

    private void changedModelToAPI(PurchaseItemModel purchaseItemModel) {
Log.v(GlobalVar.MY_LOG, "change item " + purchaseItemModel.toString());

        EditablePurchaseItemsModel model=new EditablePurchaseItemsModel(purchaseItemModel);


        try {
            Gson gson = new GsonBuilder().create();
            String json = gson.toJson(model);
            OkHttpClient client = new OkHttpClient();
            MediaType type = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(type, json);
            Request request = new Request.Builder()
                    .url(GlobalVar.URL_API + "api/PurchaseItem?token=" + GlobalVar.API_TOKEN+"&purchaseGuid="+purchaseGuid)
                    .post(body)
                    .build();

            client.newCall(request).enqueue(this);
            Log.v(GlobalVar.MY_LOG,json );
        } catch (Exception e) {
            e.printStackTrace();
        }


  }
    @Override
    public void onFailure(Call call, IOException e) {
        Log.v(GlobalVar.MY_LOG,"failed" );
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        Log.v(GlobalVar.MY_LOG, "saved" );
    }
}
