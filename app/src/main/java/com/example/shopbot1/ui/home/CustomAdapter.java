package com.example.shopbot1.ui.home;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Bundle;

import com.example.shopbot1.R;
import com.example.shopbot1.SubCatProductList;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter{

//    String [] Text;
//    int [] Image;
    ArrayList<String> Text=new ArrayList<>();
    ArrayList<Integer> Image=new ArrayList<>();
    Context context;

    private static LayoutInflater inflater=null;
    public CustomAdapter(SubCatProductList mainActivity, ArrayList<String> TextItem, ArrayList<Integer> mainImages) {
        Text = TextItem;
        context = mainActivity;
        Image = mainImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    // Get number of items in list
    public int getCount() {
        return Text.size();
    }

    @Override
    // Get position of item in list
    public Object getItem(int position) {
        return position;
    }

    @Override
    // ???
    public long getItemId(int position) {
        return position;
    }

    public class Holder
    {
        TextView tv;
        ImageView img;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        Holder holder=new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.sub_listview, null);

        /** Get relevant view
         * Set image/text resource, based on list name, based on list position
         */
        holder.img=(ImageView) rowView.findViewById(R.id.sub_imageView);
        holder.img.setImageResource(Image.get(position));

        holder.tv=(TextView) rowView.findViewById(R.id.sub_textView);
        holder.tv.setText(Text.get(position));

        return rowView;
    }

}