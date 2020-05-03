package com.example.gogreen.map;

public class Common {
    public static double base_fare = 2.55;
    public static double time_rate = 0.35;
    public static double distance_rate = 1.75;
    public static final String googleAPIUrl = "https://maps.googleapis.com";

    public static double getPrice(double km, int min) {

        return (int) ((base_fare + (time_rate * min) + (distance_rate * km)));
    }


}
