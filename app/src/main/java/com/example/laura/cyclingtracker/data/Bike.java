package com.example.laura.cyclingtracker.data;


import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

@ParseClassName("Bike")
public class Bike extends ParseObject {

    public Bike(){    }


    private static ParseQuery<Bike> createQuery() {
        ParseQuery<Bike> query = new ParseQuery<Bike>(Bike.class);
        return query;
    }

    public static void findInBackground(ParseUser user, final FindCallback<Bike> callback){
        ParseQuery<Bike> query = createQuery();
        //query.fromLocalDatastore();
        if(user != null){
            query.whereEqualTo("userId", user);
        }
        query.findInBackground(new FindCallback<Bike>(){
            @Override
            public void done(List<Bike> bikes, ParseException e) {

                callback.done(bikes, e);
            }
        });
    }
}
