package com.comsci.michelaustin.comscisummative;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * This class deals with the expandable list view in the test completion screen. It populates
 * the listview with information given in arrays.
 * Created by Austin on 2017-12-21.
 */

public class ExpandableListViewAdapter extends BaseExpandableListAdapter{

    ArrayList<String> groupNames = new ArrayList<String>();//array list of questions
    ArrayList<ArrayList<String>> childNames = new ArrayList<ArrayList<String>>();//array list of the child names (eg: User's answer and correct answers)

    Context context;

    public ExpandableListViewAdapter(Context c, ArrayList<String> gn, ArrayList<ArrayList<String>> cn){
        this.context = c;
        groupNames = (ArrayList<String>) gn.clone();//cloning arrays for use with expandable list view
        childNames = (ArrayList<ArrayList<String>>) cn.clone();

    }

    @Override
    public int getGroupCount() {
        return groupNames.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return childNames.get(i).size();
    }

    @Override
    public Object getGroup(int i) {
        return groupNames.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return childNames.get(i).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {

        TextView txtView = new TextView(context);
        txtView.setText((i+1)+"."+groupNames.get(i));
        txtView.setPadding(100,0,50,0);
        txtView.setTextColor(Color.WHITE);
        //based on whether the user got a question correct, the text is displayed either green or red.
        if (TestCompletionScreen.isCorrect(i)){
            txtView.setBackgroundColor(ContextCompat.getColor(context, R.color.testgreen)); //without theme);
        }
        else{
            txtView.setBackgroundColor(ContextCompat.getColor(context, R.color.testred));
        }
        txtView.setTextSize(17);
        return txtView;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        TextView txtView = new TextView(context);
        txtView.setBackgroundColor(Color.WHITE);
        txtView.setText(childNames.get(i).get(i1));
        txtView.setPadding(100,0,50,0);
        txtView.setTextColor(Color.BLACK);
        txtView.setTextSize(15);
        return txtView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}