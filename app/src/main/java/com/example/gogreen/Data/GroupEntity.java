package com.example.gogreen.Data;

import java.util.ArrayList;

/**
 * The entity class of Recycle's main project grouping
 */
public class GroupEntity {

    private String header;
    private String footer;
    private ArrayList<RecycleChildEntity> children;
    private int icon;

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public GroupEntity(String header, String footer, ArrayList<RecycleChildEntity> children, int icon) {
        this.header = header;
        this.footer = footer;
        this.children = children;
        this.icon=icon;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public ArrayList<RecycleChildEntity> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<RecycleChildEntity> children) {
        this.children = children;
    }
}
