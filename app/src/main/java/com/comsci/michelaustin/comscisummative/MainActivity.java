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
                startActivity(startIntent);

            }
        });

        dialog = new Dialog(this);


    }


    /*public void showPopup(View v) {
        Log.d("Test", "test");
        TextView nextButton;
        TextView explanationLabel;
        TextView explanationText;
        dialog.setContentView(R.layout.custompopup);

        explanationLabel = (TextView) dialog.findViewById(R.id.explanationLabel);
        nextButton = (TextView) dialog.findViewById(R.id.nextText);
        explanationText = (TextView) dialog.findViewById(R.id.explanationText);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }*/
}