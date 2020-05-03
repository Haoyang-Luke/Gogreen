package com.example.gogreen.Data;

public class ModelUser {
    //use same name as in firebase database
    String name;
    String image;
    String  points;
    String  place;
    String email;

    public ModelUser() {
    }

    public ModelUser(String name, String image, String points, String place,String email) {
        this.name = name;
        this.image = image;
        this.points = points;
        this.place = place;
        this.email=email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
