package com.isolomonik.trisho.fragments;


import android.os.Bundle;

import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.isolomonik.trisho.R;

import java.util.Arrays;
import java.util.List;


public class DialogNewItemFragment extends DialogFragment {


    public DialogNewItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v=inflater.inflate(R.layout.fragment_dialog_new_item, container, false);

        int layoutItemId = android.R.layout.simple_dropdown_item_1line;
        String[] products =  {"Pizza", "Peper", "Murshmulo", "kjghfgfdfj", "kakao", "coffe"};
        List<String> dogList = Arrays.asList(products);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), layoutItemId, dogList);

        AutoCompleteTextView autocompleteView =
                (AutoCompleteTextView) v.findViewById(R.id.etNewItem);
        autocompleteView.setAdapter(adapter);
        return v;
    }


}
