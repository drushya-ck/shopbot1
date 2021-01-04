package com.example.shopbot1.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shopbot1.R;

import java.util.ArrayList;


/**
 * Created by Jerry on 12/23/2017.
 */

public class FragmentRight extends Fragment {
    View retView;
    ListView listview;
    Button clearFilters,clearSubFilters;
    ArrayList<String> ar=null;
//FRAGMENTLEFT.SUB_SELITEMS IS SELITEMS WITH SUBCATEGORIES

    // This method will be invoked when the Fragment view object is created.
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        retView = inflater.inflate(R.layout.fragment_right, container);
        ar = new ArrayList<>();


            clearSubFilters = (Button) retView.findViewById(R.id.clearsubfilters);
            clearSubFilters.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentLeft.selitems.removeAll(ar);//ar here wont be null cz first,txt.change() is called so ar=str....
                    for (int i = 0; i < ar.size(); i++) {
                        FragmentLeft.sub_selitems.remove(ar.get(i));
                    }
                    MyAdapter arr = new MyAdapter(getActivity(), ar, FragmentLeft.selitems);
                    listview.setAdapter(arr);

                }
            });
            ArrayList<String> temp=new ArrayList<>();
            clearFilters = (Button) retView.findViewById(R.id.clearfilters);
            clearFilters.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for(int i = 0; i< FragmentLeft.selitems.size(); i++){
                        if(FragmentLeft.sub_selitems.get(FragmentLeft.selitems.get(i))== productList.subcategory){
                            temp.add(FragmentLeft.selitems.get(i));
                            FragmentLeft.sub_selitems.remove(FragmentLeft.selitems.get(i));
                        }
                    }
                    FragmentLeft.selitems.removeAll(temp);

                    MyAdapter arr = new MyAdapter(getActivity(), ar, FragmentLeft.selitems);

                    listview.setAdapter(arr);
                }
            });

            return retView;
        }
        public void change (ArrayList < String > str) {
            ar = str;
            listview = (ListView) retView.findViewById(R.id.checkable_list);
            listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            //ArrayAdapter<String> arr=new ArrayAdapter<String>(getActivity(), R.layout.rowlayout, s);

            MyAdapter arr = new MyAdapter(getActivity(), str, FragmentLeft.selitems);

            listview.setAdapter(arr);

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    String selecteditem;
                    TextView tvv = view.findViewById(R.id.tv1);
                    ImageView im = view.findViewById(R.id.im1);
                    selecteditem = tvv.getText().toString();
                    if (FragmentLeft.selitems.contains(selecteditem)) {
                        FragmentLeft.selitems.remove(selecteditem);
                        FragmentLeft.sub_selitems.remove(selecteditem);
                        im.setImageResource(R.drawable.unchecked1);
                    } else {
                        FragmentLeft.selitems.add(selecteditem);
                        FragmentLeft.sub_selitems.put(selecteditem, productList.subcategory);
                        im.setImageResource(R.drawable.checked);
                    }
                    //Toast.makeText(getActivity(),FragmentLeft.selitems.toString(),Toast.LENGTH_SHORT).show();
                }
            });


        }

    }
