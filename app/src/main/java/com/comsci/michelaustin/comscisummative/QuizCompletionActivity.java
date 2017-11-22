package com.comsci.michelaustin.comscisummative;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class QuizCompletionActivity extends AppCompatActivity {

    private Button menuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_completion);

        menuButton = (Button) findViewById(R.id.menuButton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplication(), menuopening.class);
                startActivity(startIntent);
            }
        });


    }
}