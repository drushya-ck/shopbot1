package com.example.shopbot1.ui.home;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopbot1.R;
import com.example.shopbot1.amazon;
import com.example.shopbot1.flipkart;
import com.example.shopbot1.snapdeal;

import static com.example.shopbot1.ui.home.FragmentLeft.selitems;


public class FiltersMainActivity extends AppCompatActivity {
    Button apply;

    public static String stuff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters_main);
        Bundle bundle = getIntent().getExtras();

        if (bundle == null) {
            Log.d("null", "its null");
        }

        stuff = bundle.getString("subcategory1");
        stuff= productList.subcategory;



        //Toast.makeText(getApplication(), stuff, Toast.LENGTH_SHORT).show();
        apply = (Button) findViewById(R.id.apply1);
//final apply button to get the urls
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amazon amazon=new amazon();
                flipkart flipkart=new flipkart();
                snapdeal snapdeal=new snapdeal();

                String amaz_filter=amazon.filter(selitems);
                String flip_filter=flipkart.filter(selitems);
                String snap_filter=snapdeal.filter(selitems);

//                if(filter!=null||!filter.isEmpty()){
//                    Log.d("inside filtermain",filter);
//                }else{
//                    Log.d("inside filtermain","filter is empty in filtermainactivity");
//                }

                Intent resultIntent = new Intent();
                resultIntent.putExtra("amaz_filter",amaz_filter);
                resultIntent.putExtra("flip_filter",flip_filter);
                resultIntent.putExtra("snap_filter",snap_filter);
//                resultIntent.putExtra("changedkeycategory",amazon.changed_Keycategory);
                setResult(RESULT_OK, resultIntent);
                finish();
            }


        });
    }



}