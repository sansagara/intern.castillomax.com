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

public class PlacesAdapter extends ArrayAdapter {
    protected Context mContext;
    protected List<ParseObject> mPlaces;

    public PlacesAdapter(Context context, List place) {
        super(context, R.layout.places_custom_fragment, place);
        mContext = context;
        mPlaces = place;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.places_custom_fragment, null);
            holder = new ViewHolder();
            holder.placeName = (TextView) convertView
                    .findViewById(R.id.txtPlace);
            holder.placeDescription = (TextView) convertView
                    .findViewById(R.id.txtPlaceDescription);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ParseObject placesObject = mPlaces.get(position);

        // title
        String place = placesObject.getString("Clinica");
        place = capitalizeString(place);
        holder.placeName.setText(place);

        // content
        String description = placesObject.getString("Direccion");
        description = capitalizeString(description);
        holder.placeDescription.setText(description);

        return convertView;
    }

    public static class ViewHolder {
        TextView placeName;
        TextView placeDescription;
    }

    public static String capitalizeString(String string) {
        char[] chars = string.toLowerCase().toCharArray();
        boolean found = false;
        for (int i = 0; i < chars.length; i++) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i]) || chars[i]=='.' || chars[i]=='\'') { // You can add other chars here
                found = false;
            }
        }
        return String.valueOf(chars);
    }

}