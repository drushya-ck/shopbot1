package com.example.shopbot1.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.shopbot1.R;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentLeft extends Fragment {
    String[] filters;

    ListView listview;
    View retView;
    static public String subcategory;
    public static Button apply;
    public static ArrayList<String> brand_array=new ArrayList<>();
    public static ArrayList<String> author_array=new ArrayList<>();
    public static ArrayList<String> category_array=new ArrayList<>();
    public static ArrayList<String> selitems = new ArrayList<>();
    public static HashMap<String,String> sub_selitems = new HashMap<>();

    int flag=0;

    // This method will be invoked when the Fragment view object is created.
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        retView = inflater.inflate(R.layout.fragment_left, container);
        return retView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        subcategory = productList.subcategory;
        if (subcategory == null) {
            Toast.makeText(getActivity(), "its null", Toast.LENGTH_SHORT).show();
        }
        if(productList.hashMap!=null){
            //Log.d("map","map is not nullokay");

            filters = productList.hashMap.get(subcategory.toLowerCase());

            if(productList.hashMap.get(subcategory.toLowerCase())==null){
                Log.d("array","array array is nullokay");
            }

        }
        final FragmentActivity fragmentBelongActivity = getActivity();
        MyAdapterLeft arr = new MyAdapterLeft(getActivity(), filters);
        listview = (ListView) retView.findViewById(R.id.lv1);
        listview.setAdapter(arr);
        listview.setBackgroundColor(Color.LTGRAY);;

        View convertView=null;
        View view1=arr.getView(0,convertView,listview);
        TextView tv1  = view1.findViewById(R.id.tv2);
        tv1.setTextColor(Color.parseColor("#0000FF"));
        final View[] prevview = {view1};


        setFiltersValues(tv1);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                if (prevview[0] != null) {
                    TextView prevtv = prevview[0].findViewById(R.id.tv2);
                    prevtv.setTextColor(Color.BLACK);
                }
                TextView tv = view.findViewById(R.id.tv2);
                tv.setTextColor(Color.parseColor("#0000FF"));

                prevview[0] = view;

                setFiltersValues(tv);

            }
        });

    }

    private void setFiltersValues(TextView tv) {
        FragmentRight txt = (FragmentRight) getFragmentManager().findFragmentById(R.id.fragmentRight);
        ArrayList<String> ar = new ArrayList<>();
        String filter = tv.getText().toString();
        flag=0;

        switch (productList.mainkey.toLowerCase()) {

            case "electronics":

                switch (subcategory.toLowerCase()) {
                    case "mobiles":
                        switch (filter.toLowerCase()) {
                            case "brand": ar.add("Samsung");ar.add("Apple");ar.add("OnePlus");flag=1;break;
                            case "price": ar.add("Under ₹ 5000");ar.add("₹ 5000 - ₹ 20,000");ar.add("Above ₹ 20,000");break;
                            case "review": ar.add("4 Stars & Up");ar.add("3 Stars & Up");ar.add("2 Stars & Up");break; default: break;
                        }break;
                    case "television":
                        //ar = new ArrayList<>();
                        switch (filter.toLowerCase()) {
                            case "brand": ar.add("Panasonic");ar.add("LG");ar.add("Sony");flag=1;break;
                            case "price": ar.add("Under ₹ 20,000");ar.add("₹ 20,000 - ₹ 50,000");ar.add("Above ₹ 50,000");break;
                            case "review": ar.add("4 Stars & Up");ar.add("3 Stars & Up");ar.add("2 Stars & Up");break; default: break;
                            }break;
                            case "laptops":
                               // ar = new ArrayList<>();
                                switch (filter.toLowerCase()) {
                                    case "brand": ar.add("HP");ar.add("Acer");ar.add("Dell");flag=1;break;
                                    case "price": ar.add("Under ₹ 30,000");ar.add("₹ 30,000 - ₹ 50,000");ar.add("Above ₹ 50,000");break;
                                    case "review": ar.add("4 Stars & Up");ar.add("3 Stars & Up");ar.add("2 Stars & Up");break; default: break;
                                }break;
                }break;
            case "clothing":
                switch (subcategory.toLowerCase()) {
                    case "men's fashion":
                        switch (filter.toLowerCase()) {
                            case "brand": ar.add("Lee Cooper");ar.add("Nike");ar.add("Adidas");flag=1;break;
                            case "price": ar.add("Under  ₹ 500");ar.add("₹ 500 - ₹ 1000");ar.add("Above ₹ 1000");break;
                            case "category":ar.add("Watches");ar.add("Shoes");ar.add("Shirts");ar.add("Indian clothing");flag=3;break;
                            case "review": ar.add("4 Stars & Up");ar.add("3 Stars & Up");ar.add("2 Stars & Up");break; default: break;
                        }break;
                    case "women's fashion":
                        switch (filter.toLowerCase()) {
                            case "brand": ar.add("Adidas");ar.add("Nike");ar.add("Forever 21");flag=1;break;
                            case "price": ar.add("Under  ₹ 500");ar.add("₹ 500 - ₹ 1000");ar.add("Above ₹ 1000");break;
                            case "category":ar.add("Indian Wear");ar.add("Handbags");ar.add("Footwear");ar.add("Jewellery");flag=3;break;
                            case "review": ar.add("4 Stars & Up");ar.add("3 Stars & Up");ar.add("2 Stars & Up");break; default: break;
                        }break;
                    case "kid's fashion":
                        switch (filter.toLowerCase()) {
                            case "brand": ar.add("Allen Solly");ar.add("Max");ar.add("Adidas");ar.add("Nike");flag=1;break;
                            case "price": ar.add("Under  ₹ 500");ar.add("₹ 500 - ₹ 1000");ar.add("Above ₹ 1000");break;
                            case "category":ar.add("Boys' Clothing");ar.add("Girls' Clothing");ar.add("Kids' Shoes");flag=3;break;
                            case "review": ar.add("4 Stars & Up");ar.add("3 Stars & Up");ar.add("2 Stars & Up");break; default: break;
                        }break;
                }break;
            case "books":
                switch (subcategory.toLowerCase()) {
                    case "textbooks":
                        switch (filter.toLowerCase()) {
                            case "subject": ar.add("Maths");ar.add("Computer");ar.add("Science");break;
                            case "price": ar.add("Under  ₹ 300");ar.add("₹ 300 - ₹ 700");ar.add("Above ₹ 700");break;
                            case "category":ar.add("School");ar.add("Higher Education");ar.add("Study Guides");flag=3;break;
                            case "review": ar.add("4 Stars & Up");ar.add("3 Stars & Up");ar.add("2 Stars & Up");break; default: break;
                        }break;
                    case "storybooks and novels":
                        switch (filter.toLowerCase()) {
                            case "author": ar.add("Sudha Murthy");ar.add("J K Rowling");ar.add("Ruskin Bond");flag=2;break;
                            case "price": ar.add("Under ₹ 500");ar.add("₹ 500 - ₹ 1000");ar.add("Above ₹ 1000");break;
                            case "category":flag=3;ar.add("Literature and Fiction");ar.add("Non Fiction");ar.add("Comics and Novels");ar.add("Kids' Story Books");break;
                            case "review": ar.add("4 Stars & Up");ar.add("3 Stars & Up");ar.add("2 Stars & Up");break; default: break;
                        }break;
                    case "personal development":
                        switch (filter.toLowerCase()) {
                            case "author": ar.add("Napoleon Hill");ar.add("Rujuta Diwekar");ar.add("Joseph Murphy");flag=2;break;
                            case "price": ar.add("Under ₹ 500");ar.add("₹ 500 - ₹ 1000");ar.add("Above ₹ 1000");break;
                            case "category":flag=3;ar.add("SelfHelp");ar.add("Fitness");ar.add("Lifestyle Guides");break;
                            case "review": ar.add("4 Stars & Up");ar.add("3 Stars & Up");ar.add("2 Stars & Up");break; default: break;
                        }break;
                }break;
            case "beauty and personal care":
                switch (subcategory.toLowerCase()) {
                    case "makeup":
                        switch (filter.toLowerCase()) {
                            case "category":flag=3; ar.add("Compact Powder");ar.add("Lipstick");ar.add("Muscara");ar.add("Foundation");ar.add("Eye Shadow");ar.add("Makeup Kits");break;
                            case "price": ar.add("Under  ₹ 500");ar.add("₹ 500 - ₹ 1000");ar.add("Above ₹ 1000");break;
                            case "brand":ar.add("Lakme");ar.add("Maybelline");ar.add("L'Oreal Paris");flag=1;break;
                            case "review": ar.add("4 Stars & Up");ar.add("3 Stars & Up");ar.add("2 Stars & Up");break; default: break;
                        }break;
                    case "skincare":
                        switch (filter.toLowerCase()) {
                            case "category":flag=3; ar.add("Cream");ar.add("Moisturizer");ar.add("SunScreen");ar.add("FaceWash");ar.add("Bathing Bars");break;
                            case "price": ar.add("Under ₹ 500");ar.add("₹ 500 - ₹ 1000");ar.add("Above ₹ 1000");break;
                            case "brand":ar.add("Himalaya");ar.add("Nivea");ar.add("Vaseline");ar.add("Ponds");flag=1;break;
                            case "review": ar.add("4 Stars & Up");ar.add("3 Stars & Up");ar.add("2 Stars & Up");break; default: break;
                        }break;
                    case "haircare":
                        switch (filter.toLowerCase()) {
                            case "brand": ar.add("L'Oreal Paris");ar.add("TRESemme");ar.add("Parachute");flag=1;break;
                            case "price": ar.add("Under ₹ 500");ar.add("₹ 500 - ₹ 1000");ar.add("Above ₹ 1000");break;
                            case "category":flag=3;ar.add("Hair Oil");ar.add("Shampoo");ar.add("Conditioner");break;
                            case "review": ar.add("4 Stars & Up");ar.add("3 Stars & Up");ar.add("2 Stars & Up");break; default: break;
                        }break;
                }break;
            case "grocery and gourmet food":
                switch (subcategory.toLowerCase()) {
                    case "staples":
                        switch (filter.toLowerCase()) {
                            case "category": flag=3;ar.add("Dal and Pulses");ar.add("Dry Fruits");ar.add("Rice and Rice Products");ar.add("Ghee and Oils");ar.add("Sugar");ar.add("Salt");break;
                            case "price": ar.add("Under  ₹ 200");ar.add("₹ 200 - ₹ 500");ar.add("₹ 500-₹ 1000");ar.add("Above  ₹ 1000");break;
                            case "brand":ar.add("Aashirvaad");ar.add("Fortune");ar.add("Tata");ar.add("Madhur");ar.add("Nandini");ar.add("Sunfeast");flag=1;break;
                            case "review": ar.add("4 Stars & Up");ar.add("3 Stars & Up");ar.add("2 Stars & Up");break; default: break;
                        }break;
                    case "snacks":
                        switch (filter.toLowerCase()) {
                            case "category": flag=3;ar.add("Biscuits");ar.add("Chocolates");ar.add("Chips");break;
                            case "price": ar.add("Under  ₹ 200");ar.add("₹ 200 - ₹ 500");ar.add("Above ₹ 500");break;
                            case "brand":ar.add("Paper Boat");ar.add("Lay's");ar.add("Sunfeast");ar.add("Kellogg's");ar.add("Cadbury");ar.add("Red Label");ar.add("Nestle");flag=1;break;
                            case "review": ar.add("4 Stars & Up");ar.add("3 Stars & Up");ar.add("2 Stars & Up");break; default: break;
                        }break;
                    case "dairy products":
                        switch (filter.toLowerCase()) {
                            case "category": flag=3;ar.add("Milk");ar.add("Cheese");ar.add("Butter");ar.add("Curd");ar.add("Paneer");break;
                            case "price": ar.add("Under  ₹ 200");ar.add("₹ 200 - ₹ 500");ar.add("Above ₹ 500");break;
                            case "brand":ar.add("Nandini");ar.add("Amul");ar.add("Mother Dairy");flag=1;break;
                            case "review": ar.add("4 Stars & Up");ar.add("3 Stars & Up");ar.add("2 Stars & Up");break; default: break;
                        }break;
                }break;
            case "home and kitchen":
                switch (subcategory.toLowerCase()) {
                    case "kitchen appliances":
                        switch (filter.toLowerCase()) {
                            case "brand": ar.add("Pigeon");ar.add("Prestige");ar.add("Phillips");flag=1;break;
                            case "price": ar.add("Under  ₹ 500");ar.add("₹ 500 - ₹ 1500");ar.add("Above ₹ 1500");break;
                            case "category":flag=3;ar.add("Kitchen electrical Appliances");ar.add("Kitchen Tools");ar.add("Jars and Containers");break;
                            case "review": ar.add("4 Stars & Up");ar.add("3 Stars & Up");ar.add("2 Stars & Up");break; default: break;
                        }break;
                    case "home decor":
                        switch (filter.toLowerCase()) {
                            case "brand": ar.add("Xtore");ar.add("eCraftIndia");ar.add("HomeSake");flag=1;break;
                            case "price": ar.add("Under ₹ 500");ar.add("₹ 500 - ₹ 1000");ar.add("Above ₹ 1000");break;
                            case "category":flag=3;ar.add("Wall Decor");ar.add("Clocks");ar.add("Idols");ar.add("Flowers and Vase");ar.add("Diyas");ar.add("Candles and Holders");break;
                            case "review": ar.add("4 Stars & Up");ar.add("3 Stars & Up");ar.add("2 Stars & Up");break; default: break;
                        }break;
                    case "furniture":
                        switch (filter.toLowerCase()) {
                            case "brand": ar.add("Furny");ar.add("Cello");ar.add("BucketList");flag=1;break;
                            case "price": ar.add("Under ₹ 500");ar.add("₹ 500 - ₹ 1000");ar.add("Above ₹ 1000");break;
                            case "category":flag=3;ar.add("Sofas and Couches");ar.add("Tables");ar.add("Wall Shelves");ar.add("Beds");ar.add("Chairs");ar.add("WardRobes");ar.add("Shoe Rack");break;
                            case "review": ar.add("4 Stars & Up");ar.add("3 Stars & Up");ar.add("2 Stars & Up");break; default: break;
                        }break;
                }break;
                


        }


        if(!brand_array.containsAll(ar)&&flag==1){
            brand_array.addAll(ar);flag=0;
        }else if(!author_array.containsAll(ar)&&flag==2){
            author_array.addAll(ar);flag=0;
        }else if(!category_array.containsAll(ar)&&flag==3){
            category_array.addAll(ar);flag=0;}

        Log.d("brand",brand_array.toString());
        Log.d("author",author_array.toString());
        Log.d("category",category_array.toString());
        Log.d("values array",ar.toString());
        txt.change(ar);
    }
}
