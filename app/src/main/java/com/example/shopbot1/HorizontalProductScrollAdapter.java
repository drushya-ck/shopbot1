package com.example.shopbot1;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


import com.squareup.picasso.Picasso;

public class HorizontalProductScrollAdapter extends RecyclerView.Adapter<HorizontalProductScrollAdapter.ViewHolder> {

    List<HorizontalProductScrollModel> horizontalProductScrollModelList;

    public HorizontalProductScrollAdapter(List<HorizontalProductScrollModel> horizontalProductScrollModelList){
        this.horizontalProductScrollModelList=horizontalProductScrollModelList;
    }


    @NonNull
    @Override
    public HorizontalProductScrollAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalProductScrollAdapter.ViewHolder holder, int position) {
        if(!horizontalProductScrollModelList.get(position).getProductImage().equals("")) {
            Picasso.get()
                    .load(horizontalProductScrollModelList.get(position).getProductImage())
                    .into(holder.productImage);
        }
//        holder.setProductImage(horizontalProductScrollModelList.get(position).getProductImage());
        holder.setProductTitle(horizontalProductScrollModelList.get(position).getProductTitle());
        holder.setProductPrice(horizontalProductScrollModelList.get(position).getProductPrice());
    }

    @Override
    public int getItemCount() {
        return horizontalProductScrollModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productTitle,productPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage=itemView.findViewById(R.id.hs_pimg);
            productPrice=itemView.findViewById(R.id.hs_pp);
            productTitle=itemView.findViewById(R.id.hs_pt);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Clicked -> " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                    Intent browserIntent = null;
                    if (!horizontalProductScrollModelList.get(getAdapterPosition()).productUrl.isEmpty()) {
                        browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(horizontalProductScrollModelList.get(getAdapterPosition()).productUrl));
                    }
                    v.getContext().startActivity(browserIntent);
                }
            });
        }

        public void setProductImage(String resource) {

//            productImage.setImageResource(resource);
        }

        public void setProductTitle(String title) {
           productTitle.setText(title);
        }

        public void setProductPrice(String price) {
            productPrice.setText(price);
        }
    }
}
