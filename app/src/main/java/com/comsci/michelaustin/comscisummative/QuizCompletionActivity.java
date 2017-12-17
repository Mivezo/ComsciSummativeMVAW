package com.comsci.michelaustin.comscisummative;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuizCompletionActivity extends AppCompatActivity {

    private Button menuButton;
    private TextView correctAnswerTextView;
    private int amountCorrect;
    private int totalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_completion);

        menuButton = (Button) findViewById(R.id.menuButton);
        correctAnswerTextView = (TextView) findViewById(R.id.correctAnswerTextView);

        amountCorrect = getIntent().getIntExtra("AMOUNT_CORRECT",0);
        totalAmount = getIntent().getIntExtra("TOTAL_AMOUNT", 0);

        correctAnswerTextView.setText("You got "+amountCorrect+" correct out of "+totalAmount+".");

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplication(), menuopening.class);
                startActivity(startIntent);
            }
        });


    }
}