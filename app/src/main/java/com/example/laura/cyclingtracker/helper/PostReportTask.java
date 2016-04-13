package com.example.laura.cyclingtracker.helper;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

/**
 * Created by laura on 11/04/16.
 */
public class PostReportTask extends AsyncTask<Object, Void, String> {


    private Context mContext;
    private HttpURLConnection urlConnection;
    private BufferedReader bufferedReader;
    private final String PROTOCOL = "http";
    private final String HOST = "10.0.3.2";
    private final int PORT = 8000;
    private final String FILE = "reports";
    private JSONObject mBody;


    private List<Observer> observers = new ArrayList<>();

    public PostReportTask(Context context, JSONObject body){

        mContext = context;
        mBody = body;





    }


    @Override
    protected void onPreExecute(){


    }


    @Override
    protected String doInBackground(Object... objs) {


        try {
            URL url = new URL(PROTOCOL, HOST, PORT, FILE);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 ( compatible ) ");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("Accept", "*/*");
            urlConnection.setDoOutput(true);

            Log.v("INFO", mBody.toString());
            String json = String.valueOf(mBody);
            Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
            writer.write(json);
            writer.close();

            bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String sessionKey = "";

            while ( (sessionKey = bufferedReader.readLine()) != null){
                Log.v("INFO", sessionKey);
                sb.append(sessionKey);

            }
            String result = sb.toString();
            Log.v("INFO", result);
            bufferedReader.close();
            return result;


        }catch (Exception e){

            Log.e("ERROR", e.getMessage(), e);
            return null;
        }

    }


    protected void onPostExecute(String response){
        if(response == null) {
            //response = "THERE WAS AN ERROR";
            Log.i("INFO", "something went wrong");
        }else {
            Log.v("Info", "Line 108 " + response);
            MySettings.save(mContext, response);
            notifyAllObservers(response);


        }


    }

    public void attach(Observer observer){
        observers.add(observer);
    }

    public void notifyAllObservers(String response){


        for (Observer observer : observers) {
            observer.update(null, response );
        }


    }






}
