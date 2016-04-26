package com.example.laura.cyclingtracker.helper;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.laura.cyclingtracker.R;
import com.example.laura.cyclingtracker.data.Bike;

import java.util.ArrayList;

public class ListGearAdapter extends BaseAdapter {


    private Context context;

    LayoutInflater inflater;
    ViewHolder viewHolder;
    GlobalState gs;
    ArrayList<Bike> list;



    public ListGearAdapter(Context context, ArrayList<Bike> list ) {
        super();
        Log.v("INFO", "BikeListAdapter creates");
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = list;
        Log.v("Bike list in adapter", list.toString());


    }


    @Override
    public int getCount() {
        Log.v("Info", "get count called " + list.size());
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        Log.v("Infor", "get itme called " + position);
        return list.get(position);

    }

    @Override
    public long getItemId(int position) {
        Log.v("Infor", "get itemid called" + position);
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            viewHolder = new ViewHolder();
            //inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.gear_list_item, parent, false);

            viewHolder.gearNickname = (TextView) convertView.findViewById(R.id.gearTypeText);
            viewHolder.gearType = (TextView) convertView.findViewById(R.id.gearNicknameText);

            convertView.setTag(viewHolder);

        } else {


            viewHolder = (ViewHolder) convertView.getTag();
        }

        Log.d("Custom adapter reached", "ListGearAdapter");
        Bike bike = list.get(position);
        viewHolder.gearType.setText(bike.getType());
        viewHolder.gearNickname.setText(bike.getNickname());

        return convertView;


    }


    static class ViewHolder {
        TextView gearType;
        TextView gearNickname;


    }

}