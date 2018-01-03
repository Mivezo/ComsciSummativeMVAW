package com.comsci.michelaustin.comscisummative;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

public class menuopening extends AppCompatActivity {

    String name = MainActivity.userName;
    ImageButton module1Button, module2Button, module3Button, module4Button, module5Button, module6Button;
    int height, width;

    DisplayMetrics displayMetrics = new DisplayMetrics();

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

        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels/2;
        width = displayMetrics.widthPixels/2;



        (module1Button = findViewById(R.id.module1)).startAnimation(shake);
        (module2Button = findViewById(R.id.module2)).startAnimation(shake);
        (module3Button = findViewById(R.id.module3)).startAnimation(shake);
        (module4Button = findViewById(R.id.module4)).startAnimation(shake);
        (module5Button = findViewById(R.id.module5)).startAnimation(shake);
        module6Button = findViewById(R.id.module6);

        module1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                animateButton(module1Button);

                module2Button.setEnabled(false);
                module3Button.setEnabled(false);
                module4Button.setEnabled(false);
                module5Button.setEnabled(false);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after a certain amount of time
                        Intent startQuiz = new Intent(getApplicationContext(), QuizMenuActivity.class);
                        startQuiz.putExtra("MODULE_ID",1);
                        startActivity(startQuiz);
                    }
                }, 2000);

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

        module6Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                module1Button.setEnabled(false);
                module2Button.setEnabled(false);
                module3Button.setEnabled(false);
                module4Button.setEnabled(false);
                module5Button.setEnabled(false);
                Intent startQuiz = new Intent(getApplicationContext(), QuizMenuActivity.class);
                startQuiz.putExtra("MODULE_ID",6);
                startActivity(startQuiz);
            }
        });
    }

    public void animateButton(ImageButton b) {
        b.clearAnimation();

        ObjectAnimator animation1 = ObjectAnimator.ofFloat(b, "translationY", 0, height - b.getHeight()/2-b.getY());
        animation1.setDuration(1000);

        ObjectAnimator animation2 = ObjectAnimator.ofFloat(b, "translationX", 0, width - b.getWidth()/2-b.getX());
        animation2.setDuration(1000);

        ObjectAnimator animation3 = ObjectAnimator.ofFloat(b, "scaleX", 0, 5);
        animation3.setDuration(1000);

        ObjectAnimator animation4 = ObjectAnimator.ofFloat(b, "scaleY", 0, 5);
        animation4.setDuration(1000);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animation1, animation2,animation3, animation4);
        animatorSet.start();

        int buttonId = b.getId();

        switch(buttonId){
            case R.id.module1:
                module3Button.setEnabled(false);
                module2Button.setEnabled(false);
                module4Button.setEnabled(false);
                module5Button.setEnabled(false);
                module6Button.setEnabled(false);
                fadeButton(module2Button);
                fadeButton(module3Button);
                fadeButton(module4Button);
                fadeButton(module5Button);
                fadeButton(module6Button);
                break;
            case R.id.module2:
                module1Button.setEnabled(false);
                module3Button.setEnabled(false);
                module4Button.setEnabled(false);
                module5Button.setEnabled(false);
                module6Button.setEnabled(false);
                fadeButton(module1Button);
                fadeButton(module3Button);
                fadeButton(module4Button);
                fadeButton(module5Button);
                fadeButton(module6Button);
                break;
            case R.id.module3:
                module1Button.setEnabled(false);
                module2Button.setEnabled(false);
                module4Button.setEnabled(false);
                module5Button.setEnabled(false);
                module6Button.setEnabled(false);
                fadeButton(module2Button);
                fadeButton(module1Button);
                fadeButton(module4Button);
                fadeButton(module5Button);
                fadeButton(module6Button);
                break;
            case R.id.module4:
                module1Button.setEnabled(false);
                module2Button.setEnabled(false);
                module3Button.setEnabled(false);
                module5Button.setEnabled(false);
                module6Button.setEnabled(false);
                fadeButton(module2Button);
                fadeButton(module3Button);
                fadeButton(module1Button);
                fadeButton(module5Button);
                fadeButton(module6Button);
                break;
            case R.id.module5:
                module1Button.setEnabled(false);
                module2Button.setEnabled(false);
                module4Button.setEnabled(false);
                module3Button.setEnabled(false);
                module6Button.setEnabled(false);
                fadeButton(module2Button);
                fadeButton(module3Button);
                fadeButton(module4Button);
                fadeButton(module1Button);
                fadeButton(module6Button);
                break;
            default:


        }
    }

    public void fadeButton(ImageButton b){
        Animation out = new AlphaAnimation(1.0f, 0.0f);
        out.setDuration(1000);

        b.startAnimation(out);
        b.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        Intent switchpanel = new Intent(getApplicationContext(), mainMenu.class);
        startActivity(switchpanel);
    }

}

