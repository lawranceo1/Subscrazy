package com.example.subscrazy;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SubscriptionRVAdapter extends RecyclerView.Adapter<SubscriptionRVAdapter.ViewHolder> {

    private ArrayList<Subscription> subscriptionArrayList;
    private Context context;

    public SubscriptionRVAdapter(ArrayList<Subscription> subscriptionArrayList, Context context) {
        this.subscriptionArrayList = subscriptionArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subscription_rv_item,
                parent,
                false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Subscription sub = subscriptionArrayList.get(position);
        holder.subscriptionNameTV.setText(sub.getName());
        holder.subscriptionPriceTV.setText(sub.getPayment());
        holder.subscriptionRecurrenceTV.setText(sub.getRecurrence());
        holder.subscriptionBillingTimeTV.setText(sub.getBillingTime());
        holder.subscriptionNotesTV.setText(sub.getNotes());

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Fragment thirdFragment = new ThirdFragment();
                Bundle bundle = new Bundle();
                bundle.putString("name", sub.getName());
                bundle.putString("price", sub.getPayment());
                bundle.putString("recurrence", sub.getRecurrence());
                bundle.putString("billDate", sub.getBillingTime());
                bundle.putString("notes", sub.getNotes());

                thirdFragment.setArguments(bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subscriptionArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView subscriptionNameTV,
                subscriptionPriceTV,
                subscriptionRecurrenceTV,
                subscriptionBillingTimeTV,
                subscriptionNotesTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subscriptionNameTV = itemView.findViewById(R.id.idSubscriptionName);
            subscriptionPriceTV = itemView.findViewById(R.id.idSubscriptionPrice);
            subscriptionRecurrenceTV = itemView.findViewById(R.id.idSubscriptionRecurrence);
            subscriptionBillingTimeTV = itemView.findViewById(R.id.idSubscriptionBillingTime);
            subscriptionNotesTV = itemView.findViewById(R.id.idSubscriptionNotes);

        }
    }
}
