package com.example.subscrazy;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.subscrazy.databinding.FragmentFirstBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

@SuppressWarnings("unused")
public class FirstFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private ArrayList<Subscription> subscriptionArrayList;
    private DBHandler dbHandler;
    private SubscriptionRVAdapter subscriptionRVAdapter;
    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        subscriptionArrayList = new ArrayList<>();
        dbHandler = new DBHandler(this.getContext());
        subscriptionArrayList = dbHandler.readSubscriptions();
        addListeners();
        showSelectedExpense();
        setSortMenu();
        setRVAdapter(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i == 0) { //selected name from drop down list
            sort_with_Name(subscriptionArrayList);
        } else if (i == 1) { //price
            sort_with_price(subscriptionArrayList);
        } else if (i == 2) { //Date
            sort_with_Date(subscriptionArrayList);
        }
        subscriptionRVAdapter.notifyDataSetChanged();
    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        sort_with_Name(subscriptionArrayList);
        subscriptionRVAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public void setRVAdapter(View view){
        subscriptionRVAdapter = new SubscriptionRVAdapter(subscriptionArrayList,
                this.getContext(),
                this);
        RecyclerView subscriptionRV = view.findViewById(R.id.idRVSubscriptions);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(),
                RecyclerView.VERTICAL,
                false);
        subscriptionRV.setLayoutManager(linearLayoutManager);

        subscriptionRV.setAdapter(subscriptionRVAdapter);
    }

    public void addListeners(){
        binding.buttonFirst.setOnClickListener(view1 -> NavHostFragment.findNavController(FirstFragment.this)
                .navigate(R.id.action_FirstFragment_to_SecondFragment));
        binding.fab.setOnClickListener(view12 -> NavHostFragment.findNavController(FirstFragment.this)
                .navigate(R.id.action_FirstFragment_to_Calculator));

    }

    public void setSortMenu(){
        Spinner sortMenu = requireView().findViewById(R.id.spinner_sort);
        sortMenu.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(requireActivity().getBaseContext(),
                        R.array.sortMenu, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortMenu.setAdapter(adapter);
    }

    public void sort_with_price(ArrayList<Subscription> s) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Collections.sort(s, Comparator.comparingDouble(s2 -> Double.parseDouble(s2.getPayment())));
        }
    }

    public void sort_with_Name(ArrayList<Subscription> s) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Collections.sort(s, Comparator.comparing(Subscription::getName));
        }
    }


    public void sort_with_Date(ArrayList<Subscription> s) {
        Collections.sort(s, (s1, s2) -> {
            Calendar cal1 = Calendar.getInstance();
            cal1.clear();
            Calendar cal2 = Calendar.getInstance();
            cal2.clear();

            cal1 = s1.getDate();
            cal2 = s2.getDate();
            return cal1.compareTo(cal2);
        });
    }


    public double getRemainingBudget(double budget, double totalExpense) {
        double remainingBudget = budget - totalExpense;
        if (remainingBudget < 0)
            remainingBudget = 0;
        return remainingBudget;
    }

    public void showSelectedExpense() {
        DecimalFormat df = new DecimalFormat("#.##");
        TextView showExpense = requireView().findViewById(R.id.showExpense);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this.requireContext());
        String option = pref.getString("options", "Total Expense");
        TextView expenseOption = requireView().findViewById(R.id.expense_option);
        expenseOption.setText(option);
        String output = "$";
        switch (option) {
            case "Remaining Budget":
                double budget = Double.parseDouble(pref.getString("budget", "0.0"));
                output += df.format(getRemainingBudget(budget, dbHandler.getTotalSpending()));
                break;
            case "Remaining Expense":
                output+= df.format(dbHandler.getRemainingExpense());
                break;
            default:
                output += df.format(dbHandler.getTotalSpending());
        }
        showExpense.setText(output);
    }
}
