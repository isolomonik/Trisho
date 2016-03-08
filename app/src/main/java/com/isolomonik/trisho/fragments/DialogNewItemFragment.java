package com.isolomonik.trisho.fragments;


import android.os.Bundle;

import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.isolomonik.trisho.R;


public class DialogNewItemFragment extends DialogFragment {


    public DialogNewItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v=inflater.inflate(R.layout.fragment_dialog_new_item, container, false);


        return v;
    }


}
