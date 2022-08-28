package com.example.icecommtest.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.icecommtest.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private final Context mContext;
    private final List<String> dataSet;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClickListener(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mListener = onItemClickListener;
    }
    String[] images = {
            "https://images.unsplash.com/photo-1550009158-9ebf69173e03",
            "https://images.unsplash.com/photo-1608042314453-ae338d80c427",
            "https://images.unsplash.com/photo-1562157873-818bc0726f68",
            "https://images.unsplash.com/photo-1509319117193-57bab727e09d"
    };

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView title;
        private final AppCompatImageView imageView;
        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            title = itemView.findViewById(R.id.categoryTitle);
            imageView = itemView.findViewById(R.id.categoryImage);

            imageView.setOnClickListener(v -> {
                if (listener != null){
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        listener.onItemClickListener(position);
                    }
                }
            });
        }
    }

    public CategoryAdapter(Context mContext, List<String> dataSet) {
        this.mContext = mContext;
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item, parent, false);

        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        holder.title.setText(dataSet.get(position));
        //Load image into view
        Glide.with(mContext).
                load(images[position])
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
