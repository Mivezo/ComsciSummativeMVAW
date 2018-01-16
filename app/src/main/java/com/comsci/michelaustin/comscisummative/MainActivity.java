package com.comsci.michelaustin.comscisummative;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences prefs = null;
    public static String userName; //variables to be used
    private Intent startMenu;
    public static int first;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme); //setting the theme
        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences("com.comsci.michelaustin.comscisummative", MODE_PRIVATE);

        userName = fileIo.readFromFile(this, "lifeguardname.txt"); //read the users name and set it to public variable

        if (prefs.getBoolean("firstrun", true) || userName == null) { //if it is the first run or the user has not set their name for the first time
            prefs.edit().putBoolean("firstrun", false).apply();
            startMenu = new Intent(this, firstopening.class);
            createResumeFiles(); //create the module resume files
            startActivity(startMenu); //start the menu
        }
        else{
            startMenu = new Intent(this, mainMenu.class);
            first=0;//if the user has a name and is not running for the first time, send to menu
            startActivity(startMenu);
        }
    }

    private void createResumeFiles(){ //creating all the fileio text files for when the app is resumed
        for(int i=0; i<=6; i++){
            fileIo.writeFile("0", "resumeModule"+i+".txt", getApplicationContext());
            fileIo.writeFile("0", "resumeCorrectAnswers"+i+".txt",getApplicationContext());
        }
        fileIo.writeFile("0;", "testScores.txt", getApplicationContext());

        //Log.d("Test0",fileIo.readFromFile(this,"testScores.txt"));
        fileIo.appendLineFile("1;", "testScores.txt", getApplicationContext());
        //Log.d("Test1",fileIo.readFromFile(this,"testScores.txt"));
        fileIo.appendLineFile("2;", "testScores.txt", getApplicationContext());
        //Log.d("Test2",fileIo.readFromFile(this,"testScores.txt"));
        fileIo.appendLineFile("3;", "testScores.txt", getApplicationContext());
        //Log.d("Test3",fileIo.readFromFile(this,"testScores.txt"));
        fileIo.appendLineFile("4;", "testScores.txt", getApplicationContext());
        //Log.d("Test4",fileIo.readFromFile(this,"testScores.txt"));
        fileIo.appendLineFile("5;", "testScores.txt", getApplicationContext());
        fileIo.appendLineFile("6;", "testScores.txt", getApplicationContext());
        fileIo.appendLineFile("7;", "testScores.txt", getApplicationContext());
        fileIo.appendLineFile("8;", "testScores.txt", getApplicationContext());
        fileIo.appendLineFile("9;", "testScores.txt", getApplicationContext());
        fileIo.appendLineFile("10;", "testScores.txt", getApplicationContext());
        fileIo.appendLineFile("100;", "testScores.txt", getApplicationContext());
        Log.d("Test5",fileIo.readFromFile(this,"testScores.txt"));

        fileIo.writeFile("notmuted", "voiceMute.txt", getApplicationContext());
        fileIo.writeFile("on", "vibration.txt", getApplicationContext());
        fileIo.writeFile("1.0", "volume.txt", getApplicationContext());
        fileIo.writeFile("80", "seekbar.txt", getApplicationContext());
    }
}