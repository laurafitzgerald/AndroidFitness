package com.example.laura.cyclingtracker.helper;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.laura.cyclingtracker.data.Report;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

/**
 * Created by laura on 11/04/16.
 */
public class PostReportTask extends AsyncTask<Object, Void, ArrayList<Report>> {


    private Context mContext;
    private HttpURLConnection urlConnection;
    private BufferedReader bufferedReader;
    private final String PROTOCOL = "http";
    private final String HOST = "api.itsmybike.io";
    private final int PORT = 80;
    private final String FILE = "reports";
    private JSONObject mBody;
    private Type mClazz;
    private String mXAuth;

    Gson gson = new Gson();

    private List<Observer> observers = new ArrayList<>();

    public PostReportTask(Context context, String XAuth, JSONObject body, Type clazz){

        mContext = context;
        mBody = body;
        mClazz = clazz;
        mXAuth = XAuth;





    }


    @Override
    protected void onPreExecute(){


    }


    @Override
    public ArrayList<Report> doInBackground(Object... objs) {


        try {
            URL url = new URL(PROTOCOL, HOST, PORT, FILE);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 ( compatible ) ");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("Accept", "*/*");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("XAuth", mXAuth);

            Log.v("INFO", mBody.toString());
            if(mBody!=null) {
                String json = String.valueOf(mBody);
                Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
                writer.write(json);
                writer.close();
            }

            bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));




            ArrayList<Report> jsonList = gson.fromJson(bufferedReader, mClazz);


            bufferedReader.close();
            return jsonList;


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
