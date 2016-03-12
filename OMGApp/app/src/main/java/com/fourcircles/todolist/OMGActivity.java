package com.fourcircles.todolist;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fourcircles.todo.db.TaskContract;
import com.fourcircles.todo.db.TaskDBHelper;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;



public class OMGActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    TextView t1;
    Button myFirstBtn;
    Button myClearBtn;
    ListView mainListView;
    ArrayAdapter mArrayAdapter;
    ArrayList mToDoList = new ArrayList();

    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_omg);

        SQLiteDatabase sqlDB = new TaskDBHelper(this).getWritableDatabase();
        Cursor cursor = sqlDB.query(TaskContract.TABLE,
                new String[]{TaskContract.Columns.TASK},
                null, null, null, null, null);
        System.out.println("---- rajeev 00000 ");

        while (cursor.moveToNext()) {
            String s = cursor.getString(
                    cursor.getColumnIndexOrThrow(
                            TaskContract.Columns.TASK));
            mToDoList.add(s);


        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /* find controls
              find the button and text field
         */
        t1 = (TextView) findViewById(R.id.TodoText);
        myFirstBtn = (Button) findViewById(R.id.main_button);
        myFirstBtn.setOnClickListener(this);
        myClearBtn = (Button) findViewById(R.id.main_button2);
        myClearBtn.setOnClickListener(this);
        mainListView = (ListView) findViewById(R.id.listViewNames);
        // Create an ArrayAdapter for the ListView
        mArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mToDoList);
        mainListView.setOnItemClickListener(this);

// Set the ListView to use the ArrayAdapter
        mainListView.setAdapter(mArrayAdapter);
        // long focus on the item will delete the item as Done.
        setupListViewListener();
//        t1.setText("setting in Java");

    }

   /* private void displayWelcome() {
        // Access the device's key-value storage
        mSharedPreferences = getSharedPreferences(PREFS, MODE_PRIVATE);

        // Read the user's name,
        // or an empty string if nothing found
        String name = mSharedPreferences.getString(PREF_NAME, "");

        if (name.length() > 0) {

            // If the name is valid, display a Toast welcoming them
            Toast.makeText(this, "Welcome back, " + name + "!", Toast.LENGTH_LONG).show();
        } else {

            // otherwise, show a dialog to ask for their name
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Hello!");
            alert.setMessage("What is your name?");

            // Create EditText for entry
            final EditText input = new EditText(this);
            alert.setView(input);

            // Make an "OK" button to save the name
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int whichButton) {

                    // Grab the EditText's input
                    String inputName = input.getText().toString();

                    // Put it into memory (don't forget to commit!)
                    SharedPreferences.Editor e = mSharedPreferences.edit();
                    e.putString(PREF_NAME, inputName);
                    e.commit();

                    // Welcome the new user
                    Toast.makeText(getApplicationContext(), "Welcome, " + inputName + "!", Toast.LENGTH_LONG).show();
                }
            });

            // Make a "Cancel" button
            // that simply dismisses the alert
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int whichButton) {
                }
            });

            alert.show();
        }

    }*/
/*
   We comment this menu for about for now.
 */
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_omg, menu);
//        return true;
//    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_button:
                addTask(t1.getText().toString());
//                mToDoList.add(t1.getText().toString());
                t1.setText("");
                mArrayAdapter.notifyDataSetChanged();
                break;

            case R.id.main_button2:
                mToDoList.clear();
                clearAllDB();
                mArrayAdapter.notifyDataSetChanged();
                break;

            default:
                break;
        }

//        myFirstBtn.setActivated(false);
    }

    private void clearAllDB() {
        TaskDBHelper helper = new TaskDBHelper(OMGActivity.this);
        SQLiteDatabase db = helper.getWritableDatabase();

        db.execSQL("delete from tasks;");

        //insertWithOnConflict(TaskContract.TABLE,null,values,
        //      SQLiteDatabase.CONFLICT_IGNORE);
    }

    private void addTask(String s) {
        mToDoList.add(s);
        TaskDBHelper helper = new TaskDBHelper(OMGActivity.this);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.clear();
        values.put(TaskContract.Columns.TASK, s);

        db.insertWithOnConflict(TaskContract.TABLE, null, values,
                SQLiteDatabase.CONFLICT_IGNORE);
    }

    // Attaches a long click listener to the listview
    private void setupListViewListener() {
        mainListView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View item, int pos, long id) {
                        // Remove the item within array at position
                        int position = pos;
                        String x = (String) mToDoList.get(pos);
                        mToDoList.remove(position);
                        // toggle if done or not done.
                        if (x.indexOf(" -- Done") > 0) {
                            x = x.replaceFirst(" -- Done", "");
                        } else {
                            x = x + " -- Done";
                        }

                        mToDoList.add(position, x);
                        //mToDoList.remove(pos);
                        // Refresh the adapter
                        mArrayAdapter.notifyDataSetChanged();
                        // Return true consumes the long click event (marks it handled)
                        return true;
                    }

                });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //System.out.println (" the name"+parent.getItemAtPosition(position).toString());

        //t1.setText(parent.getItemAtPosition(position).toString());
    }


}
