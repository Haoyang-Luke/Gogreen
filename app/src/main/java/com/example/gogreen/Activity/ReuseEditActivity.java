package com.example.gogreen.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;


import com.example.gogreen.Data.MySingleton;
import com.example.gogreen.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ReuseEditActivity extends AppCompatActivity {
    private Button pointsButton;
    private Spinner editItems;
    private ArrayList<String> arrayList;
    private ImageView addButton;
    private ImageButton removeButton;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

       /*
     Initialises the activity i.e. buttons gain functionality.
     - Add new item button YES
     - Points button YES
     - Populate spinner YES
     - Select item into Stack?
     - Recently viewed text box is top stack objects
     - Selecting recently viewed text box adds to new stack
     - View today's items - go to page
     - Today's items - view array list or stack
     - Today's items - remove items
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_reused_items);
        arrayList=new ArrayList<>();
        arrayList=MySingleton.getInstance().getArray();
        //import ArrayList of reused items from the MainActivity - Reuse page.
        //Bundle bundle = getIntent().getExtras();
        //arrayList = bundle.getStringArrayList("list");

        //back to home page
        goToPoints(pointsButton);

        //go to Edits page


        //get data
        populateSpinner();

        //and another item
        addReusedItem(addButton);

        removeReusedItem(removeButton);

        //remove an item
        //removeReusedItem(removeButton);
    }




    /*
    Method to go to Points page when 'points' button selected
     */
    public void goToPoints(View view)
    {
        pointsButton = findViewById(R.id.points2);
        pointsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             finish();
            }
        });

    }

    /*
    Removes selected item from ArrayList.
    Doesn't update the OG ArrayList however.
     */
    public void removeReusedItem(View view)
    {
        removeButton = findViewById(R.id.remove);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item=editItems.getSelectedItem().toString();
                arrayList.remove(item);
                populateSpinner();
            }
        });


    }

    /*
    Adds another item with the same name as the selected item to the ArrayList.
    Doesn't update the OG ArrayList however.
     */
    public void addReusedItem(View view)
    {
        addButton = findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String item=editItems.getSelectedItem().toString();
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference=firebaseDatabase.getReference("ReusableItems");
                databaseReference.child("Items").push().setValue(item);
                arrayList.remove(item);
                /*
                String item =  editItems.getSelectedItem().toString();
                String item2 = item;
                //arrayList.add(item2);
                MySingleton.getInstance().addToArray(item2);*/
                populateSpinner();
            }
        });

    }

    /*
    Populate spinner from selected items in main Reuse page
     */
    public void populateSpinner()
    {

        editItems =  findViewById(R.id.listOfItems);

        //set a arraylist to get date from Mysingledata

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ReuseEditActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayList );
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editItems.setAdapter(arrayAdapter);

    }
}

