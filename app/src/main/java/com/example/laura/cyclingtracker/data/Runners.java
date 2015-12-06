package com.example.laura.cyclingtracker.data;

import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

@ParseClassName("Runners")
public class Runners extends ParseObject{

    public Runners(){    }


    private static ParseQuery<Runners> createQuery() {
        ParseQuery<Runners> query = new ParseQuery<Runners>(Runners.class);
        return query;
    }

    public static void findInBackground(ParseUser user, final FindCallback<Runners> callback){
        ParseQuery<Runners> query = createQuery();
        //query.fromLocalDatastore();
        if(user != null){
            query.whereEqualTo("userId", user);
        }
        query.findInBackground(new FindCallback<Runners>(){
            @Override
            public void done(List<Runners> runners, ParseException e) {

                callback.done(runners, e);
            }
        });
    }
}
