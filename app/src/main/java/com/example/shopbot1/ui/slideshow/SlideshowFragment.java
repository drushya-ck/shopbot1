package com.example.shopbot1.ui.slideshow;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbot1.R;
import com.example.shopbot1.recyclerAdapter;
import com.example.shopbot1.ui.home.ItemsList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import  com.example.shopbot1.ui.home.ItemsList;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    //ConstraintLayout parentLayout;
     ArrayList<ItemsList.item> list =new ArrayList<ItemsList.item>();;
    RecyclerView recyclerView;
    recyclerAdapter recyclerAdap;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    View root ;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("favourites");
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);


        root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        recyclerView = root.findViewById(R.id.recView1);

        disp();


        return root;
    }


    public void disp(){

        mDatabase.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    ItemsList.item item = ds.getValue(ItemsList.item.class);
                    Log.d("retrieve-hehe", "" + item.getName());
                    list.add(item);
                }
                recyclerAdap = new recyclerAdapter(list);
                recyclerView.setAdapter(recyclerAdap);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
                recyclerView.addItemDecoration(dividerItemDecoration);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("firebase_retrieval", "loadPost:onCancelled", databaseError.toException());
            }
            });
    }
}