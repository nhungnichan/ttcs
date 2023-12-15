package com.example.duan_client.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.duan_client.R;
import com.example.duan_client.model.Photo;

public class SlideViewPager2Adapter extends RecyclerView.Adapter<SlideViewPager2Adapter.PhotoViewHolder> {
    List<Photo> photos;

    public SlideViewPager2Adapter(List<Photo> photos) {
        this.photos = photos;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PhotoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slide,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Photo photo = photos.get(position);
        if (photo == null)
            return;
        holder.imgSlide.setImageResource(photo.getResourceId());
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView imgSlide;
        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSlide = itemView.findViewById(R.id.imgSlide);
        }
    }
}
