package com.comsci.michelaustin.comscisummative;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.List;

public class info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
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
                        mainMenu.mediaPlayerMain.stop();
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

}
