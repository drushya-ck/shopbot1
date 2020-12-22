package com.example.shopbot1.ui.home;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import  androidx.appcompat.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.ProxySelector;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.example.shopbot1.R;
import com.example.shopbot1.recyclerAdapter;
import com.example.shopbot1.searchActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.appcompat.app.AlertDialog;
import javax.crypto.AEADBadTagException;


public class ItemsList extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.items_list);


    }

    public static class item{
        String id;
        public String name,price,rating,img_url="",website="",productUrl="";
        public boolean fav;
        public item(){name="";price="";rating="";img_url="";fav=false;}
        public item(String name,String price,String rating,String img_url){
            this.name=name;
            this.price=price;
            this.rating=rating;
            this.img_url=img_url;
        }

        public String getId() {return id;}

        public void setId(String id) {
            this.id = id;
        }

        public String getName(){
            return name;
        }
        public String getPrice(){
            return price;
        }
        public String getRating(){
            return rating;
        }
        public String getImg_url(){
            return img_url;
        }
        public boolean getFav(){
            return fav;
        }
        public void setName(String name){
            this.name= name;
        }
        public void setPrice(String price){
            this.price= price;
        }
        public void setRating(String rating){
            this.rating= rating;
        }
        public void setImg_url(String img_url){
            this.img_url= img_url;
        }
        public void setFav(boolean fav){
            this.fav= fav;
        }
        public void setWebsite(String web){ website=web;}
        public String getWebsite(){return website;}
        public void setProductUrl(String productUrl){ this.productUrl=productUrl;}
        public String getProductUrl(){return productUrl;}
    }
}