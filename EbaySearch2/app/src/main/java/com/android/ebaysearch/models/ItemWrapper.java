package com.android.ebaysearch.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by deepaksingh on 28/04/15.
 */
public class ItemWrapper implements Serializable {
    List<Item> itemList;
    public  ItemWrapper (List itemList) {
        this.itemList = itemList;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }
}
