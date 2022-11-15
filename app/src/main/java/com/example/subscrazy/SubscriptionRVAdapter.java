package com.example.subscrazy;

import android.content.Context;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class SubscriptionRVAdapter extends RecyclerView.Adapter<SubscriptionRVAdapter.ViewHolder> {

    //can be the key for searching
    public static String subname;
    public static String subpayment;
    public static String subrecurrence;
    public static String subbillingTime;
    public static String subnotes;

    private final ArrayList<Subscription> subscriptionArrayList;
    private final Context context;
    private final Fragment fragment;

    public SubscriptionRVAdapter(ArrayList<Subscription> subscriptionArrayList,
                                 Context context,
                                 Fragment fragment) {
        this.subscriptionArrayList = subscriptionArrayList;
        this.context = context;
        this.fragment = fragment;
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
        String remainingDays = "Renewing in: "+sub.getRemainingDays()+" Days";
        String payment = "$"+sub.getPayment();
        holder.subscriptionNameTV.setText(sub.getName());
        holder.subscriptionPriceTV.setText(payment);
        holder.subscriptionRecurrenceTV.setText(sub.getRecurrence());
        holder.subscriptionBillingTimeTV.setText(remainingDays);
        holder.subscriptionNotesTV.setText(sub.getNotes());

        holder.itemView.setOnClickListener(view -> {

            Bundle bundle = new Bundle();
            Fragment frag = new SecondFragment();
            bundle.putString("name",sub.getName());
            frag.setArguments(bundle);

            subname = sub.getName();
            subpayment = sub.getPayment();
            subrecurrence = sub.getRecurrence();
            subbillingTime = sub.getBillingTime();
            subnotes = sub.getNotes();

            NavHostFragment.findNavController(fragment)
                    .navigate(R.id.action_FirstFragment_to_ThirdFragment);
        });
    }


    @Override
    public int getItemCount() {
        return subscriptionArrayList.size();
    }

    @SuppressWarnings("unused")
    public static class ViewHolder extends RecyclerView.ViewHolder {

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


    public double getRemainingExpense(){
        double result = 0;


        return result;
    }
}
