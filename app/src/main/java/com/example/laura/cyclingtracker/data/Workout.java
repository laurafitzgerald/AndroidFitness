package com.example.laura.cyclingtracker.data;

import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;


@ParseClassName("Workout")
public class Workout extends ParseObject {


    public Workout(){    }

    private static ParseQuery<Workout> createQuery(){
        ParseQuery<Workout> query = new ParseQuery<Workout>(Workout.class);
          return query;
    }

    public static void findInBackground(ParseUser user, final FindCallback<Workout> callback){
        ParseQuery<Workout> query = Workout.createQuery();
        //query.fromLocalDatastore();
        if(user != null){
            query.whereEqualTo("userId", user);
        }
        query.findInBackground(new FindCallback<Workout>(){
            @Override
            public void done(List<Workout> workouts, ParseException e) {

                callback.done(workouts, e);
            }
        });
    }



    public String getType() {
        return getString("Type");
    }

    public String getMins() {
        return getString("Mins");
    }

    public String getHours(){
        return getString("Hours");
    }

    public String getSecs(){
        return getString("Secs");
    }
    public String getLocation(){
        return getString("Location");
    }

    public String getDistance(){
        return getString("distance");
    }




}
