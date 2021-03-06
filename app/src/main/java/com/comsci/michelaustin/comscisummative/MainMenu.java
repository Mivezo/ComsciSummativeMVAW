package com.comsci.michelaustin.comscisummative;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import java.util.List;

public class MainMenu extends AppCompatActivity {

    ImageButton playBuoy, resetButton,infoButton;
    public static MediaPlayer mediaPlayerMain;
    private int marker, marker2;
    PowerManager pm;

    /*
            SIMILAR TO FIRSTOPENING - Comments
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        marker = 0;
        marker2 = 0;

        pm = (PowerManager) getSystemService(Context.POWER_SERVICE);

        playBuoy = findViewById(R.id.playbuoy);
        infoButton = findViewById(R.id.info);
        resetButton = findViewById(R.id.resetButton);

        Animation shake = AnimationUtils.loadAnimation(this, R.anim.turn);
        playBuoy.startAnimation(shake);

        mediaPlayerMain = MediaPlayer.create(getApplicationContext(), R.raw.beachwaves);
        mediaPlayerMain.setLooping(true);
        mediaPlayerMain.start();

        playBuoy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startClass(ModuleMenu.class);
                mediaPlayerMain.stop();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startClass(SettingsPage.class);
            }
        });


    }

    public void changelayoutinfo (View view){
        Intent infoPage = new Intent (this, Info.class);
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
        }

        Context context = getApplicationContext();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);

        if (!taskInfo.isEmpty()) {
            ComponentName topActivity = taskInfo.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                mediaPlayerMain.pause();
                marker2=1;
            }
            else {
                //Toast.makeText(MainMenu.this, "YOU SWITCHED ACTIVITIES WITHIN YOUR APP", Toast.LENGTH_SHORT).show();
            }
        }
        super.onPause();
    }


    @Override
    protected void onResume(){
        if (marker2==1){
            mediaPlayerMain.start();
        }
        super.onResume();
    }

    @Override
    protected void onStop(){
        if(!(pm.isInteractive())){
            if (mediaPlayerMain.isPlaying()){
                mediaPlayerMain.pause();
                marker = 1;
            }
        }
        super.onStop();
    }

    @Override
    protected void onRestart(){
        if (marker == 1){
            mediaPlayerMain.start();
        }
        super.onRestart();
    }
}

