package com.android.ebaysearch.models;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by deepaksingh on 26/04/15.
 */
public class Item implements Serializable{
    private String itemId, itemName, imageUrl, shipping, viewUrl, price;

    public Item (JSONObject jsonObject) {
        itemName = jsonObject.optString("Title");
        itemId = jsonObject.optString("ItemID");
        imageUrl = jsonObject.optString("GalleryURL");
        viewUrl = jsonObject.optString("ViewItemURLForNaturalSearch");
        shipping = "";
        JSONObject priceObject = jsonObject.optJSONObject("ConvertedCurrentPrice");
        price = String.valueOf(priceObject.optLong("Value"))
                + priceObject.optString("CurrencyID");
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }
}
