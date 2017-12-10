package com.comsci.michelaustin.comscisummative;

import java.util.ArrayList;

/**
 * Created by Austin on 2017-11-06.
 */

public class QuestionLibrary {

    private int moduleNumber;

    public QuestionLibrary(int m){
        moduleNumber=m;
    }

    private ArrayList<String> questions;

    //string array of questions
    private String questions1[] = {
            "What is the medical meaning of shock?",
            "Can shock be lethal?",
            "Tap possible causes of shock",
            "What is not a symptom of shock?",
            "What is not an appropriate way to treat shock"
    };

    private String questions2[] = {
            "What is the least prominent symptom of a fully obscured airway?",
            "How big must a toy be to be considered a choking hazard for toddlers?",
            "When performing back blows, where must the hits be directed?",
            "Which of these should be performed first?",
            "When a person becomes unconscious and/or unresponsive, what must you begin after calling 911?"
    };
    private String questions3[] = {
            "What is the medical meaning of shock?",
            "Can shock be lethal?",
            "Tap possible causes of shock",
            "What is not a symptom of shock?",
            "What is not an appropriate way to treat shock"
    };
    private String questions4[] = {
            "What is the medical meaning of shock?",
            "Can shock be lethal?",
            "Tap possible causes of shock",
            "What is not a symptom of shock?",
            "What is not an appropriate way to treat shock"
    };
    private String questions5[] = {
            "What is the medical meaning of shock?",
            "Can shock be lethal?",
            "Tap possible causes of shock",
            "What is not a symptom of shock?",
            "What is not an appropriate way to treat shock"
    };

    //string array of answers for each specific question
    private String answers1[][]={
            {"Vital organs lack oxygen rich blood","Cut off circulation to the limbs", "Fright caused by a certain situation", "Excessive shaking"},
            {"Yes", "No", "", ""},
            {"Vomiting and diarrhoea", "Significant blood loss", "Severe infection", "Contusion to the brain"},
            {"Excessive thirst", "Rapid breathing", "Pale skin", "Eyes roll to back of head"},
            {"Warming up the victim", "Having the victim lay down in a comfortable position", "Performing cpr on the victim", "Monitor airway and breathing"}
    };

    private String answers2[][]={
            {"Excessive arm movements","No sound coming out of the victim", "Red flushed face", "One or both hands clutching the throat"},
            {"1 inch ", "4 cm", "2 cm", "1.5 cm"},
            {"Base of the neck", "Between the shoulder blades", "Middle of the back", "Back of the head"},
            {"Back Blows", "Abdominal Thrusts", "", ""},
            {"Continue back blows and chest compressions", "Perform CPR","Attempt to the dislodge the object by pulling it out", "Nothing, wait for 911"}
    };

    //string array of correct answers
    private String correctAnswers1[][] = {
            { "Vital organs lack oxygen rich blood"},
            {"Yes"},
            {"Vomiting and diarrhoea", "Significant blood loss", "Severe infection"},
            {"Eyes roll to back of head"},
            {"Performing cpr on the victim"}
    };

    private String correctAnswers2[][] = {
            {"Excessive arm movements"},
            {"4 cm"},
            {"Between the shoulder blades"},
            {"Back Blows"},
            {"Perform CPR"}
    };


    private String explanations1[]={
            "Explanation1",
            "Explanation2",
            "Explanation3",
            "Explanation4",
            "Explanation5"
    };

    private String explanations2[]={
            "Explanationa",
            "Explanationb",
            "Explanationc",
            "Explanationd",
            "Explanatione"
    };

    private int questionAmount = 5;

    ArrayList currentCorrectAnswerArray = new ArrayList();

    //Returns the specific question in the array
    public String getQuestion(int i){
        switch (moduleNumber) {
            case 1:
                return questions1[i];
            case 2:
                return questions2[i];
            case 3:
                return questions3[i];
            case 4:
                return questions4[i];
            case 5:
                return questions5[i];
            default:return "0";

        }

    }

    public String getChoice(int i,int q){

        switch (moduleNumber){
            case 1:
                return isAnswerBlank(answers1, i, q);
            case 2:
                return isAnswerBlank(answers2, i, q);
            default: return "0";

        }

    }

    public String getExplanation(int i){

        switch (moduleNumber){
            case 1:
                return explanations1[i];
            case 2:
                return explanations2[i];
            default: return "0";
        }

    }


    //returns the correct answer based on the question
    public ArrayList getCorrectAnswer(int i){

        currentCorrectAnswerArray.clear();

        switch (moduleNumber){
            case 1:
                addCorrectAnswerToArray(correctAnswers1, i);
            case 2:
                addCorrectAnswerToArray(correctAnswers2, i);
        }

        return currentCorrectAnswerArray;
    }

    //for getCorrectAnswer method, adds correct answers to arraylist
    private void addCorrectAnswerToArray(String[][] array, int i){
        for(int j = 0; j<array[i].length; j++){
            currentCorrectAnswerArray.add(array[i][j]);
        }
    }

    //for the getchoice method, returns a string
    public String isAnswerBlank(String[][] array, int i, int q){
        if(array[i][q]!=""){
            String choice= array[i][q];
            return choice;
        }
        else{
            return "";
        }
    }


    //Returns the number of possible correct answers
    public int getNumCorrect(int c){

        switch (moduleNumber){
            case 1: return correctAnswers1[c].length;
            case 2: return correctAnswers2[c].length;
            default: return 0;
        }

    }

    private void createArray(ArrayList a, String file){

    }

    //returns the amount of questions
    public int getQuestionAmount(){
        return questionAmount;
    }

}
