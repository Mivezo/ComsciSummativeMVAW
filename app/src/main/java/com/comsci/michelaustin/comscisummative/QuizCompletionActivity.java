package com.comsci.michelaustin.comscisummative;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * This class displays the completion screen
 */
public class QuizCompletionActivity extends AppCompatActivity {

    private ImageButton menuButton;
    private TextView completionText;
    private int moduleID;
    private String completionString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_completion);

        menuButton = findViewById(R.id.menuButton);
        completionText = findViewById(R.id.completionText);

        moduleID = getIntent().getIntExtra("MODULEID", 0);


        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplication(), ModuleMenu.class);
                startActivity(startIntent);
            }
        });

        writeCompletionText();

        FileIO.writeFile("0", "resumeModule"+moduleID+".txt", getApplicationContext());//clears the resume file for the module
    }

    //writes different text depending on module
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
            case 5: completionString="General Info & symptoms";
                break;
        }

        completionText.setText("Congratulations! You have completed the "+completionString+" module!");
    }

    @Override
    public void onBackPressed() {
        Intent change = new Intent(getApplicationContext(), ModuleMenu.class);
        startActivity(change);
    }
}