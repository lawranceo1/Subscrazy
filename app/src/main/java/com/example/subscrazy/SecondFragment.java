package com.example.subscrazy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.subscrazy.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private EditText subNameEdt, priceEdt, dateEdt;
    private Button saveBtn;
    private DBHandler dbHandler;

    private FragmentSecondBinding binding;
    Spinner recurrenceSpinner;



    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbHandler = new DBHandler(this.getContext());

        recurrenceSpinner = getView().findViewById(R.id.spinner_time);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                R.array.timeselectarray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        recurrenceSpinner.setAdapter(adapter);

        subNameEdt = getView().findViewById(R.id.editText_name);
        priceEdt = getView().findViewById(R.id.editText_price);
//        recurrenceSpinner = getView().findViewById(R.id.spinner_time);
        dateEdt = getView().findViewById(R.id.editText_date);


        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String subName = subNameEdt.getText().toString();
                String price = priceEdt.getText().toString();
                String recurrence = recurrenceSpinner.getSelectedItem().toString();
                String subDate = dateEdt.getText().toString();

                if (subName.isEmpty() || price.isEmpty() || recurrence.isEmpty() || subDate.isEmpty()) {
                    Toast.makeText(SecondFragment.this.getContext(), "Please fill all fields..", Toast.LENGTH_SHORT).show();
                    return;
                }

                Subscription sub = new Subscription(subName, price, recurrence, subDate, "");
                dbHandler.addNewSubscription(sub);

                Toast.makeText(SecondFragment.this.getContext(), "Subscription has been added..", Toast.LENGTH_SHORT).show();

                subNameEdt.setText("");
                priceEdt.setText("");
                recurrenceSpinner.setAdapter(null);
                dateEdt.setText("");

//                NavHostFragment.findNavController(SecondFragment.this)
//                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}