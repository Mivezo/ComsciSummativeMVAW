package com.comsci.michelaustin.comscisummative;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class QuizMenuActivity extends AppCompatActivity {

    private TextView questionLabel;
    private Button option1;
    private Button option2;
    private Button option3;
    private Button option4;
    /*private String mAnswer;*/
    private int questionNumber=0;
    private int amountCorrect;//integer needed if there are multiple answers
    private int amountCorrectComparison=0;//integer to compare
    private String explanation="";

    private Dialog dialog;


    ArrayList answerArray = new ArrayList();


    private QuestionLibrary mQuestionLibrary = new QuestionLibrary();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_menu);

        option1 = (Button) findViewById(R.id.option1);
        option2 = (Button) findViewById(R.id.option2);
        option3 = (Button) findViewById(R.id.option3);
        option4 = (Button) findViewById(R.id.option4);
        questionLabel = (TextView) findViewById(R.id.questionLabel);

        displayQuestions(); //displaying the questions on the option buttons as well as retrieving specific question info

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
        questionLabel.setText(mQuestionLibrary.getQuestion(questionNumber));

        option1.setText(getQuestion(questionNumber,0));
        option1.setBackgroundColor(Color.WHITE);
        option2.setText(getQuestion(questionNumber,1));
        option2.setBackgroundColor(Color.WHITE);
        option3.setBackgroundColor(Color.WHITE);
        option4.setBackgroundColor(Color.WHITE);
        //to test specifically for true and false questions and to remove visibility of the last two buttons
        if (testWhetherBlank(2)) {
            option3.setVisibility(View.GONE);
        }
        else{
            option3.setVisibility(View.VISIBLE);
            option3.setText(getQuestion(questionNumber,2));
        }

        if (testWhetherBlank(3)) {
            option4.setVisibility(View.GONE);
        }
        else{
            option4.setVisibility(View.VISIBLE);
            option4.setText(getQuestion(questionNumber,3));
        }

        //
        answerArray=(ArrayList<Object>)mQuestionLibrary.getCorrectAnswer(questionNumber).clone();

        explanation=mQuestionLibrary.getExplanation(questionNumber);

        amountCorrect=mQuestionLibrary.getNumCorrect(questionNumber);


    }

    //allows the UI to test and remove buttons if the options are blank
    public boolean testWhetherBlank(int q){
        if(mQuestionLibrary.getChoice(questionNumber,q).equals("")){
            return true;
        }
        else{
            return false;
        }

    }


    //fetches the correct question from the QuestionLibrary class
    private String getQuestion(int q, int n){
        String result = mQuestionLibrary.getChoice(q,n);
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

    //checks whether the button pressed is a correct answer or not
    private void checkAnswer(Button b){

        boolean correct = false;

        for (int i = 0; i<answerArray.size(); i++){
            //compares the text on the button with the arraylist
            if(b.getText()== answerArray.get(i)){
                b.setBackgroundColor(Color.GREEN); //sets to green to indicate correct answer
                amountCorrectComparison++; //increment integer to compare with set number of correct answers
                correct=true;

                if(testComplete()){
                    questionNumber+=1;
                    showPopup(this.findViewById(android.R.id.content));
                }
            }
        }
        if(!correct){
            b.setBackgroundColor(Color.RED);
        }

    }

    //Adding delay to the button to make green visible
    private void switchQuestion(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 1.5s = 1500ms
                displayQuestions();

            }
        }, 1500);
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
}
