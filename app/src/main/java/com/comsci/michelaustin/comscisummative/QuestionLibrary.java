package com.comsci.michelaustin.comscisummative;

import java.util.ArrayList;

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
    };

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
            { "Vital organs lack oxygen rich blood"},
            {"Yes"},
            {"Vomiting and diarrhoea", "Significant blood loss", "Severe infection"},
            {"Eyes roll to back of head"},
            {"Performing cpr on the victim"}
    };

    private String explanations[]={
            "Explanation1",
            "Explanation2",
            "Explanation3",
            "Explanation4",
            "Explanation5"
    };

    private int questionAmount = mQuestions.length;

    ArrayList currentCorrectAnswerArray = new ArrayList();

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

    public String getExplanation(int i){
        String explanation = explanations[i];
        return explanation;
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
    public ArrayList getCorrectAnswer(int i){

        /*int correctAnswerLength = mCorrectAnswers[i].length;
        int numCorrect = Integer.parseInt(mCorrectAnswers[i][correctAnswerLength-1]);


        if(numCorrect==1){
            return mCorrectAnswers[i][0];
        }
        else{
            return "Test";
        }*/


        currentCorrectAnswerArray.clear();
        for(int j = 0; j<mCorrectAnswers[i].length; j++){
           currentCorrectAnswerArray.add(mCorrectAnswers[i][j]);
        }

        return currentCorrectAnswerArray;


    }


    //Returns the number of possible correct answers
    public int getNumCorrect(int c){
       return mCorrectAnswers[c].length;
    }

    public int getQuestionAmount(){
        return questionAmount;
    }

}
