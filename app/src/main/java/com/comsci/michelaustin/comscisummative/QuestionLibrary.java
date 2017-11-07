package com.comsci.michelaustin.comscisummative;

/**
 * Created by Austin on 2017-11-06.
 */

public class QuestionLibrary {
    private String mQuestions[] = {
            "What is the medical meaning of shock?",
            "Can shock be lethal?",
            "Tap possible causes of shock",
            "What is not a symptom of shock?",
            "What is not an appropriate way to treat shock"
    };;

    private String mAnswers[][]={
            {"Vital organs lack oxygen rich blood","Cut off circulation to the limbs", "Fright caused by a certain situation", "Excessive shaking"},
            {"Yes", "No"},
            {"Vomiting and diarrhoea", "Significant blood loss", "Severe infection", "Contusion to the brain"},
            {"Excessive thirst", "Rapid breathing", "Pale skin", "Eyes roll to back of head"},
            {"Warming up the victim", "Having the victim lay down in a comfortable position", "Performing cpr on the victim", "Monitor airway and breathing"}
    };

    private String mCorrectAnswers[][] = {
            { "Vital organs lack oxygen rich blood"},
            {"Yes"},
            {"Vomiting and diarrhoea", "Significant blood loss", "Severe infection"},
            {"Eyes roll to back of head"},
            {"Performing cpr on the victim"}
    };

    public String getQuestion(int i){
        String question = mQuestions[i];
        return question;
    }

    public String getChoice1(int i){
        if(mAnswers[i][0]!=null){
            String choice= mAnswers[i][0];
            return choice;
        }
        else{
            return "test";
        }
    }

    public String getChoice2(int i){
        if(mAnswers[i][1]!=null){
            String choice= mAnswers[i][1];
            return choice;
        }
        else return "test";
    }

    public String getChoice3(int i){
        if(mAnswers[i][2]!=null){
            String choice= mAnswers[i][2];
            return choice;
        }
        else return "test";
    }

    public String getChoice4(int i){
        if(mAnswers[i][3]!=null){
            String choice= mAnswers[i][3];
            return choice;
        }
        else return "test";
    }

    public String getCorrectAnswer(int i){


        if(mCorrectAnswers[i].length==1){
            return mCorrectAnswers[i][0];
        }
        else{
            return "Test";
        }
    }


}
