package com.example.laura.cyclingtracker.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


public class ListActivitiesAdapter extends BaseAdapter {

    Context context;
    private ViewHolder viewHolder;
    List<Object> workoutList;
    LayoutInflater inflater;
    GlobalState gs;

    public ListActivitiesAdapter(Context context,  GlobalState gs, List<Object> listWorkouts) {

        this.gs = gs;
        this.workoutList = listWorkouts;
        this.context = context;
        this.inflater = LayoutInflater.from(context);


    }

    @Override
    public int getCount() {
        return workoutList.size();
    }

    @Override
    public Object getItem(int position) {
        return workoutList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return convertView;

    }



    static class ViewHolder {
        TextView workoutType;
        TextView location;
        TextView distance;
        TextView time;
        TextView speed;

    }
}
