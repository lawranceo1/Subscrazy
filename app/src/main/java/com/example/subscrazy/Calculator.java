package com.example.subscrazy;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.subscrazy.databinding.CalculatorLayoutBinding;

import java.util.ArrayList;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

@SuppressWarnings("ALL")
public class Calculator  extends Fragment
{

    private TextView workingsTV;
    private TextView resultsTV;

    private String workings = "";
    private String formula = "";
    private String tempFormula = "";
    private CalculatorLayoutBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull  Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        binding = CalculatorLayoutBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTextViews();
        binding.c.setOnClickListener(view119 -> clearOnClick(view119));
        binding.one.setOnClickListener(view118 -> oneOnClick(view118));
        binding.two.setOnClickListener(view117 -> twoOnClick(view117));
        binding.three.setOnClickListener(view116 -> threeOnClick(view116));
        binding.four.setOnClickListener(view115 -> fourOnClick(view115));
        binding.five.setOnClickListener(view114 -> fiveOnClick(view114));
        binding.six.setOnClickListener(view113 -> sixOnClick(view113));
        binding.seven.setOnClickListener(view112 -> sevenOnClick(view112));
        binding.eight.setOnClickListener(view111 -> eightOnClick(view111));
        binding.nine.setOnClickListener(view110 -> nineOnClick(view110));
        binding.zero.setOnClickListener(view19 -> zeroOnClick(view19));
        binding.multiply.setOnClickListener(view18 -> timesOnClick(view18));
        binding.divide.setOnClickListener(view17 -> divisionOnClick(view17));
        binding.plus.setOnClickListener(view16 -> plusOnClick(view16));

        binding.minus.setOnClickListener(view15 -> minusOnClick(view15));
        binding.equals.setOnClickListener(view14 -> equalsOnClick(view14));
        binding.power.setOnClickListener(view13 -> powerOfOnClick(view13));
        binding.brackets.setOnClickListener(view12 -> bracketsOnClick(view12));
    }

    private void initTextViews()
    {
        workingsTV = (TextView)getView().findViewById(R.id.workingsTextView);
        resultsTV = (TextView)getView().findViewById(R.id.resultTextView);

    }

    private void setWorkings(String givenValue)
    {
        workings = workings + givenValue;
        workingsTV.setText(workings);
    }

    public void exitOnClick(View view){
//        NavHostFragment.findNavController(Calculator.this)
//                .navigate(R.id.action_Calculator_to_FirstFragment);
    }
    public void equalsOnClick(View view)
    {
        Double result = null;
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("rhino");
        checkForPowerOf();

        try {
            result = (double)engine.eval(formula);
        } catch (ScriptException e)
        {
            Toast.makeText(this.getContext(), "Invalid Input", Toast.LENGTH_SHORT).show();
        }

        if(result != null)
            resultsTV.setText(String.valueOf(result.doubleValue()));

    }

    private void checkForPowerOf()
    {
        ArrayList<Integer> indexOfPowers = new ArrayList<>();
        for(int i = 0; i < workings.length(); i++)
        {
            if (workings.charAt(i) == '^')
                indexOfPowers.add(i);
        }

        formula = workings;
        tempFormula = workings;
        for(Integer index: indexOfPowers)
        {
            changeFormula(index);
        }
        formula = tempFormula;
    }

    private void changeFormula(Integer index)
    {
        String numberLeft = "";
        String numberRight = "";

        for(int i = index + 1; i< workings.length(); i++)
        {
            if(isNumeric(workings.charAt(i)))
                numberRight = numberRight + workings.charAt(i);
            else
                break;
        }

        for(int i = index - 1; i >= 0; i--)
        {
            if(isNumeric(workings.charAt(i)))
                numberLeft = numberLeft + workings.charAt(i);
            else
                break;
        }

        String original = numberLeft + "^" + numberRight;
        String changed = "Math.pow("+numberLeft+","+numberRight+")";
        tempFormula = tempFormula.replace(original,changed);
    }

    private boolean isNumeric(char c)
    {
        return (c <= '9' && c >= '0') || c == '.';
    }


    public void clearOnClick(View view)
    {
        workingsTV.setText("");
        workings = "";
        resultsTV.setText("");
        leftBracket = true;
    }

    boolean leftBracket = true;

    public void bracketsOnClick(View view)
    {
        if(leftBracket)
        {
            setWorkings("(");
            leftBracket = false;
        }
        else
        {
            setWorkings(")");
            leftBracket = true;
        }
    }

    public void powerOfOnClick(View view)
    {
        setWorkings("^");
    }

    public void divisionOnClick(View view)
    {
        setWorkings("/");
    }

    public void sevenOnClick(View view)
    {
        setWorkings("7");
    }

    public void eightOnClick(View view)
    {
        setWorkings("8");
    }

    public void nineOnClick(View view)
    {
        setWorkings("9");
    }

    public void timesOnClick(View view)
    {
        setWorkings("*");
    }

    public void fourOnClick(View view)
    {
        setWorkings("4");
    }

    public void fiveOnClick(View view)
    {
        setWorkings("5");
    }

    public void sixOnClick(View view)
    {
        setWorkings("6");
    }

    public void minusOnClick(View view)
    {
        setWorkings("-");
    }

    public void oneOnClick(View view)
    {
        setWorkings("1");
    }

    public void twoOnClick(View view)
    {
        setWorkings("2");
    }

    public void threeOnClick(View view)
    {
        setWorkings("3");
    }

    public void plusOnClick(View view)
    {
        setWorkings("+");
    }

    public void decimalOnClick(View view)
    {
        setWorkings(".");
    }

    public void zeroOnClick(View view)
    {
        setWorkings("0");
    }

}
