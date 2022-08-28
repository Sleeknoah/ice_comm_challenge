package com.example.icecommtest.view.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.icecommtest.R;
import com.example.icecommtest.model.response.ProductResponse;
import com.example.icecommtest.view.activities.DetailsActivity;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    Context context;
    private final List<ProductResponse> dataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView price;
        private final AppCompatImageView imageView;
        private final ConstraintLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.productTitle);
            price = itemView.findViewById(R.id.cost);
            imageView = itemView.findViewById(R.id.productImage);
            layout    = itemView.findViewById(R.id.item);
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

        String decimals = String.valueOf(dataSet.get(position).getPrice()).split("\\.")[1];
        String cost = null;
        if (decimals.length() == 1){
            cost = "$" +dataSet.get(position).getPrice() + "0";
        }else{
            cost = "$" +dataSet.get(position).getPrice();
        }
        holder.price.setText(cost);
        //Load image into view
        Glide.with(context).
                load(dataSet.get(position).getImage())
                .into(holder.imageView);

        //Send details of selected item to details screen(class) here

        holder.layout.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("title", dataSet.get(holder.getAdapterPosition()).getTitle());
            intent.putExtra("desc", dataSet.get(holder.getAdapterPosition()).getDescription());
            intent.putExtra("cost", "$" + dataSet.get(holder.getAdapterPosition()).getPrice());
            intent.putExtra("image", dataSet.get(holder.getAdapterPosition()).getImage());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
