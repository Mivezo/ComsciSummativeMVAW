package com.comsci.michelaustin.comscisummative;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class QuizCompletionActivity extends AppCompatActivity {

    private ImageButton menuButton;
    private TextView completionText;
    private int amountCorrect;
    private int totalAmount;
    private int moduleID;
    private String completionString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_completion);

        menuButton = findViewById(R.id.menuButton);
        completionText = (TextView) findViewById(R.id.completionText);

        amountCorrect = getIntent().getIntExtra("AMOUNT_CORRECT",0);
        totalAmount = getIntent().getIntExtra("TOTAL_AMOUNT", 0);
        moduleID = getIntent().getIntExtra("MODULEID", 0);
        //correctAnswerTextView.setText("You got "+amountCorrect+" correct out of "+totalAmount+".");

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplication(), menuopening.class);
                startActivity(startIntent);
            }
        });

        writeCompletionText();

        fileIo.writeFile("0", "resumeModule"+moduleID+".txt", getApplicationContext());//clears the resume file for the module
    }

    private void writeCompletionText(){
        switch (moduleID){
            case 1: completionString="Shock and Environment Stresses";
                    break;
            case 2: completionString="Anaphylaxis/Obscured Airway";
                break;
            case 3: completionString="Circulatory Emergencies";
                break;
            case 4: completionString="Skin/Bone Injuries & Seizures";
                break;
            case 5: completionString="General info & symptoms";
                break;
        }

        completionText.setText("Congratulations! You have completed the "+completionString+" module!");
    }

    @Override
    public void onBackPressed() {
        Intent change = new Intent(getApplicationContext(), menuopening.class);
        startActivity(change);
    }
}