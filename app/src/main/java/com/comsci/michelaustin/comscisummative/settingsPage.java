package com.comsci.michelaustin.comscisummative;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.List;

public class settingsPage extends AppCompatActivity {

    ImageButton reset, name;
    Context context = this;
    ToggleButton voice, vib;
    String mutestatus, vibstatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        reset = findViewById(R.id.resetbutton);
        name = findViewById(R.id.changename);
        voice = findViewById(R.id.voice);
        vib = findViewById(R.id.vibration);

        mutestatus = fileIo.readFromFile(this,"voiceMute.txt");
        vibstatus = fileIo.readFromFile(this, "vibration.txt");

        if (mutestatus.equals("muted")){
            voice.setChecked(true);
        }

        if (vibstatus.equals("off")){
            vib.setChecked(true);
        }

        name.setOnClickListener(new View.OnClickListener() {
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
                                        writenew(MainActivity.userName, "lifeguardname.txt");
                                        finish();

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

        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (voice.isChecked()){
                    writenew("muted","voiceMute.txt");
                    Toast.makeText(settingsPage.this, "Voice Assist MUTED", Toast.LENGTH_SHORT).show();
                }
                else{
                    writenew("notmuted","voiceMute.txt");
                    Toast.makeText(settingsPage.this, "Voice Assist UnMuted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        vib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (vib.isChecked()){
                    writenew("off","vibration.txt");
                    Toast.makeText(settingsPage.this, "Vibrations turned OFF", Toast.LENGTH_SHORT).show();
                }
                else{
                    writenew("on","vibration.txt");
                    Toast.makeText(settingsPage.this, "Vibrations turned ON", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void writenew (String name, String file){
        fileIo.writeFile(name,file,this);
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
                mainMenu.mediaPlayerMain.stop();
                Toast.makeText(settingsPage.this, "YOU LEFT YOUR APP", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
