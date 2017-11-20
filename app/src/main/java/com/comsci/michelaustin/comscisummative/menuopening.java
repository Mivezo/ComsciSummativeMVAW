package com.comsci.michelaustin.comscisummative;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class menuopening extends AppCompatActivity {

    String name = MainActivity.userName;
    ImageButton menuBuoyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuopening);
        fileIo.writeFile(name,this);

        TextView nameshow = (TextView) findViewById(R.id.menuname);
        nameshow.setText(name);

        menuBuoyButton = findViewById(R.id.menuBuoy);

        menuBuoyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startQuiz = new Intent(getApplicationContext(), QuizMenuActivity.class);
                startQuiz.putExtra("MODULE_ID",1);
                startActivity(startQuiz);
            }
        });

    }
}
