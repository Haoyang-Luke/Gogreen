package com.example.gogreen.Data;

/**
 * Entity class of child data
 */
public class RecycleChildEntity {

    private String child;
    private int icon;
    private int back;

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }


    public int getBack() {
        return back;
    }

    public void setBack(int back) {
        this.back = back;
    }

    public RecycleChildEntity(String child, int icon, int back) {
        this.child = child;
        this.icon=icon;
        this.back=back;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }
}
