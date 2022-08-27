package com.example.icecommtest.view.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.icecommtest.R;
import com.example.icecommtest.repositories.local.entity.Cart;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    Context context;
    List<Cart> cartList;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView title;
        private final TextView cost;
        private final ImageView imageView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.cartTitle);
            cost = itemView.findViewById(R.id.cartCost);
            imageView = itemView.findViewById(R.id.cart_image);;
        }
    }

    public CartAdapter(Context context, List<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item, parent, false);
        return new ViewHolder(view);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        String title = cartList.get(position).getTitle();
        if (title.length() > 48){
            String titleConcat = title.substring(0, 48);
            holder.title.setText(titleConcat +  "...");
        }else {
            holder.title.setText(title);
        }

        String decimals = cartList.get(position).getPrice().split("\\.")[1];
        if (decimals.length() == 1){
            holder.cost.setText(cartList.get(position).getPrice() + "0");
        }else{
            holder.cost.setText(cartList.get(position).getPrice());
        }
        //Load image into view
        Glide.with(context).
                load(cartList.get(position).getImage())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }
}
