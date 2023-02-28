package com.example.markmyspot;

public class userSettings {

    String Email;
    String FavEmail;
    String PrefferedType;
    String MeasurementSystem;

    public String getLati() {
        return Lati;
    }

    public String getLongi() {
        return Longi;
    }

    public String getName() {
        return name;
    }

    String Lati;
    String Longi;
    String name;
    public void setEmail(String email) {
        Email = email;
    }
    public userSettings(String emmail){
        Email = emmail;
    }
    public userSettings(String email, String lat, String aLong, String name) {
        Lati = lat;
        Longi = aLong;
        this.name = name;
        Email = email;
    }

    public userSettings(String email, String prefferedType, String measurementSystem) {
        Email = email;
        PrefferedType = prefferedType;
        MeasurementSystem = measurementSystem;
    }
    public userSettings(){

    }
    public  String getEmail() {
        return Email;
    }
    public  String getFavEmail() {
        return FavEmail;
    }

    public String getPrefferedType() {
        return PrefferedType;
    }

    public String getMeasurementSystem() {
        return MeasurementSystem;
    }
    public static class credentials{
        private String Lati;
        private String Longi;
        private String name;
        private  String DummyEmail;
        public  String getDummyEmail() {
            return DummyEmail;
        }

        public credentials() {
        }

        public String getLati() {
            return Lati;
        }

        public credentials(String email, String lat, String aLong, String name) {
            Lati = lat;
            Longi = aLong;
            this.name = name;
            DummyEmail = email;
        }
    }
    public static class userEmai{
        public static String myEmail;
    }
}
