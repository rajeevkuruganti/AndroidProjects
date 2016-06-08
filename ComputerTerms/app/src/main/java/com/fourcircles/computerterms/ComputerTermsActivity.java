/*
 * Copyright (c) 2016. This application and all code is copyright of 4 Circles LLC, USA.
 */

package com.fourcircles.computerterms;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComputerTermsActivity extends AppCompatActivity implements Filterable{
	ListView listView;
	SearchView searchView;
	static List<String> computerTerms = new ArrayList<>();
	static List<String> originalTerms = new ArrayList<>();

	public static Map<String, String> ITEM_MAPS = new HashMap<>();
	//String[] computerTerms = {"Apple", "ADA", "BASIC", "FORTRAN", "Android", "C++", "JVM", "CPU", "Visual Basic","Objective C", "Groovy", "GRAILS","UNIX", "Hibernate", "Spring", "Batch Job","DEC VMS"};
	ArrayAdapter<String> arrayAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_computer_terms);
		listView = (ListView) findViewById(R.id.listView);
	    searchView =(SearchView) findViewById(R.id.searchView);
		arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, computerTerms);
		listView.setAdapter(arrayAdapter);
        System.out.println("here it is --> "+loadJSONFromAsset().toString());
		System.out.println("rajeev rajeev --->>>> called the loadJSON from aSSET & & terms is "+computerTerms.toString() );
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String s) {
				System.out.println( "ONQUERY TEXT SUBMIT what is ithis "+s);

												return false;
			}
			@Override
			public boolean onQueryTextChange(String s) {
				arrayAdapter.getFilter().filter( s );
						//arrayAdapter.notifyDataSetChanged();
				//arrayAdapter.filter(listView,s);
				//listView.setAdapter(arrayAdapter);

				return false;
			}


		});
		// listening to single list item on click
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {

				// selected item
//				String product = ((TextView) view).getText().toString();
System.out.println("positin = "+position +" --- parent "+parent.toString());


				// Launching new Activity on selecting single List Item
				Intent i = new Intent( getApplicationContext(), SingleListItem.class);
				// sending data to new activity
//				i.putExtra("product", "hello hello rajeev ");

				i.putExtra("position", position);
				i.putExtra("key", computerTerms.get(position));
				System.out.println("computerTerms.get(position) == "+computerTerms.get(position));
				System.out.println("ITEM_MAPS.get(computerTerms.get(position)) === "+ITEM_MAPS.get(computerTerms.get(position)));
				i.putExtra("details", ITEM_MAPS.get(computerTerms.get(position)));
				startActivity(i);

			}
		});

	}


	private void filter( String s,ListView listView) {


	}

	public String loadJSONFromAsset() {
	String json = null;
	computerTerms.clear();

	try {

		InputStream inputStream = getAssets().open("comp.json");
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		String line = null;
//		ObjectMapper mapper = new ObjectMapper();
		int location=0;
//            System.out.println("reader --- "+ reader.toString());
		JSONArray jsonArray = new JSONArray();
		while ((line = reader.readLine()) != null) {

			try {
				if (line!= null){
					JSONObject singleObj = new JSONObject(line);
					//int x = Integer.getInteger(singleObj.getString("id")).intValue();
					computerTerms.add ( singleObj.getString("content"));
                	ITEM_MAPS.put(singleObj.getString("content"),singleObj.getString("details")) ;
					json +=  line;

				} else {
					System.out.println("line is null");
				}
				originalTerms.addAll(computerTerms);
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

        jsonArray = new JSONArray(computerTerms);

		System.out.println("rajeev JSON file size is ---->>>> "+jsonArray);
		Comparator<String> cmp = new Comparator<String>() {
			public int compare(String o1, String o2) throws NumberFormatException {
				/*String o1Content= o1.content;
				String o2Content= o2.content;
				return o1Content.compareTo(o2Content);*/
				String o1Content= o1;
				String o2Content= o2;;
				return o1Content.compareTo(o2Content);
			}
		};
      // Collections.sort(jsonArray, cmp);
	} catch (IOException ex) {
		ex.printStackTrace();
		return null;
	}
	return json;

}


	@Override
	public Filter getFilter() {

		return null;
	}
}
