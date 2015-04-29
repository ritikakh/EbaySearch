package com.android.ebaysearch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.ebaysearch.async_client.SearchAsyncClient;
import com.android.ebaysearch.models.Item;
import com.android.ebaysearch.models.ItemWrapper;
import com.android.ebaysearch.models.SearchEvent;
import com.android.ebaysearch.util.StringConstants;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;


public class EbaySearch extends ActionBarActivity implements View.OnClickListener {

    Button btnClear, btnSearch;
    EditText txtKeyWord, txtPriceFrom, txtPriceTo;
    Spinner sortBy;
    TextView lblErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebay_search);
        initializeView(); // Initializing view
        // Adding OnClick listeners to buttons
        btnClear.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
    }

    /**
     * Method used to initialize view of activity
     */
    private void initializeView () {
        btnClear = (Button) findViewById(R.id.btn_clear);
        btnSearch = (Button) findViewById(R.id.btn_search);
        txtKeyWord = (EditText) findViewById(R.id.txt_keyword);
        txtPriceFrom = (EditText) findViewById(R.id.txt_price_from);
        txtPriceTo = (EditText) findViewById(R.id.txt_price_to);
        sortBy = (Spinner) findViewById(R.id.spinner_sort_by);
        lblErrorMessage = (TextView) findViewById(R.id.lbl_error_msg);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_clear:
                txtKeyWord.setText("");
                txtPriceFrom.setText("");
                txtPriceTo.setText("");
                sortBy.setSelection(0);
                lblErrorMessage.setText("");
                break;
            case R.id.btn_search:
                boolean result = validateSelections();
                if (result) {
                    lblErrorMessage.setText("");
                    initiateSearch();
                }
                break;
            default:
                break;
        }
    }

    public void onResume () {
        super.onResume();
        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    public void onDestroy () {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    public void onEventMainThread (SearchEvent event) {
        if (event.getResult().equals("success")) {
            JSONObject jsonObject = event.getJsonObject();
            List<Item> itemList = new ArrayList<>();
            JSONArray itemArray = jsonObject.optJSONArray("Item");
            if (itemArray.length() > 0) {
                for (int i =0; i < itemArray.length(); i++) {
                    itemList.add(new Item(itemArray.optJSONObject(i)));
                }
            }
            Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
            intent.putExtra("SearchResults", new ItemWrapper(itemList));
            startActivity(intent);
        }
    }

    /**
     * Method to validate if all the fields for search are filled or not
     * @return flag indicating whether search should be conducted or not
     */
    private boolean validateSelections () {
        if (txtKeyWord.getText().toString().equalsIgnoreCase("")) {
            lblErrorMessage.setText("Please enter a keyword");
            return false;
        } else if (txtPriceFrom.getText().toString().equalsIgnoreCase("")) {
            lblErrorMessage.setText("Please enter initial price");
            return false;
        } else if (txtPriceTo.getText().toString().equalsIgnoreCase("")) {
            lblErrorMessage.setText("Please enter price limit");
            return false;
        }
        return true;
    }

    /**
     * Method to initiate search using search filters
     */
    private void initiateSearch () {
        // Creating request params from search filters to search item
        RequestParams params = new RequestParams();
        params.put("version", StringConstants.VERSION);
        params.put("appid", StringConstants.APP_ID);
        params.put("callname", StringConstants.CALL_NAME);
        params.put("responseencoding", StringConstants.RESPONSE_ENCODING);
        params.put("QueryKeywords", txtKeyWord.getText().toString());
        params.put("ItemSort", "BestMatch");
        // Creating weak reference for searching
        WeakReference<Context> contextWeakReference = new WeakReference<Context>(this);
        SearchAsyncClient searchAsyncClient = new SearchAsyncClient(contextWeakReference.get());
        searchAsyncClient.searchItem(params);
    }
}
