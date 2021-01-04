package com.example.shopbot1;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbot1.ui.home.ItemsList;
import com.example.shopbot1.ui.slideshow.SlideshowFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class firebase {
    String mainCollection="users",subCollection="favourites";
    ItemsList.item favItem;
    ArrayList<ItemsList.item> list ;
    boolean flag=false;

    public DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("favourites");

    public boolean existsInFav(String trim){
            flag=false;
//        Log.d("regex",trim);
        mDatabase.child(trim).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
//                    holder.fav1.setImageResource(R.drawable.ic_baseline_favorite_24);
//                    Log.d("firebase_fav",moviesList.get(position).name+" does  exist in fav");
                        flag=true;
                }else{
//                   Log.d("firebase_fav",moviesList.get(position).name+" does not exist in fav");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return flag;
    }
    public void storeFav(ItemsList.item favItem,String trim){
        this.favItem=favItem;
        mDatabase.child(trim).setValue(favItem).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("realtime_firebase", e.getLocalizedMessage());
            }
        });
    }
    public ArrayList<ItemsList.item> getFav(RecyclerView rv, Activity ac){
        list=new ArrayList<ItemsList.item>();
        mDatabase.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ItemsList.item item = ds.getValue(ItemsList.item.class);
                    Log.d("retrieve-hehe", "" + item.getName());
                    list.add(item);
//                }
                }
                recyclerAdapter ra = new recyclerAdapter(list);
                rv.setAdapter(ra);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(ac, DividerItemDecoration.VERTICAL);
                rv.addItemDecoration(dividerItemDecoration);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return list;
    }

    public void removeFav(String trim){
        mDatabase.child(trim).removeValue();
    }

}
