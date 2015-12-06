package com.example.laura.cyclingtracker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.laura.cyclingtracker.helper.GlobalState;

/**
 * Created by laura on 06/12/15.
 */
public class StartActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

        Intent mainIntent = new Intent(StartActivity.this, LogInActivity.class);

        GlobalState gs = (GlobalState) getApplication();

        if((gs.isLoggedIn())){

            Toast.makeText(getApplicationContext(), "Logging In...", Toast.LENGTH_SHORT).show();

            mainIntent = new Intent(StartActivity.this, MainActivity.class);

        }

        startActivity(mainIntent);


    }

}
