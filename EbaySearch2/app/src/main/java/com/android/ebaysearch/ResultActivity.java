package com.android.ebaysearch;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.android.ebaysearch.adapters.SearchResultAdapter;
import com.android.ebaysearch.models.Item;
import com.android.ebaysearch.models.ItemWrapper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


public class ResultActivity extends ActionBarActivity {

    ListView listSearchResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        initializeView(); // Initializing view
        List<Item> listItem = new ArrayList<>();
        if (getIntent().hasExtra("SearchResults")) {
            ItemWrapper itemWrapper = (ItemWrapper) getIntent().getSerializableExtra("SearchResults");
            listItem = itemWrapper.getItemList();
        }
        WeakReference<Context> contextWeakReference = new WeakReference<Context>(this);
        SearchResultAdapter searchResultAdapter = new SearchResultAdapter(contextWeakReference.get()
                ,listSearchResult
                ,listItem);
        listSearchResult.setAdapter(searchResultAdapter);
    }

    /**
     * Method used to initialize view of activity
     */
    private void initializeView () {
        listSearchResult = (ListView) findViewById(R.id.list_search_result);
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
}
