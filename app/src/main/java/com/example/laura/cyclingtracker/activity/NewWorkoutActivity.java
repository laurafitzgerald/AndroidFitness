package com.example.laura.cyclingtracker.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laura.cyclingtracker.R;

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


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _workoutType.setAdapter(adapter);


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

        //String type = _workoutType.getSelectedItem().toString();
        //Double distance = _distanceText.getText();




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
        //llaurString type = _workoutType.getSelectedItem().toString();
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
