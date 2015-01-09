package br.liveo.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import br.liveo.adapter.PlacesAdapter;
import br.liveo.navigationliveo.R;
import br.liveo.utils.Constant;
import br.liveo.utils.Menus;

public class PlacesFragment extends ListFragment {

    private boolean mSearchCheck;
    protected List<ParseObject> mPlaces;

    public PlacesFragment newInstance(String text) {
        PlacesFragment mFragment = new PlacesFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString(Constant.TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View rootView = inflater.inflate(R.layout.places_fragment, container, false);
        rootView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        //Query All Places from Parse
        final ListView mlist = (ListView) getView().findViewById(android.R.id.list);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Clinicas");
        query.orderByAscending("Clinica");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> place, ParseException e) {
                if (e == null) {
                    mPlaces = place;

                    PlacesAdapter adapter = new PlacesAdapter(getActivity(), mPlaces);
                    mlist.setAdapter(adapter);
                } else {
                }
            }
        });

        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        ParseObject placesObject = mPlaces.get(position);
        String placeLink = placesObject.getString("MapsLink");
        if(placeLink == null || !placeLink.isEmpty()) {
            placeLink = "https://www.google.co.ve/maps/";
        }
        //Toast.makeText(getActivity(), placeLink, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse(placeLink));
        startActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Auto-generated method stub
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(Menus.SEARCH));
        searchView.setQueryHint(this.getString(R.string.search));

        ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text))
                .setHintTextColor(getResources().getColor(R.color.white));
        searchView.setOnQueryTextListener(OnQuerySearchView);

        menu.findItem(Menus.ADD).setVisible(false);
        menu.findItem(Menus.UPDATE).setVisible(false);
        menu.findItem(Menus.SEARCH).setVisible(true);

        mSearchCheck = false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub

        switch (item.getItemId()) {

            case Menus.ADD:
                break;

            case Menus.UPDATE:
                break;

            case Menus.SEARCH:
                mSearchCheck = true;
                break;
        }
        return true;
    }

    private OnQueryTextListener OnQuerySearchView = new OnQueryTextListener() {

        @Override
        public boolean onQueryTextSubmit(final String SearchQuery) {
            
            final ListView mlist = (ListView) getView().findViewById(android.R.id.list);
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Clinicas");
            query.whereContains("Clinica", SearchQuery);
            query.orderByAscending("Clinica");
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> place, ParseException e) {
                    if (e == null) {
                        mPlaces = place;
                        final Integer mPlacesLength = mPlaces.size();
                        Toast.makeText(getActivity(), R.string.yourqueryfor + SearchQuery + R.string.returned + mPlacesLength +  R.string.elements, Toast.LENGTH_SHORT).show();

                        PlacesAdapter adapter = new PlacesAdapter(getActivity(), mPlaces);
                        mlist.setAdapter(adapter);
                    } else {
                    }
                }
            });

            return false;
        }

        @Override
        public boolean onQueryTextChange(String arg0) {
            // TODO Auto-generated method stub
            if (mSearchCheck) {
                // implement your search here
            }
            return false;
        }

    };
}
