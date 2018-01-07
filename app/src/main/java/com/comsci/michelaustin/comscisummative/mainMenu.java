package com.comsci.michelaustin.comscisummative;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

public class mainMenu extends AppCompatActivity {

    ImageButton playBuoy, resetButton;
    public static MediaPlayer mediaPlayerMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        playBuoy = findViewById(R.id.playbuoy);
        resetButton = findViewById(R.id.resetButton);

        Animation shake = AnimationUtils.loadAnimation(this, R.anim.turn);
        playBuoy.startAnimation(shake);

        mediaPlayerMain = MediaPlayer.create(getApplicationContext(), R.raw.beachwaves);
        mediaPlayerMain.setLooping(true);
        mediaPlayerMain.start();

        playBuoy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startClass(menuopening.class);
                mediaPlayerMain.stop();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startClass(settingsPage.class);
            }
        });


    }

    public void changelayoutinfo (View view){
        Intent infoPage = new Intent (this, info.class);
        startActivity(infoPage);
    }

    public void startClass (Class c){
        Intent mainMenu = new Intent (this, c);
        startActivity(mainMenu);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onPause() {
        if (this.isFinishing()){ //basically BACK was pressed from this activity
            mediaPlayerMain.stop();
            Toast.makeText(mainMenu.this, "YOU PRESSED BACK FROM YOUR 'HOME/MAIN' ACTIVITY", Toast.LENGTH_SHORT).show();
        }

        Context context = getApplicationContext();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);

        if (!taskInfo.isEmpty()) {
            ComponentName topActivity = taskInfo.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                mediaPlayerMain.stop();
                Toast.makeText(mainMenu.this, "YOU LEFT YOUR APP", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(mainMenu.this, "YOU SWITCHED ACTIVITIES WITHIN YOUR APP", Toast.LENGTH_SHORT).show();
            }
        }
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(mediaPlayerMain.isPlaying()){
            mediaPlayerMain.stop();
        }
    }
}

