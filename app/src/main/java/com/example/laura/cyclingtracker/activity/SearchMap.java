package com.example.laura.cyclingtracker.activity;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laura.cyclingtracker.R;
import com.example.laura.cyclingtracker.helper.GlobalState;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class SearchMap extends Activity implements OnMapReadyCallback, ConnectionCallbacks, OnConnectionFailedListener, LocationListener{



    private Handler handler;
    private GlobalState gs;
    private MapFragment mapFr;
    private LocationManager manager;
    GoogleMap map;
    private GoogleApiClient client;

    private Button useButton;
    private TextView promptView;
    private MapView mapView;

    private View rootView;
    private Marker marker;
    private MarkerOptions markerOptions;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate( savedInstanceState);






        setContentView(R.layout.activity_map_search);

        gs = (GlobalState)getApplication();
        mapFr= (MapFragment) getFragmentManager().findFragmentById(R.id.searchMap);




        mapFr.getMapAsync(this);
        map = (mapFr).getMap();
        map.setMyLocationEnabled(true);


        client = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        client.connect();



        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

    }












    @Override
    public void onMapReady(GoogleMap arg0) {



        Log.v("map ready", "mapready");
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng position) {
                if(marker!=null){


                    marker.remove();

                }

                LatLng selected = new LatLng(position.latitude, position.longitude);

                markerOptions = new MarkerOptions().position(selected);

                gs.selectedLat = selected.latitude;
                gs.selectedLng = selected.longitude;

                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

                marker = map.addMarker(markerOptions);

            }
        });





        useButton = (Button) findViewById(R.id.btn_show);
        promptView = (TextView) findViewById(R.id.prompt);



        useButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if(marker==null){

                    Toast.makeText(getApplicationContext(), "Please click map to select your location", Toast.LENGTH_SHORT).show();

                }else{





                    finish();


                }
            }




        });



    }





    @Override
    public void onConnectionFailed(ConnectionResult arg0) {
        // TODO Auto-generated method stub

    }



    @Override
    public void onConnected(Bundle arg0) {
        // TODO Auto-generated method stub

    }



    @Override
    public void onConnectionSuspended(int arg0) {
        // TODO Auto-generated method stub

    }


    @Override
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub

    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }


    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }


    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }












}