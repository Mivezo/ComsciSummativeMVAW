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
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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

    private TextToSpeech talker;
    int result;

    private int moduleNumber;

    private Dialog dialog;//dialog for popup


    ArrayList answerArray = new ArrayList();

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
        prefs = getSharedPreferences("com.comsci.michelaustin.comscisummative", MODE_PRIVATE);
        moduleNumber = getIntent().getIntExtra("MODULE_ID", 0);
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
            mp.start();
        }

        talker = new TextToSpeech(QuizMenuActivity.this, this);

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
        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(option1);
            }
        });
        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(option2);
            }
        });
        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(option3);
            }
        });
        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(option4);
            }
        });
    }

    //Displays the questions on the screen as well as fetches the correct answer
    //Also fetches the correct amount of answers to test to allow for multiple answers
    private void displayQuestions(){
        String ques = mQuestionLibraryTest.getQuestion(questionNumber);
        questionLabel.setText(ques);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            talker.speak(ques,TextToSpeech.QUEUE_FLUSH,null,null);
        } else {
            talker.speak(ques, TextToSpeech.QUEUE_FLUSH, null);
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
        }
        else{
            option3.setVisibility(View.VISIBLE);
            option3.setText(getChoice(questionNumber,2));
        }

        if (testWhetherBlank(3)) {
            option4.setVisibility(View.GONE);
        }
        else{
            option4.setVisibility(View.VISIBLE);
            option4.setText(getChoice(questionNumber,3));
        }

        //
        //answerArray=(ArrayList<Object>)mQuestionLibrary.getCorrectAnswer(questionNumber).clone();
        answerArray = (ArrayList<String>) mQuestionLibraryTest.getCorrectAnswer(questionNumber).clone();
        //Collections.copy(mQuestionLibraryTest.getCorrectAnswer(questionNumber), answerArray);

        //explanation=mQuestionLibrary.getExplanation(questionNumber);
        explanation = mQuestionLibraryTest.getExplanation(questionNumber);
        amountCorrect=mQuestionLibraryTest.getAnswerAmount(questionNumber);


        nextArrowButton.setVisibility(View.GONE);

        writeResume();
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
        /*if(questionNumber == mQuestionLibrary.getQuestionAmount()){
            return true;
        }*/
        if(questionNumber == mQuestionLibraryTest.getQuestionAmount()){
            mp.stop();
            mp.release();
            return true;
        }
        else return false;
    }

    @Override
    public void onBackPressed() {
        talker.stop();
        mp.stop();
        mp.release();
        Intent change = new Intent(getApplicationContext(), menuopening.class);
        startActivity(change);
    }

    //checks whether the button pressed is a correct answer or not
    private void checkAnswer(Button b){

        boolean correct = false;

        for (int i = 0; i<answerArray.size(); i++){
            //compares the text on the button with the arrayList
            if(b.getText().equals(answerArray.get(i))){
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
            amountGetCorrect--;
        }

    }



    //Adding delay to the button to make green visible
    private void switchQuestion(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 1.5s = 1500ms
                if(!testFullyComplete()){
                    displayQuestions();
                }
                else displayCompletionScreen();
            }
        }, 1000);
        amountCorrectComparison=0;
    }

    //shows the explanation popup
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

    public void displayCompletionScreen(){
        Intent startIntent= new Intent(getApplicationContext(), QuizCompletionActivity.class);
        startIntent.putExtra("AMOUNT_CORRECT", amountGetCorrect);
        startIntent.putExtra("TOTAL_CORRECT" +
                "", mQuestionLibraryTest.getQuestionAmount());
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
        amountGetCorrect=Integer.parseInt(line2);

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
        if (this.isFinishing()){ //basically BACK was pressed from this activity
            mp.stop();
            Toast.makeText(QuizMenuActivity.this, "YOU PRESSED BACK FROM YOUR 'HOME/MAIN' ACTIVITY", Toast.LENGTH_SHORT).show();
        }

        Context context = getApplicationContext();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);

        if (!taskInfo.isEmpty()) {
            ComponentName topActivity = taskInfo.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                mp.stop();
                talker.stop();
                Toast.makeText(QuizMenuActivity.this, "YOU LEFT YOUR APP", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(QuizMenuActivity.this, "YOU SWITCHED ACTIVITIES WITHIN YOUR APP", Toast.LENGTH_SHORT).show();
            }
        }
        super.onPause();
    }
}