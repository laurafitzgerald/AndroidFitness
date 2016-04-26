package com.example.laura.cyclingtracker.helper;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.laura.cyclingtracker.data.Bike;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

/**
 * Created by laura on 17/04/16.
 */
public class GetBikeTask extends AsyncTask<Void, Void, ArrayList<Bike>> {



    private Context mContext;
    private HttpURLConnection urlConnection;
    private BufferedReader bufferedReader;
    private final String PROTOCOL = "http";
    private final String HOST = "api.itsmybike.io";
    private final int PORT = 80;
    private String file = "bikes";
    private JSONObject mBody;
    private Type mClazz;
    private String mXAuth;

    GlobalState gs;

    Gson gson = new Gson();

    private List<Observer> observers = new ArrayList<>();

    public GetBikeTask(Context context, String XAuth, JSONObject body, GlobalState gs, Type clazz){

        this.gs = gs;
        mContext = context;
        mBody = body;
        mXAuth = XAuth;
        mClazz = clazz;
        Log.v("SessionKey", mXAuth.toString());

        //file+="/Laura";





    }


    @Override
    protected void onPreExecute(){


    }


    @Override
    public ArrayList<Bike> doInBackground(Void...voids) {

        Log.v("GET BIKE TASK", "Inside do In Background");
        try {
            URL url = new URL(PROTOCOL, HOST, PORT, file);
            Log.v("URL", url.toString() );

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 ( compatible ) ");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("Accept", "*/*");
            //urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("XAuth", mXAuth);
            Log.v("SessionKey", mXAuth.toString());

/*
            if(mBody!=null) {
                Log.v("INFO", mBody.toString());
                String json = String.valueOf(mBody);
                Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
                writer.write(json);
                writer.close();
            }
*/

            bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            ArrayList<Bike> jsonList = gson.fromJson(bufferedReader, mClazz);
            Log.v("GETBIKETASK", "Response data - " + jsonList.toString());
            Log.v("Bike", jsonList.get(0).getColor());

            bufferedReader.close();
            return jsonList;

        }catch (Exception e){

            Log.e("ERROR", e.getMessage(), e);
            return null;
        }

    }


    protected void onPostExecute(ArrayList<Bike> response){
        if(response == null) {
            //response = "THERE WAS AN ERROR";
            Log.i("INFO", "something went wrong");
        }else {
            Log.v("Info", "Line 108 " + response);
            //MySettings.save(mContext, response);
            notifyAllObservers(response);


        }


    }

    public void attach(Observer observer){
        observers.add(observer);
    }

    public void notifyAllObservers(ArrayList<Bike> response){


        for (Observer observer : observers) {
            observer.update(null, response );
        }


    }



}
