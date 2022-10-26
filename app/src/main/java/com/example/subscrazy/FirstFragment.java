package com.example.subscrazy;

import android.net.IpSecManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.subscrazy.databinding.FragmentFirstBinding;

import java.util.ArrayList;

public class FirstFragment extends Fragment {

    private ArrayList<Subscription> subscriptionArrayList;
    private DBHandler dbHandler;
    private SubscriptionRVAdapter subscriptionRVAdapter;
    private RecyclerView subscriptionRV;

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        subscriptionArrayList = new ArrayList<>();
        dbHandler = new DBHandler(this.getContext());

        subscriptionArrayList = dbHandler.readSubscriptions();

        subscriptionRVAdapter = new SubscriptionRVAdapter(subscriptionArrayList, this.getContext());
        subscriptionRV = view.findViewById(R.id.idRVSubscriptions);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(),
                RecyclerView.VERTICAL,
                false);
        subscriptionRV.setLayoutManager(linearLayoutManager);

        subscriptionRV.setAdapter(subscriptionRVAdapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}