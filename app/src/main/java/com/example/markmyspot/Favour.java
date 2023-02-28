package com.example.markmyspot;

public class Favour {
    private String Lati;
    private String Longi;
    private String name;
    private  String DummyEmail;
    public  String getDummyEmail() {
        return DummyEmail;
    }

    public Favour() {
    }

    public String getLati() {
        return Lati;
    }

    public String getLongi() {
        return Longi;
    }

    public String getName() {
        return name;
    }

    public Favour(String email, String lat, String aLong, String name) {
        Lati = lat;
        Longi = aLong;
        this.name = name;
        DummyEmail = email;
    }
}
