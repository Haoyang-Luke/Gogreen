package com.example.gogreen.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.donkingliang.groupedadapter.holder.BaseViewHolder;
import com.example.gogreen.Adapter.RecycleAdapter;
import com.example.gogreen.Data.GroupModel;
import com.example.gogreen.R;


/**
 *This Activity belongs to the Recycle interface, mainly for the configuration of Recylerview
 * In the Recycle interface, the title is implemented with RecycleView, and the sub-items of the title are also implemented with recycleview
 */


public class RecycleActivity extends AppCompatActivity {

    private RecyclerView rvList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);

        rvList = (RecyclerView) findViewById(R.id.rv_list);



        rvList.setLayoutManager(new LinearLayoutManager(this));
        RecycleAdapter adapter = new RecycleAdapter(this, GroupModel.getExpandableGroups());
        adapter.setOnHeaderClickListener(new GroupedRecyclerViewAdapter.OnHeaderClickListener() {
            @Override
            public void onHeaderClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder,
                                      int groupPosition) {
                RecycleAdapter recycleAdapter = (RecycleAdapter) adapter;
                if (recycleAdapter.isExpand(groupPosition)) {
                    recycleAdapter.collapseGroup(groupPosition);
                } else {
                    recycleAdapter.expandGroup(groupPosition);
                }
            }
        });
        rvList.setAdapter(adapter);

    }

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, RecycleActivity.class);
        context.startActivity(intent);
    }
}
