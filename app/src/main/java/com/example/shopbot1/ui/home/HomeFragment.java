package com.example.shopbot1.ui.home;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbot1.HorizontalProductScrollAdapter;
import com.example.shopbot1.HorizontalProductScrollModel;
import com.example.shopbot1.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class HomeFragment extends Fragment{

    private HomeViewModel homeViewModel;
    CardView c1,c2,c3,c4,c5,c6;
    SearchView searchView;
    TextView htitle;
    Button hbutton;
    RecyclerView hrv;
    String productName="",productPrice="",productUrl="";
    List<HorizontalProductScrollModel> horizontalProductScrollModelList=new ArrayList<>();
    LinearLayout linearLayout;
     String imageUrl;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final FragmentActivity a=HomeFragment.this.getActivity();
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        htitle=root.findViewById(R.id.hscroll_title);

        hrv=root.findViewById(R.id.hscroll_rv);
        linearLayout=root.findViewById(R.id.linlayout);
        hbutton=linearLayout.findViewById(R.id.hscroll_button);
        hbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(a, DealsPage.class));
            }
        });
        new doIT().execute();
        c1=(CardView) root.findViewById(R.id.card1);
        c1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i=new Intent(a, productList.class);
                Bundle bundle = new Bundle();
                bundle.putString("key", "electronics");
                i.putExtras(bundle); 
                startActivity(i);
            }
        });
        c2=(CardView)root.findViewById(R.id.card2);
        c2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i=new Intent(a, productList.class);
                Bundle bundle = new Bundle();
                bundle.putString("key", "clothing");
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        c3=(CardView)root.findViewById(R.id.card3);
        c3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i=new Intent(a, productList.class);
                Bundle bundle = new Bundle();
                bundle.putString("key", "books");
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        c4=(CardView)root.findViewById(R.id.card4);
        c4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i=new Intent(a, productList.class);
                Bundle bundle = new Bundle();
                bundle.putString("key", "grocery and gourmet food");
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        c5=(CardView)root.findViewById(R.id.card5);
        c5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i=new Intent(a, productList.class);
                Bundle bundle = new Bundle();
                bundle.putString("key", "beauty and personal care");
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        c6=(CardView)root.findViewById(R.id.card6);
        c6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i=new Intent(a, productList.class);
                Bundle bundle = new Bundle();
                bundle.putString("key", "home and kitchen");
                i.putExtras(bundle);
                startActivity(i);
            }
        });
//        searchView=root.findViewById(R.id.search_home);
//
//        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                Toast.makeText(root.getContext(), query, Toast.LENGTH_SHORT).show();
//                Intent i=new Intent(a, productList.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("key", query);
//                i.putExtras(bundle);
//                startActivity(i);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//
//                return false;
//            }
//        });
        return root;
    }
    public  class doIT extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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
            HorizontalProductScrollAdapter horizontalProductScrollAdapter=new HorizontalProductScrollAdapter(horizontalProductScrollModelList);
            LinearLayoutManager linearLayoutManager= new LinearLayoutManager(HomeFragment.this.getActivity());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            hrv.setLayoutManager(linearLayoutManager);
            hrv.setAdapter(horizontalProductScrollAdapter);
            horizontalProductScrollAdapter.notifyDataSetChanged();
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
                    horizontalProductScrollModelList.add(new HorizontalProductScrollModel(imageUrl,productName,productPrice,"https://www.amazon.in"+productUrl));
                    System.out.println("link="+productUrl+" name="+productName+"price="+productPrice);
                }


            }
        } catch (IOException e) {
//                e.printStackTrace();
            System.out.println("error while scraping");
        }
    }
}