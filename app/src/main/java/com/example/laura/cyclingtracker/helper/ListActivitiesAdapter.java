package com.example.laura.cyclingtracker.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laura.cyclingtracker.R;
import com.example.laura.cyclingtracker.data.Workout;

import java.util.List;

/**
 * Created by laura on 05/12/15.
 */
public class ListActivitiesAdapter extends BaseAdapter {

    Context context;
    private ViewHolder viewHolder;
    List<Workout> workoutList;
    LayoutInflater inflater;
    GlobalState gs;

    public ListActivitiesAdapter(Context context,  GlobalState gs, List<Workout> listWorkouts) {

        this.gs = gs;
        this.workoutList = listWorkouts;
        this.context = context;
        this.inflater = LayoutInflater.from(context);


    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = this.inflater.inflate(R.layout.list_activities, parent, false);
            convertView.setTag(viewHolder);
        } else{
            viewHolder = (ViewHolder) convertView.getTag();

        }

            Workout workout = workoutList.get(position);



        return convertView;

    }



    static class ViewHolder {
        TextView nickname_editable;
        TextView serial_editable;
        TextView make_editable;
        ImageView imgBike_editable;
        ImageView editIcon;
        ImageView deleteIcon;

    }
}