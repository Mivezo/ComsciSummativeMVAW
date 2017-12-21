package com.comsci.michelaustin.comscisummative;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TestCompletionScreen extends AppCompatActivity {

    ExpandableListView expandableListView;

    private TextView passText;

    private ArrayList<String> userResult = new ArrayList<String>();
    private ArrayList<String> testCorrectAnswers = new ArrayList<String>();
    private ArrayList<String> testQuestions = new ArrayList<String>();
    private boolean pass;
    private int passPercent;

    private QuestionLibrary mQuestionibrary;

    private int questionRight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_completion_screen);

        testQuestions = getIntent().getStringArrayListExtra("TEST_QUESTION_ARRAY");
        testCorrectAnswers = getIntent().getStringArrayListExtra("TEST_CORRECT_ANSWER_ARRAY");
        userResult = getIntent().getStringArrayListExtra("TEST_RESULT_ARRAY");

        mQuestionibrary = new QuestionLibrary(getApplicationContext());
        test();
        questionRight=20;

        //buildTestResult();
        testAnswer();
        testIfPassed();

        passText = (TextView) findViewById(R.id.passText);
        passText.setText("You have completed the test!");



    }

    private void buildTestResult(){

            BufferedReader reader;

            try{
                InputStream inputStream = getApplicationContext().openFileInput("testResult.txt");
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line = reader.readLine();
                while(line != null){
                    userResult.add(line);
                    line=reader.readLine();
                }
            } catch(IOException ioe){
                ioe.printStackTrace();
            }
    }

    private void testAnswer(){
        for(int i=0; i<20; i++){
            Log.d("Test", testCorrectAnswers.get(i)+"");

            if(!userResult.get(i).equals(testCorrectAnswers.get(i))){
                questionRight--;
            }
        }
    }

    private void test(){
        for(int i = 0; i<testCorrectAnswers.size(); i++){
            Log.d("CorrectAnswer: "+i, testCorrectAnswers.get(i));
            Log.d("UserChoice: "+i, userResult.get(i));
        }
    }

    private void testIfPassed(){
        double temp = (double) questionRight/(double)20;
        double percent = temp*100;

        if(percent>=80){
            pass=true;
        }
        else{
            pass=false;
        }

        passPercent = (int) Math.round(percent);

    }
}
