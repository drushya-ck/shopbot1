package com.example.shopbot1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.shopbot1.ui.home.CustomAdapter;
import com.example.shopbot1.ui.home.Mobiles;
import com.example.shopbot1.ui.home.productList;

import java.util.ArrayList;
import java.util.Arrays;

public class SubCatProductList extends AppCompatActivity {
    ListView subList;
    ArrayList<String> subCatList=new ArrayList<>();
    ArrayList<Integer> subCatImageList=new ArrayList<>();
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_cat_product_list);
        subList=(ListView)findViewById(R.id.subcat_listview) ;
        bundle = getIntent().getExtras();
        String key=bundle.get("key").toString();

        if(key.equalsIgnoreCase("electronics")){
            subCatList.addAll(Arrays.asList(new String[]{"mobiles","laptops","cameras","televisions"}));
            subCatImageList.addAll(Arrays.asList(new Integer[]{R.drawable.amazon,R.drawable.amazon,R.drawable.amazon,R.drawable.amazon}));
        }
        else if(key.equalsIgnoreCase("fashion")){
            subCatList.addAll(Arrays.asList(new String[]{"men's fashion","women's fashion"}));
        }
        else if(key.equalsIgnoreCase("books")){
            subCatList.addAll(Arrays.asList(new String[]{"textbooks","novels"}));
        }
        else if(key.equalsIgnoreCase("grocery")){
            subCatList.addAll(Arrays.asList(new String[]{"dairy products","snacks"}));
        }
        else if(key.equalsIgnoreCase("beauty")){
            subCatList.addAll(Arrays.asList(new String[]{"skin care","make-up"}));
        }
        else if(key.equalsIgnoreCase("kitchen")){
            subCatList.addAll(Arrays.asList(new String[]{"dining","utility"}));
        }
            CustomAdapter ca=new CustomAdapter(this,subCatList,subCatImageList);
        subList.setAdapter(ca);
        subList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(SubCatProductList.this, productList.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("key", subCatList.get(position));
                    i.putExtras(bundle);
                    startActivity(i);
            }
        });
    }
}