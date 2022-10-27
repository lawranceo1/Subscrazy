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

public class Calculator  extends Fragment
{

    private TextView workingsTV;
    private TextView resultsTV;

    private String workings = "";
    private String formula = "";
    private String tempFormula = "";
    private CalculatorLayoutBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        binding = CalculatorLayoutBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTextViews();
        binding.c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearOnClick(view);
            }
        });
        binding.one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oneOnClick(view);
            }
        });
        binding.two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                twoOnClick(view);
            }
        }); binding.three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                threeOnClick(view);
            }
        }); binding.four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fourOnClick(view);
            }
        }); binding.five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               fiveOnClick(view);
            }
        }); binding.six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sixOnClick(view);
            }
        }); binding.seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sevenOnClick(view);
            }
        }); binding.eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eightOnClick(view);
            }
        }); binding.nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               nineOnClick(view);
            }
        }); binding.zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               zeroOnClick(view);
            }
        }); binding.multiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timesOnClick(view);
            }
        }); binding.divide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                divisionOnClick(view);
            }
        }); binding.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               plusOnClick(view);
            }
        }); binding.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               minusOnClick(view);
            }
        });binding.equals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                equalsOnClick(view);
            }
        });binding.power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              powerOfOnClick(view);
            }
        });binding.bracket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bracketsOnClick(view);
            }
        });

        binding.exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Calculator.this)
                        .navigate(R.id.action_Calculator_to_FirstFragment);
            }
        });

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
        if((c <= '9' && c >= '0') || c == '.')
            return true;

        return false;
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
