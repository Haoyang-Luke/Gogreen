package com.example.gogreen.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.HashMap;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

public class RewardActivity extends AppCompatActivity  {



    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ProgressDialog pd;

    StorageReference storageReference;

    static int points,oder;


    /**
     * Obtain user score information from the database and make a judgment.
     * If the score exceeds 50 points, you can change a cup of coffee. If it is less than fifty,
     * it will not work
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);
        getdata();
        pd = new ProgressDialog(RewardActivity.this);
        Button reward=findViewById(R.id.reward_change);
        reward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(points>=50){
                    points-=50;
                    oder+=50;
                    TextView points_No=findViewById(R.id.reward_point);
                    points_No.setText("Points: "+points);
                    datachange();

                }else{
                    Toast.makeText(RewardActivity.this,"Sorry your points is not enough",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void datachange() {
        HashMap<String,Object> result=new HashMap<>();
        result.put("points",points);
        result.put("oder",oder);
        databaseReference.child(user.getUid()).updateChildren(result)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        pd.dismiss();
                        Toast.makeText(RewardActivity.this,"cost 50 points",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }


    /**
     * Update database information
     */
    private void getdata() {
        final TextView points_No=findViewById(R.id.reward_point);
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
                    String point=""+ds.child("points").getValue();
                    String oders=""+ds.child("oder").getValue();
                    points=Integer.valueOf(point);
                    oder=Integer.valueOf(oders);
                    points_No.setText("Points: "+points);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
