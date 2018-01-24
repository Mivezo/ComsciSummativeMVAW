package com.comsci.michelaustin.comscisummative;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Austin on 2018-01-15.
 */

public class ItemAdapter extends BaseAdapter{

    private ArrayList<String> testResults = new ArrayList<>();//arraylist for past test results
    Context context;
    private TestResult testResult;//object for calling the test results

    public ItemAdapter(Context c){
        this.context = c;
        testResult = new TestResult(context);

        testResults = testResult.getTestResults();
        testResults.remove(0);
    }

    @Override
    public int getCount() {
        return testResults.size();
    }

    @Override
    public Object getItem(int i) {
        return testResults.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView txtView = new TextView(context);

        //depending on whether the result is above or below 80%, the text is displayed green or red
        if(Integer.parseInt(testResults.get(i))>=80){
            txtView.setTextColor(Color.GREEN);
        }
        else{
            txtView.setTextColor(Color.RED);
        }

        txtView.setEnabled(false);

        txtView.setText("Test Result "+(i+1)+": "+testResults.get(i)+"%");
        txtView.setPadding(100,0,50,0);
        txtView.setTextSize(20);

        return txtView;
    }
}
