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

public class ModuleMenu extends AppCompatActivity{

    String name = MainActivity.userName;
    private ImageButton module1Button, module2Button, module3Button, module4Button, module5Button, module6Button;
    private int scrheight, scrwidth;

    private String resumeModule;
    private boolean resumeState;

    private AnimatorSet animatorSet;

    private ArrayList <ImageButton> buttons = new ArrayList<>();
    private ArrayList <TextView> textViews = new ArrayList();

    private int currentModule;
    ImageButton currentButton;
    TextView currentText;

    private TextView nameshow, welcome, mod1Text, mod2Text, mod3Text, mod4Text, mod5Text;

    private float buttonY, buttonX, centerY, centerX;

    private Animation shake;

    private boolean fade=true;

    private Dialog menupopup, testpopup;

    private Animation in;

    DisplayMetrics displayMetrics = new DisplayMetrics();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuopening);

        currentModule = 0;

        in = new AlphaAnimation(0.0f, 1.0f);
        in.setDuration(500);

        if(MainActivity.first==1){
            FileIO.writeFile(name,"lifeguardname.txt",this);
        }

        shake = AnimationUtils.loadAnimation(this, R.anim.turn);

        menupopup = new Dialog(this,R.style.PauseDialog);
        testpopup = new Dialog(this);

        nameshow = findViewById(R.id.menuname);
        nameshow.setText("Welcome " + name+ "!");

        welcome = findViewById(R.id.welcome);
        welcome.setText("Please pick a\nModule:");

        //For the animations, the screen width and height are used to calculate distances
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        scrheight = displayMetrics.heightPixels/2;
        scrwidth = displayMetrics.widthPixels/2;

        mod1Text = findViewById(R.id.mod1);//creating textview objects
        mod2Text = findViewById(R.id.mod2);
        mod3Text = findViewById(R.id.mod3);
        mod4Text = findViewById(R.id.mod4);
        mod5Text = findViewById(R.id.mod5);

        //Creating the module buttons and adding a shake animation
        (module1Button = findViewById(R.id.module1)).startAnimation(shake);
        (module2Button = findViewById(R.id.module2)).startAnimation(shake);
        (module3Button = findViewById(R.id.module3)).startAnimation(shake);
        (module4Button = findViewById(R.id.module4)).startAnimation(shake);
        (module5Button = findViewById(R.id.module5)).startAnimation(shake);
        module6Button = findViewById(R.id.module6);


        //Adding the buttons and textviews to arraylists to be easier to manage
        buttons.add(module1Button);
        buttons.add(module2Button);
        buttons.add(module3Button);
        buttons.add(module4Button);
        buttons.add(module5Button);

        textViews.add(mod1Text);
        textViews.add(mod2Text);
        textViews.add(mod3Text);
        textViews.add(mod4Text);
        textViews.add(mod5Text);

        //For loop for testing the onclick of any button dynamically
        for(int i=0; i<5; i++){
            final int tempmodnum = i;//have to set final because onClickListener needs it
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

        //Onclick of test button
        module6Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTestPopup();
            }
        });


        //If the menu popup is cancelled, the buttons will animate back after a delay
        menupopup.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        animback();
                    }
                }, 500);
            }
        });

    }

    /**
     * This method animates the buttons and textview to the center of the screen then displays the popup
     * @param button button clicked
     * @param tutton textview attached to button
     */
    private void animateButton(ImageButton button, TextView tutton) {

        //need to set final because handler delay
        final ImageButton b= button;
        final TextView t = tutton;

        b.setEnabled(false);
        b.clearAnimation();

        buttonY = b.getY();
        buttonX = b.getX();

        centerY = (scrheight - b.getHeight()/2-buttonY);//Using math to determine distance to center of screen
        centerX = scrwidth - b.getWidth()/2-b.getX();

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


        animatorSet = new AnimatorSet();//Plays all the animations
        animatorSet.playTogether(animation1, animation2,animation3, animation4, animation5, animation6, animation7, animation8);
        animatorSet.start();

        fadeText(nameshow);//fading out name and welcome text
        fadeText(welcome);

        //Fading out all the other textviews and buttons except for current one
        for(int i=0; i<5; i++){
            if(b==buttons.get(i)){
                continue;
            }
            else{
                buttons.get(i).setEnabled(false);
                fadeButton(buttons.get(i));
                fadeText(textViews.get(i));
            }
        }

        fadeButton(module6Button);

        //Shows the popup after animation is done
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    if (b == buttons.get(i)) {
                        continue;
                    } else {
                        buttons.get(i).clearAnimation();
                    }
                }
                showMenuPopup();
            }
        }, 1000);

        fade=false;
    }

    /**
     * This method fades in and out an image button.
     * @param b ImageButton
     */
    public void fadeButton(ImageButton b){

        //fade in animation
        ObjectAnimator animation1 = ObjectAnimator.ofFloat(b, "alpha", 0f, 1f);
        animation1.setDuration(2000);

        AnimatorSet animatorSet = new AnimatorSet();

        //fade out animation
        Animation out = new AlphaAnimation(1.0f, 0.0f);
        out.setDuration(500);

        //Using the fade boolean, method determines whether to fade in out fade out the button.
        if(fade){
            b.startAnimation(out);
        }
        else{
            animatorSet.playTogether(animation1);
            animatorSet.start();
        }


        //sets visibility of button
        if(fade){
            b.setVisibility(View.INVISIBLE);
        }
        else{
            b.setVisibility(View.VISIBLE);
        }

    }

    /**
     * This method fades in and out a textview. Very similar to fadeButton method.
     * @param t
     */
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

        //sets onclick of restart icon
        restartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Because of the way dialogs work, there is no way to add a dialog on top of another
                //normally. This is a way to somehow circumvent this problem.
                //Bundle is passed in so the propdialogfragment knows which module to rewrite to 0
                Bundle b = new Bundle();
                b.putInt("ID", currentModule);

                DialogFragment newFragment = RestartDialogFragment.newInstance();
                newFragment.setArguments(b);

                showDialog(newFragment);

            }
        });



        ImageView modulePic = menupopup.findViewById(R.id.modulePic);

        //Sets the image inside the buoy to be the corresponding image of the module
        switch (currentModule+1){
            case 1:
                modulePic.setImageResource(R.drawable.module1pic);
                break;
            case 2:
                modulePic.setImageResource(R.drawable.module2pic);
                break;
            case 3:
                modulePic.setImageResource(R.drawable.module3pic);
                modulePic.getLayoutParams().height = (int) getResources().getDimension(R.dimen.imageview_height);//need to resize this picture because
                modulePic.getLayoutParams().width = (int) getResources().getDimension(R.dimen.imageview_width);//it was too big originally
                break;
            case 4:
                modulePic.setImageResource(R.drawable.module4pic);
                break;
            case 5:
                modulePic.setImageResource(R.drawable.module5pic);
                break;
        }


        //Sets text in play button to resume or play depending on whether module is resumeable
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

        //Play button displays the quiz
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menupopup.dismiss();
                displayQuiz();
            }
        });
        menupopup.show();//shows the menupopup
    }


    //Switches activity to the testpopup activity. Even thought it looks like a dialog
    //A dialog would be hard to implement so an activity is used.
    private void showTestPopup(){
        Intent startQuiz = new Intent(getApplicationContext(), TestPopup.class);
        startActivity(startQuiz);
    }

    /**
     * This method does the exact opposite of the animate button method.
     * @param b button clicked
     * @param t textview attached to button
     */
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

        fadeText(nameshow);
        fadeText(welcome);

        for(int i=0; i<5; i++){
            if(b==buttons.get(i)){
                continue;
            }
            else{
                fadeButton(buttons.get(i));
                fadeText(textViews.get(i));
            }
        }

        fadeButton(module6Button);

        fade=true;

        //Starting the animation and enabling the buttons only once the reverse animation is done
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                for(int i=0; i<5; i++){
                    buttons.get(i).startAnimation(shake);
                    buttons.get(i).setEnabled(true);
                }

            }
        }, 1000);


    }

    //Switches activity to quizmenuactivity
    private void displayQuiz(){
        Intent startQuiz = new Intent(getApplicationContext(), QuizMenuActivity.class);
        startQuiz.putExtra("MODULE_ID",currentModule+1);
        startActivity(startQuiz);
        finish();
    }

    //Overriding back press in order to switch to the correct panel
    @Override
    public void onBackPressed() {

        if (animatorSet!=null){
            if(animatorSet.isRunning()){
                Intent switchpanel = new Intent(getApplicationContext(), ModuleMenu.class);
                startActivity(switchpanel);
            }
            else{
                Intent switchpanel = new Intent(getApplicationContext(), MainMenu.class);
                startActivity(switchpanel);
            }
        }
        else{
            Intent switchpanel = new Intent(getApplicationContext(), MainMenu.class);
            startActivity(switchpanel);
        }

    }

    /**
     * This method runs the animation when the dialog is backpressed
     */
    private void dialogBackPressed(){
        reverseButtonAnimation(buttons.get(currentModule), textViews.get(currentModule) );
    }

    /**
     * This method dismisses the menupopup
     */
    private void animback(){
        menupopup.dismiss();
        dialogBackPressed();
    }

    /**
     * This method reads a line from the resumemodule txt file to determine whether a module is resumeable
     * or not
     */
    private void resumeModule(){

        int temp;
        String line = FileIO.readFromFile(getApplicationContext(), resumeModule);
        temp=Integer.parseInt(line);

        //
        if(temp!=0){
            resumeState=true;
        }else{
            resumeState=false;
        }
    }

    /**
     * This method shows the menu popup dialogfragment
     * @param dialog dialogfragment
     */
    private void showDialog(DialogFragment dialog) {
        dialog.show(getFragmentManager(),"tag");
    }
}

