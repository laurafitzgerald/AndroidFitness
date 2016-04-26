package com.example.laura.cyclingtracker.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.laura.cyclingtracker.R;
import com.example.laura.cyclingtracker.data.Bike;
import com.example.laura.cyclingtracker.data.Report;
import com.example.laura.cyclingtracker.helper.BikeAdapter;
import com.example.laura.cyclingtracker.helper.GlobalState;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import butterknife.ButterKnife;

/**
 * Created by laura on 11/04/16.
 */





public class ReportFragment extends Fragment implements Observer {


    private Spinner spinner;
    private View rootView;
    private RadioGroup radioGroup;
    private GridLayout gl;
    private EditText editlat;
    private EditText editlng;
    private RadioButton input;
    private RadioButton useCurrent;
    private Button reportButton;
    BikeAdapter adapter;
    //TODO populate from the bike list
    List<Bike> finalBikes = new ArrayList<Bike>();;
    ViewPager viewPager;


    GlobalState gs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_report, container, false);
        viewPager = (ViewPager) getActivity().findViewById(R.id.pager);
        ButterKnife.bind(this, view);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gs = (GlobalState) getActivity().getApplication();


        reportButton = (Button) getActivity().findViewById(R.id.button_report);
        reportButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                reportBike();



            }
        });

        spinner  = (Spinner) getActivity().findViewById(R.id.bikes_spinner);
        reportButton.setEnabled(true);
        //get the bikes here

        Resources res = getResources();
        adapter = new BikeAdapter(getActivity(), R.layout.bike_spinner_item, gs.bikes, res);
        spinner.setAdapter(adapter);



        input = (RadioButton) getActivity().findViewById(R.id.radioInputLocation);
        useCurrent = (RadioButton) getActivity().findViewById(R.id.radioCurrentLocation);

        radioGroup = (RadioGroup) getActivity().findViewById(R.id.lat_lng_radiogroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (input.isChecked()) {

                    Intent intent = new Intent(getActivity(), SearchMap.class);
                    startActivity(intent);


                    gl.setEnabled(true);
                    editlat.setText("");
                    editlng.setText("");

                } else if (useCurrent.isChecked()) {

                    if (gs.getCurrentLat() == null || gs.getCurrentLng() == null) {

                        Toast.makeText(getActivity().getApplicationContext(), "There was a problem getting your current location, please check your location settings", Toast.LENGTH_SHORT).show();


                    } else {

                        gl.setEnabled(false);
                        editlat.setEnabled(false);
                        editlat.setText(gs.getCurrentLat().toString());
                        editlng.setEnabled(false);
                        editlng.setText(gs.getCurrentLng().toString());
                    }
                }

            }


        });



    }

    public void reportBike(){



        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(this.getResources().getString(R.string.report_message));
        builder.setPositiveButton(this.getResources().getString(R.string.report_bike), new DialogInterface.OnClickListener() {



            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                Report report = new Report();





        /*
                if(input.isChecked()){



                    report.setLocation_lat(gs.selectedLat);
                    report.setLocation_lng(gs.selectedLng);



                }else{

                    report.setLocation_lat(gs.getCurrentLat());
                   report.setLocation_lng(gs.getCurrentLng());


                }

               //report.setSerial_number( spinner.getSelectedItem(). );
                //stolenBike.saveInBackground();


                Bike bike = finalBikes.get((int)spinner.getSelectedItemPosition());



                //bike.put("registeredStolen", true);

                finalBikes.remove(spinner.getSelectedItemPosition());
                if(finalBikes.size()==0){

                    reportButton.setEnabled(false);
                }
                adapter.setList(finalBikes);
                adapter.notifyDataSetChanged();

*/



            }


        });



        builder.setNegativeButton(this.getResources().getString(R.string.do_not_report_bike), new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });


        AlertDialog dialog = builder.create();
        dialog.show();

    }


    @Override
    public void update(Observable observable, Object data) {

        //switch to another tab
        viewPager.setCurrentItem(0);

        //Log.v("Observer.update", data.toString());
        //_signupLink.setText(data.toString());

    }



}
