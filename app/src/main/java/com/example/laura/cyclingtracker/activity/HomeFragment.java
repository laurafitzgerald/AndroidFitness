package com.example.laura.cyclingtracker.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.laura.cyclingtracker.R;
import com.example.laura.cyclingtracker.helper.GlobalState;
import com.example.laura.cyclingtracker.helper.ListActivitiesAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;


public class HomeFragment extends Fragment{


    public static  ListActivitiesAdapter adapter;
    private View rootView;
    private ListView workoutListView;

    GlobalState gs;


    @Bind(R.id.workout_list)
    ListView list;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){



        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {


        gs = (GlobalState) getActivity().getApplication();


//        ParseQuery<Workout> query=new ParseQuery<Workout>("Workout");
//        query.whereEqualTo("userId", ParseUser.getCurrentUser());
//        if(gs.connectedToInternet(getActivity())== false){
//
//            query.fromLocalDatastore();
//
//        }
//        query.findInBackground(new FindCallback<Workout>() {
//            @Override
//            public void done(List<Workout> objects, ParseException e) {
//                adapter = new ListActivitiesAdapter(getActivity(), gs);
//                workoutListView = (ListView) getActivity().findViewById(R.id.workout_list);
//                workoutListView.setAdapter(adapter);
//            }
//        });
//
                adapter = new ListActivitiesAdapter(getActivity(), gs, gs.getListWorkouts());
        workoutListView = (ListView) getActivity().findViewById(R.id.workout_list);
        workoutListView.setAdapter(adapter);

    }

}
