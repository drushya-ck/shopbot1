package com.example.shopbot1.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shopbot1.R;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> arrayList;
    private ArrayList<String> selitems;
    private TextView txt;
    private ImageView im;
    public MyAdapter(Context context, ArrayList<String> arrayList,ArrayList<String> selitems) {
        this.context = context;
        this.arrayList = arrayList;
        this.selitems=selitems;
    }
    @Override
    public int getCount() {
        return arrayList.size();
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
        convertView = LayoutInflater.from(context).inflate(R.layout.rowlayout, parent, false);

        txt = convertView.findViewById(R.id.tv1);
        im = convertView.findViewById(R.id.im1);
        txt.setText(arrayList.get(position));
        if(selitems.contains(arrayList.get(position))){
            //txt.setBackgroundColor(Color.BLUE);
            if(FragmentLeft.sub_selitems.get(arrayList.get(position))== productList.subcategory){
                im.setImageResource(R.drawable.checked);
            }

        }


        return convertView;
    }
}
