package com.example.gogreen.Data;


import java.util.ArrayList;

public class MySingleton {

    private static MySingleton mInstance;
    private ArrayList<String> reusedList = null;

    public static MySingleton getInstance()
    {
        if (mInstance == null)
        {
            mInstance = new MySingleton();
        }
        return mInstance;
    }

    private MySingleton()
    {
        reusedList = new ArrayList<String>();
    }

    public ArrayList<String> getArray()
    {
        return this.reusedList;
    }

    public void addToArray(String s)
    {
        reusedList.add(s);
    }

    //not working.
    public void removeFromArray(String s)
    {
        reusedList.remove(s);
    }


}
