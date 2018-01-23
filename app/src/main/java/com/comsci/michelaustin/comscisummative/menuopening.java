package com.comsci.michelaustin.comscisummative;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class menuopening extends AppCompatActivity{

    String name = MainActivity.userName;
    private ImageButton module1Button, module2Button, module3Button, module4Button, module5Button, module6Button;
    private int height, width;

    private String resumeModule;
    private boolean resumeState;

    private AnimatorSet animatorSet;

    private ArrayList <ImageButton> buttons = new ArrayList<>();
    private ArrayList <TextView> textViews = new ArrayList();

    private int currentModule;
    private ImageButton currentButton;
    private TextView currentText;

    private TextView nameshow, welcome, mod1, mod2, mod3, mod4, mod5;

    private float buttonY, buttonX, centerY, centerX;

    private Animation shake;

    private boolean fade=true;

    private Dialog menupopup, testpopup;

    private Animation in;

    /*TestResult testResult;*/

    DisplayMetrics displayMetrics = new DisplayMetrics();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //overridePendingTransition( R.anim.fadein, R.anim.fadeout );
        setContentView(R.layout.activity_menuopening);

        /*testResult = new TestResult(getApplicationContext());*/

        currentModule = 0;

        in = new AlphaAnimation(0.0f, 1.0f);
        in.setDuration(500);

        if(MainActivity.first==1){
            fileIo.writeFile(name,"lifeguardname.txt",this);
        }

        shake = AnimationUtils.loadAnimation(this, R.anim.turn);

        menupopup = new Dialog(this,R.style.PauseDialog);
        testpopup = new Dialog(this);

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

        textViews.add(mod1);
        textViews.add(mod2);
        textViews.add(mod3);
        textViews.add(mod4);
        textViews.add(mod5);

        for(int i=0; i<5; i++){
            final int tempmodnum = i;
            buttons.get(i).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    currentModule = tempmodnum;
                    resumeModule = "resumeModule"+(currentModule+1)+".txt";

                    resumeModule();

                    animateButton(buttons.get(tempmodnum), textViews.get(tempmodnum));
                    currentButton = buttons.get(tempmodnum);
                    currentText = textViews.get(tempmodnum);

                }
            });
        }


        module6Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTestPopup();
                /*module1Button.setEnabled(false);
                module2Button.setEnabled(false);
                module3Button.setEnabled(false);
                module4Button.setEnabled(false);
                module5Button.setEnabled(false);*/

            }
        });


        menupopup.setOnCancelListener(new DialogInterface.OnCancelListener() {
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

    private void animateButton(ImageButton button, TextView tutton) {

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
                fadeText(textViews.get(i));
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

    private void showMenuPopup(){
        menupopup.setContentView(R.layout.menu_popup);

        ImageView restartIcon = menupopup.findViewById(R.id.restartIcon);

        restartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle b = new Bundle();
                b.putInt("ID", currentModule);

               /* DialogFragment newFragment = new DialogFragment();*/

                DialogFragment newFragment = PropDialogFragment.newInstance();
                newFragment.setArguments(b);

                showDialog(newFragment);

            }
        });



        ImageView modulePic = menupopup.findViewById(R.id.modulePic);

        //Sets
        switch (currentModule+1){
            case 1:
                modulePic.setImageResource(R.drawable.module1pic);
                break;
            case 2:
                modulePic.setImageResource(R.drawable.module2pic);
                break;
            case 3:
                modulePic.setImageResource(R.drawable.module3pic);
                modulePic.getLayoutParams().height = (int) getResources().getDimension(R.dimen.imageview_height);
                modulePic.getLayoutParams().width = (int) getResources().getDimension(R.dimen.imageview_width);
                break;
            case 4:
                modulePic.setImageResource(R.drawable.module4pic);
                break;
            case 5:
                modulePic.setImageResource(R.drawable.module5pic);
                break;
        }


        TextView resumeText = menupopup.findViewById(R.id.resumeText);
        if(!resumeState){
            resumeText.setText("Play");
        }
        else{
            restartIcon.setVisibility(View.VISIBLE);
            resumeText.setText("Resume");
            resumeText.setTextSize((int) getResources().getDimension(R.dimen.textSize));
        }

        ImageButton play  =  menupopup.findViewById(R.id.nextButton);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menupopup.dismiss();
                displayQuiz();
            }
        });
        menupopup.show();
    }

    private void showTestPopup(){

        Intent startQuiz = new Intent(getApplicationContext(), TestPopup.class);
        startActivity(startQuiz);
    }

    private void reverseButtonAnimation(ImageButton b, TextView t){

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
                fadeText(textViews.get(i));
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

    private void displayQuiz(){
        Intent startQuiz = new Intent(getApplicationContext(), QuizMenuActivity.class);
        startQuiz.putExtra("MODULE_ID",currentModule+1);
        startActivity(startQuiz);
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

    private void dialogBackPressed(){
        /*//menupopup.dismiss();

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
        }*/

        reverseButtonAnimation(buttons.get(currentModule), textViews.get(currentModule) );
    }

    private void animback(){
       // currentText.setVisibility(View.INVISIBLE);
        menupopup.dismiss();
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

    private void showDialog(DialogFragment dialog) {
        dialog.show(getFragmentManager(),"tag");
    }
}

