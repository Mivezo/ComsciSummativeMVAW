package com.comsci.michelaustin.comscisummative;

import android.content.Context;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

/**
 * This class handles the test results when
 * Created by Austin on 2018-01-14.
 */

public class TestResult {

    private Context appcontext;
    private ArrayList<String> testResults = new ArrayList();//arraylist of results

    public TestResult(Context c){
        appcontext = c;
        buildTestResult();
    }

    public void buildTestResult() {

        testResults.add("arbitrary valule");//arbitrary value to be cleared to avoid null pointer
        testResults.clear();

        String strLine = FileIO.readFromFile(appcontext, "testScores.txt");

        int count = StringUtils.countMatches(strLine, ";");


        for(int i=0; i<count; i++){//based on the amount of colons in the line, grabs each substring and stores it into array
            int colon = strLine.indexOf(";");

            String temp = strLine.substring(0,colon);

            testResults.add(temp);

            strLine = strLine.replaceFirst(temp+";","");
        }

    }

    //Returns amount of past test scores
    public int getArraySize(){
        return testResults.size();
    }

    //returns the previous test result
    public int getlastResult(){

        return Integer.parseInt(testResults.get(testResults.size()-1));

    }

    //returns the test results array
    public ArrayList getTestResults(){
        return testResults;
    }

}
