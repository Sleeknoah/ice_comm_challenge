package com.example.icecommtest.view.adapters;

import android.annotation.SuppressLint;
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
import com.example.icecommtest.model.response.ProductResponse;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    Context context;
    private final List<ProductResponse> dataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView price;
        private final AppCompatImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.productTitle);
            price = itemView.findViewById(R.id.cost);
            imageView = itemView.findViewById(R.id.productImage);
        }
    }

    public ProductAdapter(Context mContext, List<ProductResponse> mDataSet) {
        this.context = mContext;
        this.dataSet = mDataSet;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        String title = dataSet.get(position).getTitle();
        if (title.length() > 12){
            String titleConcat = title.substring(0, 12);
            holder.title.setText(titleConcat +  "...");
        }else {
            holder.title.setText(title);
        }

        holder.price.setText("$" + String.valueOf(dataSet.get(position).getPrice()));
        //Load image into view
        Glide.with(context).
                load(dataSet.get(position).getImage())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
