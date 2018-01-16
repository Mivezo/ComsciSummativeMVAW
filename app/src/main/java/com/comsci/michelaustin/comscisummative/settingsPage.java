package com.comsci.michelaustin.comscisummative;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.List;

public class settingsPage extends AppCompatActivity {

    private ImageButton reset, name;
    private Context context = this;
    private ToggleButton voice, vib;
    private String mutestatus, vibstatus;
    private final static int MAX_VOLUME = 100;
    private AudioManager mAudioManager;
    private SeekBar mVolumeControl;
    private MediaPlayer hi;
    private float volume;
    private String retrievedVolume;
    private float userVolume;
    private int progressBar;
    private int marker, marker2;
    PowerManager pm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);

        pm = (PowerManager) getSystemService(Context.POWER_SERVICE);

        progressBar = 0;

        marker = 0;
        marker2 = 0;

        reset = findViewById(R.id.resetbutton);
        name = findViewById(R.id.changename);
        voice = findViewById(R.id.voice);
        vib = findViewById(R.id.vibration);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.restart_module_prompt, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                TextView tv = promptsView.findViewById(R.id.textView1);
                tv.setText("Are you sure?");
                tv.setTextColor(Color.BLACK);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        fileIo.writeFile("0;","testScores.txt", getApplicationContext());
                                        dialog.dismiss();
                                        Toast.makeText(settingsPage.this, "SCORES RESET!", Toast.LENGTH_SHORT).show();
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

        retrievedVolume = fileIo.readFromFile(this, "volume.txt");
        userVolume = Float.parseFloat(retrievedVolume);

        progressBar = Integer.parseInt(fileIo.readFromFile(this, "seekbar.txt"));

        hi = MediaPlayer.create(getApplicationContext(), R.raw.song1);
        hi.setLooping(true);
        hi.setVolume(userVolume, userVolume);

        mVolumeControl = findViewById(R.id.volume_control);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mVolumeControl.setMax(MAX_VOLUME);

        //if (progressBar == null) {
       //     mVolumeControl.setProgress(MAX_VOLUME * mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC) / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
      //  } else {
            mVolumeControl.setProgress(progressBar);
      //  }

        mVolumeControl.setOnSeekBarChangeListener(mVolumeControlChangeListener);

        mutestatus = fileIo.readFromFile(this, "voiceMute.txt");
        vibstatus = fileIo.readFromFile(this, "vibration.txt");

        Log.d("myTag3", ""+mVolumeControl.getProgress());
        if (mutestatus.equals("muted")) {
            voice.setChecked(true);
        }

        if (vibstatus.equals("off")) {
            vib.setChecked(true);
        }


        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.name_enter_popup, null);

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
                                    public void onClick(DialogInterface dialog, int id) {
                                        MainActivity.userName = userInput.getText().toString();
                                        // get user input and set it to result
                                        // edit text
                                        writenew(MainActivity.userName, "lifeguardname.txt");
                                        //   finish();

                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });
        Log.d("myTag4", ""+mVolumeControl.getProgress());
        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (voice.isChecked()) {
                    writenew("muted", "voiceMute.txt");
                    Toast.makeText(settingsPage.this, "Voice Assist MUTED", Toast.LENGTH_SHORT).show();
                } else {
                    writenew("notmuted", "voiceMute.txt");
                    Toast.makeText(settingsPage.this, "Voice Assist UnMuted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        vib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (vib.isChecked()) {
                    writenew("off", "vibration.txt");
                    Toast.makeText(settingsPage.this, "Vibrations turned OFF", Toast.LENGTH_SHORT).show();
                } else {
                    writenew("on", "vibration.txt");
                    Toast.makeText(settingsPage.this, "Vibrations turned ON", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Log.d("myTag5", ""+mVolumeControl.getProgress());
    }

    private SeekBar.OnSeekBarChangeListener mVolumeControlChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            volume = (float) (1 - (Math.log(MAX_VOLUME - progress) / Math.log(MAX_VOLUME)));
            hi.setVolume(volume, volume);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            Log.d("myTag", ""+mVolumeControl.getProgress());
            hi.start();
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            int mProgress = mVolumeControl.getProgress();
            fileIo.writeFile(mProgress+"", "seekbar.txt", context);
            fileIo.writeFile("" + volume, "volume.txt", context);
            hi.pause();
        }
    };

    private void writenew(String name, String file) {
        fileIo.writeFile(name, file, this);
    }

    @Override
    protected void onPause() {

        if (this.isFinishing()) {
            hi.stop();
        }
        super.onPause();
        Context context = getApplicationContext();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        if (!taskInfo.isEmpty()) {
            ComponentName topActivity = taskInfo.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                mainMenu.mediaPlayerMain.pause();
                hi.stop();
                marker2 = 1;
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