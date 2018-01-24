package com.comsci.michelaustin.comscisummative;

import android.content.Intent;
import android.net.Uri;
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

        questionRight=20;

        //buildTestResult();
        testAnswer();//Gen
        testIfPassed();

        buildListViewChild();

        Button courseText = findViewById(R.id.courseButton);

        if(pass){
            courseText.setVisibility(View.VISIBLE);

            verdict = "PASSED!";
        }else{
            courseText.setVisibility(View.GONE);
            verdict = "FAILED!";
        }

        passText = (TextView) findViewById(R.id.passText);
        passText.setText("You have completed the test!\n Your percentage was "+passPercent+".\n"+"Verdict: "+verdict);

        courseText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("http://www.lifesavingsociety.com/find-a-course.aspx");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });


        menuButton = findViewById(R.id.menuButton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent startIntent = new Intent(getApplicationContext(), ModuleMenu.class);
                startActivity(startIntent);
            }
        });

        ExpandableListViewAdapter adapter = new ExpandableListViewAdapter(getApplicationContext(), testQuestions, childNames);
        expandableListView.setAdapter(adapter);
        buildTestResult();
    }


    //Added the score on to the previous test
    private void buildTestResult(){
        FileIO.appendLineFile(passPercent+";", "testScores.txt", this);
        Log.d("updated", FileIO.readFromFile(this,"testScores.txt"));
    }

    /**
     * This Method tests whether the user's results are correct, and writes down how many they got correct
     */
    private void testAnswer(){
        for(int i=0; i<20; i++){
            if(!userResult.get(i).equals(testCorrectAnswers.get(i))){
                questionRight--;
            }
        }
    }

    /**
     * The expandable list view adapter requires arrays for the child view.
     * "Your answer" and "correct answers" are appended before
     */
    private void buildListViewChild(){
        childNames.add(new ArrayList<String>());// clears
        childNames.clear();
        childNames.add(new ArrayList<String>());

        for(int i=0; i<20; i++){
            childNames.get(i).add("Your answer: "+userResult.get(i));
            childNames.get(i).add("Correct answer: "+testCorrectAnswers.get(i));
            childNames.add(new ArrayList<String>());//adds to arraylist
        }
    }

    /**
     * Calculates the percentage of the questions the user got correct. If above 80%, passed
     */
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

    //Returns true or false for each question specifically for the expandable list view adapter
    public static boolean isCorrect(int i){
        if (userResultS.get(i).equals(testCorrectAnswersS.get(i))){
            return true;
        }
        else {
            return false;
        }
    }

    //On back press goes to the menu opening class
    @Override
    public void onBackPressed() {
        Intent change = new Intent(getApplicationContext(), ModuleMenu.class);
        startActivity(change);
    }

}