package com.comsci.michelaustin.comscisummative;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.List;

public class info extends AppCompatActivity {

    PowerManager pm;
    int marker, marker2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        marker = 0;
        marker2 = 0;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Context context = getApplicationContext();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        if (!taskInfo.isEmpty()) {
            ComponentName topActivity = taskInfo.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                if (mainMenu.mediaPlayerMain!=null){
                    if (mainMenu.mediaPlayerMain.isPlaying()) {
                        mainMenu.mediaPlayerMain.pause();
                        marker2=1;
                    }
                }
                if (firstopening.mediaPlayer!=null){
                    if (firstopening.mediaPlayer.isPlaying()) {
                        firstopening.mediaPlayer.stop();
                    }
                }
                Toast.makeText(info.this, "YOU LEFT YOUR APP", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume(){
        if (marker2==1){
            mainMenu.mediaPlayerMain.start();
        }
        super.onResume();
    }

    @Override
    protected void onStop(){
        if(!(pm.isInteractive())){
            if (mainMenu.mediaPlayerMain.isPlaying()){
                mainMenu.mediaPlayerMain.pause();
                marker = 1;
            }
        }
        super.onStop();
    }

    @Override
    protected void onRestart(){
        if (marker == 1){
            mainMenu.mediaPlayerMain.start();
        }
        super.onRestart();
    }

}
