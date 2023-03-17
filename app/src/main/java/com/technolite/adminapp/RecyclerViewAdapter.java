package com.technolite.adminapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<CardViewItem> cardViewItems;
    Context context;
    RequestOptions option;
    private Activity activity;

    public RecyclerViewAdapter(Context context, List<CardViewItem> cardViewItems,Activity activity) {
        this.context = context;
        this.cardViewItems = cardViewItems;
        this.activity = activity;

        this.option = new RequestOptions().centerCrop().placeholder(R.drawable.design1).error(R.drawable.design1);
    }
    public List<CardViewItem> getCardViewItems() {
        return cardViewItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CardViewItem cardViewItem = cardViewItems.get(position);
        holder.titleTextView.setText(cardViewItem.getTitle());
        Glide.with(context).load(cardViewItems.get(position).getImage()).fitCenter().apply(option).into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, View_customer.class);
                intent.putExtra("title", cardViewItem.getTitle());
                intent.putExtra("imageUrl", cardViewItem.getImage());
                activity.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return cardViewItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public ImageView imageView;

        private CardView cardView;

        public ViewHolder(View view) {
            super(view);
            titleTextView = view.findViewById(R.id.title_text_view);
            imageView = view.findViewById(R.id.image_view);
            cardView=view.findViewById(R.id.cardview);
        }
    }

    public void addCardViewItem(CardViewItem cardViewItem) {
        cardViewItems.add(cardViewItem);
        notifyDataSetChanged();
    }
}
