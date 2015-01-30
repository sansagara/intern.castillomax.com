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
 * Custom Adapter for Places List
 * Created by Leonel on 01-01-2015.
 */

public class PhonesAdapter extends ArrayAdapter {
    protected Context mContext;
    protected List<ParseObject> mPhones;

    public PhonesAdapter(Context context, List phone) {
        super(context, R.layout.phones_custom_fragment, phone);
        mContext = context;
        mPhones = phone;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.phones_custom_fragment, null);
            holder = new ViewHolder();
            holder.phoneOwner = (TextView) convertView
                    .findViewById(R.id.txtPhone);
            holder.phoneNumber = (TextView) convertView
                    .findViewById(R.id.txtPhoneNumber);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ParseObject placesObject = mPhones.get(position);

        // title
        String place = placesObject.getString("phoneOwner");
        holder.phoneOwner.setText(place);

        // content
        String description = placesObject.getString("phoneNumber");
        holder.phoneNumber.setText(description);

        return convertView;
    }

    public static class ViewHolder {
        TextView phoneOwner;
        TextView phoneNumber;
    }
}
