package com.comsci.michelaustin.comscisummative;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Austin on 2017-12-04.
 */

public class QuestionLibrary {

    private final Context context;

    String questionFileName;
    String answerFileName;
    String explanationsFileName;
    String correctAnswersFileName;

    private ArrayList<Integer> randomInt = new ArrayList<Integer>();

    private long seed = System.nanoTime();

    private ArrayList <String> questions= new ArrayList<String>();
    private List<List<String>> answers = new ArrayList<List<String>>();
    ArrayList<ArrayList<String>> correctAnswers = new ArrayList<ArrayList<String>>();
    private ArrayList <String> explanations= new ArrayList<String>();

    public ArrayList <String> testQuestions= new ArrayList<String>();
    public List<List<String>> testAnswers = new ArrayList<List<String>>();
    public ArrayList<String> testCorrectAnswers = new ArrayList<String>();

    public ArrayList <String> tempCorrectAnswers= new ArrayList<String>();


    int moduleID;
    int answerAmount;


    public QuestionLibrary(Context c){
        context=c;
    }

    public QuestionLibrary(int mID, Context c){
        moduleID=mID;
        context=c;

        createFileNames();

        randomInt.add(0);

        buildArrays();

        if(moduleID==6) {
            randomizeArrays();
        }

    }

    private void createFileNames(){
        if(moduleID!=6){
            explanationsFileName="explanations"+moduleID+".txt";
        }
        questionFileName="questions"+moduleID+".txt";
        answerFileName="answers"+moduleID+".txt";
        correctAnswersFileName="correctanswers"+moduleID+".txt";
    }

    /**
     * Creates all arrays
     */
    private void buildArrays(){
        //adds arbitrary values to the arraylist as using the arrays.clear method returns null if there are no preassigned values
        questions.add("!");
        answers.add(Arrays.asList("1"));
        correctAnswers.add(new ArrayList<String>());
        correctAnswers.get(0).add("1");

        //no explanations needed for the test
        if(moduleID!=6){
            explanations.add("!");
            createArray(explanations, explanationsFileName);
            createCorrectAnswerArray(correctAnswersFileName);
        }
        else{
            testQuestions.add("f");
            testAnswers.add(Arrays.asList("f"));
            testCorrectAnswers.add("1");
            createArray(tempCorrectAnswers, correctAnswersFileName);
        }

        createArray(questions, questionFileName);
        createAnswerArray(answerFileName);

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

    /**
     * This method creates the answer arraylist based off of the answer txt file
     * @param fn filename
     */
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

    /**
     * Creates the correct answer arraylist based off of the txt file
     * @param fn
     */
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

        correctAnswers.remove(linecount);

    }

    //returns the answer choices for the buttons
    public String getChoice(int i,int q){
        if(moduleID!=6){
            return isAnswerBlank(i,q,answers);
        }
        else{
            return isAnswerBlank(i,q,testAnswers); //for test module
        }

    }

    public String getExplanation(int i){
        if(moduleID!=6){
            return explanations.get(i);
        }
        else return null;//for test module
    }

    public String getQuestion(int i){
        if(moduleID!=6){
            return questions.get(i);
        }
        else{
            return testQuestions.get(i);//for test module
        }
    }

    public ArrayList getCorrectAnswer(int i){
        return correctAnswers.get(i);
    }

    public String getTestCorrectAnswer(int i){
        return testCorrectAnswers.get(i);
    }



    //returns the amount of questions
    public int getAnswerAmount(int i){
        if(moduleID!=6){
            answerAmount=correctAnswers.get(i).size();
        }
        else answerAmount=1;//for test module

        return answerAmount;
    }

    //Shuffles the question, answer and correctanswer arrays. Takes the 1st 20 and puts it into a new arraylist with the prefixes test
    public void randomizeArrays(){

        randomInt.clear();

        for(int i=0; i<50; i++){
            randomInt.add(i);
        }
        Collections.shuffle(randomInt, new Random(seed));

        testQuestions.clear();
        testAnswers.clear();
        testCorrectAnswers.clear();


        for(int i=0; i<20; i++){
            testQuestions.add(questions.get(randomInt.get(i)));
            testAnswers.add(answers.get(randomInt.get(i)));
            testCorrectAnswers.add(tempCorrectAnswers.get(randomInt.get(i)));
        }


    }

    public int getQuestionAmount(){
        if(moduleID!=6){
            return questions.size();
        }
        else return testQuestions.size();

    }

    /**
     * Tests whether the answer is blank or not to delete the button
     * @param i int row
     * @param q int column
     * @param a 2d answer arraylist
     * @return
     */
    public String isAnswerBlank(int i, int q, List<List<String>> a){
        if(!a.get(i).get(q).equals(" ")){
            String choice= a.get(i).get(q);
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
        Log.d("MyTag", correctAnswers.size()+"");

        for (int i=0; i<correctAnswers.size(); i++){
            Log.d("TEST2: "+i, questions.get(i)+"");
            Log.d("TEST1: "+i, correctAnswers.get(i)+"");

        }
    }
}