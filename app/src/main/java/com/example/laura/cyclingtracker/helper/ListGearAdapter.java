package com.example.laura.cyclingtracker.helper;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.laura.cyclingtracker.R;

public class ListGearAdapter extends BaseAdapter {


    private Context context;

    LayoutInflater inflater;
    ViewHolder viewHolder;


    public ListGearAdapter(Context context ) {


        this.context = context;


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

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            viewHolder = new ViewHolder();
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.gear_list_item, parent, false);

            viewHolder.gearNickname = (TextView) convertView.findViewById(R.id.gearTypeText);
            viewHolder.gearType = (TextView) convertView.findViewById(R.id.gearNicknameText);

            convertView.setTag(viewHolder);

        } else {


            viewHolder = (ViewHolder) convertView.getTag();
        }

        Log.d("Custom adapter reached", "ListGearAdapter");
        //Gear gear = gearList.get(position);
        //viewHolder.gearType.setText(gear.getType());
        //viewHolder.gearNickname.setText(gear.getNickname());

        return convertView;


    }


    static class ViewHolder {
        TextView gearType;
        TextView gearNickname;


    }

}