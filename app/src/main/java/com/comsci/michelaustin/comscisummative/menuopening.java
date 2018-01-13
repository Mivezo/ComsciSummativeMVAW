package com.comsci.michelaustin.comscisummative;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.DialogInterface;
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

import java.util.ArrayList;

public class menuopening extends AppCompatActivity {

    String name = MainActivity.userName;
    ImageButton module1Button, module2Button, module3Button, module4Button, module5Button, module6Button;
    int height, width;

    private boolean isAnimationStarted;

    String resumeModule;
    boolean resumeState;

    AnimatorSet animatorSet;

    ArrayList <ImageButton> buttons = new ArrayList<>();
    ArrayList <TextView> textview = new ArrayList();

    int currentModule;
    ImageButton currentButton;
    TextView currentText;

    TextView nameshow, welcome, mod1, mod2, mod3, mod4, mod5;

    float buttonY, buttonX, centerY, centerX;

    Animation shake;

    boolean fade=true;

    private Dialog dialog;

    Animation in;

    DisplayMetrics displayMetrics = new DisplayMetrics();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuopening);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        currentModule = 0;

        in = new AlphaAnimation(0.0f, 1.0f);
        in.setDuration(500);

        fileIo.writeFile(name,"lifeguardname.txt",this);
        shake = AnimationUtils.loadAnimation(this, R.anim.turn);

        dialog = new Dialog(this,R.style.PauseDialog);

        nameshow = (TextView) findViewById(R.id.menuname);
        nameshow.setText("Welcome " + name+ "!");

        welcome = (TextView) findViewById(R.id.welcome);
        welcome.setText("Please pick a\nModule:");

        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels/2;
        width = displayMetrics.widthPixels/2;

        mod1 = findViewById(R.id.mod1);
        mod2 = findViewById(R.id.mod2);
        mod3 = findViewById(R.id.mod3);
        mod4 = findViewById(R.id.mod4);
        mod5 = findViewById(R.id.mod5);


        /*module1Button = findViewById(R.id.module1);
        module2Button = findViewById(R.id.module2);
        module3Button = findViewById(R.id.module3);
        module4Button = findViewById(R.id.module4);
        module5Button = findViewById(R.id.module5);*/
        (module1Button = findViewById(R.id.module1)).startAnimation(shake);
        (module2Button = findViewById(R.id.module2)).startAnimation(shake);
        (module3Button = findViewById(R.id.module3)).startAnimation(shake);
        (module4Button = findViewById(R.id.module4)).startAnimation(shake);
        (module5Button = findViewById(R.id.module5)).startAnimation(shake);
        module6Button = findViewById(R.id.module6);


        /*final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                module1Button.startAnimation(shake);
                module2Button.startAnimation(shake);
                module3Button.startAnimation(shake);
                module4Button.startAnimation(shake);
                module5Button.startAnimation(shake);
            }
        }, 2000);
*/

        buttons.add(module1Button);
        buttons.add(module2Button);
        buttons.add(module3Button);
        buttons.add(module4Button);
        buttons.add(module5Button);

        textview.add(mod1);
        textview.add(mod2);
        textview.add(mod3);
        textview.add(mod4);
        textview.add(mod5);

        for(int i=0; i<5; i++){
            final int tempmodnum = i;
            buttons.get(i).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    currentModule = tempmodnum;
                    resumeModule = "resumeModule"+(currentModule+1)+".txt";

                    resumeModule();

                    animateButton(buttons.get(tempmodnum), textview.get(tempmodnum));
                    currentButton = buttons.get(tempmodnum);
                    currentText = textview.get(tempmodnum);

                }
            });
        }


        /*module1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentModule = 0;
                animateButton(module1Button, mod1);
                currentButton = module1Button;
                currentText = mod1;

            }
        });

        module2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentModule = 1;
                animateButton(module2Button, mod2);
                currentButton = module2Button;
                currentText = mod2;

            }
        });

        module3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentModule = 2;
                animateButton(module3Button, mod3);
                currentButton = module3Button;
                currentText = mod3;

            }
        });

        module4Button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                currentModule = 3;
                animateButton(module4Button, mod4);
                currentButton = module4Button;
                currentText = mod4;

            }
        });*/

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


        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        animback();
                        //currentText.startAnimation(in);
                    }
                }, 500);
            }
        });

    }

    public void animateButton(ImageButton button, TextView tutton) {

        final ImageButton b= button;
        final TextView t = tutton;

        b.setEnabled(false);
        b.clearAnimation();

        buttonY = b.getY();
        buttonX = b.getX();

        centerY = (height - b.getHeight()/2-buttonY);
        centerX = width - b.getWidth()/2-b.getX();

        ObjectAnimator animation1 = ObjectAnimator.ofFloat(b, "translationY", 0, centerY);
        animation1.setDuration(1000);

        ObjectAnimator animation2 = ObjectAnimator.ofFloat(b, "translationX", 0, centerX);
        animation2.setDuration(1000);

        ObjectAnimator animation3 = ObjectAnimator.ofFloat(b, "scaleX", 1, 5);
        animation3.setDuration(1000);

        ObjectAnimator animation4 = ObjectAnimator.ofFloat(b, "scaleY", 1, 5);
        animation4.setDuration(1000);

        ObjectAnimator animation5 = ObjectAnimator.ofFloat(t, "translationY", 0, centerY);
        animation5.setDuration(1000);

        ObjectAnimator animation6 = ObjectAnimator.ofFloat(t, "translationX", 0, centerX);
        animation6.setDuration(1000);

        ObjectAnimator animation7 = ObjectAnimator.ofFloat(t, "scaleX", 1, 4);
        animation7.setDuration(1000);

        ObjectAnimator animation8 = ObjectAnimator.ofFloat(t, "scaleY", 1, 4);
        animation8.setDuration(1000);


        animatorSet = new AnimatorSet();
        animatorSet.playTogether(animation1, animation2,animation3, animation4, animation5, animation6, animation7, animation8);
        animatorSet.start();

        int buttonId = b.getId();

        fadeText(nameshow);
        fadeText(welcome);

        for(int i=0; i<5; i++){
            if(b==buttons.get(i)){
                continue;
            }
            else{
                //buttons.get(i).clearAnimation();
                //buttons.get(i).setRotation(0);
                buttons.get(i).setEnabled(false);
                fadeButton(buttons.get(i));
                fadeText(textview.get(i));
            }
        }

        fadeButton(module6Button);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                for(int i=0; i<5; i++){
                    if(b==buttons.get(i)){
                        continue;
                    }
                    else{
                        buttons.get(i).clearAnimation();
                    }
                }
                showMenuPopup();

                //dialogBackPressed(b, t);

            }
        }, 1000);



        fade=false;
    }

    public void fadeButton(ImageButton b){

        ObjectAnimator animation1 = ObjectAnimator.ofFloat(b, "alpha", 0f, 1f);
        animation1.setDuration(2000);

        ObjectAnimator animation3 = ObjectAnimator.ofFloat(b, "rotation", -45f, 45f);
        animation3.setDuration(2000);

        AnimatorSet animatorSet = new AnimatorSet();

        Animation out = new AlphaAnimation(1.0f, 0.0f);
        out.setDuration(500);

        if(fade){
            b.startAnimation(out);
        }
        else{
            if(b!=module6Button){
                animatorSet.playTogether(animation1/*, animation3*/);
            }
            else{
                animatorSet.playTogether(animation1);
            }

            animatorSet.start();
        }


        if(fade){
            b.setVisibility(View.INVISIBLE);
        }
        else{
            b.setVisibility(View.VISIBLE);
        }

    }

    public void fadeText(TextView t){

        Animation out;

        if(fade){
            out = new AlphaAnimation(1.0f, 0.0f);
            out.setDuration(500);
        }
        else{
            out = new AlphaAnimation(0.0f, 1.0f);
            out.setDuration(2000);
        }

        t.startAnimation(out);

        if(fade){
            t.setVisibility(View.INVISIBLE);
        }
        else{
            t.setVisibility(View.VISIBLE);
        }

    }

    public void showMenuPopup(){
        dialog.setContentView(R.layout.menu_popup);

        TextView resumeText = dialog.findViewById(R.id.resumeText);
        if(!resumeState){
            resumeText.setText("Play");
        }
        else{
            resumeText.setText("Resume");
        }

        ImageButton play  =  dialog.findViewById(R.id.nextButton);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent startQuiz = new Intent(getApplicationContext(), QuizMenuActivity.class);
                startQuiz.putExtra("MODULE_ID",currentModule+1);
                startActivity(startQuiz);
            }
        });
        dialog.show();
    }

    public void reverseButtonAnimation(ImageButton b, TextView t){

        ObjectAnimator animation1 = ObjectAnimator.ofFloat(b, "translationY",  centerY,0);
        animation1.setDuration(1000);

        ObjectAnimator animation2 = ObjectAnimator.ofFloat(b, "translationX", centerX, 0);
        animation2.setDuration(1000);

        ObjectAnimator animation3 = ObjectAnimator.ofFloat(b, "scaleX", 5, 1);
        animation3.setDuration(1000);

        ObjectAnimator animation4 = ObjectAnimator.ofFloat(b, "scaleY", 5, 1);
        animation4.setDuration(1000);

        ObjectAnimator animation5 = ObjectAnimator.ofFloat(t, "translationY", centerY,0);
        animation5.setDuration(1000);

        ObjectAnimator animation6 = ObjectAnimator.ofFloat(t, "translationX", centerX,0);
        animation6.setDuration(1000);

        ObjectAnimator animation7 = ObjectAnimator.ofFloat(t, "scaleX", 4, 1);
        animation7.setDuration(1000);

        ObjectAnimator animation8 = ObjectAnimator.ofFloat(t, "scaleY", 4, 1);
        animation8.setDuration(1000);


        AnimatorSet animatorSet = new AnimatorSet();

        animatorSet.playTogether(animation1,animation2, animation3, animation4, animation5, animation6, animation7, animation8);
        animatorSet.start();

        int buttonId = b.getId();

        fadeText(nameshow);
        fadeText(welcome);

        for(int i=0; i<5; i++){
            if(b==buttons.get(i)){
                continue;
            }
            else{
                //buttons.get(i).setEnabled(false);
                //buttons.get(i).setRotation(0);
                //buttons.get(i).startAnimation(shake);
                fadeButton(buttons.get(i));
                fadeText(textview.get(i));
            }
        }

        fadeButton(module6Button);

        fade=true;

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                for(int i=0; i<5; i++){
                    //buttons.get(i).setRotation(0);
                    buttons.get(i).startAnimation(shake);
                }
                //currentButton.startAnimation(shake);
                /*module2Button.startAnimation(shake);
                module3Button.startAnimation(shake);
                module4Button.startAnimation(shake);
                module5Button.startAnimation(shake);
*/
                module1Button.setEnabled(true);
                module2Button.setEnabled(true);
                module3Button.setEnabled(true);
                module4Button.setEnabled(true);
                module5Button.setEnabled(true);

            }
        }, 1000);


    }

    @Override
    public void onBackPressed() {

        if (animatorSet!=null){
            if(animatorSet.isRunning()){
                Intent switchpanel = new Intent(getApplicationContext(), menuopening.class);
                startActivity(switchpanel);
            }
            else{
                Intent switchpanel = new Intent(getApplicationContext(), mainMenu.class);
                startActivity(switchpanel);
            }
        }
        else{
            Intent switchpanel = new Intent(getApplicationContext(), mainMenu.class);
            startActivity(switchpanel);
        }

    }

    public void dialogBackPressed(){
        //dialog.dismiss();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 1000);

        for(int i=0; i<5; i++){
            if(currentButton==buttons.get(i)){
                continue;
            }
            else{
                //buttons.get(i).setEnabled(false);
                //buttons.get(i).setRotation(0);

            }
        }

        reverseButtonAnimation(buttons.get(currentModule), textview.get(currentModule) );
    }

    private void animback(){
       // currentText.setVisibility(View.INVISIBLE);
        dialog.dismiss();
        dialogBackPressed();
    }

    private void resumeModule(){

        int temp;
        String line = fileIo.readFromFile(getApplicationContext(), resumeModule);
        temp=Integer.parseInt(line);

        if(temp!=0){
            resumeState=true;
        }else{
            resumeState=false;
        }



    }

}

