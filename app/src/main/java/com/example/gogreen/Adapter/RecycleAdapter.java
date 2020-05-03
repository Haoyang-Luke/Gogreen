package com.example.gogreen.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.donkingliang.groupedadapter.holder.BaseViewHolder;
import com.example.gogreen.Data.RecycleChildEntity;
import com.example.gogreen.Data.RecycleGroupEntity;
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

public class RecycleAdapter extends GroupedRecyclerViewAdapter {
    private ArrayList<RecycleGroupEntity> mGroups;
    private EditText number;
    private ImageButton minus;
    private ImageButton plus;
    private Button sure;
    private int count;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    ProgressDialog pd;

    private static int currentPoint,oderPoint;

    public RecycleAdapter(Context context, ArrayList<RecycleGroupEntity> groups) {
        super(context);
        mGroups = groups;
    }

    @Override
    public int getGroupCount() {
        return mGroups == null ? 0 : mGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        //如果当前组收起，就直接返回0，否则才返回子项数。这是实现列表展开和收起的关键。
        if (!isExpand(groupPosition)) {
            return 0;
        }
        ArrayList<RecycleChildEntity> children = mGroups.get(groupPosition).getChildren();
        return children == null ? 0 : children.size();
    }

    @Override
    public boolean hasHeader(int groupPosition) {
        return true;
    }

    @Override
    public boolean hasFooter(int groupPosition) {
        return false;
    }

    @Override
    public int getHeaderLayout(int viewType) {
        return R.layout.adapter_expandable_header;
    }

    @Override
    public int getFooterLayout(int viewType) {
        return 0;
    }

    @Override
    public int getChildLayout(int viewType) {
        return R.layout.adapter_child;
    }

    @Override
    public void onBindHeaderViewHolder(BaseViewHolder holder, int groupPosition) {
        RecycleGroupEntity entity = mGroups.get(groupPosition);
        holder.setText(R.id.tv_expandable_header, entity.getHeader());
        holder.setImageResource(R.id.iv_head, entity.getIcon());
        ImageView ivState = holder.get(R.id.iv_state);
        if (entity.isExpand()) {
            ivState.setRotation(90);
        } else {
            ivState.setRotation(0);
        }
    }

    @Override
    public void onBindFooterViewHolder(BaseViewHolder holder, int groupPosition) {
    }

    @Override
    public void onBindChildViewHolder(BaseViewHolder holder, int groupPosition, final int childPosition) {

        RecycleChildEntity entity = mGroups.get(groupPosition).getChildren().get(childPosition);
        holder.setText(R.id.tv_child, entity.getChild());
        holder.setImageResource(R.id.IV_SSSSSS, entity.getIcon());
        holder.setBackgroundColor(R.id.child_back, super.mContext.getResources().getColor(entity.getBack()));

        //setBotton
        sure = holder.itemView.findViewById(R.id.recycle_sure);

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get the points data from firebase
                getdatafromfirebase();

                //change the point and refresh
                changedatafromfirebase();
            }
        });


    }

    private void changedatafromfirebase() {
        currentPoint+=5;
        oderPoint-=5;
        HashMap<String,Object> result=new HashMap<>();
        result.put("points",currentPoint);
        result.put("oder",oderPoint);
        databaseReference.child(user.getUid()).updateChildren(result)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(mContext, "ADD 5 POINTS", Toast.LENGTH_SHORT).show();
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
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    String oder=""+ds.child("oder").getValue();
                    String point=""+ds.child("points").getValue();
                    currentPoint=Integer.valueOf(point);
                    oderPoint=Integer.valueOf(oder);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public boolean isExpand(int groupPosition) {
        RecycleGroupEntity entity = mGroups.get(groupPosition);
        return entity.isExpand();
    }


    public void expandGroup(int groupPosition) {
        expandGroup(groupPosition, false);
    }


    public void expandGroup(int groupPosition, boolean animate) {
        RecycleGroupEntity entity = mGroups.get(groupPosition);
        entity.setExpand(true);
        if (animate) {
            notifyChildrenInserted(groupPosition);
        } else {
            notifyDataChanged();
        }
    }

    public void collapseGroup(int groupPosition) {
        collapseGroup(groupPosition, false);
    }


    public void collapseGroup(int groupPosition, boolean animate) {
        RecycleGroupEntity entity = mGroups.get(groupPosition);
        entity.setExpand(false);
        if (animate) {
            notifyChildrenRemoved(groupPosition);
        } else {
            notifyDataChanged();
        }
    }
}

