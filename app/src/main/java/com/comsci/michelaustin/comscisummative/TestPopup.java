package com.comsci.michelaustin.comscisummative;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class TestPopup extends Activity {

    TestResult testResult;
    Dialog dialog;

    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_popup);

        dialog = new Dialog(this,R.style.testScores);

        TextView viewScores = findViewById(R.id.viewScores);

        testResult = new TestResult(getApplicationContext());

        testResult.buildTestResult();

        TextView scoreText = findViewById(R.id.scoreText);
        if(testResult.getArraySize()>1){
            scoreText.setText("Your last score was: "+testResult.getlastResult()+"%");
            viewScores.setVisibility(View.VISIBLE);
        }
        else{
            viewScores.setVisibility(View.GONE);
        }




        viewScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setContentView(R.layout.view_test_scores);

                TextView okTest = dialog.findViewById(R.id.okTest);
                okTest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                listView = dialog.findViewById(R.id.listView);
                ItemAdapter adapter = new ItemAdapter(getApplicationContext());
                listView.setAdapter(adapter);
                dialog.show();
            }
        });


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
