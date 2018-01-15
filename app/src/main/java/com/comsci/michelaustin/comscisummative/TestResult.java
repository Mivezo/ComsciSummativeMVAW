package com.comsci.michelaustin.comscisummative;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Austin on 2018-01-14.
 */

public class TestResult {

    private Context appcontext;
    private ArrayList<String> testResults = new ArrayList();

    public TestResult(Context c){
        appcontext = c;
    }

    public void buildTestResult() {

        testResults.add("arbitrary valule");
        testResults.clear();

        int count=0;


        try {



            InputStream inputStream = appcontext.openFileInput("testScores.txt");

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(inputStreamReader);

            //String strLine = br.readLine();

            //Read File Line By Line
            /*while (strLine!= null) {
                // Print the content on the console
                count++;
                Log.d("count",count+"");
                testResults.add(strLine);

                strLine=br.readLine();
            }
            */



            for(int i=0; i<4; i++){
                Log.d("count",i+"");


                String strLine=br.readLine();
                testResults.add(strLine);
                //Log.d("Test"+i,strLine);
            }


            //Close the input stream
            br.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }


    }

    public int getArraySize(){
        return testResults.size();
    }

    public int getlastResult(){

        int tempint = Integer.parseInt(testResults.get(testResults.size()-1));

        return tempint;
    }

    public void test(){

        Log.d("testsize", testResults.size()+"");
        /*Log.d("Test0", testResults.get(0));
        Log.d("Test1", testResults.get(1));*/


        for(int i=0; i<testResults.size(); i++ ){
            Log.d("Test", testResults.get(i));
        }
    }

}
