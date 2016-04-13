package com.example.laura.cyclingtracker.helper;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
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
 * Created by laura on 06/04/16.
 */
public class RetrieveTask<T> extends AsyncTask<Object, Void, ArrayList<T>>  {



    private Exception exception;
    private Context mContext;
    private HttpURLConnection urlConnection;
    private BufferedReader bufferedReader;
    private final String PROTOCOL = "http";
    private final String HOST = "10.0.3.2";
    private final int PORT = 8000;
    private String mFile;
    private String mXAuth;
    private Type mClazz;
    private JSONObject mBody;
    GlobalState gs;

    private List<Observer> observers = new ArrayList<>();
    private ArrayList<T> state;
    Gson gson = new Gson();
    DataOutputStream printout;


    public RetrieveTask(Context context, String file, String XAuth, Type clazz, JSONObject body ){
        super();
        mContext = context;
        mFile = file;
        mXAuth = XAuth;

        mClazz = clazz;
        mBody = body;


    }

    protected void onPreExecute() {
       // progressBar.setVisibility(View.VISIBLE);
        //responseView.setText("");
    }

    protected ArrayList<T> doInBackground(Object... objs) {
        //String email = emailText.getText().toString();
        // Do some validation here

        try {
            Log.v("INFO","Inside Retrieve Task");
            URL url = new URL(PROTOCOL, HOST, PORT, mFile);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("Accept", "*/*");
            urlConnection.setDoOutput(true);
            //Log.v("Auth", mXAuth);
            urlConnection.setRequestProperty("XAuth",mXAuth );

            if(mBody!=null){
                Log.v("INFO", mBody.toString());
                String json = String.valueOf(mBody);
                Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
                writer.write(json);
                writer.close();

            }
            try {

                       Log.i("INFO" ,String.valueOf(urlConnection.getResponseCode()));
                        bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                            Log.v("INFO", mFile);
                            //Type type = new TypeToken<ArrayList<>>(){}.getType();

                                ArrayList<T> jsonList = gson.fromJson(bufferedReader, mClazz);
                                bufferedReader.close();
                                return jsonList;

            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    protected void onPostExecute(ArrayList<T> response) {
        if(response == null) {

            Log.i("INFO", "something went wrong");
        }else {
            for (Observer observer : observers) {
                observer.update(null, response );
            }


            Log.i("INFO", "Response " + response.toString());

        }
    }

    public void attach(Observer observer){
        observers.add(observer);
    }



}
