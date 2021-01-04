package com.example.shopbot1.ui.home;


import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbot1.R;
import com.example.shopbot1.amazon;
import com.example.shopbot1.recyclerAdapter;
import com.example.shopbot1.flipkart;
import com.example.shopbot1.snapdeal;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class productList<subcat_selitmes> extends AppCompatActivity implements  View.OnClickListener {
    static public String mainkey="";
    SearchView search;
    Context context;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ChipGroup chipGroup;
    String[] subcatarray;
    RecyclerView recyclerView;
    com.example.shopbot1.recyclerAdapter recyclerAdapter;
    ImageButton filterButton;
    public String mainQuery="";
    List<ItemsList.item> mainList;
    FirebaseAuth firebaseAuth;

    ProgressBar progressBar;
    Bundle bundle;
    String sortBy="relevance";
    static public String key;

    flipkart flip;
    snapdeal snap;
    amazon amaz;


    RadioGroup rg;
    BottomSheetDialog bsd;
    FloatingActionButton bottom;
    View bsv;

    static public String subcategory="";
    static HashMap<String,String[]> hashMap;
    ArrayList<String> navig_filter=new ArrayList<>();
    LinearLayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.items_list);
        context=this;
        hashMap=new HashMap<>();

        flip=new flipkart();
        snap=new snapdeal();
        amaz=new amazon();
        bundle = getIntent().getExtras();
        mainList=new ArrayList<>();
        recyclerView = findViewById(R.id.recView);
        search = findViewById(R.id.searchview);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        key=bundle.get("key").toString();
        mainkey=key;


        amaz.setUrl(key,"");
        flip.setUrl(key,"");
        snap.setUrl(key,"");

        setNavigationDrawer();
        setHashMapValues();

        // set chips part 1() called
        setChipsPart1();


//        String url=amaz.getUrl();
        new doIT().execute();


//on querytext listener
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onQueryTextSubmit(String query) {
                amaz.setUrl(query+" "+subcategory,"");
                flip.setUrl(query+" "+subcategory,"");
                snap.setUrl(query+" "+subcategory,"");
                mainQuery=query;
                key=query;
//                String url=amaz.getUrl();
                new doIT().execute();
                return false;
            }
            //on query change listener
            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() == 0) {
                    //key=HomeFragment.key;
                    mainQuery="";
                    key=bundle.get("key").toString();
                    if(subcategory!=""){
                        amaz.setUrl(subcategory,"");
                        flip.setUrl(subcategory,"");
                        snap.setUrl(subcategory,"");
                    }else{
                        amaz.setUrl(key,"");
                        flip.setUrl(key,"");
                        snap.setUrl(key,"");
                    }
//                    String url=amaz.getUrl();
                    new doIT().execute();
                } else {

                }
                return false;
            }
        });


//click on the filters button for filters fragment
        filterButton = (ImageButton) findViewById(R.id.filter_l);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
//call showpop()
                showpop();

            }
        });




//bottom filters dialog code
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
                                Log.e("selected sort filter->", btn.getText().toString());
                                sortBy = btn.getText().toString();
//                                String filter=amaz.getFilter();
//                                sort=amaz.sort(sortBy);
//                                filter=sort+filter;
                                if(subcategory!=""){
                                    amaz.setUrl(mainQuery+" "+subcategory,amaz.sort(sortBy)+ amaz.getFilter());
                                    flip.setUrl(mainQuery+" "+subcategory, flip.getFilter() + flip.sort(sortBy));
                                    snap.setUrl(mainQuery+" "+subcategory, snap.getFilter() + snap.sort(sortBy));
                                }else{
                                    amaz.setUrl(mainQuery+" "+key, amaz.getFilter());
                                    flip.setUrl(mainQuery+" "+key, flip.getFilter());
                                    snap.setUrl(mainQuery+" "+key, snap.getFilter());
                                }
//                                String url=amaz.getUrl();
                                new doIT().execute();
                            }
                        }
                    }
                });
                bsd.setContentView(bsv);
                bsd.show();
            }
        });


    }



    //setNavigationDrawer() navig drawer activities
    private void setNavigationDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.items_list);
        navigationView = (NavigationView) findViewById(R.id.nav_view1);

//        TextView user=  navigationView.getHeaderView(R.id.navHeader).getRootView().findViewById(R.id.nav_userName);
//        TextView email=  navigationView.getHeaderView(R.id.navHeader).getRootView().findViewById(R.id.nav_userEmailId);
        firebaseAuth= FirebaseAuth.getInstance();
//        user.setText(firebaseAuth.getCurrentUser().getDisplayName());
//        email.setText(firebaseAuth.getCurrentUser().getEmail());
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.nav_userName);
        navUsername.setText(firebaseAuth.getCurrentUser().getDisplayName());
        TextView navEmailId = (TextView) headerView.findViewById(R.id.nav_userEmailId);
        navEmailId.setText(firebaseAuth.getCurrentUser().getEmail());

        MenuItem switchItem = navigationView.getMenu().findItem(R.id.flipkart);
        CompoundButton switchView = (CompoundButton) MenuItemCompat.getActionView(switchItem);
        switchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(getApplicationContext(),"flipkart",Toast.LENGTH_SHORT).show();
                if(isChecked){
                    navig_filter.add("flipkart");
                }
                else{
                    navig_filter.remove("flipkart");
                }

                mainList.clear();
                new doIT().execute();
            }
        });

        MenuItem switchItem1 = navigationView.getMenu().findItem(R.id.amazon);
        CompoundButton switchView1 = (CompoundButton) MenuItemCompat.getActionView(switchItem1);
        switchView1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    navig_filter.add("amazon");
                }
                else{
                    navig_filter.remove("amazon");
                }

                mainList.clear();
                new doIT().execute();
            }
        });

        MenuItem switchItem2 = navigationView.getMenu().findItem(R.id.snapdeal);
        CompoundButton switchView2 = (CompoundButton) MenuItemCompat.getActionView(switchItem2);
        switchView2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(getApplicationContext(),"snapdeal",Toast.LENGTH_SHORT).show();
                if(isChecked){
                    navig_filter.add("snapdeal");
                }
                else{
                    navig_filter.remove("snapdeal");
                }

                mainList.clear();
                new doIT().execute();
            }
        });
    }



    //set hash map values() code ie, subcategories with their respective filters
    private void setHashMapValues() {
//setting the values for the left fragment ....
        //electronics subcategories
        hashMap.put("mobiles",new String[]{"Brand","Price","Review"});
        hashMap.put("laptops",new String[]{"Brand","Price","Review"});
        hashMap.put("television",new String[]{"Brand","Price","Review"});

        //clothing subcat
        hashMap.put("men's fashion",new String[]{"Brand","Price","Review","Category"});
        hashMap.put("women's fashion",new String[]{"Brand","Price","Review","Category"});
        hashMap.put("kid's fashion",new String[]{"Brand","Price","Review","Category"});

        //books subcat
        hashMap.put("textbooks",new String[]{"Subject","Price","Review","Category"});
        hashMap.put("storybooks and novels",new String[]{"Author","Price","Review","Category"});
        hashMap.put("personal development",new String[]{"Author","Price","Review","Category"});

        //beauty and personal care
        hashMap.put("makeup",new String[]{"Brand","Price","Review","Category"});
        hashMap.put("skincare",new String[]{"Brand","Price","Review","Category"});
        hashMap.put("haircare",new String[]{"Brand","Price","Review","Category"});

        //Grocery and gourmet food
        hashMap.put("staples",new String[]{"Brand","Price","Review","Category"});
        hashMap.put("snacks",new String[]{"Brand","Price","Review","Category"});
        hashMap.put("dairy products",new String[]{"Brand","Price","Review","Category"});

        //Home and kitchen
        hashMap.put("kitchen appliances",new String[]{"Brand","Price","Review","Category"});
        hashMap.put("home decor",new String[]{"Brand","Price","Review","Category"});
        hashMap.put("furniture",new String[]{"Brand","Price","Review","Category"});
    }



    //setChipsPart1() ie setting the respective chips(subcategories) for every main category
    private void setChipsPart1() {
        ChipGroup chipGroup = findViewById(R.id.chipGroup);

        switch(key) {
            case "electronics":

                subcatarray=new String[]{"Mobiles","Laptops","Television"};
                setChipsPart2();

                break;
            case "clothing":

                subcatarray=new String[]{"Men's fashion","Women's fashion","Kid's fashion"};
                setChipsPart2();
                break;
            case "books":

                subcatarray=new String[]{"TextBooks","StoryBooks and novels","Personal Development"};
                setChipsPart2();
                break;
            case "grocery and gourmet food":

                subcatarray=new String[]{"Staples","Snacks","Dairy Products"};
                setChipsPart2();
                break;
            case "beauty and personal care":

                subcatarray=new String[]{"Makeup","Skincare","Haircare"};
                setChipsPart2();
                break;
            case "home and kitchen":

                subcatarray=new String[]{"Kitchen Appliances","Home Decor","Furniture"};
                setChipsPart2();
                break;
            default:
                break;
        }

    }



    //setChipsPart1() ie setting the respective chips(subcategories) for every main category
    private void setChipsPart2() {
        chipGroup= findViewById(R.id.chipGroup);
        int i=0;
        for(i=0;i<subcatarray.length;i++){
            Chip chip = new Chip(this);
            chip.setText(subcatarray[i]);
            chip.setChipBackgroundColorResource(R.color.colorAccent);
            chip.setCheckable(true);
            chip.setTextColor(getResources().getColor(R.color.white));
            subcategory=subcatarray[i];
// code for when u click on a specific chip(ie subcategory)
            chip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(chip.isChecked()){
                        subcategory=chip.getText().toString();
                        amaz.setUrl(mainQuery+subcategory,"");
                        flip.setUrl(mainQuery+subcategory,"");
                        snap.setUrl(mainQuery+subcategory,"");
//                        String url=amaz.getUrl();
                        new doIT().execute();
                    }
                }
            });
            chipGroup.addView(chip);
        }
    }



    //showpop() code it calls setfiltersfragment() if subcategory is not null
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void showpop() {

        if(subcategory!=null||!subcategory.isEmpty()){
            setFiltersFragment(subcategory);
        }else{
            new doIT().execute();
        }


    }



    //setFiltersFragment() code, passes the subcategory to filtersmainactivity class
    private void setFiltersFragment(String subcategory) {
        Intent send = new Intent(productList.this, FiltersMainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("subcategory1",subcategory);
        send.putExtras(bundle);
        startActivityForResult(send, 1);
    }




    @Override
    public void onClick(View v) {

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
            amaz.searchQuery();
            snap.searchQuery();
            mergeLists();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            recyclerAdapter = new recyclerAdapter(mainList);
            recyclerAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(recyclerAdapter);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(productList.this, DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(dividerItemDecoration);
        }
    }




    @RequiresApi(api = Build.VERSION_CODES.N)
    public void mergeLists() {
        mainList.clear();
        int i = 0;
        while (i < amaz.getProductList().size()) {
            if (navig_filter.contains("amazon") || navig_filter.isEmpty()) {
                if (i < amaz.getProductList().size()) {
                    mainList.add(amaz.getProductList().get(i));

                    Log.d("list_inmerge", "list=" + mainList.toString());
                }
            }

            if (navig_filter.contains("flipkart") || navig_filter.isEmpty()) {
                if(i<flip.getProductList().size()) {
                    mainList.add(flip.getProductList().get(i));

                    Log.d("list_inmerge", "list=" + mainList.toString());
                }
            }

            if (navig_filter.contains("snapdeal") || navig_filter.isEmpty()) {
                if(i<snap.getProductList().size()) {
                    mainList.add( snap.getProductList().get(i));

                    Log.d("list_inmerge", "list=" + mainList.toString());
                }
            }


            i++;
        }

        if (sortBy.equalsIgnoreCase("price- low to high")) {
            Collections.sort(mainList, new Comparator<ItemsList.item>() {
                public int compare(ItemsList.item one, ItemsList.item other) {
                    if (Integer.parseInt(one.getPrice().replaceAll("[^0-9]", "")) == Integer.parseInt(other.getPrice().replaceAll("[^0-9]", "")))
                        return 0;
                    else if (Integer.parseInt(one.getPrice().replaceAll("[^0-9]", "")) > Integer.parseInt(other.getPrice().replaceAll("[^0-9]", "")))
                        return 1;
                    else
                        return -1;
                }
            });
        }
        if (sortBy.equalsIgnoreCase("price- high to low")) {
            Collections.sort(mainList, new Comparator<ItemsList.item>() {
                public int compare(ItemsList.item one, ItemsList.item other) {
                    if (Integer.parseInt(one.getPrice().replaceAll("[^0-9]", "")) == Integer.parseInt(other.getPrice().replaceAll("[^0-9]", "")))
                        return 0;
                    else if (Integer.parseInt(one.getPrice().replaceAll("[^0-9]", "")) > Integer.parseInt(other.getPrice().replaceAll("[^0-9]", "")))
                        return -1;
                    else
                        return 1;

                }
            });
        }
    }




    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle = getIntent().getExtras();
        String amaz_filter="",flip_filter="",snap_filter="",amaz_key="",flip_key="",snap_key="";
        if(data!=null){
            amaz_filter = data.getStringExtra("amaz_filter");
            flip_filter = data.getStringExtra("flip_filter");
            snap_filter = data.getStringExtra("snap_filter");
//             changed_querycategory=data.getStringExtra("changedkeycategory");
        }
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {

                if(subcategory!=""){
                    amaz_key=subcategory;
                    flip_key=subcategory;
                    snap_key=subcategory;
                } //we cant include the category with this if subcat is present cz it doesnt give accurate results(like moblie electronics will show something else oh god

                if(amazon.changed_Keycategory!="" || flipkart.modifiedKey!="" || snapdeal.modifiedKey!=""){
//                    key+=" "+changed_querycategory;//this is name as changed_Keycategory in amazon class
                    amaz_key+=" "+amazon.changed_Keycategory;
                    flip_key=flipkart.modifiedKey + flip_key;
                    snap_key+= " "+snapdeal.modifiedKey;
                } else{
                    amaz_key+= amaz_key + mainQuery;
                    flip_key= mainQuery + flip_key;
                    snap_key= snap_key + mainQuery;
                }
                amaz_filter=amaz_filter+amaz.sort(sortBy);
                flip_filter=flip_filter+flip.sort(sortBy);
                snap_filter=snap_filter+snap.sort(sortBy);

                amaz.setUrl(amaz_key,amaz_filter);
                flip.setUrl(flip_key,flip_filter);
                snap.setUrl(snap_key,snap_filter);

                Toast.makeText(getApplicationContext(),amaz.getUrl(),Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),flip.getUrl(),Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),snap.getUrl(),Toast.LENGTH_SHORT).show();

                new doIT().execute();

            }
        }
    }


}