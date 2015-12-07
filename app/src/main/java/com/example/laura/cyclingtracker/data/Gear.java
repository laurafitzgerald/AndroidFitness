package com.example.laura.cyclingtracker.data;

import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

@ParseClassName("Gear")
public class Gear extends ParseObject {

    public Gear(){}


    private static ParseQuery<Gear> createQuery(){
        ParseQuery<Gear> query = new ParseQuery<Gear>(Gear.class);
        return query;
    }

    public static void findInBackground(ParseUser user, final FindCallback<Gear> callback){
        ParseQuery<Gear> query = Gear.createQuery();
        //query.fromLocalDatastore();
        if(user != null){
            query.whereEqualTo("userId", user);
        }
        query.findInBackground(new FindCallback<Gear>(){
            @Override
            public void done(List<Gear> gear, ParseException e) {

                callback.done(gear, e);
            }
        });
    }

    public String getNickname() {
        return getString("nickname");
    }


    public String getType() {
        return getString("type");
    }


}
