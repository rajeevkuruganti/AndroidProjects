/*
 * Copyright (c) 2016. This application and all code is copyright of 4 Circles LLC, USA.
 */

package com.fourcircles.computerterms;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class ComputerTermsActivity extends AppCompatActivity implements Filterable{
	ListView listView;
	SearchView searchView;
	private final String CONTENTFILE ="comp.json";
	static List<String> computerTerms = new ArrayList<>();
	static List<String> originalTerms = new ArrayList<>();
	Filter mFilter = new ItemFilter();

	public static Map<String, String> ITEM_MAPS = new HashMap<>();
	public static Map<String, String>  SEARCH_MAPS = new HashMap<>();
	public Logger log;
	ArrayAdapter<String> arrayAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_computer_terms);
		listView = (ListView) findViewById(R.id.listView);
	    searchView =(SearchView) findViewById(R.id.searchView);
		arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, computerTerms);
		listView.setAdapter(arrayAdapter);
		String listofTerms = loadJSONFromAsset();
//        System.out.println("here it is --> "+loadJSONFromAsset().toString());
//        listView.notifyAll();
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String s) {
						return false;
			}
			@Override
			public boolean onQueryTextChange(String s) {

				computerTerms.clear();

				mFilter.filter(s);
				arrayAdapter.notifyDataSetChanged();
				return false;
			}

			public boolean onQueryTextChangeAfter(String s){
				return false;
			}


		});
		// listening to single list item on click
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {

				// Launching new Activity on selecting single List Item
				Intent i = new Intent( getApplicationContext(), SingleListItem.class);
				// sending data to new activity

				i.putExtra("position", position);
				i.putExtra("key", computerTerms.get(position));


				i.putExtra("details",ITEM_MAPS.get(computerTerms.get(position)));
				startActivity(i);

			}
		});

	}


	public String loadJSONFromAsset() {
	String json ="";
	computerTerms.clear();
    originalTerms.clear();
	try {
		InputStream inputStream = getAssets().open(CONTENTFILE);
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		String line = null;
		int location=0;
		JSONArray jsonArray = new JSONArray();
		while ((line = reader.readLine()) != null) {

			try {
				if (line!= null){
					JSONObject singleObj = new JSONObject(line);
					computerTerms.add ( singleObj.getString("content"));
                	ITEM_MAPS.put(singleObj.getString("content"),singleObj.getString("details")) ;
					json +=  line;

				} else {
					System.out.println("line is null");
				}
				// make a new list of original Terms.


			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		originalTerms.addAll(computerTerms);

        jsonArray = new JSONArray(computerTerms);

		Comparator<String> cmp = new Comparator<String>() {
			public int compare(String o1, String o2) throws NumberFormatException {
				String o1Content= o1;
				String o2Content= o2;;
				return o1Content.compareTo(o2Content);
			}
		};
      Collections.sort(originalTerms, cmp);
		Collections.sort(computerTerms, cmp);
	} catch (IOException ex) {
		ex.printStackTrace();
		return null;
	}
	return json;

}


	@Override
	public Filter getFilter() {
		return mFilter;
	}

	private class ItemFilter extends Filter {
		@Override
		protected FilterResults performFiltering(CharSequence charSequence) {

			computerTerms.clear();
			FilterResults results = new FilterResults();
			// We implement here the filter logic
			if (charSequence == null || charSequence.length() == 0) {
				// No filter implemented we return all the list
				computerTerms.addAll(originalTerms);
				results.values = computerTerms;
				results.count = originalTerms.size();
			}
			else {
				// We perform filtering operation
		//				List<String> nPlanetList = new ArrayList<String>();
				for (int i =0; i< originalTerms.size();i++){
					String original = originalTerms.get(i).toLowerCase();
					if (original.startsWith(charSequence.toString().toLowerCase()))
						computerTerms.add(originalTerms.get(i));
				}

				results.values = computerTerms;
				results.count = computerTerms.size();

			}

			return results;

		}

		@Override
		protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
			if (filterResults.count == 0)
				arrayAdapter.notifyDataSetInvalidated();
			else {
			//	computerTerms.addAll( filterResults);
				arrayAdapter.notifyDataSetChanged();
			}


		}
	}
}
