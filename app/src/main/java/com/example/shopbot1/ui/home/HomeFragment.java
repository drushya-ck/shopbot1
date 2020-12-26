package com.example.shopbot1.ui.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.shopbot1.R;
import com.example.shopbot1.SubCatProductList;

import java.lang.reflect.AccessibleObject;

public class HomeFragment extends Fragment{

    private HomeViewModel homeViewModel;
    CardView c1,c2,c3,c4,c5,c6;
    SearchView searchView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final FragmentActivity a=HomeFragment.this.getActivity();
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        c1=(CardView) root.findViewById(R.id.card1);
        c1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i=new Intent(a, productList.class);
                Bundle bundle = new Bundle();
                bundle.putString("key", "mobiles");
                i.putExtras(bundle); 
                startActivity(i);
            }
        });
        c2=(CardView)root.findViewById(R.id.card2);
        c2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i=new Intent(a, productList.class);
                Bundle bundle = new Bundle();
                bundle.putString("key", "Fashion");
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        c3=(CardView)root.findViewById(R.id.card3);
        c3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i=new Intent(a, productList.class);
                Bundle bundle = new Bundle();
                bundle.putString("key", "Books");
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        c4=(CardView)root.findViewById(R.id.card4);
        c4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i=new Intent(a, productList.class);
                Bundle bundle = new Bundle();
                bundle.putString("key", "Grocery");
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        c5=(CardView)root.findViewById(R.id.card5);
        c5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i=new Intent(a, productList.class);
                Bundle bundle = new Bundle();
                bundle.putString("key", "Beauty");
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        c6=(CardView)root.findViewById(R.id.card6);
        c6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i=new Intent(a, productList.class);
                Bundle bundle = new Bundle();
                bundle.putString("key", "Kitchen");
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        searchView=root.findViewById(R.id.search_home);

        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(root.getContext(), query, Toast.LENGTH_SHORT).show();
                Intent i=new Intent(a, productList.class);
                Bundle bundle = new Bundle();
                bundle.putString("key", query);
                i.putExtras(bundle);
                startActivity(i);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
        return root;
    }
}