/*
 * Copyright (c) 2016. This application and all code is copyright of 4 Circles LLC, USA.
 */

package com.fourcircles.hinduterms;

/**
 * Created by rajeev on 6/5/2016.
 */

import android.content.Intent;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

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

		String details = HinduTermsActivity.ITEM_MAPS.get((i.getStringExtra("details")));
		txtShow.setText(i.getStringExtra("details"));

		String contentShowTemp = i.getStringExtra("key");
		String contentShow = null;
		if (contentShowTemp.length() > 30) {
			contentShow = contentShowTemp.substring(0, 30)+"<p><br/>" + contentShowTemp.substring(31,contentShowTemp.length());
		} else
		{
			contentShow = contentShowTemp;
		}
	//	int iFirstPeriod = contentShow.indexOf('.');
	//	contenShow.
        content.setText(contentShow);

	}
}