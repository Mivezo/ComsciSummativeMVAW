package com.comsci.michelaustin.comscisummative;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuizMenuActivity extends AppCompatActivity {

    private TextView questionLabel;
    private Button option1;
    private Button option2;
    private Button option3;
    private Button option4;

    private String mAnswer;
    private int questionNumber=0;


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

        //
        displayQuestions(); //displaying the questions on the option buttons


        // Sets onclick listener for each button to test whether the user clicked the right one
        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(option1.getText()== mAnswer){
                    option1.setBackgroundColor(Color.GREEN);
                    questionNumber+=1;

                    //Adding delay to the button to make green visible
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Do something after 1.5s = 1500ms
                            displayQuestions();

                        }
                    }, 1500);

                }
                else option1.setBackgroundColor(Color.RED);
            }
        });
        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(option2.getText()== mAnswer){
                    option2.setBackgroundColor(Color.GREEN);
                    questionNumber++;
                    displayQuestions();
                }
                else option2.setBackgroundColor(Color.RED);
            }
        });
        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(option3.getText()== mAnswer){
                    option3.setBackgroundColor(Color.GREEN);
                    questionNumber++;
                    displayQuestions();
                }
                else option3.setBackgroundColor(Color.RED);
            }
        });
        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(option4.getText()== mAnswer){
                    option4.setBackgroundColor(Color.GREEN);
                    questionNumber++;
                    displayQuestions();
                }
                else option4.setBackgroundColor(Color.RED);
            }
        });

    }

    //Displays the questions on the screen as well as fetches the correct answer
    private void displayQuestions(){
        questionLabel.setText(mQuestionLibrary.getQuestion(questionNumber));
        option1.setText(getQuestion(questionNumber,0));
        option1.setBackgroundColor(Color.WHITE);
        option2.setText(getQuestion(questionNumber,1));
        option2.setBackgroundColor(Color.WHITE);


        option3.setBackgroundColor(Color.WHITE);
        if (testWhetherBlank(2) == true) {
            option3.setVisibility(View.GONE);
        }
        else{
            option3.setVisibility(View.VISIBLE);
            option3.setText(getQuestion(questionNumber,2));
        }

        if (testWhetherBlank(3) == true) {
            option4.setVisibility(View.GONE);
        }
        else{
            option4.setVisibility(View.VISIBLE);
            option4.setText(getQuestion(questionNumber,3));
        }

        option4.setBackgroundColor(Color.WHITE);
        mAnswer = mQuestionLibrary.getCorrectAnswer(questionNumber);

    }

    public boolean testWhetherBlank(int q){
        if(mQuestionLibrary.getChoice(questionNumber,q).equals("")){
            return true;
        }
        else{
            return false;
        }

    }


    private String getQuestion(int q, int n){
        String result = mQuestionLibrary.getChoice(q,n);
        return result;
    }
}
