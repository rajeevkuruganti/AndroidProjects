package com.example.fourcircles.omgapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class OMGActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    TextView t1;
    Button myFirstBtn;
    ListView mainListView;
    ArrayAdapter mArrayAdapter;
    ArrayList mToDoList = new ArrayList();
    private static final String PREFS = "prefs";
    private static final String PREF_NAME = "name";
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_omg);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /* find controls
              find the button and text field
         */
        t1 = (TextView) findViewById(R.id.TodoText);
        myFirstBtn = (Button) findViewById(R.id.main_button);
        myFirstBtn.setOnClickListener(this);
        mainListView = (ListView) findViewById(R.id.listViewNames);
        // Create an ArrayAdapter for the ListView
        mArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                mToDoList);
        mainListView.setOnItemClickListener(this);

// Set the ListView to use the ArrayAdapter
        mainListView.setAdapter(mArrayAdapter);
        // long focus on the item will delete the item as Done.
        setupListViewListener();
//        t1.setText("setting in Java");

    }

    private void displayWelcome() {
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

                public void onClick(DialogInterface dialog, int whichButton) {}
            });

            alert.show();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_omg, menu);
        return true;
    }


    @Override
    public void onClick(View v) {
        //t1.setText("Button preseeed.. l");
        myFirstBtn.setText("Add");
        mToDoList.add(t1.getText().toString());
        //System.out.println("mToDoList.size() = "+mToDoList.size());
        mArrayAdapter.notifyDataSetChanged();
//        myFirstBtn.setActivated(false);
    }
    // Attaches a long click listener to the listview
    private void setupListViewListener() {
        mainListView.setOnItemLongClickListener(
				new AdapterView.OnItemLongClickListener() {
					@Override
					public boolean onItemLongClick(AdapterView<?> adapter,
												   View item, int pos, long id) {
						// Remove the item within array at position
						//        mToDoList.remove(pos);

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
		//w tItemView = (TextView) v.findViewById(R.id.textView);
		t1.setPaintFlags(t1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        t1.setText(parent.getItemAtPosition(position).toString());
    }
}
