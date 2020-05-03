package com.example.gogreen.Data;

import com.example.gogreen.R;


import java.util.ArrayList;

/**
 *
 *Set data, background picture, color, etc. for recycle
 *
 */
public class GroupModel {


    public static ArrayList<GroupEntity> getGroups() {

        RecycleChildEntity recycleChildEntity1 =new RecycleChildEntity("Alkalinebateries", R.drawable.alkalinebateries,R.color.bg1);
        RecycleChildEntity recycleChildEntity2 =new RecycleChildEntity("Buttonbateries", R.drawable.buttonbateries,R.color.bg2);
        RecycleChildEntity recycleChildEntity3 =new RecycleChildEntity("Rechargeablebateries", R.drawable.rechargeablebatteries,R.color.bg3);
        ArrayList<RecycleChildEntity> list1=new ArrayList<>();
        list1.add(recycleChildEntity1);
        list1.add(recycleChildEntity2);
        list1.add(recycleChildEntity3);
        GroupEntity group1=new GroupEntity("bateries","00",list1,R.drawable.bateries);


        RecycleChildEntity recycleChildEntity4 =new RecycleChildEntity("Bottle", R.drawable.bottle,R.color.bg4);
        RecycleChildEntity recycleChildEntity5 =new RecycleChildEntity("Colorlessglass", R.drawable.colorlessglass,R.color.bg5);
        RecycleChildEntity recycleChildEntity6 =new RecycleChildEntity("tintedglass", R.drawable.tintedglass,R.color.bg1);
        RecycleChildEntity recycleChildEntity7 =new RecycleChildEntity("porcelaincup", R.drawable.porcelaincup,R.color.bg2);
        ArrayList<RecycleChildEntity> list2=new ArrayList<>();
        list2.add(recycleChildEntity4);
        list2.add(recycleChildEntity5);
        list2.add(recycleChildEntity6);
        list2.add(recycleChildEntity7);
        GroupEntity group2=new GroupEntity("glass","",list2,R.drawable.glass);


        RecycleChildEntity recycleChildEntity8 =new RecycleChildEntity("cans", R.drawable.cans,R.color.bg3);
        RecycleChildEntity recycleChildEntity9 =new RecycleChildEntity("knife", R.drawable.knife,R.color.bg4);
        RecycleChildEntity recycleChildEntity10 =new RecycleChildEntity("paperclip", R.drawable.paperclip,R.color.bg5);
        RecycleChildEntity recycleChildEntity11 =new RecycleChildEntity("scissors", R.drawable.scissors,R.color.bg1);
        RecycleChildEntity recycleChildEntity12 =new RecycleChildEntity("staples", R.drawable.staples,R.color.bg2);
        ArrayList<RecycleChildEntity> list3=new ArrayList<>();
        list3.add(recycleChildEntity8);
        list3.add(recycleChildEntity9);
        list3.add(recycleChildEntity10);
        list3.add(recycleChildEntity11);
        list3.add(recycleChildEntity12);
        GroupEntity group3=new GroupEntity("metal","",list3,R.drawable.metal);

        RecycleChildEntity recycleChildEntity13 =new RecycleChildEntity("officepaper", R.drawable.officepaper,R.color.bg3);
        RecycleChildEntity recycleChildEntity14 =new RecycleChildEntity("tissuepaper", R.drawable.tissuepaper,R.color.bg4);
        ArrayList<RecycleChildEntity> list4=new ArrayList<>();
        list4.add(recycleChildEntity13);
        list4.add(recycleChildEntity14);
        GroupEntity group4=new GroupEntity("paper","00",list4,R.drawable.paper);


        RecycleChildEntity recycleChildEntity15 =new RecycleChildEntity("plasticbag", R.drawable.plasticbag,R.color.bg5);
        RecycleChildEntity recycleChildEntity16 =new RecycleChildEntity("plasticbottle", R.drawable.plasticbottle,R.color.bg1);
        RecycleChildEntity recycleChildEntity17 =new RecycleChildEntity("tableware", R.drawable.tableware,R.color.bg2);
        ArrayList<RecycleChildEntity> list5=new ArrayList<>();
        list5.add(recycleChildEntity15);
        list5.add(recycleChildEntity16);
        list5.add(recycleChildEntity17);
        GroupEntity group5=new GroupEntity("plastic","00",list5,R.drawable.plastic);

        ArrayList<GroupEntity> mList=new ArrayList<>();
        mList.add(group1);
        mList.add(group2);
        mList.add(group3);
        mList.add(group4);
        mList.add(group5);
        return mList;
    }


    public static ArrayList<RecycleGroupEntity> getExpandableGroups() {


        RecycleChildEntity recycleChildEntity1 =new RecycleChildEntity("Alkalinebateries", R.drawable.alkalinebateries,R.color.bg1);
        RecycleChildEntity recycleChildEntity2 =new RecycleChildEntity("Buttonbateries", R.drawable.buttonbateries,R.color.bg2);
        RecycleChildEntity recycleChildEntity3 =new RecycleChildEntity("Rechargeablebateries", R.drawable.rechargeablebatteries,R.color.bg3);
        ArrayList<RecycleChildEntity> list1=new ArrayList<>();
        list1.add(recycleChildEntity1);
        list1.add(recycleChildEntity2);
        list1.add(recycleChildEntity3);
        RecycleGroupEntity group1=new RecycleGroupEntity("bateries","00",true,list1,R.drawable.bateries);


        RecycleChildEntity recycleChildEntity4 =new RecycleChildEntity("Bottle", R.drawable.bottle,R.color.bg4);
        RecycleChildEntity recycleChildEntity5 =new RecycleChildEntity("Colorlessglass", R.drawable.colorlessglass,R.color.bg5);
        RecycleChildEntity recycleChildEntity6 =new RecycleChildEntity("tintedglass", R.drawable.tintedglass,R.color.bg1);
        RecycleChildEntity recycleChildEntity7 =new RecycleChildEntity("porcelaincup", R.drawable.porcelaincup,R.color.bg2);
        ArrayList<RecycleChildEntity> list2=new ArrayList<>();
        list2.add(recycleChildEntity4);
        list2.add(recycleChildEntity5);
        list2.add(recycleChildEntity6);
        list2.add(recycleChildEntity7);
        RecycleGroupEntity group2=new RecycleGroupEntity("glass","11",true,list2,R.drawable.glass);


        RecycleChildEntity recycleChildEntity8 =new RecycleChildEntity("cans", R.drawable.cans,R.color.bg3);
        RecycleChildEntity recycleChildEntity9 =new RecycleChildEntity("knife", R.drawable.knife,R.color.bg4);
        RecycleChildEntity recycleChildEntity10 =new RecycleChildEntity("paperclip", R.drawable.paperclip,R.color.bg5);
        RecycleChildEntity recycleChildEntity11 =new RecycleChildEntity("scissors", R.drawable.scissors,R.color.bg1);
        RecycleChildEntity recycleChildEntity12 =new RecycleChildEntity("staples", R.drawable.staples,R.color.bg2);
        ArrayList<RecycleChildEntity> list3=new ArrayList<>();
        list3.add(recycleChildEntity8);
        list3.add(recycleChildEntity9);
        list3.add(recycleChildEntity10);
        list3.add(recycleChildEntity11);
        list3.add(recycleChildEntity12);
        RecycleGroupEntity group3=new RecycleGroupEntity("metal","11",true,list3,R.drawable.metal);

        RecycleChildEntity recycleChildEntity13 =new RecycleChildEntity("officepaper", R.drawable.officepaper,R.color.bg3);
        RecycleChildEntity recycleChildEntity14 =new RecycleChildEntity("tissuepaper", R.drawable.tissuepaper,R.color.bg4);
        ArrayList<RecycleChildEntity> list4=new ArrayList<>();
        list4.add(recycleChildEntity13);
        list4.add(recycleChildEntity14);
        RecycleGroupEntity group4=new RecycleGroupEntity("paper","11",true,list4,R.drawable.paper);

        RecycleChildEntity recycleChildEntity15 =new RecycleChildEntity("plasticbag", R.drawable.plasticbag,R.color.bg5);
        RecycleChildEntity recycleChildEntity16 =new RecycleChildEntity("plasticbottle", R.drawable.plasticbottle,R.color.bg1);
        RecycleChildEntity recycleChildEntity17 =new RecycleChildEntity("tableware", R.drawable.tableware,R.color.bg2);
        ArrayList<RecycleChildEntity> list5=new ArrayList<>();
        list5.add(recycleChildEntity15);
        list5.add(recycleChildEntity16);
        list5.add(recycleChildEntity17);
        RecycleGroupEntity group5=new RecycleGroupEntity("plastic","11",true,list5,R.drawable.plastic);

        ArrayList<RecycleGroupEntity> mList=new ArrayList<>();
        mList.add(group1);
        mList.add(group2);
        mList.add(group3);
        mList.add(group4);
        mList.add(group5);
        return mList;
    }

}
