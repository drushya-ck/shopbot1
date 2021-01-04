package com.example.shopbot1.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.shopbot1.R;

public class MyAdapterLeft extends BaseAdapter {
    private Context context;
    private String[] filters;
    private TextView txt;
    public MyAdapterLeft(Context context, String[] filters) {
        this.context = context;
        this.filters=filters;
    }
    @Override
    public int getCount() {
        return filters.length;
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.rowlayout1, parent, false);

        txt = convertView.findViewById(R.id.tv2);

        txt.setText(filters[position]);
        //if(selitems.contains(arrayList.get(position))){
        //txt.setBackgroundColor(Color.BLUE);
        //  im.setImageResource(R.drawable.checked);
        //}


        return convertView;
    }
}


