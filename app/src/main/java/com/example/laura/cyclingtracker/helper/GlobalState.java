package com.example.laura.cyclingtracker.helper;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.laura.cyclingtracker.data.Bike;

import java.util.ArrayList;


public class GlobalState extends Application {


    public ArrayList<Bike> bikes = new ArrayList<Bike>();


    public String session;
    private Double currentLat;
    private Double currentLng;
    public String currentUser;
        //http://10.2.3.2:8000
    public Double selectedLat;
    public Double selectedLng;

    private boolean loggedIn;
    //private ArrayList<Workout> listWorkouts = new ArrayList<Workout>();

//    @Override
    public void onCreate(){
        super.onCreate();



    }


    public boolean connectedToInternet(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = connMgr.getActiveNetworkInfo();
        if (networkinfo != null && networkinfo.isConnected()) {
            return true;
        }
        return false;


    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }



    public Double getCurrentLat() {
        return currentLat;
    }

    public void setCurrentLat(Double currentLat) {
        this.currentLat = currentLat;
    }

    public Double getCurrentLng() {
        return currentLng;
    }

    public void setCurrentLng(Double currentLng) {
        this.currentLng = currentLng;
    }



    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }


    public void populateWorkoutList(){


    }




}


