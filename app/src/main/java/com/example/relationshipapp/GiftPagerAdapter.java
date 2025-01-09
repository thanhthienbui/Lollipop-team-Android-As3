package com.example.relationshipapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

public class GiftPagerAdapter extends RecyclerView.Adapter<GiftPagerAdapter.GiftViewHolder> {
    private Context context;
    private int[] giftIcons = new int[]{
        R.drawable.ic_gift,
        R.drawable.ic_gift_heart,
        R.drawable.ic_gift_flower,
        R.drawable.ic_gift_ring
    };

    private String[] giftTexts = new String[]{
        "Gift",
        "Heart",
        "Dinner",
        "Rose"
    };

    public GiftPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public GiftViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.gift_pager_item, parent, false);
        return new GiftViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GiftViewHolder holder, int position) {
        final int actualPosition = position % giftIcons.length;
        holder.imageView.setImageResource(giftIcons[actualPosition]);
        holder.textView.setText(giftTexts[actualPosition]);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, GiftDetailActivity.class);
            intent.putExtra("gift_type", actualPosition);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    static class GiftViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        GiftViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.giftImage);
            textView = itemView.findViewById(R.id.giftText);
        }
    }
} 