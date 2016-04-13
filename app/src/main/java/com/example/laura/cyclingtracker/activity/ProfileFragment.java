package com.example.laura.cyclingtracker.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laura.cyclingtracker.R;
import com.example.laura.cyclingtracker.data.Bike;
import com.example.laura.cyclingtracker.helper.GlobalState;
import com.example.laura.cyclingtracker.helper.ListGearAdapter;
import com.example.laura.cyclingtracker.helper.MySettings;
import com.example.laura.cyclingtracker.helper.RetrieveTask;

import java.util.Observable;
import java.util.Observer;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProfileFragment extends Fragment implements Observer {
    private View rootView;


    @Bind(R.id.btn_logout) Button _logout;
    @Bind(R.id.usernameView)
    TextView username;
    @Bind(R.id.bike_list)
    ListView listView;

    public static  ListGearAdapter adapter;


    GlobalState gs;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        gs = (GlobalState) getActivity().getApplication();
        RetrieveTask<Bike> rt = new RetrieveTask<>(this.getActivity(), "bikes", gs.getSession(), Bike.class, null);


        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

        gs = (GlobalState) getActivity().getApplication();
        //username.setText(ParseUser.getCurrentUser().getUsername());






        _logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MySettings.save(getActivity(), "");

                Toast.makeText(getActivity(), "Logging Out...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), LogInActivity.class);
                startActivity(intent);
                getActivity().finish();


            }
        });



    }

    @Override
    public void update(Observable observable, Object data) {



        Log.v("Profile Fragment.update", data.toString());
        //_signupLink.setText(data.toString());

    }

}
