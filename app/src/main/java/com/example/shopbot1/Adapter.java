package com.example.shopbot1;

import android.content.Context;
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

import java.util.List;

import static com.squareup.picasso.Picasso.get;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    List<String> titles;
    List<String> images;
    List<String> dealsProductUrl;
    LayoutInflater inflater;

    public Adapter(Context ctx, List<String> titles, List<String> images, List<String> dealsProductUrl){
        this.titles = titles;
        this.images = images;
        this.dealsProductUrl=dealsProductUrl;
        this.inflater = LayoutInflater.from(ctx);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_grid_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(titles.get(position));
//        holder.gridIcon.setImageResource(images.get(position));
        if(!images.get(position).equals("")) {
            get()
                    .load(images.get(position))
                    .into(holder.gridIcon);
        }
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageView gridIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView2);
            gridIcon = itemView.findViewById(R.id.imageView2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Clicked -> " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                    Intent browserIntent = null;
                    if (!dealsProductUrl.get(getAdapterPosition()).isEmpty()) {
                        browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(dealsProductUrl.get(getAdapterPosition())));
                    }
                    v.getContext().startActivity(browserIntent);
                }
            });
        }
    }
}
