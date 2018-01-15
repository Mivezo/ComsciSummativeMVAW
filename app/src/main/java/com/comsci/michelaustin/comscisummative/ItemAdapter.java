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

    ArrayList testResults = new ArrayList<>();
    Context context;
    TestResult testResult;

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
        txtView.setText("Test Result "+(i+1)+": "+testResults.get(i)+"%");
        txtView.setTextColor(Color.BLACK);
        txtView.setPadding(100,0,50,0);
        txtView.setTextSize(20);

        return txtView;
    }
}
