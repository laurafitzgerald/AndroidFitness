package com.example.laura.cyclingtracker.helper;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.laura.cyclingtracker.R;
import com.example.laura.cyclingtracker.data.Bike;

import java.util.List;


public class GearAdapter  extends BaseAdapter {



    private Context context;
    protected List<Bike> listBikes;
    public Resources res;
    LayoutInflater inflater;
    Bike currRowVal;


    public GearAdapter(Context context, int resource, List<Bike> list, Resources resLocal) {
       // super(context, resource, list);

        this.context = context;
        this.listBikes = list;
        this.res = resLocal;
    }



     public View getCustomView(int position, View convertView, ViewGroup parent) {
        View row = inflater.inflate(R.layout.gear_spinner_item, parent, false);
        currRowVal = null;
        currRowVal = (Bike) listBikes.get(position);
        TextView label = (TextView) row.findViewById(R.id.spinnerItem);


        label.setText(currRowVal.getNickname());

        return row;
    }

    @Override
    public int getCount() {
        //return currRowVal;
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
        return null;
    }
}
