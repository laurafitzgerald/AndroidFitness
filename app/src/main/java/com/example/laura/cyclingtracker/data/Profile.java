package com.example.laura.cyclingtracker.data;


import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

@ParseClassName("_User")
public class Profile extends ParseUser {


    public Profile() {
    }




    private static ParseQuery<Profile> createQuery() {
        ParseQuery<Profile> query = new ParseQuery<Profile>(Profile.class);
        query.include("userId");
        query.include("lng");
        query.include("serialNumber");
        query.include("createdAt");
        //query.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
        return query;
    }


    public static void findInBackground(String email, final FindCallback<Profile> callback) {

        ParseQuery<Profile> query = Profile.createQuery();

        if (email != null) {

            query.whereEqualTo("email", email);

        }

        query.findInBackground(new FindCallback<Profile>() {

            @Override
            public void done(List<Profile> profile, ParseException e) {

                callback.done(profile, e);
            }
        });


    }


    public String getPassword() {
        return getString("password");
    }

    public String getEmail() {
        return getString("email");
    }

    public String getFirstName() {
        return getString("firstName");
    }

    public String getSecondName() {
        return getString("surName");
    }


    public String getLocation() {
        return getString("location");
    }


}
