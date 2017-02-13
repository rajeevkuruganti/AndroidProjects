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
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import static android.graphics.Color.BLACK;

public class SingleListItem extends Activity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_list_item_view);

		//TextView txtProduct = (TextView) findViewById(R.id.product_label);
			TextView txtShow = (TextView) findViewById(R.id.textView2);
		    // txtShow.setTextColor(getResources().getColor(BLACK) );
		    TextView content = (TextView) findViewById(R.id.termText);
		Intent i = getIntent();


		//String key = ComputerTermsActivity.computerTerms.get(position);
		String details = ComputerTermsActivity.ITEM_MAPS.get((i.getStringExtra("details")));
		txtShow.setText(i.getStringExtra("details"));

		String contentShow = i.getStringExtra("key");
        content.setText((contentShow));

	}
}