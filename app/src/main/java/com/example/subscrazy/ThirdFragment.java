package com.example.subscrazy;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.subscrazy.databinding.FragmentThirdBinding;

import java.util.Calendar;


public class ThirdFragment extends Fragment {

    private EditText subNameEdt, priceEdt, dateEdt;
    private Spinner recurrenceSpinner;
    private DBHandler dbHandler;
    private FragmentThirdBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentThirdBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {


        super.onViewCreated(view, savedInstanceState);
        Context thisContext = this.getContext();
        dbHandler = new DBHandler(thisContext);
        recurrenceSpinner = getView().findViewById(R.id.spinner_time);
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                R.array.timeselectarray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        recurrenceSpinner.setAdapter(adapter);

        subNameEdt = getView().findViewById(R.id.editText_name);
        priceEdt = getView().findViewById(R.id.editText_price);
        dateEdt = getView().findViewById(R.id.editText_date);
        dateEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(thisContext,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                dateEdt.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);
                            }
                        },
                        mYear,
                        mMonth,
                        mDay);
                datePickerDialog.show();
            }
        });

        // show sub info from bundle
        subNameEdt.setText(SubscriptionRVAdapter.subname);
        priceEdt.setText(SubscriptionRVAdapter.subpayment);
        dateEdt.setText(SubscriptionRVAdapter.subbillingTime);
        // recurrenceSpinner

        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subName = subNameEdt.getText().toString();
                String price = priceEdt.getText().toString();
                String recurrence = recurrenceSpinner.getSelectedItem().toString();
                String subDate = dateEdt.getText().toString();


                if (subName.isEmpty() ||
                        price.isEmpty() ||
                        recurrence.isEmpty() ||
                        subDate.isEmpty()) {
                    Toast.makeText(ThirdFragment.this.getContext(),
                            "Please fill all fields..", Toast.LENGTH_SHORT).show();
                    return;
                }

                dbHandler.updateSubscription(
                        SubscriptionRVAdapter.subname,
                        subName,
                        price,
                        recurrence,
                        subDate);

                Toast.makeText(ThirdFragment.this.getContext(),
                        "Subscription has been updated..", Toast.LENGTH_SHORT).show();

                subNameEdt.setText("");
                priceEdt.setText("");
                recurrenceSpinner.setAdapter(null);
                dateEdt.setText("");
                NavHostFragment.findNavController(ThirdFragment.this)
                        .navigate(R.id.action_ThirdFragment_to_FirstFragment);
            }
        });

        binding.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subNameEdt.setText("");
                priceEdt.setText("");
                recurrenceSpinner.setAdapter(null);
                dateEdt.setText("");
                dbHandler.deleteSubscription(SubscriptionRVAdapter.subname);
                Toast.makeText(ThirdFragment.this.getContext(),
                        "Subscription has been deleted..", Toast.LENGTH_SHORT).show();
                NavHostFragment.findNavController(ThirdFragment.this)
                        .navigate(R.id.action_ThirdFragment_to_FirstFragment);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}