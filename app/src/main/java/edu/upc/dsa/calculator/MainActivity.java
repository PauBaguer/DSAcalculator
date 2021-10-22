package edu.upc.dsa.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.chip.Chip;

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
        StringBuilder sb = new StringBuilder();
        for (String n : numList){
            sb.append(n);
        }
        Log.d("OPS", sb.toString());
        list.add(sb.toString());
        list.add(s);
        numList.clear();
    }

    protected void clear(){
        textView.setText("0");
        list.clear();
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

    protected String solve(List<String> list){
        StringBuilder sb = new StringBuilder();
        for (String n : numList){
            sb.append(n);
        }
        Log.d("OPS", sb.toString());
        list.add(sb.toString());

        List<List<String>> opPriority = new LinkedList<>();
        List<String> par = new LinkedList<>();
        par.add("("); par.add("sin("); par.add("cos("); par.add("tan(");
        List<String> mult = new LinkedList<>();
        mult.add("×"); mult.add("÷");
        List<String> sum = new LinkedList<>();
        sum.add("+"); sum.add("-");
        opPriority.add(mult); opPriority.add(sum);


        String foundOp = null;
        Integer opPos = null;

        for (int i = 0; i<list.size();i++) {
            if(foundOp == null) {
                for (String op : par) {
                    if (list.get(i).equals(op)) {
                        foundOp = op;
                        opPos = i;
                    }
                }
            }
            else{
                if(list.get(i).equals(")")){
                    List<String> l = list.subList(opPos+1,i);
                    String res = null;
                    if(l.size() > 1){
                        solve(l);
                    }
                    else {
                        switch (foundOp) {
                            case "sin(":
                                res = Double.toString(Math.sin(Double.parseDouble(l.get(0))));
                                break;
                            case "cos(":
                                res = Double.toString(Math.cos(Double.parseDouble(l.get(0))));
                                break;
                            case "tan(":
                                res = Double.toString(Math.tan(Double.parseDouble(l.get(0))));
                                break;
                            default:
                                res = l.get(0);
                                break;
                        }
                    }
                    List<String> li = new LinkedList<>();
                    li.addAll(list.subList(0,opPos));
                    li.add(res);
                    li.addAll(list.subList(i+1, list.size()));
                    if(li.size()==1) return li.get(0);
                    else solve(li);
                }
            }
        }

        for(List<String> ops : opPriority) {
            for (int i = 0; i< list.size(); i++) {
                for(String op : ops){
                    if (list.get(i).equals(op)){
                        String res = null;
                        switch (op){
                            case "×":
                                res = Double.toString(Double.parseDouble(list.get(i-1)) * Double.parseDouble(list.get(i+1)));
                                break;
                            case "÷":
                                res = Double.toString(Double.parseDouble(list.get(i-1)) / Double.parseDouble(list.get(i+1)));
                                break;
                            case "+":
                                res = Double.toString(Double.parseDouble(list.get(i-1)) + Double.parseDouble(list.get(i+1)));
                                break;
                            case "-":
                                res = Double.toString(Double.parseDouble(list.get(i-1)) - Double.parseDouble(list.get(i+1)));
                                break;
                        }
                        List<String> li = new LinkedList<>();
                        li.addAll(list.subList(0, i-1));
                        li.add(res);
                        if(i+2< list.size())
                        li.addAll(list.subList(i+2, list.size()-1));
                        if(li.size()==1) return li.get(0);
                        else solve(li);
                    }
                }

            }
        }
        return "";

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
       String res = solve(this.list);
       clear();
       addNumber(res);
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
        addOp(getString(R.string.sin) + "(");
    }

    public void cos_click(View v){
        addOp(getString(R.string.cos) + "(");
    }

    public void tan_click(View v){
        addOp(getString(R.string.tan) + "(");
    }

    public void open_parentesis_click(View v){
        addOp(getString(R.string.open_parentesis));
    }

    public void close_parentesis_click(View v){
        addOp(getString(R.string.close_parentesis));
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