package com.example.gogreen;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.gogreen.Adapter.AdapterUsers;
import com.example.gogreen.Data.ModelUser;
import com.example.gogreen.Picture.CircularImage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

import static com.google.firebase.storage.FirebaseStorage.getInstance;


/**
 * A simple {@link Fragment} subclass.
 */
public class RankFragment extends Fragment {

    RecyclerView recyclerView;
    CircularImage mPhoto;
    private TextView myName,myPlace,myPoint;
    AdapterUsers adapterUsers;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    final List<ModelUser> modelUsers = new ArrayList<>();


    public RankFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rank, container, false);

        //init recyclerview
        recyclerView = view.findViewById(R.id.user_recyclerView);
        //set it's properties

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        //getall Users
        getAllUsers();

        myName=view.findViewById(R.id.your_name);
        myPlace=view.findViewById(R.id.your_place);
        myPoint=view.findViewById(R.id.your_point);
        mPhoto=view.findViewById(R.id.your_photo);

        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds:dataSnapshot.getChildren()){
                    String image = ""+ds.child("image").getValue();
                    String name = ""+ds.child("name").getValue();
                    String email=""+ds.child("email").getValue();
                    String points = "" + ds.child("points").getValue().toString();
                    myName.setText(name);
                    myPoint.setText(points+"pts");
                    try{
                        Picasso.get().load(image).placeholder(R.drawable.ic_face).into(mPhoto);

                    }catch (Exception e){

                    }int count=0;
                    for (int i = 0; i <modelUsers.size() ; i++) {
                        if(modelUsers.get(i).getEmail().equals(email)){
                            myPlace.setText(modelUsers.get(i).getPlace());
                            continue;
                        }
                        count++;
                    }
                    if(count==modelUsers.size()){
                        myPlace.setText("No.10+");
                    }


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        return view;
    }



    private void getAllUsers() {
        //get curretn user

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        storageReference = getInstance().getReference();



        final int[] i = {1};
        Query query = databaseReference.orderByChild("oder");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                modelUsers.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String name = "" + ds.child("name").getValue().toString();
                    String points = "" + ds.child("points").getValue().toString();
                    String image = "" + ds.child("image").getValue().toString();
                    String email=""+ds.child("email").getValue();
                    String place = "No." + i[0];
                    i[0]++;
                    ModelUser mo = new ModelUser(name, image, points, place,email);
                    if (modelUsers.size() <= 10) modelUsers.add(mo);
                }
                adapterUsers = new AdapterUsers(getActivity(), modelUsers);

                //set adapter to recycler view

                recyclerView.setAdapter(adapterUsers);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}


