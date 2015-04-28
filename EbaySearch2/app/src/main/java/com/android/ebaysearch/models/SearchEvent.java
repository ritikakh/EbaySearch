package com.android.ebaysearch.models;

import org.json.JSONObject;

/**
 * Created by deepaksingh on 28/04/15.
 */
public class SearchEvent {
    private String result;
    private JSONObject jsonObject;

    public SearchEvent (String result, JSONObject jsonObject) {
        this.result = result;
        this.jsonObject = jsonObject;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
