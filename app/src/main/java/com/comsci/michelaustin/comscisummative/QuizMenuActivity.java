package com.comsci.michelaustin.comscisummative;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class QuizMenuActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{

    private TextView questionLabel;
    private Button option1;
    private Button option2;
    private Button option3;
    private Button option4;
    private ImageButton nextArrowButton;
    /*private String mAnswer;*/
    private int questionNumber=0;
    private int amountCorrect;//integer needed if there are multiple answers
    private int amountCorrectComparison=0;//integer to compare
    private int amountGetCorrect;//Integer to store how many questions the user has gotten right

    private String explanation="";
    private String resumeModule;
    private String getCorrectString;
    private MediaPlayer mp;
    private String grabbedAnswer;
    private String voiceMuted;
    private String vibrations;

    private Animation in;
    private Animation option1in,option2in,option3in,option4in;
    private Animation shake;
    private Animation out;
    private ImageView lifeg;
    private float userVolume;
    PowerManager pm;
    int marker;
    int marker2;

    private Vibrator vib;

    private TextToSpeech talker;
    boolean playvoice;
    int result;
    int openingQuestion;

    private int moduleNumber;

    private Dialog dialog;//dialog for popup


    ArrayList answerArray = new ArrayList();

    ArrayList<String> testCorrectAnswerArray = new ArrayList<String>();
    ArrayList<String> testQuestionArray = new ArrayList<String>();
    ArrayList<String> testResult = new ArrayList<String>();

    SharedPreferences prefs = null;



    //private QuestionLibrary mQuestionLibrary;//QuestionLibrary Object
    private QuestionLibrary mQuestionLibraryTest;

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS){
            result = talker.setLanguage(Locale.UK);
            displayQuestions();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Talking feature not supported on this device", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        marker = 0;
        marker2 = 0;
        shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        prefs = getSharedPreferences("com.comsci.michelaustin.comscisummative", MODE_PRIVATE);
        moduleNumber = getIntent().getIntExtra("MODULE_ID", 0);
        voiceMuted = fileIo.readFromFile(this,"voiceMute.txt");
        vibrations = fileIo.readFromFile(this, "vibration.txt");
        String temp = fileIo.readFromFile(this, "volume.txt");
        userVolume = Float.parseFloat(temp);
        lifeg = findViewById(R.id.lifeguard);
        openingQuestion = 0;

        option1in = new AlphaAnimation(0.0f, 1.0f);
        option1in.setDuration(1200);

        option2in = new AlphaAnimation(0.0f, 1.0f);
        option2in.setDuration(1600);

        option3in = new AlphaAnimation(0.0f, 1.0f);
        option3in.setDuration(2000);

        option4in = new AlphaAnimation(0.0f, 1.0f);
        option4in.setDuration(2400);

        in = new AlphaAnimation(0.0f, 1.0f);
        in.setDuration(1000);

        out = new AlphaAnimation(1.0f, 0.0f);
        out.setDuration(800);

        //lifeg.startAnimation(in);

        int musicchoice = 0;

        if (moduleNumber == 1){
            musicchoice = R.raw.song1;
        }
        else if (moduleNumber == 2){
            musicchoice = R.raw.song2;
        }
        else if (moduleNumber == 3){
            musicchoice = R.raw.song3;
        }
        else if (moduleNumber == 4){
            musicchoice = R.raw.song4;
        }
        else if (moduleNumber == 5){
            musicchoice = R.raw.song5;
        }

        if (musicchoice != 0){
            mp = MediaPlayer.create(getApplicationContext(), musicchoice);
            mp.setLooping(true);
            mp.setVolume(userVolume,userVolume);
            mp.start();
        }

        talker = new TextToSpeech(QuizMenuActivity.this, this);

        if (voiceMuted.equals("notmuted")){
            playvoice = true;
        }

        //initializing the buttons as well as the question labels
        option1 = (Button) findViewById(R.id.option1);
        option2 = (Button) findViewById(R.id.option2);
        option3 = (Button) findViewById(R.id.option3);
        option4 = (Button) findViewById(R.id.option4);
        nextArrowButton = (ImageButton) findViewById(R.id.nextArrowButton);
        questionLabel = (TextView) findViewById(R.id.questionLabel);

        // mQuestionLibrary = new QuestionLibrary(moduleNumber);

        mQuestionLibraryTest = new QuestionLibrary(moduleNumber, getApplicationContext());


        resumeModule="resumeModule"+moduleNumber+".txt";
        getCorrectString="resumeCorrectAnswers"+moduleNumber+".txt";


        resumeModule();
        amountGetCorrect=mQuestionLibraryTest.getQuestionAmount();

        //displayQuestions(); //displaying the questions on the option buttons as well as retrieving specific question info



        //creating a dialog for the popup window
        dialog = new Dialog(this);

        // Sets onclick listener for each button to test whether the user clicked the right one
        // Sets onclick listener for each button to test whether the user clicked the right one
        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(moduleNumber==6){
                    option2.setBackgroundColor(getResources().getColor(R.color.colorLightblue));
                    option3.setBackgroundColor(getResources().getColor(R.color.colorLightblue));
                    option4.setBackgroundColor(getResources().getColor(R.color.colorLightblue));
                    option1.setBackgroundColor(Color.WHITE);

                    grabbedAnswer=(String) option1.getText();
                    showNextArrowButton();
                }
                else{
                    checkAnswer(option1);
                }

            }
        });
        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(moduleNumber==6){
                    option1.setBackgroundColor(getResources().getColor(R.color.colorLightblue));
                    option3.setBackgroundColor(getResources().getColor(R.color.colorLightblue));
                    option4.setBackgroundColor(getResources().getColor(R.color.colorLightblue));
                    option2.setBackgroundColor(Color.WHITE);


                    grabbedAnswer=(String) option2.getText();
                    showNextArrowButton();
                }
                else{
                    checkAnswer(option2);
                }

            }
        });
        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(moduleNumber==6){
                    option3.setBackgroundColor(Color.WHITE);
                    option2.setBackgroundColor(getResources().getColor(R.color.colorLightblue));
                    option1.setBackgroundColor(getResources().getColor(R.color.colorLightblue));
                    option4.setBackgroundColor(getResources().getColor(R.color.colorLightblue));
                    option3.setBackgroundColor(Color.WHITE);
                    grabbedAnswer=(String) option3.getText();
                    showNextArrowButton();
                }
                else{
                    checkAnswer(option3);
                }

            }
        });
        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(moduleNumber==6){
                    option2.setBackgroundColor(getResources().getColor(R.color.colorLightblue));
                    option3.setBackgroundColor(getResources().getColor(R.color.colorLightblue));
                    option1.setBackgroundColor(getResources().getColor(R.color.colorLightblue));
                    option4.setBackgroundColor(Color.WHITE);
                    grabbedAnswer=(String) option4.getText();
                    showNextArrowButton();
                }
                else{
                    checkAnswer(option4);
                }

            }
        });

        if(moduleNumber==6){
            buildTestArrays();
        }


        /*if(moduleNumber==6){
            test();
        }*/

        testResult.add("Arbitrary Value");
        testResult.clear();

        //fileIo.writeFile("", "testResult.txt", this);
    }

    //Displays the questions on the screen as well as fetches the correct answer
    //Also fetches the correct amount of answers to test to allow for multiple answers
    private void displayQuestions(){
        String ques = mQuestionLibraryTest.getQuestion(questionNumber);
        String displayQues =(questionNumber+1)+". "+mQuestionLibraryTest.getQuestion(questionNumber);
        if(questionNumber!=openingQuestion){
            lifeg.startAnimation(in);
            option1.startAnimation(option1in);
            option2.startAnimation(option2in);

            if (!testWhetherBlank(2) && !testWhetherBlank(3)) {
                option3.startAnimation(option3in);
                option4.startAnimation(option4in);
            }
        }

        questionLabel.startAnimation(in);

        questionLabel.setText(displayQues);

        if (playvoice == true) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                talker.speak(ques, TextToSpeech.QUEUE_FLUSH, null, null);
            } else {
                talker.speak(ques, TextToSpeech.QUEUE_FLUSH, null);
            }
        }

        option1.setText(getChoice(questionNumber,0));
        option2.setText(getChoice(questionNumber,1));


        option1.setEnabled(true);
        option2.setEnabled(true);
        option3.setEnabled(true);
        option4.setEnabled(true);

        option1.setBackgroundColor(getResources().getColor(R.color.colorLightblue));
        option2.setBackgroundColor(getResources().getColor(R.color.colorLightblue));
        option3.setBackgroundColor(getResources().getColor(R.color.colorLightblue));
        option4.setBackgroundColor(getResources().getColor(R.color.colorLightblue));
        //to test specifically for true and false questions and to remove visibility of the last two buttons
        if (testWhetherBlank(2)) {
            option3.setVisibility(View.GONE);
            option3.setEnabled(false);
        }
        else{
            option3.setVisibility(View.VISIBLE);
            option3.setText(getChoice(questionNumber,2));
        }

        if (testWhetherBlank(3)) {
            option4.setVisibility(View.GONE);
            option4.setEnabled(false);
        }
        else{
            option4.setVisibility(View.VISIBLE);
            option4.setText(getChoice(questionNumber,3));
        }

        if(moduleNumber!=6){
            answerArray = (ArrayList<String>) mQuestionLibraryTest.getCorrectAnswer(questionNumber).clone();
        }
        else{
            answerArray.add(mQuestionLibraryTest.getTestCorrectAnswer(questionNumber));
        }

        explanation = mQuestionLibraryTest.getExplanation(questionNumber);
        amountCorrect=mQuestionLibraryTest.getAnswerAmount(questionNumber);

        nextArrowButton.setVisibility(View.GONE);

        if(moduleNumber!=6){
            writeResume();
        }
    }

    //allows the UI to test and remove buttons if the options are blank
    public boolean testWhetherBlank(int q){
        /*if(mQuestionLibrary.getChoice(questionNumber,q).equals("")){
            return true;
        }*/
        if(mQuestionLibraryTest.getChoice(questionNumber,q).equals("")){
            return true;
        }
        else{
            return false;
        }

    }


    //fetches the correct question from the QuestionLibrary class
    private String getChoice(int q, int n){
        //String result = mQuestionLibrary.getChoice(q,n);
        String result = mQuestionLibraryTest.getChoice(q,n);
        return result;
    }

    //Testing whether the user chose all possible answers
    private boolean testComplete(){
        if(amountCorrectComparison == amountCorrect){
            return true;
        }
        else{
            return false;
        }
    }

    private boolean testFullyComplete(){

        if(moduleNumber!=6){
            if(questionNumber == mQuestionLibraryTest.getQuestionAmount()){
                mp.stop();
                mp.release();
                return true;
            }
            else return false;
        }
        else{
            if(questionNumber == mQuestionLibraryTest.getQuestionAmount()-1){
                return true;
            }
            else return false;
        }

    }

    @Override
    public void onBackPressed() {
        talker.stop();

        if (moduleNumber!=6){
            mp.stop();
            mp.release();
        }
        Intent change = new Intent(getApplicationContext(), menuopening.class);
        startActivity(change);
    }

    //checks whether the button pressed is a correct answer or not
    private void checkAnswer(Button b){

        boolean correct = false;

        for (int i = 0; i<answerArray.size(); i++){
            //compares the text on the button with the arrayList
            if(b.getText().equals(answerArray.get(i))) {
                b.setBackgroundColor(Color.GREEN); //sets to green to indicate correct answer
                b.setEnabled(false);
                amountCorrectComparison++; //increment integer to compare with set number of correct answers
                correct=true;

                if(testComplete()){
                    questionNumber+=1;//increases questionNumber to switch question

                    showPopup(this.findViewById(android.R.id.content));
                }
            }
        }
        if(!correct){

            b.setBackgroundColor(Color.RED);
            b.startAnimation(shake);

            if (vibrations.equals("on")){
                vib.vibrate(150);
            }

            b.setEnabled(false);
            amountGetCorrect--;
        }
    }

    //Adding delay to the button to make green visible
    private void switchQuestion(){
        final Handler handler = new Handler();
        questionLabel.startAnimation(out);
        lifeg.startAnimation(out);
        option1.startAnimation(out);
        option2.startAnimation(out);

        if ((option3.getVisibility() == View.VISIBLE) && (option4.getVisibility() == View.VISIBLE)) {
            option3.startAnimation(out);
            option4.startAnimation(out);
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after a certain amount of time
                if(!testFullyComplete()){

                    if(moduleNumber==6){
                        questionNumber++;
                        testResult.add(grabbedAnswer);
                    }
                    displayQuestions();
                }
                else {
                    if(moduleNumber!=6){
                        displayCompletionScreen();
                    }
                    else{
                        testResult.add(grabbedAnswer);
                        displayTestCompletionScreen();
                    }
                }
            }
        }, 800);
        amountCorrectComparison=0;
    }

    public void showPopup(View v) {
        TextView nextButton;
        TextView explanationLabel;
        TextView explanationText;
        dialog.setContentView(R.layout.custompopup);

        explanationLabel = (TextView) dialog.findViewById(R.id.explanationLabel);
        nextButton = (TextView) dialog.findViewById(R.id.nextText);
        explanationText = (TextView) dialog.findViewById(R.id.explanationText);
        explanationText.setText(explanation);
        explanationText.setMovementMethod(new ScrollingMovementMethod());

        nextArrowButton.setVisibility(View.VISIBLE);


        nextArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchQuestion();
            }
        });

        //Switches the question when the next button is pressed
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                switchQuestion();
            }
        });
        dialog.show();
    }

    //Displays the completion screen
    public void displayCompletionScreen(){
        talker.stop();
        Intent startIntent= new Intent(getApplicationContext(), QuizCompletionActivity.class);
        startIntent.putExtra("AMOUNT_CORRECT", amountGetCorrect);
        startIntent.putExtra("TOTAL_CORRECT", mQuestionLibraryTest.getQuestionAmount());
        startIntent.putExtra("MODULEID", moduleNumber);
        startActivity(startIntent);
    }

    public void displayTestCompletionScreen(){
        talker.stop();
        Intent startIntent= new Intent(getApplicationContext(), TestCompletionScreen.class);
        startIntent.putStringArrayListExtra("TEST_QUESTION_ARRAY", testQuestionArray);//passing the test arrays to the test completion activity
        startIntent.putStringArrayListExtra("TEST_CORRECT_ANSWER_ARRAY", testCorrectAnswerArray);
        startIntent.putStringArrayListExtra("TEST_RESULT_ARRAY", testResult);

        startActivity(startIntent);
    }

    /**
     * Gets called everytime the questions are displayed to store the question number in their respected resume module txts
     */
    private void writeResume(){

        fileIo.writeFile(questionNumber+"", resumeModule, this);
        fileIo.writeFile(amountGetCorrect+"", getCorrectString, this);
        //Log.d("MyTag", fileIo.readFromFile(this, resumeModule));
    }

    /**
     * Grabs the question number from the respected resume module and sets that as the question number
     */
    private void resumeModule(){

        String line = fileIo.readFromFile(getApplicationContext(), resumeModule);
        String line2= fileIo.readFromFile(getApplicationContext(), getCorrectString);

        questionNumber=Integer.parseInt(line);
        openingQuestion=questionNumber;
        amountGetCorrect=Integer.parseInt(line2);

    }

    private void buildTestArrays(){
        testCorrectAnswerArray.add("fsdf");//arbitrary values to get cleared as cannot clear a null txt file
        testQuestionArray.add("ggd");

        testQuestionArray.clear();
        testCorrectAnswerArray.clear();


        for(int i=0; i<20; i++){
            testCorrectAnswerArray.add(mQuestionLibraryTest.getTestCorrectAnswer(i));
            testQuestionArray.add(mQuestionLibraryTest.getQuestion(i));
        }

    }

    public void test(){
        Log.d("MyTag", answerArray.size()+"");

        for (int i = 0; i<answerArray.size(); i++){
            Log.d("MyTag", ""+answerArray.get(i));
        }
    }

    public boolean fileExists(Context context, String filename) {
        File file = context.getFileStreamPath(filename);
        if(file == null || !file.exists()) {
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if (talker!=null)
        {
            talker.stop();
            talker.shutdown();
        }
    }

    protected void onPause() {

        if (this.isFinishing()) { //basically BACK was pressed from this activity
            if (moduleNumber!= 6) {
                mp.stop();
            }
            Toast.makeText(QuizMenuActivity.this, "YOU PRESSED BACK FROM YOUR 'HOME/MAIN' ACTIVITY", Toast.LENGTH_SHORT).show();
        }

        Context context = getApplicationContext();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);

        if (!taskInfo.isEmpty()) {
            ComponentName topActivity = taskInfo.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                if(moduleNumber!=6){
                    mp.pause();
                    marker2=1;
                }
                talker.stop();
                Toast.makeText(QuizMenuActivity.this, "YOU LEFT YOUR APP", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(QuizMenuActivity.this, "YOU SWITCHED ACTIVITIES WITHIN YOUR APP", Toast.LENGTH_SHORT).show();
            }
        }
        super.onPause();

    }

    private void showNextArrowButton(){
        nextArrowButton.setVisibility(View.VISIBLE);
        nextArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextArrowButton.setVisibility(View.GONE);
                switchQuestion();

            }
        });
    }

    @Override
    protected void onResume(){
        if (marker2==1){
            mp.start();
        }
        super.onResume();
    }

    @Override
    protected void onStop(){
        if(!(pm.isInteractive())){
            if (mp.isPlaying()){
                mp.pause();
                marker = 1;
            }
            if(talker.isSpeaking()){
                talker.stop();
            }
        }
        super.onStop();
    }

    @Override
    protected void onRestart(){
        if (marker == 1){
            mp.start();
        }
        super.onRestart();
    }
}