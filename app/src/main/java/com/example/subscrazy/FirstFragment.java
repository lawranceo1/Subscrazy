package com.example.subscrazy;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.subscrazy.databinding.FragmentFirstBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class FirstFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    private ArrayList<Subscription> subscriptionArrayList;
    private DBHandler dbHandler;
    private SubscriptionRVAdapter subscriptionRVAdapter;
    private RecyclerView subscriptionRV;
    private Spinner sortMenu;
    private FragmentFirstBinding binding;
    private TextView textView_for_total;

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
        textView_for_total = getView().findViewById(R.id.totalPrice);
        sortMenu = getView().findViewById(R.id.spinner_sort);
        sortMenu.setOnItemSelectedListener(this);
        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_Calculator);
            }
         });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                R.array.sortMenu, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortMenu.setAdapter(adapter);

        subscriptionArrayList = new ArrayList<>();
        dbHandler = new DBHandler(this.getContext());

        subscriptionArrayList = dbHandler.readSubscriptions();
        //textView_for_total.setText(""+dbHandler.getTotalSpending());
        textView_for_total.setText("$"+ Math.round(dbHandler.getTotalSpending() * 100.0) / 100.0);
        subscriptionRVAdapter = new SubscriptionRVAdapter(subscriptionArrayList, this.getContext(), this);
        subscriptionRV = view.findViewById(R.id.idRVSubscriptions);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(),
                RecyclerView.VERTICAL,
                false);
        subscriptionRV.setLayoutManager(linearLayoutManager);

        subscriptionRV.setAdapter(subscriptionRVAdapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
       System.out.println("i = "+i);
        if(i == 0){ //selected name from drop down list
            sort_with_Name(subscriptionArrayList);
        }else if(i==1){ //price
            sort_with_price(subscriptionArrayList);
        }else if (i==2){ //Date
            sort_with_Date(subscriptionArrayList);
        }
        subscriptionRVAdapter.notifyDataSetChanged();
    }



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

    public void sort_with_price(ArrayList<Subscription> s ) {
        Collections.sort(s, new Comparator<Subscription>() {
            public int compare(Subscription s1, Subscription s2) {
                int comp;
                if (Double.parseDouble(s1.getPayment()) < Double.parseDouble(s2.getPayment())) {
                    comp = -1;
                } else if (Double.parseDouble(s1.getPayment()) == Double.parseDouble(s2.getPayment())) {
                    comp = 0;
                } else {
                    comp = 1;
                }
                return comp;
            }
        });
    }

    public void sort_with_Name(ArrayList<Subscription> s ) {
        Collections.sort(s, new Comparator<Subscription>() {
            public int compare(Subscription s1, Subscription s2) {
                return s1.getName().compareTo(s2.getName());
            }
        });
    }


    public void sort_with_Date(ArrayList<Subscription> s ) {
        Collections.sort(s, new Comparator<Subscription>() {
            public int compare(Subscription s1, Subscription s2) {
                Calendar cal1 = Calendar.getInstance(); cal1.clear();
                Calendar cal2 = Calendar.getInstance(); cal2.clear();

                cal1.set(s1.getDatePart("YEAR"), s1.getDatePart("MONTH"), s1.getDatePart("DAY"));
                cal2.set(s2.getDatePart("YEAR"), s2.getDatePart("MONTH"), s2.getDatePart("DAY"));
                return cal1.compareTo(cal2);
            }
        });
    }
}
