package com.example.shopbot1.ui.home;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.shopbot1.Adapter;
import com.example.shopbot1.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class DealsPage extends AppCompatActivity {
    RecyclerView dataList;
    List<String> titles;
    List<String> images;
    List<String> dealsProductUrl;
    Adapter adapter;
    String productName="",productPrice="",productUrl="";
    ProgressBar progressBar;
     String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deals_page);
        dataList = findViewById(R.id.dataList);

        titles = new ArrayList<>();
        images = new ArrayList<>();
        dealsProductUrl = new ArrayList<>();
        progressBar=findViewById(R.id.progressBar_deals);


        new doIT().execute();

    }
    public  class doIT extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Void doInBackground(Void... voids) {
            amazonDeals();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            adapter = new Adapter(DealsPage.this,titles,images,dealsProductUrl);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(DealsPage.this,2,GridLayoutManager.VERTICAL,false);
            dataList.setLayoutManager(gridLayoutManager);
            dataList.setAdapter(adapter);
        }
    }

    public void amazonDeals(){
        try {
            Document document = Jsoup.connect("https://www.amazon.in").get();
            Log.d("heree",document.getElementById("desktop-1").getElementsByAttribute("data-sgproduct").text());
            Element content=document.getElementById("desktop-1");
            ListIterator<Element> items = content.getElementsByAttribute("data-sgproduct").listIterator();
            if(!items.hasNext()) {
                System.out.println("empty");

            }
            while(items.hasNext()) {

                Element item=items.next();
                Elements e=item.getElementsByClass("product-image");
                imageUrl=e.attr("src");
                productUrl=item.getElementsByClass("deals-shoveler-card-image").select("a").attr("href");
//                    Elements name1=item.getElementsByClass("a-link-normal a-text-normal");
//                    productName=name1.text();
                Elements price1=item.select("div.dealPrice");
                productPrice=price1.text();
                if(productPrice.contains("from")){
                    productPrice=productPrice.substring(0,productPrice.indexOf("from"));

                }
                if(!imageUrl.isEmpty() && !productPrice.isEmpty()) {
                    titles.add(productPrice);
                    images.add(imageUrl);
                    dealsProductUrl.add("https://www.amazon.in"+productUrl);
                    System.out.println("link="+productUrl+" name="+productName+"price="+productPrice);
                }


            }
        } catch (IOException e) {
//                e.printStackTrace();
            System.out.println("error while scraping");
        }
    }
}