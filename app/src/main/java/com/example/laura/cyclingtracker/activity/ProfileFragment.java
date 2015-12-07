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
import com.example.laura.cyclingtracker.data.Gear;
import com.example.laura.cyclingtracker.helper.GlobalState;
import com.example.laura.cyclingtracker.helper.ListGearAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProfileFragment extends Fragment {
    private View rootView;


    @Bind(R.id.btn_logout) Button _logout;
    @Bind(R.id.userNameText)
    TextView username;
    @Bind(R.id.gear_list)
    ListView list;

    public static  ListGearAdapter adapter;

    List<Gear> gearList;

    GlobalState gs;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){


        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

        gs = (GlobalState) getActivity().getApplication();
        username.setText(ParseUser.getCurrentUser().getUsername());

        ParseQuery<Gear> query=new ParseQuery<Gear>("Gear");
        query.whereEqualTo("user_id", ParseUser.getCurrentUser());
        if(gs.connectedToInternet(getActivity())== false){

            query.fromLocalDatastore();

        }
        query.findInBackground(new FindCallback<Gear>() {
            @Override
            public void done(List<Gear> objects, ParseException e) {
                gearList = objects;
                Toast.makeText(getActivity(), "size of gear list" +String.valueOf(objects.size()) , Toast.LENGTH_LONG).show();
                Log.v("Size of gear list: ", String.valueOf(objects.size()) );
            }
        });
        adapter = new ListGearAdapter(getActivity(), gearList);

        list.setAdapter(adapter);



        _logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseUser.logOut();
                Toast.makeText(getActivity(), "Logging Out...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), LogInActivity.class);
                startActivity(intent);
                getActivity().finish();


            }
        });



    }

}
