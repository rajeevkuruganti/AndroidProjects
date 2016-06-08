/*
 * Copyright (c) 2016. This application and all code is copyright of 4 Circles LLC, USA.
 */

package com.fourcircles.computerterms;

/**
 * Created by rajeev on 6/5/2016.
 */

import android.content.Intent;

import android.app.Activity;
		import android.content.Intent;
		import android.os.Bundle;
		import android.widget.TextView;

public class SingleListItem extends Activity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_list_item_view);
System.out.println(("you are i singleListItem Java file."));
		//TextView txtProduct = (TextView) findViewById(R.id.product_label);
			TextView txtShow = (TextView) findViewById(R.id.textView2);
		Intent i = getIntent();
		// getting attached intent data
//        i.getStringExtra("position");
	//	Integer product = Integer.valueOf(i.getStringExtra("position"));

		System.out.println("position in singlelist item is ----->  "+(i.getStringExtra("key")));
		txtShow.setText("changing me baby!");
		//String key = ComputerTermsActivity.computerTerms.get(position);
		String details = ComputerTermsActivity.ITEM_MAPS.get((i.getStringExtra("details")));
		txtShow.setText(i.getStringExtra("details"));
		// displaying selected product name
	//	txtProduct.setText(ComputerTermsActivity.computerTerms.get(1));
		//txtProduct.setText("Helllo hello ");

	}
}