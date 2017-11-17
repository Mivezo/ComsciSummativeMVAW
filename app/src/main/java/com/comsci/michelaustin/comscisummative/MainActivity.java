package com.comsci.michelaustin.comscisummative;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ///for testing quiz
        Button testButton = (Button) findViewById(R.id.testbutton);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //switching to QuizMenuActivity class
                Intent startIntent = new Intent(getApplicationContext(), QuizMenuActivity.class);
                startIntent.putExtra("MODULE_ID",1);
                startActivity(startIntent);

            }
        });

        Button testButton2 = (Button) findViewById(R.id.testButton2);
        testButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), QuizMenuActivity.class);
                startIntent.putExtra("MODULE_ID",2);
                startActivity(startIntent);
            }
        });


    }

}