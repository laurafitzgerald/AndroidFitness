package com.example.laura.cyclingtracker.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.laura.cyclingtracker.R;
import com.example.laura.cyclingtracker.helper.GlobalState;
import com.example.laura.cyclingtracker.helper.ListActivitiesAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import butterknife.ButterKnife;


public class HomeFragment extends Fragment  implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, OnMapReadyCallback, GoogleMap.OnMapLongClickListener {


    public static  ListActivitiesAdapter adapter;
    private View rootView;
    private ListView workoutListView;
   // List<Workout> workoutList;


    private GlobalState gs;
    private LocationManager manager;
    private GoogleApiClient client;
    private GoogleMap map;
    private MapFragment mapFr;
    View view;



    //@Bind(R.id.workout_list)
    //ListView list;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //http://stackoverflow.com/questions/27160508/supportmapfragment-is-null-when-i-implement-it-with-navigation-drawer
        if(view !=null){
            ViewGroup parent = (ViewGroup) view.getParent();
            if(parent != null)
                parent.removeView(view);
        }

        try {
            view = inflater.inflate(R.layout.fragment_home, container, false);
            ButterKnife.bind(this, view);
        }catch(InflateException e){
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {


        gs = (GlobalState) getActivity().getApplication();

        mapFr = (MapFragment)getActivity().getFragmentManager().findFragmentById(R.id.map);
        map = mapFr.getMap();




        client = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                //.addConnectionCallbacks()
                .addOnConnectionFailedListener(this)
                .build();
        client.connect();

        checkGPStatus();


        manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);


        Criteria criteria = new Criteria();
        String  mBestProvider = manager.getBestProvider(criteria, true);
        manager.requestLocationUpdates(mBestProvider, 50000, 10000, this);



        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                Toast.makeText(getActivity(), point.latitude + " " + point.longitude, Toast.LENGTH_SHORT).show();

            }
        });


    }


        @Override
        public void onDestroyView () {
        super.onDestroyView();
        MapFragment f = (MapFragment) getActivity().getFragmentManager()
                .findFragmentById(R.id.map);
        if (f != null)
            getActivity().getFragmentManager().beginTransaction().remove(f).commit();
    }
    //http://stackoverflow.com/questions/27160508/supportmapfragment-is-null-when-i-implement-it-with-navigation-drawer

    private boolean checkGPStatus() {


        boolean gpsEnabled = false;
        boolean networkEnabled = false;
        try{
            gpsEnabled = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            Log.v("checkgps", "check gps: " + gpsEnabled);
        }catch(Exception ex){}
        try{
            networkEnabled = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            Log.v("checkgps","check network: "+networkEnabled);
        }catch(Exception ex){}

        if(!gpsEnabled && !networkEnabled){

            return false;
        }

        return true;
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
        gs.setCurrentLat(location.getLatitude());
        gs.setCurrentLng(location.getLongitude());

        map.moveCamera(CameraUpdateFactory.newLatLng(latlng));
        map.animateCamera(CameraUpdateFactory.zoomTo(16));

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onConnected(Bundle connectionHint) {

        if(!checkGPStatus())
        {
            turnOnLocationServices();
        }
        else {


            Location lastKnownLocation = LocationServices.FusedLocationApi.getLastLocation(client);
            gs.setCurrentLat(lastKnownLocation.getLatitude());
            gs.setCurrentLng(lastKnownLocation.getLongitude());
            map = mapFr.getMap();
            map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude())));

            map.animateCamera(CameraUpdateFactory.zoomTo(16));

        }


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    public void turnOnLocationServices()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getActivity().getResources().getString(R.string.gps_network_not_enabled));
        builder.setPositiveButton(getActivity().getResources().getString(R.string.open_location_settings), new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);

            }
        });

        AlertDialog dialog=builder.create();
        dialog.show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
