package com.example.subscrazy;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
//import androidx.preference.ListPreference;
//import androidx.preference.Preference;
//import androidx.preference.PreferenceManager;
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
    private TextView showExpense;
    private TextView expenseOption;
    private ListPreference list;

    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "subscrazyNotifications";
    private NotificationManager mNotificationManager;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        createNotificationChannel();
        return binding.getRoot();

    }

    @SuppressLint("SetTextI18n")
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNotificationManager = (NotificationManager) getActivity().getSystemService(Context
                .NOTIFICATION_SERVICE);

        SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        String str = s.getString("options","");
        expenseOption = getView().findViewById(R.id.expense_option);
        System.out.println("Selected list = "+str);
        expenseOption.setText(str);
        showExpense = getView().findViewById(R.id.showExpense);
        sortMenu = getView().findViewById(R.id.spinner_sort);
        sortMenu.setOnItemSelectedListener(this);
        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);

                createNotification("testing notification");
            }
        });
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_Calculator);
            }
         });

        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                R.array.sortMenu, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortMenu.setAdapter(adapter);

        subscriptionArrayList = new ArrayList<>();
        dbHandler = new DBHandler(this.getContext());

        subscriptionArrayList = dbHandler.readSubscriptions();
       // textView_for_total.setText(""+dbHandler.getTotalSpending());
        showExpense.setText("$"+ Math.round(dbHandler.getRemainingExpense()* 100.0) / 100.0);
        subscriptionRVAdapter = new SubscriptionRVAdapter(subscriptionArrayList,
                this.getContext(),
                this);
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
                } else if (Double.parseDouble(s1.getPayment()) ==
                        Double.parseDouble(s2.getPayment())) {
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

                cal1 = s1.getDate();
                cal2 = s2.getDate();
                return cal1.compareTo(cal2);
            }
        });
    }
    public double getRemainingBudget(double budget, double totalExpense){
        double remainingBudget = budget-totalExpense;
        if(remainingBudget < 0)
            remainingBudget=0;
        return remainingBudget;
    }

//   remainingBudget public double showExpenseTextValue(String option, DBHandler db){
//        if(option.compareTo("Remaining Budget")){
//            return getRemainingBudget();
//        }
//
//    }

private void createNotification(String msg) {

        Context c = this.getContext();
        Intent intent = new Intent(c, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(c, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(c, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Subscrazy")
                .setContentText(msg)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(c);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager =
                    this.getContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
