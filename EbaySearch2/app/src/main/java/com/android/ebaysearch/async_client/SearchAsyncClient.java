package com.android.ebaysearch.async_client;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.android.ebaysearch.R;
import com.android.ebaysearch.models.SearchEvent;
import com.android.ebaysearch.util.StringConstants;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import de.greenrobot.event.EventBus;

/**
 * Created by deepaksingh on 28/04/15.
 */
public class SearchAsyncClient {
    ProgressDialog progressDialog;
    Context context;
    public SearchAsyncClient(Context context) {
        this.context = context;
    }
    public void searchItem (RequestParams params) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.get(context, StringConstants.SEARCH_URL, params, new JsonHttpResponseHandler(){
            @Override
            public void onStart() {
                super.onStart();
                progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Please wait...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                super.onSuccess(statusCode, headers, json);
                try {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    EventBus.getDefault().post(new SearchEvent("success", json));
                } catch (Exception e) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject json) {
                super.onFailure(statusCode, headers, throwable, json);
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(context , context.getResources().getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
            }
        });
    }
}
