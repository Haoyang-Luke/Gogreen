package com.example.gogreen.Data;

import java.util.ArrayList;

/**
 * Entity class of expandable and collapsible group data It is just a boolean isExpand more than GroupEntity,
 * which is used to indicate the expanded and collapsed state.
 */
public class RecycleGroupEntity {

    private String header;
    private String footer;
    private ArrayList<RecycleChildEntity> children;
    private boolean isExpand;
    private int icon;

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public RecycleGroupEntity(String header, String footer, boolean isExpand,
                              ArrayList<RecycleChildEntity> children, int icon) {
        this.header = header;
        this.footer = footer;
        this.isExpand = isExpand;
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

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }

    public ArrayList<RecycleChildEntity> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<RecycleChildEntity> children) {
        this.children = children;
    }
}
