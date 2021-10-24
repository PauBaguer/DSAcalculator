package edu.upc.dsa.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected TextView textView;
    protected Chip angleChip;

    private boolean isDegree = true;
    private List<String> list = new LinkedList<>();
    private List<String> numList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = this.findViewById(R.id.textView);
        angleChip = this.findViewById(R.id.degree_chip);
    }

    @SuppressLint("SetTextI18n")
    protected void addSymbol(String s){
        if (textView.getText().equals("0")) textView.setText(null);
        textView.setText(textView.getText() + s);
    }

    protected void addNumber(String s){
        addSymbol(s);
        numList.add(s);
    }

    protected void addOp(String s){
        addSymbol(s);
        commitNumber();
        list.add(s);

    }

    protected void commitNumber(){
        if(numList.size()>0) {
            StringBuilder sb = new StringBuilder();
            for (String n : numList) {
                sb.append(n);
            }
            Log.d("OPS", sb.toString());
            list.add(sb.toString());
            numList.clear();
        }
    }

    protected void clear(){
        textView.setText("0");
        list.clear();
        numList.clear();
    }

    protected void back(){
        /*
        if (!textView.getText().equals("0")) {
            String t = (String) textView.getText();
            t = t.substring(0,t.length()-1);
            if(t.equals("")) t = "0";
            String last = t.substring(t.length()-1, t.length());
            Log.d("BACK_SPACE", last);
            if(last.equals("s") || last.equals("n")) t = t.substring(0,t.length()-3);
            if(t.equals("")) t = "0";
            textView.setText(t);

        }
        */
        if(numList.size()>0){
            numList.remove(numList.size()-1);
            if(numList.size() == 0 && list.size() == 0) textView.setText("0");
            else{
                StringBuilder text = new StringBuilder();
                for(String s : list){
                    text.append(s);
                }
                for(String s : numList){
                    text.append(s);
                }
                textView.setText(text);
            }
            return;
        }

        if(list.size()>0){
            list.remove(list.size()-1);
            if (list.size()==0) textView.setText("0");
            else{
                StringBuilder text = new StringBuilder();
                for(String s : list){
                    text.append(s);
                }
                textView.setText(text.toString());
            }
        }

    }

    protected Double solve(){

        try {


            Double currNum1 = null;
            String currOp = null;
            Double currNum2 = null;

            for (String item : list) {
                if (item.equals("+") || item.equals("-") || item.equals("×") || item.equals("÷")) {
                    currOp = item;
                } else {
                    if (currNum1 == null) {
                        currNum1 = Double.parseDouble(item);
                    } else {
                        currNum2 = Double.parseDouble(item);
                        currNum1 = doOp(currNum1, currOp, currNum2);

                        currOp = null;
                        currNum2 = null;
                    }
                }
            }

            return currNum1;
        }catch (NumberFormatException e){
            clear();
            return 0d;
        }
    }

    private Double doOp(Double num1, String op, Double num2) {
        switch (op){
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "×":
                return num1 * num2;
            case "÷":
                return num1 / num2;
            default:
                Log.d("OPS", "Unknown operation");
                return null;
        }

    }

    public void _0_click(View v){
        addNumber(getString(R.string._0));
    }

    public void _1_click(View v){
        addNumber(getString(R.string._1));
    }

    public void _2_click(View v){
        addNumber(getString(R.string._2));
    }

    public void _3_click(View v){
        addNumber(getString(R.string._3));

    }

    public void _4_click(View v){
        addNumber(getString(R.string._4));

    }

    public void _5_click(View v){
        addNumber(getString(R.string._5));

    }

    public void _6_click(View v){
        addNumber(getString(R.string._6));
    }

    public void _7_click(View v){
        addNumber(getString(R.string._7));
    }

    public void _8_click(View v){
        addNumber(getString(R.string._8));
    }

    public void _9_click(View v){
        addNumber(getString(R.string._9));
    }

    public void dot_click(View v){
        addNumber(".");
    }

    public void equal_click(View v){
        commitNumber();
       Double res = solve();
       clear();
       addNumber(res.toString());
    }

    public void sum_click(View v){
        addOp(getString(R.string.sum));
    }

    public void substract_click(View v){
        addOp(getString(R.string.substract));
    }

    public void multiplication_click(View v){
        addOp(getString(R.string.multiplication));
    }

    public void division_click(View v){
        addOp(getString(R.string.division));
    }

    public void sin_click(View v){
        commitNumber();
        Double res = solve();
        clear();
        if(isDegree) res = res * (2 * Math.PI) / 360;
        res = Math.sin(res);
        addNumber(res.toString());


    }

    public void cos_click(View v){
        commitNumber();
        Double res = solve();
        clear();
        if(isDegree) res = res * (2 * Math.PI) / 360;
        res = Math.cos(res);
        addNumber(res.toString());
    }

    public void tan_click(View v){
        commitNumber();
        Double res = solve();
        clear();
        if(isDegree) res = res * (2 * Math.PI) / 360;
        res = Math.tan(res);
        addNumber(res.toString());
    }



    public void clear_click(View v){
        clear();
    }

    public void back_click(View v){
        back();
    }

    public void degree_click(View v){
        isDegree = !isDegree;
        if(isDegree){
            angleChip.setText(R.string.deg);
        }else {
            angleChip.setText(R.string.rad);
        }
    }
}