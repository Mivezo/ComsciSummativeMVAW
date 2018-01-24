package com.comsci.michelaustin.comscisummative;

import android.content.Context;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

/**
 * Created by Austin on 2018-01-14.
 */

public class TestResult {

    private Context appcontext;
    private ArrayList<String> testResults = new ArrayList();

    public TestResult(Context c){
        appcontext = c;
        buildTestResult();
    }

    public void buildTestResult() {

        testResults.add("arbitrary valule");
        testResults.clear();

        String strLine = FileIO.readFromFile(appcontext, "testScores.txt");

        int count = StringUtils.countMatches(strLine, ";");


        for(int i=0; i<count; i++){
            int colon = strLine.indexOf(";");


            String temp = strLine.substring(0,colon);

            testResults.add(temp);

            strLine = strLine.replaceFirst(temp+";","");
        }

    }

    public int getArraySize(){
        return testResults.size();
    }

    public int getlastResult(){

        int tempint = Integer.parseInt(testResults.get(testResults.size()-1));

        return tempint;
    }

    public ArrayList getTestResults(){
        return testResults;
    }

}
