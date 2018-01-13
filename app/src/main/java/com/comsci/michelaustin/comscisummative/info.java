package com.comsci.michelaustin.comscisummative;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

public class info extends AppCompatActivity {

    PowerManager pm; //powermanager to see if screen is off
    int marker, marker2; //code markers

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        marker = 0;
        marker2 = 0;
    }

    @Override
    protected void onPause() {
        super.onPause();//first finish regular on pause
        Context context = getApplicationContext(); //get the application context
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE); //set an activity menu to see if user leaves app
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        if (!taskInfo.isEmpty()) { //if app is not open
            ComponentName topActivity = taskInfo.get(0).topActivity; //get the order of the recent apps
            if (!topActivity.getPackageName().equals(context.getPackageName())) { //if the top app of the recent apps is not this package (this app), then do something
                if (mainMenu.mediaPlayerMain!=null){ //error check
                    if (mainMenu.mediaPlayerMain.isPlaying()) { //error check
                        mainMenu.mediaPlayerMain.pause(); //pause the mediaplayer
                        marker2=1; //set the marker to one
                    }
                }
                if (firstopening.mediaPlayer!=null){ //if the first opening media player is set (so if this infopage was called from the first menu not the regular menu
                    if (firstopening.mediaPlayer.isPlaying()) {
                        firstopening.mediaPlayer.stop(); //stop media player
                    }
                }
            }
        }
    }

    @Override
    protected void onResume(){ //override the on resume to start the mediaplayer again if it is paused (which is checked with marker)
        if (marker2==1){
            mainMenu.mediaPlayerMain.start();
        }
        super.onResume();
    }

    @Override
    protected void onStop(){
        if(!(pm.isInteractive())){ //if the screen was turned off and the app cant be interacted with
            if (mainMenu.mediaPlayerMain.isPlaying()){
                mainMenu.mediaPlayerMain.pause(); //pause media player
                marker = 1;
            }
        }
        super.onStop();
    }

    @Override
    protected void onRestart(){
        if (marker == 1){ //if the app has been stopped and media player paused, resume it by checking the marker
            mainMenu.mediaPlayerMain.start();
        }
        super.onRestart();
    }
}
