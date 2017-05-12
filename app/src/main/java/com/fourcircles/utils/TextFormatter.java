/*
 * Copyright (c) 2016. This application and all code is copyright of 4 Circles LLC, USA.
 */

package com.fourcircles.utils;

import java.util.ArrayList;

/**
 * Created by rajeev on 4/11/2017.
 */

public final class TextFormatter {

	public ArrayList<String> splitWithAmpersand(String inText){
		ArrayList<String> displayText = new ArrayList<String>();
		int indexAmp = inText.indexOf('&');
		displayText.add(0,inText.substring(0,indexAmp));
		int old = indexAmp;
		indexAmp = inText.indexOf('&',old+1);
		displayText.add(1,inText.substring(old+1,indexAmp));
		return displayText;
	}
}
