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
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import br.liveo.adapter.PhonesAdapter;
import br.liveo.navigationliveo.R;
import br.liveo.utils.Constant;
import br.liveo.utils.Menus;

public class PhonesFragment extends ListFragment {

	private boolean mSearchCheck;
    protected List<ParseObject> mPhones;
	
	public PhonesFragment newInstance(String text){
		PhonesFragment mFragment = new PhonesFragment();
		Bundle mBundle = new Bundle();
		mBundle.putString(Constant.TEXT_FRAGMENT, text);
		mFragment.setArguments(mBundle);
		return mFragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// TODO Auto-generated method stub		
		View rootView = inflater.inflate(R.layout.phones_fragment, container, false);
		rootView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT ));		
		return rootView;		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
        //Query All Phones from Parse
        final ListView mlist = (ListView) getView().findViewById(android.R.id.list);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Phones");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> place, ParseException e) {
                if (e == null) {
                    mPhones = place;

                    PhonesAdapter adapter = new PhonesAdapter(getActivity(), mPhones);
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
        ParseObject placesObject = mPhones.get(position);
        String phoneNumber = placesObject.getString("phoneNumber");

        //Toast.makeText(getActivity(), phoneNumber, Toast.LENGTH_LONG).show();
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(callIntent);
    }
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);		
		inflater.inflate(R.menu.menu, menu);
		 	    
		SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(Menus.SEARCH));
	    searchView.setQueryHint(this.getString(R.string.search));
	    
	    ((EditText)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text))
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
            final String SearchQuery2 = SearchQuery.toLowerCase();
            final ListView mlist = (ListView) getView().findViewById(android.R.id.list);
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Phones");
            query.whereContains("phoneOwner", SearchQuery2);
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> phone, ParseException e) {
                    if (e == null) {
                        mPhones = phone;
                        final Integer mPhonesLength = mPhones.size();
                        Toast.makeText(getActivity(), getString(R.string.yourqueryfor) + " " + SearchQuery + " " + getString(R.string.returned) +" "+ mPhonesLength + " " + getString(R.string.elements), Toast.LENGTH_SHORT).show();

                        PhonesAdapter adapter = new PhonesAdapter(getActivity(), mPhones);
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
			if (mSearchCheck){
				// implement your search here
			}
			return false;
		}
	};	
}
