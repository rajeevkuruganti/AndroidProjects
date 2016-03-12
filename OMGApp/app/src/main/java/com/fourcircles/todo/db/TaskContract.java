/*
 * Copyright (c) 2016. This application and all code is copyright of 4 Circles LLC, USA.
 */

package com.fourcircles.todo.db;

/**
 * Created by rajeev on 2/28/2016.
 */
import android.provider.BaseColumns;

public class TaskContract {
	public static final String DB_NAME = "com.fourcircles.todo.db.tasks";
	public static final int DB_VERSION = 1;
	public static final String TABLE = "tasks";

	public class Columns {
		public static final String TASK = "task";
		public static final String DONE_FLAG = "done_flag";
		public static final String _ID = BaseColumns._ID;
	}
}