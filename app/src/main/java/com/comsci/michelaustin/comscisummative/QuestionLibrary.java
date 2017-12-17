package com.comsci.michelaustin.comscisummative;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Austin on 2017-12-04.
 */

public class QuestionLibrary {

    private final Context context;

    String questionFileName;
    String answerFileName;
    String explanationsFileName;
    String correctAnswersFileName;



    private ArrayList <String> questions= new ArrayList<String>();
    private List<List<String>> answers = new ArrayList<List<String>>();
    ArrayList<ArrayList<String>> correctAnswers = new ArrayList<ArrayList<String>>();
    private ArrayList <String> explanations= new ArrayList<String>();



    int moduleID;
    int answerAmount;

    public QuestionLibrary(int mID, Context c){
        moduleID=mID;
        context=c;

        questionFileName="questions"+moduleID+".txt";
        answerFileName="answers"+moduleID+".txt";
        explanationsFileName="explanations"+moduleID+".txt";
        correctAnswersFileName="correctanswers"+moduleID+".txt";


        buildArrays();
    }

    /**
     * Creates all arrays
     */
    private void buildArrays(){
        questions.add("!");
        answers.add(Arrays.asList("1"));
        explanations.add("!");
        correctAnswers.add(new ArrayList<String>());
        correctAnswers.get(0).add("1");

        //Log.d("Test", answerFileName);

        createArray(questions, questionFileName);
        createAnswerArray(answerFileName);
        createArray(explanations, explanationsFileName);
        createCorrectAnswerArray(correctAnswersFileName);
    }

    /**
     * Clears the arraylist then rebuilds it with correct data
     * @param a arraylist
     * @param fn filename string
     */
    private void createArray(ArrayList a, String fn){
        a.clear();
        readFile(context, fn, a);

    }

    private void createAnswerArray(String fn){

        answers.clear();
        BufferedReader reader;

        try{
            final InputStream file = context.getAssets().open(fn);
            reader = new BufferedReader(new InputStreamReader(file));
            String line = reader.readLine();
            while(line != null){

                int comma1 = line.indexOf(";");
                int comma2 = line.indexOf(";", comma1+1);
                Log.d("Test", comma1+"");
                int comma3 = line.indexOf(";", comma2+1);
                int comma4 = line.indexOf(";", comma3+1);

                String answer1 = line.substring(0,comma1) ;
                String answer2 = line.substring(comma1+1,comma2) ;
                String answer3 = line.substring(comma2+1,comma3) ;
                String answer4 = line.substring(comma3+1,comma4) ;


                answers.add(Arrays.asList(answer1,answer2,answer3,answer4));
                //answers.add(new ArrayList<String>());

                line=reader.readLine();

            }
        } catch(IOException ioe){
            ioe.printStackTrace();
        }

    }

    private void createCorrectAnswerArray(String fn){

        correctAnswers.clear();
        BufferedReader reader;

        correctAnswers.add(new ArrayList<String>());


        int linecount=0;

        try{
            final InputStream file = context.getAssets().open(fn);
            reader = new BufferedReader(new InputStreamReader(file));
            String line = reader.readLine();
            while(line != null){

                int count = line.length() - line.replace(";", "").length();//counts occurences of semicolons in line

                if(count==1){
                    int colon1 = line.indexOf(";");
                    String answer1 = line.substring(0,colon1) ;
                    correctAnswers.get(linecount).add(answer1);
                }
                else if(count ==2){
                    int colon1 = line.indexOf(";");
                    int colon2 = line.indexOf(";", colon1+1);
                    String answer1 = line.substring(0,colon1) ;
                    String answer2 = line.substring(colon1+1,colon2) ;
                    correctAnswers.get(linecount).add(answer1);
                    correctAnswers.get(linecount).add(answer2);
                }
                else if(count ==3){
                    int colon1 = line.indexOf(";");
                    int colon2 = line.indexOf(";", colon1+1);
                    int colon3 = line.indexOf(";", colon2+1);


                    String answer1 = line.substring(0,colon1) ;
                    String answer2 = line.substring(colon1+1,colon2) ;
                    String answer3 = line.substring(colon2+1,colon3) ;

                    correctAnswers.get(linecount).add(answer1);
                    correctAnswers.get(linecount).add(answer2);
                    correctAnswers.get(linecount).add(answer3);
                }

                linecount++;

                correctAnswers.add(new ArrayList<String>());

                line=reader.readLine();

            }
        } catch(IOException ioe){
            ioe.printStackTrace();
        }

    }

    //returns the answer choices for the buttons
    public String getChoice(int i,int q){
        return isAnswerBlank(i,q);
    }

    public String getExplanation(int i){
        return explanations.get(i);
    }

    public String getQuestion(int i){
        return questions.get(i);
    }

    public ArrayList getCorrectAnswer(int i){
        return correctAnswers.get(i);
    }

    //returns the amount of questions
    public int getAnswerAmount(int i){
        answerAmount=correctAnswers.get(i).size();
        return answerAmount;
    }

    public int getQuestionAmount(){
        return questions.size();
    }

    /**
     * Tests whether the answer is blank or not to delete the button
     * @param i int row
     * @param q int column
     * @return
     */
    public String isAnswerBlank(int i, int q){
        if(!answers.get(i).get(q).equals(" ")){
            String choice= answers.get(i).get(q);
            return choice;
        }
        else{
            return "";
        }
    }




    /**
     * Uses fileio to read existing txt files to fetch appropriate questions, answers, explanations, and correct answers
     * @param context application context
     * @param name filename
     * @param a arraylist
     */
    private void readFile(Context context, String name, ArrayList a){
        BufferedReader reader;

        try{
            final InputStream file = context.getAssets().open(name);
            reader = new BufferedReader(new InputStreamReader(file));
            String line = reader.readLine();
            while(line != null){


                a.add(line);
                line=reader.readLine();

            }
        } catch(IOException ioe){
            ioe.printStackTrace();
        }
    }



    private void test(){
        Log.d("MyTag", answers.size()+"");

        for (int i=0; i<answers.size(); i++){
            for(int j=0; j<answers.get(i).size(); j++){
                Log.d("MyTag", "answers.get"+i+" "+j);
                Log.d("MyTag", answers.get(i).get(j));
            }
        }
    }


}