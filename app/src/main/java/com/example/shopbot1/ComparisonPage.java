package com.example.shopbot1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shopbot1.ui.home.ItemsList;
import com.squareup.picasso.Picasso;

import java.io.Serializable;

public class ComparisonPage extends AppCompatActivity implements Serializable {
    String item_name="",flip_price="",flip_rate="",flip_pdturl="",snap_price="",snap_rate="",snap_pdturl="";
    TextView itemName,flipPrice,flipRate,snapPrice,snapRate;
    ImageView img;
    Button flipBut,snapBut,amazBut;
    ImageButton flip_fav,snap_fav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparison_page);
        savedInstanceState = getIntent().getExtras();
        firebase fb=new firebase();

        ItemsList.item fobj = (ItemsList.item) getIntent().getSerializableExtra("flipkart_obj");
        ItemsList.item sobj = (ItemsList.item) getIntent().getSerializableExtra("snapdeal_obj");
        itemName=findViewById(R.id.cp_name);
        item_name=savedInstanceState.get("item_name").toString();
        itemName.setText(item_name);
        final String trim = item_name.replaceAll("[<(\\[{\\\\^\\-=$!|\\]})?*+.>]", "").replace(" ","").trim();

        flipPrice = findViewById(R.id.cp_price_flipkart);
        flipRate = findViewById(R.id.cp_rate_flipkart);
        flip_fav=findViewById(R.id.flip_fav);
        img=findViewById(R.id.cp_image);
        flipBut=findViewById(R.id.flipkart_web);
        if(fobj!=null){
            if(fb.existsInFav(trim)){
                flip_fav.setImageResource(R.drawable.ic_baseline_favorite_24);
            }
            flipPrice.setText(fobj.price);
            flipRate.setText(fobj.rating);

            flipBut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = null;
                    browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(fobj.productUrl));
                    startActivity(browserIntent);
                }
            });

            flip_fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!fobj.fav) {
                        fobj.fav=true;
                        flip_fav.setImageResource(R.drawable.ic_baseline_favorite_24);
                        fb.storeFav(fobj,trim);

                    }
                    else{
                        fobj.fav = false;
                        flip_fav.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                        fb.removeFav(trim);
                    }
                }
            });
        } else{
            flipPrice.setText("No results found.");
        }

        snap_fav=findViewById(R.id.snap_fav);
        snapPrice = findViewById(R.id.cp_price_snapdeal);
        snapRate = findViewById(R.id.cp_rate_snapdeal);
        snapBut=findViewById(R.id.snapdeal_web);
        if(sobj!=null){
            if(fb.existsInFav(trim)){
                snap_fav.setImageResource(R.drawable.ic_baseline_favorite_24);
            }

            snapPrice.setText(sobj.price);
            snapRate.setText(sobj.rating);
            Picasso.get()
                    .load(sobj.img_url)
                    .into(img);
            snapBut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = null;
                    browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(sobj.productUrl));
                    startActivity(browserIntent);
                }
            });
            snap_fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!sobj.fav) {
                        sobj.fav=true;
                        snap_fav.setImageResource(R.drawable.ic_baseline_favorite_24);
                        fb.storeFav(sobj,trim);

                    }
                    else{
                        sobj.fav = false;
                        snap_fav.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                        fb.removeFav(trim);
                    }
                }
            });
        }else{
            snapPrice.setText("No results found.");
        }

    }
}