package com.comsci.michelaustin.comscisummative;

/**
 * Created by Austin on 2017-11-06.
 */

public class QuestionLibrary {
    //string array of questions
    private String mQuestions[] = {
            "What is the medical meaning of shock?",
            "Can shock be lethal?",
            "Tap possible causes of shock",
            "What is not a symptom of shock?",
            "What is not an appropriate way to treat shock"
    };;

    //string array of answers for each specific question
    private String mAnswers[][]={
            {"Vital organs lack oxygen rich blood","Cut off circulation to the limbs", "Fright caused by a certain situation", "Excessive shaking"},
            {"Yes", "No", "", ""},
            {"Vomiting and diarrhoea", "Significant blood loss", "Severe infection", "Contusion to the brain"},
            {"Excessive thirst", "Rapid breathing", "Pale skin", "Eyes roll to back of head"},
            {"Warming up the victim", "Having the victim lay down in a comfortable position", "Performing cpr on the victim", "Monitor airway and breathing"}
    };

    //string array of correct answers
    private String mCorrectAnswers[][] = {
            { "Vital organs lack oxygen rich blood", "1"},
            {"Yes", "1"},
            {"Vomiting and diarrhoea", "Significant blood loss", "Severe infection", "3"},
            {"Eyes roll to back of head","1"},
            {"Performing cpr on the victim","1"}
    };

    //Returns the specific question in the array
    public String getQuestion(int i){
        String question = mQuestions[i];
        return question;
    }

    public String getChoice(int i,int q){
        if(mAnswers[i][q]!=""){
            String choice= mAnswers[i][q];
            return choice;
        }
        else{
            return "";
        }
    }

    /*public String getChoice2(int i){
        if(mAnswers[i][1]!=""){
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
    }*/

    //returns the correct answer based on the question
    public String getCorrectAnswer(int i){

        int numCorrect = mCorrectAnswers[i].length;
        int numCorrectString = Integer.parseInt(mCorrectAnswers[i][numCorrect-1]);


        if(numCorrectString==1){
            return mCorrectAnswers[i][0];
        }
        else{
            return "Test";
        }
    }

    private void testComplete(int q){

    }


}
