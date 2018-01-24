package com.comsci.michelaustin.comscisummative;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * This class displays the completion screen with an expandable listview. It also allows
 * the user to return to the menu after. If the user passsed, they will be able to book a course online.
 */
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

        expandableListView = findViewById(R.id.expandableListView);

        testQuestions = getIntent().getStringArrayListExtra("TEST_QUESTION_ARRAY");
        testCorrectAnswers = getIntent().getStringArrayListExtra("TEST_CORRECT_ANSWER_ARRAY");
        testCorrectAnswersS = getIntent().getStringArrayListExtra("TEST_CORRECT_ANSWER_ARRAY");
        userResult = getIntent().getStringArrayListExtra("TEST_RESULT_ARRAY");
        userResultS = getIntent().getStringArrayListExtra("TEST_RESULT_ARRAY");

        questionRight=20;//gets subtracted everytime user gets something wrong

        testAnswer();
        testIfPassed();

        buildListViewChild();

        Button courseText = findViewById(R.id.courseButton);

        //Depending on whether the user passed or failed, the text displayed will be different
        if(pass){
            courseText.setVisibility(View.VISIBLE);
            verdict = "PASSED!";
        }else{
            courseText.setVisibility(View.GONE);
            verdict = "FAILED!";
        }

        passText = findViewById(R.id.passText);
        passText.setText("You have completed the test!\n Your percentage was "+passPercent+".\n"+"Verdict: "+verdict);

        //When user clicks the button, takes them to lifeguard link
        courseText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("http://www.lifesavingsociety.com/find-a-course.aspx");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });


        //Returns back to module menu
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
        return userResultS.get(i).equals(testCorrectAnswersS.get(i));
    }

    //On back press goes to the menu opening class
    @Override
    public void onBackPressed() {
        Intent change = new Intent(getApplicationContext(), ModuleMenu.class);
        startActivity(change);
    }

}