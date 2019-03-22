package com.example.clockdigi;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ZoneList  extends ArrayAdapter<String> {

    private List<String> dataList;
    private ArrayAdapter<String> adapter;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public ZoneList( Context context, int resource) {
        super(context, resource);
    }

 /*   @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) this.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_layout, parent, false);
            //passing the xml layout file, from which the layoutinflator creates the view

        }

        return convertView;
    }*/


}

