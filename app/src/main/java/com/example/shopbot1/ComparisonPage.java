package com.example.shopbot1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopbot1.ui.home.ItemsList;
import com.example.shopbot1.ui.home.productList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.Serializable;

public class ComparisonPage extends AppCompatActivity implements Serializable {
    String item_name="",flip_price="",flip_rate="",flip_pdturl="",snap_price="",snap_rate="",snap_pdturl="";
    TextView itemName,flipPrice,flipRate,snapPrice,snapRate,amazPrice,amazeRate;
    ImageView img;
    Button flipBut,snapBut,amazBut;
    ImageButton flip_fav,snap_fav,amaz_fav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparison_page);
        savedInstanceState = getIntent().getExtras();
        firebase fb=new firebase();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("favourites");
        ItemsList.item fobj = (ItemsList.item) getIntent().getSerializableExtra("flipkart_obj");
        ItemsList.item sobj = (ItemsList.item) getIntent().getSerializableExtra("snapdeal_obj");
        ItemsList.item aobj = (ItemsList.item) getIntent().getSerializableExtra("amazon_obj");
        itemName=findViewById(R.id.cp_name);
        item_name=savedInstanceState.get("item_name").toString();
        itemName.setText(item_name);
//        String trim=trimName(item_name);

        flipPrice = findViewById(R.id.cp_price_flipkart);
        flipRate = findViewById(R.id.cp_rate_flipkart);
        flip_fav=findViewById(R.id.flip_fav);
        img=findViewById(R.id.cp_image);
        flipBut=findViewById(R.id.flipkart_web);
        if(fobj!=null){
//            if(fb.existsInFav(trimName(fobj.name))){
//                flip_fav.setImageResource(R.drawable.ic_baseline_favorite_24);
//                Log.d("exists in fav in cp",fobj.name);
//            }

            mDatabase.child(trimName(fobj.name)).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        flip_fav.setImageResource(R.drawable.ic_baseline_favorite_24);
                        Log.d("firebase_fav",fobj.name+" does  exist in fav");
                        fobj.fav=true;
                        //flag=true;
                    }else{
                        fobj.fav=false;
                        Log.d("firebase_fav",fobj.name+" does not exist in fav");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
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
                        Toast.makeText(ComparisonPage.this,fobj.name+" marked as Favourite!",Toast.LENGTH_SHORT).show();
                        flip_fav.setImageResource(R.drawable.ic_baseline_favorite_24);
                        fb.storeFav(fobj,trimName(fobj.name));

                    }
                    else{
                        fobj.fav = false;
                        Toast.makeText(ComparisonPage.this,fobj.name+" removed from Favourites.",Toast.LENGTH_SHORT).show();;
                        flip_fav.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                        fb.removeFav(trimName(fobj.name));
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
//            if(fb.existsInFav(trimName(sobj.name))){
//                snap_fav.setImageResource(R.drawable.ic_baseline_favorite_24);
//            }
            mDatabase.child(trimName(sobj.name)).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        snap_fav.setImageResource(R.drawable.ic_baseline_favorite_24);
                        sobj.fav=true;
                        Log.d("firebase_fav",sobj.name+" does  exist in fav");
                        //flag=true;
                    }else{
                        sobj.fav=false;
                        Log.d("firebase_fav",sobj.name+" does not exist in fav");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            snapPrice.setText(sobj.price);
            snapRate.setText(sobj.rating);
//            Picasso.get()
//                    .load(sobj.img_url)
//                    .into(img);
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
                        Toast.makeText(ComparisonPage.this,sobj.name+" marked as Favourite!",Toast.LENGTH_SHORT).show();;
                        snap_fav.setImageResource(R.drawable.ic_baseline_favorite_24);
                        fb.storeFav(sobj,trimName(sobj.name));

                    }
                    else{
                        sobj.fav = false;
                        Toast.makeText(ComparisonPage.this,sobj.name+" romoved from Favourites.",Toast.LENGTH_SHORT).show();;
                        snap_fav.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                        fb.removeFav(trimName(sobj.name));
                    }
                }
            });
        }else{
            snapPrice.setText("No results found.");
        }

        amazPrice = findViewById(R.id.cp_price_amazon);
        amazeRate = findViewById(R.id.cp_rate_amazon);
        amaz_fav=findViewById(R.id.amaz_fav);
        img=findViewById(R.id.cp_image);

        amazBut=findViewById(R.id.amazon_web);
        if(aobj!=null){
//            if(fb.existsInFav(trimName(fobj.name))){
//                flip_fav.setImageResource(R.drawable.ic_baseline_favorite_24);
//                Log.d("exists in fav in cp",fobj.name);
//            }

            mDatabase.child(trimName(aobj.name)).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        amaz_fav.setImageResource(R.drawable.ic_baseline_favorite_24);
                        Log.d("firebase_fav",aobj.name+" does  exist in fav");
                        aobj.fav=true;
                        //flag=true;
                    }else{
                        aobj.fav=false;
                        Log.d("firebase_fav",aobj.name+" does not exist in fav");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            amazPrice.setText(aobj.price);
            amazeRate.setText(aobj.rating);
            Picasso.get()
                    .load(aobj.img_url)
                    .into(img);
            amazBut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = null;
                    browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(aobj.productUrl));
                    startActivity(browserIntent);
                }
            });

            amaz_fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!aobj.fav) {
                        aobj.fav=true;
                        Toast.makeText(ComparisonPage.this,aobj.name+" marked as Favourite!",Toast.LENGTH_SHORT).show();
                        amaz_fav.setImageResource(R.drawable.ic_baseline_favorite_24);
                        fb.storeFav(aobj,trimName(aobj.name));

                    }
                    else{
                        aobj.fav = false;
                        Toast.makeText(ComparisonPage.this,aobj.name+" removed from Favourites.",Toast.LENGTH_SHORT).show();;
                        amaz_fav.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                        fb.removeFav(trimName(aobj.name));
                    }
                }
            });
        } else{
            amazPrice.setText("No results found.");
        }



    }
    public String trimName(String trim){
        return trim.replaceAll("[<(\\[{\\\\^\\-=$!|\\]})?*+.>]", "").replace(" ","").trim();
    }
//
//    @Override
//    public void onBackPressed() {
//        //your method call
//        super.onBackPressed();
//        Intent i=new Intent(this, productList.class);
//        Bundle bundle = new Bundle();
//        bundle.putString("key", "mobiles");
//        i.putExtras(bundle);
//        startActivity(i);
//    }
}