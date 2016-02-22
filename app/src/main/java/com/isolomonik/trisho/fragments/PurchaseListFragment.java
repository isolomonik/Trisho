package com.isolomonik.trisho.fragments;


//import android.app.LoaderManager;
//import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.isolomonik.trisho.R;
import com.isolomonik.trisho.utils.GlobalVar;

import junit.framework.Test;


public class PurchaseListFragment extends Fragment implements  LoaderManager.LoaderCallbacks<Cursor> {

    public TextView tvTest;


    public PurchaseListFragment() {

    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Give some text to display if there is no data.  In a real
        // application this would come from a resource.
       // setEmptyText("No phone numbers");

        // We have a menu item to show in action bar.
        setHasOptionsMenu(true);

        // Create an empty adapter we will use to display the loaded data.
//        mAdapter = new SimpleCursorAdapter(getActivity(),
//                android.R.layout.simple_list_item_2, null,
//                new String[] { Contacts.DISPLAY_NAME, Contacts.CONTACT_STATUS },
//                new int[] { android.R.id.text1, android.R.id.text2 }, 0);
//        setListAdapter(mAdapter);

        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        getLoaderManager().initLoader(0, null, this);
    }
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // This is called when a new Loader needs to be created.  This
        // sample only has one Loader, so we don't care about the ID.
        // First, pick the base URI to use depending on whether we are
        // currently filtering.
//        Uri baseUri;
//        if (mCurFilter != null) {
//            baseUri = Uri.withAppendedPath(Contacts.CONTENT_FILTER_URI,
//                    Uri.encode(mCurFilter));
//        } else {
//            baseUri = Contacts.CONTENT_URI;
//        }
//
//        // Now create and return a CursorLoader that will take care of
//        // creating a Cursor for the data being displayed.
//        String select = "((" + Contacts.DISPLAY_NAME + " NOTNULL) AND ("
//                + Contacts.HAS_PHONE_NUMBER + "=1) AND ("
//                + Contacts.DISPLAY_NAME + " != '' ))";
//        return new CursorLoader(getActivity(), baseUri,
//                CONTACTS_SUMMARY_PROJECTION, select, null,
//                Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC");
        return null;
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Swap the new cursor in.  (The framework will take care of closing the
        // old cursor once we return.)
       // mAdapter.swapCursor(data);
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed.  We need to make sure we are no
        // longer using it.
       // mAdapter.swapCursor(null);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_purchase_list, container, false);
        tvTest= (TextView)v.findViewById(R.id.testTV);
        tvTest.setText(GlobalVar.API_TOKEN);
        return v;
    }


}
