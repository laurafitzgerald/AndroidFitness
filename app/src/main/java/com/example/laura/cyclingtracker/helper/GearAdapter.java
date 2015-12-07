package com.example.laura.cyclingtracker.helper;


import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.laura.cyclingtracker.R;
import com.example.laura.cyclingtracker.data.Gear;

import java.util.List;

public class GearAdapter  extends ArrayAdapter<Gear>{



    private Context context;
    private List<Gear> gear;
    public Resources res;
    LayoutInflater inflater;
    Gear currRowVal = null;

            public GearAdapter(Context context, int resource, List<Gear> list, Resources resLocal) {
                super(context, resource, list);

                this.context = context;
                this.gear = list;
                this.res = resLocal;
            }



         public View getCustomView(int position, View convertView, ViewGroup parent) {
            View row = inflater.inflate(R.layout.gear_spinner_item, parent, false);
        currRowVal = null;
        currRowVal = (Gear) gear.get(position);
        TextView label = (TextView) row.findViewById(R.id.spinnerItem);


        label.setText(currRowVal.getNickname());



        return row;
    }
        }
