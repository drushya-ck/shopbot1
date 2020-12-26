package com.example.shopbot1.ui.home;


import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbot1.R;
import com.example.shopbot1.recyclerAdapter;
import com.example.shopbot1.flipkart;
import com.example.shopbot1.snapdeal;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class productList extends AppCompatActivity implements  View.OnClickListener {

    SearchView search;

    RecyclerView recyclerView;
    com.example.shopbot1.recyclerAdapter recyclerAdapter;
    ImageButton filterButton;
    List<String> price_array;
    List<String> brand_array;
    List<ItemsList.item> mainList;
    List<String> filter_chip ;
    ProgressBar progressBar;
    Bundle bundle;
    String key,sortBy="relevance";
    flipkart flip;
    snapdeal snap;
    int priceFlag=0;
    RadioButton plth,phtl,rl,pop;
    RadioGroup rg;
    BottomSheetDialog bsd;
    FloatingActionButton bottom;
    View bsv;
    ChipGroup filterChipGroup;
    Chip chip_amazon, chip_flipkart,chip_snapdeal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.items_list);

        flip=new flipkart();
        snap=new snapdeal();

        bundle = getIntent().getExtras();
        key=bundle.get("key").toString();
        mainList=new ArrayList<>();
        recyclerView = findViewById(R.id.recView);
        search = findViewById(R.id.searchview);

        flip.setUrl(key,"");
        snap.setUrl(key,"");
        new doIT().execute();

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT).show();
                flip.setUrl(query,"");
                snap.setUrl(query,"");
                priceFlag=0;
                key=query;
                showpop();
               // new doIT().execute();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() == 0) {
                    key=bundle.get("key").toString();
                    flip.setUrl(key,"");
                    snap.setUrl(key,"");
                  // flip.searchQuery();
                    new doIT().execute();
                } else {
                    //recyclerAdapter.getFilter().filter(newText);
                }
                return false;
            }
        });
        filterButton = (ImageButton) findViewById(R.id.filter_l);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                showpop();
                priceFlag=1;
            }
        });
        bottom=findViewById(R.id.sortby_floatingActionButton);
        bsd=new BottomSheetDialog(productList.this,R.style.BottomSheetDialogTheme);
        bsv= LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_bottom_sheet,(LinearLayout)findViewById(R.id.bottomSheetContainer));
        rg=(RadioGroup)bsv.findViewById(R.id.rg);
        bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        int childCount = group.getChildCount();
                        for (int x = 0; x < childCount; x++) {
                            RadioButton btn = (RadioButton) group.getChildAt(x);
                            if (btn.getId() == checkedId) {
                                Log.e("selected RadioButton->", btn.getText().toString());
                                // btn.setChecked(true);
                                sortBy = btn.getText().toString();
                                if (priceFlag == 1) {
                                    flip.setUrl(key, flip.filter(setList(), price_array, brand_array) + flip.sort(sortBy));
                                    snap.setUrl(key, snap.filter(setList(), price_array, brand_array) + snap.sort(sortBy));
                                } else {
                                    flip.setUrl(key, flip.sort(sortBy));
                                    snap.setUrl(key, snap.sort(sortBy));
                                }
                                new doIT().execute();
                            }
                        }
                    }
                });
                bsd.setContentView(bsv);
                bsd.show();
            }
        });
         filterChipGroup = findViewById(R.id.chipGroup);
         chip_amazon = findViewById(R.id.chip_amazon);
         chip_flipkart = findViewById(R.id.chip_flipkart);
        chip_snapdeal= findViewById(R.id.chip_snapdeal);
         filter_chip=new ArrayList<>();
        CompoundButton.OnCheckedChangeListener filterChipListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Log.i("chip", buttonView.getText().toString() + "");
                    filter_chip.add(buttonView.getText().toString());
                }
                else{
                    filter_chip.remove(buttonView.getText().toString());
                }
//                updateList();
                mainList.clear();
                new doIT().execute();
                Log.i("chiplist", filter_chip.toString());
            }
        };
        chip_amazon.setOnCheckedChangeListener(filterChipListener);
        chip_flipkart.setOnCheckedChangeListener(filterChipListener);
        chip_snapdeal.setOnCheckedChangeListener(filterChipListener);
    }


    @Override
    public void onClick(View v) {

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void showpop() {
        Log.d("filter","showpop");
        flip.setUrl(key,flip.filter(setList(),price_array,brand_array)+flip.sort(sortBy));
        snap.setUrl(key,snap.filter(setList(),price_array,brand_array)+snap.sort(sortBy));
        new doIT().execute();
    }
    public ArrayList<String> setList(){
        ArrayList<String> selectedChipData=new ArrayList<String>();
        if(key.equalsIgnoreCase("mobiles")) {
            price_array= Arrays.asList(getResources().getStringArray(R.array.mobile_price));
            brand_array= Arrays.asList(getResources().getStringArray(R.array.mobile_brand));
            selectedChipData.addAll(Arrays.asList(new String[]{"Rs 1000 - Rs 5000","Samsung"}));
            Log.d("filter",price_array.toString());
        }else  if(key.equalsIgnoreCase("laptops")) {
            price_array= Arrays.asList(getResources().getStringArray(R.array.laptop_price));
            brand_array= Arrays.asList(getResources().getStringArray(R.array.laptop_brand));
            selectedChipData.addAll(Arrays.asList(new String[]{"Rs 10000 - Rs 50000","Dell"}));
        }else  if(key.equalsIgnoreCase("televisions")) {
            price_array= Arrays.asList(getResources().getStringArray(R.array.tv_price));
            brand_array= Arrays.asList(getResources().getStringArray(R.array.tv_brand));
            selectedChipData.addAll(Arrays.asList(new String[]{"Rs 10000 - Rs 60000","LG"}));
        }else  if(key.equalsIgnoreCase("cameras")) {
            price_array= Arrays.asList(getResources().getStringArray(R.array.camera_price));
            brand_array= Arrays.asList(getResources().getStringArray(R.array.camera_brand));
            selectedChipData.addAll(Arrays.asList(new String[]{"Rs 5000 - Rs 15000","Sony"}));
        }
        return selectedChipData;
    }
    public  class doIT extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            progressBar = (ProgressBar) findViewById(R.id.progressbar);
            progressBar.setVisibility(View.VISIBLE);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Void doInBackground(Void... voids) {
            flip.searchQuery();
            snap.searchQuery();
            mergeLists();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);

            recyclerAdapter = new recyclerAdapter(mainList);
            recyclerAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(recyclerAdapter);

            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(productList.this, DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(dividerItemDecoration);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void mergeLists(){
        mainList.clear();
        int i=0;
        while(i<flip.getProductList().size() || i<snap.getProductList().size()) {
            if (filter_chip.contains("Flipkart") || filter_chip.isEmpty()) {
                if(i<flip.getProductList().size()) {
                    mainList.add(flip.getProductList().get(i));
//                mainList = Stream.of(mainList, flip.getProductList())
//                        .flatMap(x -> x.stream())
//                        .collect(Collectors.toList());
                    Log.d("list_inmerge", "list=" + mainList.toString());
                }
            }
            if (filter_chip.contains("Snapdeal") || filter_chip.isEmpty()) {
                if(i<snap.getProductList().size()) {
                    mainList.add( snap.getProductList().get(i));
//                mainList = Stream.of(mainList, snap.getProductList())
//                        .flatMap(x -> x.stream())
//                        .collect(Collectors.toList());
                    Log.d("list_inmerge", "list=" + mainList.toString());
                }
            }
            i++;
        }
        if(sortBy.equalsIgnoreCase("price- low to high")) {
            Collections.sort(mainList, new Comparator<ItemsList.item>() {
                public int compare(ItemsList.item one, ItemsList.item other){
                    if(Integer.parseInt(one.getPrice().replaceAll("[^0-9]", ""))==Integer.parseInt(other.getPrice().replaceAll("[^0-9]", "")))
                        return 0;
                    else if(Integer.parseInt(one.getPrice().replaceAll("[^0-9]", ""))>Integer.parseInt(other.getPrice().replaceAll("[^0-9]", "")))
                        return 1;
                    else
                        return -1;
                }
            });
        }
        if(sortBy.equalsIgnoreCase("price- high to low")) {
            Collections.sort(mainList, new Comparator<ItemsList.item>() {
                public int compare(ItemsList.item one, ItemsList.item other){
                    if(Integer.parseInt(one.getPrice().replaceAll("[^0-9]", ""))==Integer.parseInt(other.getPrice().replaceAll("[^0-9]", "")))
                        return 0;
                    else if(Integer.parseInt(one.getPrice().replaceAll("[^0-9]", ""))>Integer.parseInt(other.getPrice().replaceAll("[^0-9]", "")))
                        return -1;
                    else
                        return 1;
                }
            });
        }
//        mainList=mainList.stream().distinct().collect(Collectors.toList());
        //alternating addition of items from diff e-commerce websites for relevance
        //add comparator to compare price for price- low to high and high to low
        //add comparator to compare rate for popularity
    }
}