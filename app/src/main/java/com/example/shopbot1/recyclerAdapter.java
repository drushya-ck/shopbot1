package com.example.shopbot1;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbot1.ui.home.ItemsList;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.database.*;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.squareup.picasso.Picasso.*;


public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.ViewHolder> implements Filterable, Serializable {
    static int i=0;
    private static final String TAG = "RecyclerAdapter";
    List<ItemsList.item> moviesList;
    ItemsList item=new ItemsList();
    int j=0;
//    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("favourites");
    public recyclerAdapter(List<ItemsList.item> moviesList) {
        this.moviesList = moviesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.card_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.pname.setText(moviesList.get(position).name);
        holder.pprice.setText(moviesList.get(position).price);
        holder.prate.setText(moviesList.get(position).rating);
        if(!moviesList.get(position).img_url.equals("")) {
            get()
                    .load(moviesList.get(position).img_url)
                    .into(holder.imageView);
        }
//        Map<String, Object> favlist = new HashMap<>();
//        favlist.put("name", moviesList.get(position).name);
//        favlist.put("price", moviesList.get(position).price);
//        favlist.put("rating", moviesList.get(position).rating);
//        favlist.put("img_url", moviesList.get(position).img_url);
//        Pattern regex = Pattern.compile("[<(\\[{\\\\^\\-=$!|\\]})?*+.>]");
//
//        Matcher matcher = regex.matcher(moviesList.get(position).name);
        firebase fb=new firebase();
        final String trim = moviesList.get(position).name.replaceAll("[<(\\[{\\\\^\\-=$!|\\]})?*+.>]", "").replace(" ","").trim();
        if(fb.existsInFav(trim)){
            holder.fav1.setImageResource(R.drawable.ic_baseline_favorite_24);
        }


        holder.fav1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Picasso.get()
                        .load(R.drawable.ic_baseline_favorite_24)
                        .into(holder.fav1);*/
                if(!moviesList.get(position).fav) {
                    moviesList.get(position).fav = true;
                    holder.fav1.setImageResource(R.drawable.ic_baseline_favorite_24);
//                    favlist.put("fav", moviesList.get(position).fav);
                   fb.storeFav(moviesList.get(position),trim);

            }
                else{
                    moviesList.get(position).fav = false;
                    holder.fav1.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                    fb.removeFav(trim);
                }
            }
        });
        if(moviesList.get(position).website.equalsIgnoreCase("flipkart")){
            holder.card_imageView.setImageResource(R.drawable.flipkart);
        }
        else if(moviesList.get(position).website.equalsIgnoreCase("snapdeal")){
            holder.card_imageView.setImageResource(R.drawable.snapdeal);
        }


    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    @Override
    public Filter getFilter() {

        return myFilter;
    }

    Filter myFilter = new Filter() {

        //Automatic on background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<ItemsList.item> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(moviesList);
            } else {
                for(int k=0; k<moviesList.size();k++) {
                    if (moviesList.get(k).name.toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredList.add(moviesList.get(k));
                }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        //Automatic on UI thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            moviesList.clear();
            moviesList.addAll((Collection<? extends ItemsList.item>) filterResults.values);
            notifyDataSetChanged();
        }
    };



    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener , Serializable{

        ImageView imageView,card_imageView;
        TextView pname, pprice,prate;
        ImageButton fav1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            card_imageView = itemView.findViewById(R.id.card_imageView);
            pname = itemView.findViewById(R.id.product_name);
            pprice = itemView.findViewById(R.id.product_price);
            prate = itemView.findViewById(R.id.product_rate);
            fav1=itemView.findViewById(R.id.product_add);

            imageView.setOnClickListener(this);
            card_imageView.setOnClickListener(this);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(),moviesList.get(getAdapterPosition()).name, Toast.LENGTH_SHORT).show();
//            Intent browserIntent = null;
//            if(!moviesList.get(getAdapterPosition()).productUrl.isEmpty()) {
//                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(moviesList.get(getAdapterPosition()).productUrl));
//            }
//            view.getContext().startActivity(browserIntent);
            Log.d("namehehe" ,moviesList.get(getAdapterPosition()).name);
            flipkart f=new flipkart();
            f.setUrl(moviesList.get(getAdapterPosition()).name,"");
            snapdeal s=new snapdeal();
            s.setUrl(moviesList.get(getAdapterPosition()).name,"");

            new Thread(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void run() {
                    j=0;
                    f.searchQuery();
                    s.searchQuery();
                    Intent i = new Intent(view.getContext(), ComparisonPage.class);
                    i.putExtra("item_name", moviesList.get(getAdapterPosition()).name);

                    while(j<f.getProductList().size()) {
                        if (containsWordsArray(f.getProductList().get(j).productDesc,moviesList.get(getAdapterPosition()).name)) {
                            Log.d("flipname" , f.getProductList().get(j).name);
                            if(moviesList.get(getAdapterPosition()).website.equalsIgnoreCase("flipkart")){
                                i.putExtra("flipkart_obj",moviesList.get(getAdapterPosition()));
                            }else {
                                i.putExtra("flipkart_obj", f.getProductList().get(j));
                            }
                            break;
                        }
                        j++;
                    }

                    j=0;
                    while(j<s.getProductList().size()) {
                        if (containsWordsArray(s.getProductList().get(j).name,moviesList.get(getAdapterPosition()).name)) {
                           Log.d("snapname" , s.getProductList().get(j).name);
                            if(moviesList.get(getAdapterPosition()).website.equalsIgnoreCase("flipkart")){
                                i.putExtra("snapdeal_obj", moviesList.get(getAdapterPosition()));
                            }else {
                                i.putExtra("snapdeal_obj", s.getProductList().get(j));
                            }
                            break;
                        }
                        j++;
                    }

                    view.getContext().startActivity(i);
                }
            }).start();

        }
        @RequiresApi(api = Build.VERSION_CODES.N)
        public  boolean containsWordsArray(String inputString, String words) {
            if(inputString.contains("GB")){
                inputString=inputString.replace("GB"," GB");
            }
            if(words.contains("GB")){
                words=words.replace("GB"," GB");
            }
            List<String> inputStringList = Arrays.asList(inputString.replaceAll("[^a-zA-Z0-9.]"," ").toLowerCase().split(" "));
            List<String> wordsList = Arrays.asList(words.replaceAll("[^a-zA-Z0-9.]"," ").toLowerCase().split(" "));
//            inputStringList.replaceAll(String::trim);
//            wordsList.replaceAll(String::trim);
            Log.d("input",inputStringList.toString());
            Log.d("words",wordsList.toString());
            return inputStringList.containsAll(wordsList);
        }
    }
}

