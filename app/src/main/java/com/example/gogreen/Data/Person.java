package com.example.gogreen.Data;

public class Person {
    private String name;
    private int point;
    private int icon;
    private int place;

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public Person(String name, int point, int icon, int place) {
        this.name = name;
        this.point = point;
        this.icon = icon;
        this.place=place;
    }

    public Person() {
    }
}
