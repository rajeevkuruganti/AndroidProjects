/*
 * Copyright (c) 2016. This application and all code is copyright of 4 Circles LLC, USA.
 */

package com.fourcircles.todo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rajeev on 2/28/2016.
 */
public class TaskDBHelper extends SQLiteOpenHelper {

	public TaskDBHelper(Context context) {
		super(context, TaskContract.DB_NAME, null, TaskContract.DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase sqlDB) {
		String sqlQuery =
				String.format("CREATE TABLE %s (" +
								"_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
								"%s TEXT,%s TEXT)", TaskContract.TABLE,
						TaskContract.Columns.TASK,TaskContract.Columns.DONE_FLAG);

//		System.out.println( new StringBuilder().append("Query to form table: ").append(sqlQuery).toString());
//		Log.d("TaskDBHelper","Query to form table: "+sqlQuery)
		sqlDB.execSQL(sqlQuery);
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqlDB, int i, int i2) {
		sqlDB.execSQL("DROP TABLE IF EXISTS "+TaskContract.TABLE);
		onCreate(sqlDB);
	}
}