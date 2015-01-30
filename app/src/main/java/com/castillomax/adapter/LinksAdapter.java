package com.castillomax.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.List;

import com.castillomax.navigationliveo.R;

/**
 * Custom Adapter for Links List
 * Created by Leonel on 01-01-2015.
 */

public class LinksAdapter extends ArrayAdapter {
    protected Context mContext;
    protected List<ParseObject> mLinks;

    public LinksAdapter(Context context, List link) {
        super(context, R.layout.places_custom_fragment, link);
        mContext = context;
        mLinks = link;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.links_custom_fragment, null);
            holder = new ViewHolder();
            holder.linkName = (TextView) convertView
                    .findViewById(R.id.txtLink);
            holder.linkURL = (TextView) convertView
                    .findViewById(R.id.txtLinkDescription);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ParseObject placesObject = mLinks.get(position);

        // title
        String place = placesObject.getString("Link");
        holder.linkName.setText(place);

        // content
        String description = placesObject.getString("LinkURL");
        holder.linkURL.setText(description);

        return convertView;
    }

    public static class ViewHolder {
        TextView linkName;
        TextView linkURL;
    }
}
