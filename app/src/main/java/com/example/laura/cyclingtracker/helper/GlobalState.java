package com.example.laura.cyclingtracker.helper;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.laura.cyclingtracker.data.Gear;
import com.example.laura.cyclingtracker.data.Profile;
import com.example.laura.cyclingtracker.data.Workout;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;


public class GlobalState extends Application {

    private Profile profile;


    private boolean loggedIn;
    private ArrayList<Workout> listWorkouts = new ArrayList<Workout>();

//    @Override
    public void onCreate(){
        super.onCreate();


        Parse.enableLocalDatastore(this);
        Parse.initialize(getApplicationContext(), "K3IYGy8Ms8ACUMozyhNGPsWrSCRX5Tke4SFIzoOg", "1DUVXOASNGruzdD0OfF7BqSMi4cEwbScVF7aiDoV");
        ParseInstallation.getCurrentInstallation().saveInBackground();

        ParseObject.registerSubclass(Workout.class);
        ParseUser.registerSubclass(Profile.class);
        ParseObject.registerSubclass(Gear.class);
/*
        Gear gear = new Gear();
        gear.put("Type", "Bike");
        gear.put("Nickname", "Wheels");
        gear.put("user_id", ParseUser.getCurrentUser());

        gear.saveEventually(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {


                } else {




                }
            }
        });*/



    }


    public boolean connectedToInternet(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = connMgr.getActiveNetworkInfo();
        if (networkinfo != null && networkinfo.isConnected()) {
            return true;
        }
        return false;


    }





    public List<Workout> getListWorkouts(){

        return listWorkouts;
    }



    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }


    public void populateWorkoutList(){
        ParseQuery<Workout> query = new ParseQuery<Workout>("Workout");
        query.whereEqualTo("userId", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<Workout>() {
            @Override
            public void done(List<Workout> workouts, ParseException e) {

                for(Workout wo: workouts){


                    wo.pinInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                            if (e != null) {
                                Log.v("Populate local workouts", "Failed to save workout locally");
                            } else {
                                Log.v("Populate local workouts", "Workout saved locally");
                            }
                        }
                    });



                }

                ;
            }
        });


    }




}


