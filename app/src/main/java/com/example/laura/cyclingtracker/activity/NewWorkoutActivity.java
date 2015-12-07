package com.example.laura.cyclingtracker.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laura.cyclingtracker.R;
import com.example.laura.cyclingtracker.data.Gear;
import com.example.laura.cyclingtracker.data.Workout;
import com.example.laura.cyclingtracker.helper.GearAdapter;
import com.example.laura.cyclingtracker.helper.GlobalState;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by laura on 04/12/15.
 */
public class NewWorkoutActivity extends Fragment {


    private static final String TAG = "NewWorkoutActivity";

    int hoursStart=0;
    int hoursEnd=12;
    int minsStart=0;
    int minsEnd=59;

    GlobalState gs;
    List<Gear> gearList;
    ArrayAdapter<CharSequence> adapter;
    GearAdapter gearAdapter;


    @Bind(R.id.input_location)
    TextView _location;

    @Bind(R.id.hours_dwn_btn)
    Button _hours_dwn_btn;
    @Bind(R.id.textView_hours_up)
    TextView _hoursup;

    @Bind(R.id.hoursText)
    TextView _hoursText;

    @Bind(R.id.hours_up_button)
    Button _hours_up_btn;
    @Bind(R.id.textView_hoursdown)
    TextView _hoursdown;


    @Bind(R.id.mins_dwn_btn)
    Button _mins_dwn_btn;
    @Bind(R.id.textView_mins_up)
    TextView _minsup;

    @Bind(R.id.minsText)
    TextView _minsText;

    @Bind(R.id.mins_up_button)
    Button _mins_up_btn;
    @Bind(R.id.textView_minsdown)
    TextView _minsdown;

    @Bind(R.id.secs_dwn_btn)
    Button _secs_dwn_btn;
    @Bind(R.id.textView_secs_up)
    TextView _secsup;

    @Bind(R.id.secsText)
    TextView _secsText;

    @Bind(R.id.secs_up_button)
    Button _secs_up_btn;
    @Bind(R.id.textView_secsdown)
    TextView _secsdown;


    @Bind(R.id.input_distance)
    EditText _distanceText;
    @Bind(R.id.workout_type)
    Spinner _workoutType;

    @Bind(R.id.gear)
    Spinner _gear;

    @Bind(R.id.btn_create_workout)
    Button _createWorkoutButton;
    private View rootView;

;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.activity_new_workout, container, false);
        ButterKnife.bind(this,view);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gs = (GlobalState) getActivity().getApplication();

        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _workoutType.setAdapter(adapter);

        updateGear();

        _hoursup.setText("0");
        _hoursdown.setText("2");
        _hoursText.setText("1");


        _minsup.setText("0");
        _minsdown.setText("2");
        _minsText.setText("1");


        _secsup.setText("0");
        _secsdown.setText("2");
        _secsText.setText("1");

        _hours_up_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                hoursUp();
            }
        });
        _hours_dwn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hoursDown();
            }


        });

        _mins_up_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                minsUp();

            }
        });
        _mins_dwn_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                minsDown();
            }


        });

        _secs_up_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                secondsUp();
            }
        });
        _secs_dwn_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                secondssDown();
            }


        });

        _createWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create();
            }
        });

        _workoutType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                updateGear();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });


    }

    public void updateGear(){



        Resources res = getResources();
        Gear.findInBackground(ParseUser.getCurrentUser(), new FindCallback<Gear>() {
            @Override
            public void done(List<Gear> gear, ParseException e) {

                gearList = new ArrayList<Gear>();
                for (Gear g : gear) {

                    if(_workoutType.getSelectedItem().toString().equals("Cycle")) {
                        if(g.getType().equals("Bike"))
                            gearList.add(g);

                    }else{

                        if(g.getType().equals("Runners")){
                            gearList.add(g);
                        }
                    }
                }

                Resources res = getResources();
                gearAdapter = new GearAdapter(getActivity(), R.layout.gear_spinner_item, gearList, res);
                _gear.setAdapter(gearAdapter);
            }
        });
    }



    public void secondssDown(){

        String getString = String.valueOf(_secsText.getText());
        int current = Integer.parseInt(getString);
        if (current > minsStart){
            current--;
            _secsup.setText(String.valueOf(current-1));
            _secsText.setText(String.valueOf(current));
            _secsdown.setText(String.valueOf(current+1));

            if(current==0 && _minsText.getText().equals("0")&& _hoursText.getText().equals("0")){

                secondsUp();
            }

        }

    }

    public void secondsUp(){

        String getString = String.valueOf(_secsText.getText());
        int current = Integer.parseInt(getString);
        if (current < minsEnd) {
            current++;
            _secsup.setText(String.valueOf(current - 1));
            _secsText.setText(String.valueOf(current));
            _secsdown.setText(String.valueOf(current + 1));
        }





    }

    public void minsDown(){

        String getString = String.valueOf(_minsText.getText());
        int current = Integer.parseInt(getString);
        if (current > minsStart){
            current--;
            _minsup.setText(String.valueOf(current-1));
            _minsText.setText(String.valueOf(current));
            _minsdown.setText(String.valueOf(current+1));
            if(current==0 && _secsText.getText().equals("0")&& _hoursText.getText().equals("0")){

                secondsUp();
            }

        }


    }


    public void minsUp(){

        String getString = String.valueOf(_minsText.getText());
        int current = Integer.parseInt(getString);
        if (current < minsEnd) {
            current++;
            _minsup.setText(String.valueOf(current - 1));
            _minsText.setText(String.valueOf(current));
            _minsdown.setText(String.valueOf(current + 1));
        }


    }

    public void hoursDown(){


        String getString = String.valueOf(_hoursText.getText());
        int current = Integer.parseInt(getString);
        if (current > hoursStart) {
            current--;
            _hoursup.setText(String.valueOf(current - 1));
            _hoursText.setText(String.valueOf(current));
            _hoursdown.setText(String.valueOf(current + 1));
            if(current==0 && _secsText.getText().equals("0")&& _minsText.getText().equals("0")){

                secondsUp();
            }

        }



    }

    public void hoursUp(){

        String getString = String.valueOf(_hoursText.getText());
        int current = Integer.parseInt(getString);
        if (current < hoursEnd) {
            current++;
            _hoursup.setText(String.valueOf(current - 1));
            _hoursText.setText(String.valueOf(current));
            _hoursdown.setText(String.valueOf(current + 1));
        }



    }

    public void create(){

        Log.d(TAG, "create");

        if (!validate()) {
            onWorkoutCreationFailed();
            return;
        }


        _createWorkoutButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating New Workout...");
        progressDialog.show();

        String type = _workoutType.getSelectedItem().toString();
        String distancestr = _distanceText.getText().toString();
        Double distance = Double.parseDouble(distancestr);
        String hours = _hoursText.getText().toString();
        String mins = _minsText.getText().toString();
        String secs = _secsText.getText().toString();
        String location = _location.getText().toString();



        Workout wo = Workout.create(Workout.class);
        wo.put("Type", type);
        wo.put("distance", distance);
        wo.put("user_id", ParseUser.getCurrentUser());
        wo.put("Hours", hours);
        wo.put("Mins", mins);
        wo.put("Location", location);
        wo.put("Secs", secs);
        gs.populateWorkoutList();
        wo.saveInBackground();
        progressDialog.dismiss();


        wo.saveEventually(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {


                } else {

                    Toast.makeText(getActivity(), "Workout added", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(), MainActivity.class);
                    getActivity().finish();
                    startActivity(i);
                    progressDialog.dismiss();


                }
            }
        });



    }

    private void onWorkoutCreationFailed() {

        Toast.makeText(getActivity().getBaseContext(), "Creation Failed", Toast.LENGTH_LONG).show();

        _createWorkoutButton.setEnabled(true);
    }


    public void onWorkoutCreationSuccess(){

        _createWorkoutButton.setEnabled(true);
        getActivity().finish();

    }

    public boolean validate(){


        boolean valid = true;

        //TODO check that all the values are valid

        String distance = _distanceText.getText().toString();
        //String type = _workoutType.getSelectedItem().toString();
        //int hour = _timeText.getHour();
        //int minute = _timeText.getMinute();

        try{

            Double.parseDouble(distance);
            //String timeStr = _timeText.getText().toString();
            //String[] timearray = timeStr.split("/");
            //Duration time = new Duration();


        }catch(NumberFormatException nfe){


             valid = false;
        }





        return valid;

    }

    }
