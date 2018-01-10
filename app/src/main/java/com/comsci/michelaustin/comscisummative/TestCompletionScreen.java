package com.comsci.michelaustin.comscisummative;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;

public class TestCompletionScreen extends AppCompatActivity {

    ExpandableListView expandableListView;

    private TextView passText;

    private ArrayList<String> userResult = new ArrayList<String>();
    private ArrayList<String> testCorrectAnswers = new ArrayList<String>();
    private static ArrayList<String> userResultS = new ArrayList<String>();
    private static ArrayList<String> testCorrectAnswersS = new ArrayList<String>();
    private ArrayList<String> testQuestions = new ArrayList<String>();
    private boolean pass;
    private int passPercent;

    String verdict;

    private  ArrayList<ArrayList<String>> childNames = new ArrayList<ArrayList<String>>();

    private QuestionLibrary mQuestionibrary;

    private int questionRight;

    Button menuButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_completion_screen);

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);

        testQuestions = getIntent().getStringArrayListExtra("TEST_QUESTION_ARRAY");
        testCorrectAnswers = getIntent().getStringArrayListExtra("TEST_CORRECT_ANSWER_ARRAY");
        testCorrectAnswersS = getIntent().getStringArrayListExtra("TEST_CORRECT_ANSWER_ARRAY");
        userResult = getIntent().getStringArrayListExtra("TEST_RESULT_ARRAY");
        userResultS = getIntent().getStringArrayListExtra("TEST_RESULT_ARRAY");

        mQuestionibrary = new QuestionLibrary(getApplicationContext());
        questionRight=20;

        //buildTestResult();
        testAnswer();
        testIfPassed();

        buildListViewChild();

        if(pass){
            verdict = "PASSED!";
        }else{
            verdict = "FAILED!";
        }

        passText = (TextView) findViewById(R.id.passText);
        passText.setText("You have completed the test!\n Your percentage was "+passPercent+".\n"+"Verdict: "+verdict);

        menuButton = (Button) findViewById(R.id.menuButton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildTestResult();
                Intent startIntent = new Intent(getApplicationContext(), menuopening.class);
                startActivity(startIntent);
            }
        });

        ExpandableListViewAdapter adapter = new ExpandableListViewAdapter(getApplicationContext(), testQuestions, childNames);
        expandableListView.setAdapter(adapter);
    }

    private void buildTestResult(){

        fileIo.appendLineFile(passPercent+"", "testScores.txt", this);
    }

    private void testAnswer(){
        for(int i=0; i<20; i++){
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

    private void buildListViewChild(){
        childNames.add(new ArrayList<String>());
        childNames.clear();
        childNames.add(new ArrayList<String>());

        for(int i=0; i<20; i++){
            childNames.get(i).add("Your answer: "+userResult.get(i));
            childNames.get(i).add("Correct answer: "+testCorrectAnswers.get(i));
            childNames.add(new ArrayList<String>());
        }
        childNames.remove(19);
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

    public static boolean isCorrect(int i){
        if (userResultS.get(i).equals(testCorrectAnswersS.get(i))){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        Intent change = new Intent(getApplicationContext(), menuopening.class);
        startActivity(change);
    }

}