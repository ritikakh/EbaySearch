package com.android.ebaysearch;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.ebaysearch.models.Item;
import com.squareup.picasso.Picasso;


public class DetailsActivity extends ActionBarActivity implements View.OnClickListener {

    ImageView itemImage;
    TextView lblItemName, lblPriceShippingInfo, lblTopRated;
    Button btnBuyNow, btnFacebookShare, btnBasicInfo, btnSeller, btnShipping;
    Item item;
    LinearLayout layoutBasicInfo, layoutShipping, layoutSeller;
    TextView lblCategoryName, lblBuyingFormat, lblShippingType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        // Get the item object from search activity
        if (getIntent().hasExtra("ItemDetails")) {
            item = (Item) getIntent().getSerializableExtra("ItemDetails");
        }
        initializeView(); // Calling method to initialize view
        Picasso.with(getApplicationContext()).load(item.getImageUrl()).resize(300, 300).into(itemImage);
        lblItemName.setText(item.getItemName());
        lblPriceShippingInfo.setText("Price : $ " + item.getPrice());
        lblCategoryName.setText(item.getItemCategory());
        lblBuyingFormat.setText(item.getBuyingFormat());
        lblShippingType.setText(item.getShippingType());

        btnBuyNow.setOnClickListener(this);
        btnBasicInfo.setOnClickListener(this);
        btnSeller.setOnClickListener(this);
        btnShipping.setOnClickListener(this);
    }

    /**
     * Method to initialize view
     */
    private void initializeView () {
        itemImage = (ImageView) findViewById(R.id.img_item_detail);
        lblItemName = (TextView) findViewById(R.id.lbl_item_detail_title);
        lblPriceShippingInfo = (TextView) findViewById(R.id.lbl_item_detail_price_shipping);
        lblTopRated = (TextView) findViewById(R.id.lbl_top_rated);
        btnBuyNow = (Button) findViewById(R.id.btn_buy_now);
        btnFacebookShare = (Button) findViewById(R.id.btn_fb_share);
        btnBasicInfo = (Button) findViewById(R.id.btn_basic_info);
        btnSeller = (Button) findViewById(R.id.btn_seller_info);
        btnShipping = (Button) findViewById(R.id.btn_shipping_info);
        layoutBasicInfo = (LinearLayout) findViewById(R.id.layout_basic_info);
        layoutSeller = (LinearLayout) findViewById(R.id.layout_seller);
        layoutShipping = (LinearLayout) findViewById(R.id.layout_shipping);
        lblCategoryName = (TextView) findViewById(R.id.lbl_category_name);
        lblBuyingFormat = (TextView) findViewById(R.id.lbl_buying_format);
        lblShippingType = (TextView) findViewById(R.id.lbl_shipping_type);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_buy_now:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getViewUrl()));
                startActivity(browserIntent);
                break;
            case R.id.btn_basic_info:
                btnBasicInfo.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
                btnSeller.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                btnShipping.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                layoutBasicInfo.setVisibility(View.VISIBLE);
                layoutSeller.setVisibility(View.GONE);
                layoutShipping.setVisibility(View.GONE);
                break;
            case R.id.btn_seller_info:
                btnBasicInfo.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                btnSeller.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
                btnShipping.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                layoutBasicInfo.setVisibility(View.GONE);
                layoutSeller.setVisibility(View.VISIBLE);
                layoutShipping.setVisibility(View.GONE);

                break;
            case R.id.btn_shipping_info:
                btnBasicInfo.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                btnSeller.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                btnShipping.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
                layoutBasicInfo.setVisibility(View.GONE);
                layoutSeller.setVisibility(View.GONE);
                layoutShipping.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }
}
