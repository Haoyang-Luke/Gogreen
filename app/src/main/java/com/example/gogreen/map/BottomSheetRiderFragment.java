package com.example.gogreen.map;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gogreen.R;
import com.example.gogreen.map.Remote.GoogleMapAPI;
import com.example.gogreen.map.Remote.IGoogleAPI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

public class BottomSheetRiderFragment extends BottomSheetDialogFragment {

    String mLocation, mDestination;
    TextView txtCalculate, txtLocation, txtDestination;
    IGoogleAPI mService;
    Button walking, cycling, driving;

    private static int currentPoint, oderPoint,addPoints;
    //get database
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    ProgressDialog pd;


    boolean isTapOnMap;

    public static BottomSheetDialogFragment newInstance(String location, String destination) {

        BottomSheetDialogFragment f = new BottomSheetRiderFragment();
        Bundle args = new Bundle();
        args.putString("location", location);
        args.putString("destination", destination);

        f.setArguments(args);
        return f;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLocation = getArguments().getString("location");
        mDestination = getArguments().getString("destination");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_ridermap, container, false);



        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        storageReference = getInstance().getReference();


        txtLocation = view.findViewById(R.id.txtLocation);
        txtDestination = view.findViewById(R.id.txtDestination);
        txtCalculate = view.findViewById(R.id.txtCalculate);
        cycling = view.findViewById(R.id.cycling);
        walking = view.findViewById(R.id.walking);
        driving = view.findViewById(R.id.driving);


        mService = GoogleMapAPI.getClient(Common.googleAPIUrl).create(IGoogleAPI.class);
        getPrice(mLocation, mDestination);

        pd = new ProgressDialog(getActivity());


        //set data
        //call this fragment from place autocomplete textView
        txtLocation.setText(mLocation);
        txtDestination.setText(mDestination);
        return view;
    }

    private void changedatafromfirebase(final int pointx) {
        currentPoint += pointx;
        oderPoint -= pointx;
        HashMap<String, Object> result = new HashMap<>();
        result.put("points", currentPoint);
        result.put("oder", oderPoint);
        databaseReference.child(user.getUid()).updateChildren(result)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

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
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String oder = "" + ds.child("oder").getValue();
                    String point = "" + ds.child("points").getValue();
                    currentPoint = Integer.valueOf(point);
                    oderPoint = Integer.valueOf(oder);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getPrice(String mLocation, String mDestination) {
        String requestUrl = null;
        try {
            requestUrl = "https://maps.googleapis.com/maps/api/directions/json?" +
                    "mode=walking&"
                    + "transit_routing_preference=less=less_driving&"
                    + "origin=" + mLocation + "&"
                    + "destination=" + mDestination + "&"
                    + "key=AIzaSyC2t1dfvCFEVT3u1B9XV1BmmCA4MnBYGbo";
            Log.e("LINK", requestUrl); // print for debug
            mService.getPath(requestUrl).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    //get Object
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        JSONArray routes = jsonObject.getJSONArray("routes");

                        JSONObject object = routes.getJSONObject(0);
                        JSONArray legs = object.getJSONArray("legs");

                        JSONObject legsObject = legs.getJSONObject(0);

                        //Get distance
                        JSONObject distance = legsObject.getJSONObject("distance");
                        String distance_text = distance.getString("text");
                        //Use regex to extract double from string
                        //This regex will remove all text not is digit
                        Double distance_value = Double.parseDouble(distance_text.replaceAll("[^0-9\\\\.]+", ""));

                        //Get Time
                        JSONObject time = legsObject.getJSONObject("duration");
                        String time_text = time.getString("text");
                        Integer time_value = Integer.parseInt(time_text.replaceAll("\\D+", ""));

                        String final_calculate = String.format("%s + %s = %.2f", distance_text, time_text,
                                Common.getPrice(distance_value, time_value));
                        String final_number=String.format("%.0f", Common.getPrice(distance_value, time_value));
                        txtCalculate.setText(final_calculate);
                       addPoints=Integer.parseInt(final_number);
                       Log.i("++++++++++",addPoints+"");

                        cycling.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                getdatafromfirebase();
                                int pointC=addPoints;
                                changedatafromfirebase(pointC);
                                Toast.makeText(getActivity(), "Added"+pointC+"points successfully", Toast.LENGTH_LONG).show();


                            }
                        });

                        walking.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getdatafromfirebase();
                                int pointw= (int) (addPoints*1.5);
                                changedatafromfirebase(pointw);
                                Toast.makeText(getActivity(), "Added"+pointw+"points successfully", Toast.LENGTH_LONG).show();

                            }
                        });
                        driving.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getActivity(), "No reward for driving", Toast.LENGTH_LONG).show();
                            }
                        });


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                    Log.e("ERROR", t.getMessage());

                }
            });
        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }
}
