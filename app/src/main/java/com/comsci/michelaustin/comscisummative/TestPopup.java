package com.comsci.michelaustin.comscisummative;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

/**
 * This class is the popup when the user clicks the test.
 * It looks like a dialog even though it is an activity.
 */
public class TestPopup extends Activity {

    TestResult testResult;
    Dialog dialog;

    ListView listView;//listview for displaying past scores


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_popup);

        dialog = new Dialog(this,R.style.testScores);

        TextView viewScores = findViewById(R.id.viewScores);

        testResult = new TestResult(getApplicationContext());//instantiate object

        testResult.buildTestResult();

        TextView scoreText = findViewById(R.id.scoreText);
        //if the user has completed the test once, the user will be able to see past scores
        if(testResult.getArraySize()>1){
            scoreText.setText("Your last score was: "+testResult.getlastResult()+"%");
            viewScores.setVisibility(View.VISIBLE);
        }
        else{
            viewScores.setVisibility(View.GONE);
        }

        //When user clicks on viewscores, the user is able to view the popup
        //dialog which shows the past scores in a listview
        viewScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setContentView(R.layout.view_test_scores);

                //When the user presses ok, the  popup closes
                TextView okTest = dialog.findViewById(R.id.okTest);
                okTest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                listView = dialog.findViewById(R.id.listView);
                ItemAdapter adapter = new ItemAdapter(getApplicationContext());
                listView.setAdapter(adapter);//setting adapter for the listview
                dialog.show();
            }
        });

        //when the user presses next, the test starts
        TextView nextText = findViewById(R.id.nextText);
        nextText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startQuiz = new Intent(getApplicationContext(), QuizMenuActivity.class);
                startQuiz.putExtra("MODULE_ID",6);
                startActivity(startQuiz);
            }
        });

    }
}
