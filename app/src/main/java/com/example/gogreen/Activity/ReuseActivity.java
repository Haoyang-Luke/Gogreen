package com.example.gogreen.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gogreen.Data.MySingleton;
import com.example.gogreen.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;


import java.util.ArrayList;
import java.util.HashMap;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

public class ReuseActivity extends AppCompatActivity {

    private ArrayList<String> selectedReusedItems;//all selectedReusedItems,How to add this arraylist to spinner?
    private ImageButton editButton;
    private ImageButton reuseItem;
    private Spinner dropDownView;
    private ImageButton addToDatabase;
    private Button pointsButton;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    private static int currentPoint,oderPoint;
    ProgressDialog pd;
    private static String first,second,last;


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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reuse);


        //Receive data from the database
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        storageReference = getInstance().getReference();
        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String rItem1 = "" + ds.child("rItem1").getValue();
                    String rItem2 = "" + ds.child("rItem2").getValue();
                    String rItem3 = "" + ds.child("rItem3").getValue();
                    TextView itemOne = findViewById(R.id.item1);
                    TextView itemTwo = findViewById(R.id.item2);
                    TextView itemThree = findViewById(R.id.item3);
                    itemOne.setText(rItem1);
                    itemTwo.setText(rItem2);
                    itemThree.setText(rItem3);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        pd = new ProgressDialog(ReuseActivity.this);


        addNewItem(addToDatabase);

        //go to Main page,Return to the main page to see the score
        goToPoints(pointsButton);

        //go to Edits page
        goToEditReusedItems(editButton);

        firebaseDatabase=FirebaseDatabase.getInstance();
        Toast.makeText(this, "Firebase connection successful!", Toast.LENGTH_SHORT).show();


        //add values to spinner from database
        populateSpinner();

        //press add button to add an item you have reused
        addReusedItem(reuseItem);

        setRecentlySelectedItems();


    }








    /*
    Method to set the text of the recently selected item boxes
     */
    public void setRecentlySelectedItems()
    {
        TextView itemOne = findViewById(R.id.item1);
        TextView itemTwo = findViewById(R.id.item2);
        TextView itemThree = findViewById(R.id.item3);
        itemOne.setText(first);
        itemTwo.setText(second);
        itemThree.setText(last);
    }



    private void addReusedItem(ImageButton reuseItem) {
        reuseItem=findViewById(R.id.imageButtonReuseItem);
        reuseItem.setOnClickListener(new View.OnClickListener() {
            //Increase score
            @Override
            public void onClick(View v) {
                String item=dropDownView.getSelectedItem().toString();


                //get the points data from firebase
                getdatafromfirebase();
                last=second;
                second=first;
                first=item;
                //change the point and refresh
                changedatafromfirebase();
            }
        });
    }


    /*
    Populate spinner with values from database.
     */
    private void populateSpinner() {
        databaseReference=firebaseDatabase.getReference("ReusableItems").child("Items");
        dropDownView = findViewById(R.id.spinner);
       databaseReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               final ArrayList<String> itemList=new ArrayList<>();
               for(DataSnapshot ds:dataSnapshot.getChildren()){
                   String itemSnapshot=ds.getValue(String.class);
                   itemList.add(itemSnapshot);
               }
               final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ReuseActivity.this, android.R.layout.simple_spinner_dropdown_item, itemList);
               arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
               dropDownView.setAdapter(arrayAdapter);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
    }

    private void goToEditReusedItems(ImageButton editButton) {
        editButton = findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReuseActivity.this, ReuseEditActivity.class);
                intent.putStringArrayListExtra("list",  MySingleton.getInstance().getArray());
                startActivity(intent);
            }
        });
    }

    private void goToPoints(Button pointsButton) {

        pointsButton = findViewById(R.id.points);
        pointsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//Reverse to the main page
                finish();
            }
        });


    }
    /*
    Method to add new items to Database using the '+' button.
     */
    private void addNewItem(View view) {//Method to add new items to Database using the '+' button.
        addToDatabase = findViewById(R.id.imageButtonAddNew);
        addToDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.typeHere2);
               // databaseReference=firebaseDatabase.getReference("ReusableItems");
                //databaseReference.child("Items").push().setValue(editText.getText().toString());
                MySingleton.getInstance().addToArray(editText.getText().toString());
                editText.setText(null);

            }
        });
    }
    private void getdatafromfirebase() {
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        storageReference = getInstance().getReference();

        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    String oder=""+ds.child("oder").getValue();
                    String point=""+ds.child("points").getValue();
                    String rItem1=""+ds.child("rItem1").getValue();
                    String rItem2=""+ds.child("rItem2").getValue();
                    String rItem3=""+ds.child("rItem3").getValue();
                    currentPoint=Integer.valueOf(point);
                    oderPoint=Integer.valueOf(oder);
                    first=rItem1;
                    second=rItem2;
                    last=rItem3;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void changedatafromfirebase() {
        currentPoint+=5;
        oderPoint-=5;
        HashMap<String,Object> result=new HashMap<>();
        result.put("rItem1",first);
        result.put("rItem2",second);
        result.put("rItem3",last);
        result.put("points",currentPoint);
        result.put("oder",oderPoint);
        databaseReference.child(user.getUid()).updateChildren(result)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("add 5 points");
                        pd.dismiss();
                        Toast.makeText(ReuseActivity.this,"add 5 points",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}
