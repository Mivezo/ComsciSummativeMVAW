package com.comsci.michelaustin.comscisummative;

import android.graphics.Color;
import android.os.Bundle;
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
        mAnswer = mQuestionLibrary.getCorrectAnswer(questionNumber);

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(option1.getText()== mAnswer){
                    option1.setBackgroundColor(Color.GREEN);
                }
                else option1.setBackgroundColor(Color.RED);
            }
        });
        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(option2.getText()== mAnswer){
                    option2.setBackgroundColor(Color.GREEN);
                }
                else option2.setBackgroundColor(Color.RED);
            }
        });
        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(option3.getText()== mAnswer){
                    option3.setBackgroundColor(Color.GREEN);
                }
                else option3.setBackgroundColor(Color.RED);
            }
        });
        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(option4.getText()== mAnswer){
                    option4.setBackgroundColor(Color.GREEN);
                }
                else option4.setBackgroundColor(Color.RED);
            }
        });

    }

    private void displayQuestions(){
        questionLabel.setText(mQuestionLibrary.getQuestion(questionNumber));
        option1.setText(mQuestionLibrary.getChoice1(questionNumber));
        option2.setText(mQuestionLibrary.getChoice2(questionNumber));
        option3.setText(mQuestionLibrary.getChoice3(questionNumber));
        option4.setText(mQuestionLibrary.getChoice4(questionNumber));

    }
}
