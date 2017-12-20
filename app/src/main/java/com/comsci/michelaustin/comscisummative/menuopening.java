package com.comsci.michelaustin.comscisummative;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

public class menuopening extends AppCompatActivity {

    String name = MainActivity.userName;
    ImageButton module1Button, module2Button, module3Button, module4Button, module5Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuopening);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        fileIo.writeFile(name,"lifeguardname.txt",this);
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.turn);

        TextView nameshow = (TextView) findViewById(R.id.menuname);
        nameshow.setText("Welcome " + name+ "!");

        TextView welcome = (TextView) findViewById(R.id.welcome);
        welcome.setText("Please pick a\nModule:");

        (module1Button = findViewById(R.id.module1)).startAnimation(shake);
        (module2Button = findViewById(R.id.module2)).startAnimation(shake);
        (module3Button = findViewById(R.id.module3)).startAnimation(shake);
        (module4Button = findViewById(R.id.module4)).startAnimation(shake);
        (module5Button = findViewById(R.id.module5)).startAnimation(shake);

        module1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                module2Button.setEnabled(false);
                module3Button.setEnabled(false);
                module4Button.setEnabled(false);
                module5Button.setEnabled(false);
                Intent startQuiz = new Intent(getApplicationContext(), QuizMenuActivity.class);
                startQuiz.putExtra("MODULE_ID",1);
                startActivity(startQuiz);
            }
        });

        module2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                module1Button.setEnabled(false);
                module3Button.setEnabled(false);
                module4Button.setEnabled(false);
                module5Button.setEnabled(false);
                Intent startQuiz = new Intent(getApplicationContext(), QuizMenuActivity.class);
                startQuiz.putExtra("MODULE_ID",2);
                startActivity(startQuiz);
            }
        });

        module3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                module1Button.setEnabled(false);
                module2Button.setEnabled(false);
                module4Button.setEnabled(false);
                module5Button.setEnabled(false);
                Intent startQuiz = new Intent(getApplicationContext(), QuizMenuActivity.class);
                startQuiz.putExtra("MODULE_ID",3);
                startActivity(startQuiz);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent switchpanel = new Intent(getApplicationContext(), mainMenu.class);
        startActivity(switchpanel);
    }

}

