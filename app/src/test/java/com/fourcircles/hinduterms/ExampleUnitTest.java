/*
 * Copyright (c) 2016. This application and all code is copyright of 4 Circles LLC, USA.
 */

package com.fourcircles.hinduterms;

import com.fourcircles.utils.TextFormatter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
	@Test
	public void addition_isCorrect() throws Exception {
		assertEquals(4, 2 + 2);
	}

	@Test
	public void textFormat() throws Exception {
		TextFormatter tf = new TextFormatter();
		String details = "Sansrkit meaning destroy.& Buddha states that one must kill the very source of one's difficulties and he uses the word abhinigraha - completely destroy.&";
		ArrayList<String> x = tf.splitWithAmpersand(details);
		System.out.println(x.size());
		System.out.println(x.get(0));
		for (int i = 0; i < x.size(); i++) {
			System.out.println(i + " = " + x.get(i));
		}
	}
		@Test
		public void jsonArrayRead() throws Exception {
			JSONObject jsObj = new JSONObject();
			JSONArray x = new JSONArray("['this is first paragraph','nextparagraph is this'']");
			jsObj.put("details",x);
			System.out.println(jsObj.toString());
//			jsObj.put("details","['this is first paragraph','nextparagraph is this'']");
			//JSONArray x = jsObj.getJSONArray("details");
			//System.out.println("jsonnary x "+ jsObj.get("details"));
			//String details = "Sansrkit meaning destroy.& Buddha states that one must kill the very source of one's difficulties and he uses the word abhinigraha - completely destroy.&";
		}



}