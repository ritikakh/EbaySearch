package com.android.ebaysearch.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.ebaysearch.DetailsActivity;
import com.android.ebaysearch.R;
import com.android.ebaysearch.models.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by deepaksingh on 26/04/15.
 */
public class SearchResultAdapter extends BaseAdapter {
    private Context context;
    private List<Item> listItem;
    public SearchResultAdapter (Context context, List<Item> listItem) {
        this.context = context;
        this.listItem = listItem;
    }
    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public Object getItem(int position) {
        return listItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_search_result, null);
            holder = new ViewHolder();
            holder.imgItemImage = (ImageView) convertView.findViewById(R.id.img_search_item);
            holder.lblItemName = (TextView) convertView.findViewById(R.id.lbl_item_name);
            holder.lblPriceShipping = (TextView) convertView.findViewById(R.id.lbl_item_price_desc);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Item item = listItem.get(position);
        if (item != null) {
            Picasso.with(context).load(item.getImageUrl()).resize(150, 150).into(holder.imgItemImage);
            holder.lblItemName.setText(item.getItemName());
            holder.lblPriceShipping.setText("Price : $ " + item.getPrice());

            holder.lblItemName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detailIntent = new Intent(context, DetailsActivity.class);
                    detailIntent.putExtra("ItemDetails", item);
                    context.startActivity(detailIntent);
                }
            });

            holder.imgItemImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getViewUrl()));
                    context.startActivity(browserIntent);
                }
            });
        }
        return convertView;
    }

    public class ViewHolder {
        ImageView imgItemImage;
        TextView lblItemName;
        TextView lblPriceShipping;
    }
}
