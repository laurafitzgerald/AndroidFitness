package com.example.laura.cyclingtracker.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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

import com.example.laura.cyclingtracker.R;
import com.example.laura.cyclingtracker.data.Report;
import com.example.laura.cyclingtracker.helper.GlobalState;
import com.example.laura.cyclingtracker.helper.ListActivitiesAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

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
    Report report;
    ArrayList<Double> latList = new ArrayList<Double>();
    ArrayList<Double> lngList = new ArrayList<Double>();


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
        data();
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

        for(int i = 0; i <latList.size(); i++) {
            LatLng templl = new LatLng(latList.get(i), lngList.get(i));
                            /*map.addMarker( new MarkerOptions()
                              .position( templl));
                              //.title("Fence " + fence.getId())
                              //.snippet("Radius: " + fence.getRadius()) ).showInfoWindow();
                            */

            Geofence.Builder fence = new Geofence.Builder();
            fence.setCircularRegion(templl.latitude, templl.latitude, 10);


            CircleOptions circleOptions = new CircleOptions()
                    .center(templl)
                    .radius(5)
                    .fillColor(getResources().getColor(R.color.locationcolor))
                    .strokeColor(Color.TRANSPARENT)
                    .strokeWidth(2);


            map.addCircle(circleOptions);

        }




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
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

       // Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if (location != null)
        {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(location.getLatitude(), location.getLongitude()), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                    .zoom(17)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        }

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


            LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();

            Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
            if (location != null)
            {
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(location.getLatitude(), location.getLongitude()), 13));

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                        .zoom(17)                   // Sets the zoom
                        .bearing(90)                // Sets the orientation of the camera to east
                        .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder
                map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            }

           // Location lastKnownLocation = LocationServices.FusedLocationApi.getLastLocation(client);
            //gs.setCurrentLat(lastKnownLocation.getLatitude());
            //gs.setCurrentLng(lastKnownLocation.getLongitude());
            //map = mapFr.getMap();
           //map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(52.2555534, -7.1481809)));
                    // lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude())));

                   // map.animateCamera(CameraUpdateFactory.zoomTo(16));

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

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if (location != null)
        {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(location.getLatitude(), location.getLongitude()), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                    .zoom(17)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        }
    }

    public void data(){







        latList.add(53.348017569532345);
        latList.add(53.3470138);
        latList.add(46.98966106916362);
        latList.add(25.078439500114058);
        latList.add(49.99420970253483);
        latList.add(52.24830654);
        latList.add(52.238608492319024);
        latList.add(52.2453922);
        latList.add(52.2455502);
        latList.add(52.2454986);
        latList.add(50.70438359407182);
        latList.add(52.39198733605534);
        latList.add(52.2555534);
        latList.add(52.2354279);
        latList.add(52.2654279);
        latList.add(52.3454555);
        latList.add(52.2954555);
        latList.add(52.2156853);
        latList.add(52.2655494);
        latList.add(52.2154582);
        latList.add(52.2854011);
        latList.add(52.295476);
        latList.add(52.12800085580909);
        latList.add(52.393740555084015);
        latList.add(52.2459066);
        latList.add(52.2451268);




        lngList.add(-6.256098300218582);
        lngList.add(-6.2586954);
        lngList.add(5.407261289656162);
        lngList.add(12.124055810272694);
        lngList.add(8.980030752718449);
        lngList.add(-6.96297447);
        lngList.add(-6.972085162997246);
        lngList.add(-7.138243);
        lngList.add(-7.1381747);
        lngList.add(-7.1381818);
        lngList.add(24.83169123530388);
        lngList.add(-6.945770680904388);
        lngList.add(-7.1481809);
        lngList.add(-7.1282374);
        lngList.add(-7.1342374);
        lngList.add(-7.1322543);
        lngList.add(-7.1342543);
        lngList.add(-7.1472437);
        lngList.add(-7.1542103);
        lngList.add(-7.1372272);
        lngList.add(-7.1682668);
        lngList.add(-7.1282474);
        lngList.add(-8.686682917177677);
        lngList.add(-6.945250667631626);
        lngList.add(-7.1322484);
        lngList.add(-7.1302484);








        for(int i = 0; i < latList.size(); i ++){

            report = new Report();
            report.setLocation_lat(latList.get(i));
            report.setLocation_lng(lngList.get(i));

        }
    }
}
