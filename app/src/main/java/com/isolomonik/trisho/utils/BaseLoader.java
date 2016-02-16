package com.isolomonik.trisho.utils;
import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.io.IOException;

import retrofit2.Response;

public abstract class BaseLoader extends AsyncTaskLoader<Response> {

    public BaseLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public Response loadInBackground() {
        try {
            Response response = apiCall();
//            if (response.getRequestResult() == RequestResult.SUCCESS) {
//                response.save(getContext());
//                onSuccess();
//            } else {
//                onError();
//            }
            return response;
        } catch (IOException e) {
            onError();

        }

        return null;
    }

    protected void onSuccess() {
    }

    protected void onError() {
    }

    protected abstract Response apiCall() throws IOException;
}