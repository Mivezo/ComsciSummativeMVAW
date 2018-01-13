package com.comsci.michelaustin.comscisummative;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

public class firstopening extends AppCompatActivity {
    ImageButton buoyButton;
    final Context context = this;
    public static MediaPlayer mediaPlayer;
    PowerManager pm;
    int marker, marker2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstopening);
        pm = (PowerManager) getSystemService(Context.POWER_SERVICE);

        buoyButton = findViewById(R.id.lgbuoy);

        marker = 0;
        marker2 = 0;

        Animation shake = AnimationUtils.loadAnimation(this, R.anim.turn);
        buoyButton.startAnimation(shake);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.beachwaves);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        buoyButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.promp, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView.findViewById(R.id.nameedit);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        MainActivity.userName = userInput.getText().toString();
                                        // get user input and set it to result
                                        // edit text

                                        if (!MainActivity.userName.isEmpty()){
                                            mainMenu(menuopening.class);
                                            finish();
                                        }
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });


    }

    public void changelayout(View view){
        Intent infoPage = new Intent (this, info.class);
        startActivity(infoPage);
    }

    public void mainMenu (Class c){
        Intent mainMenu = new Intent (this, c);
        startActivity(mainMenu);
    }

    @Override
    protected void onPause() {
        if (this.isFinishing()){ //basically BACK was pressed from this activity
            mediaPlayer.stop();
            Toast.makeText(firstopening.this, "YOU PRESSED BACK FROM YOUR 'HOME/MAIN' ACTIVITY", Toast.LENGTH_SHORT).show();
        }

        Context context = getApplicationContext();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);

        if (!taskInfo.isEmpty()) {
            ComponentName topActivity = taskInfo.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                mediaPlayer.pause();
                marker2=1;
                Toast.makeText(firstopening.this, "YOU LEFT YOUR APP", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(firstopening.this, "YOU SWITCHED ACTIVITIES WITHIN YOUR APP", Toast.LENGTH_SHORT).show();
            }
        }
        super.onPause();
    }

    @Override
    protected void onResume(){
        if (marker2==1){
            mediaPlayer.start();
        }
        super.onResume();
    }

    @Override
    protected void onStop(){
        if(!(pm.isInteractive())){
            if (mediaPlayer.isPlaying()){
                mediaPlayer.pause();
                marker = 1;
            }
        }
        super.onStop();
    }

    @Override
    protected void onRestart(){
        if (marker == 1){
            mediaPlayer.start();
        }
        super.onRestart();
    }
}
