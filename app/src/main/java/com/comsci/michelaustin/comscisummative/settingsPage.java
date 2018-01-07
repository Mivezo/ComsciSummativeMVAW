package com.comsci.michelaustin.comscisummative;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
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
    SharedPreferences mSharedPrefs;
    SharedPreferences.Editor mEditor;
    int mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mProgress = 0;
        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);

        reset = findViewById(R.id.resetbutton);
        name = findViewById(R.id.changename);
        voice = findViewById(R.id.voice);
        vib = findViewById(R.id.vibration);

        retrievedVolume = fileIo.readFromFile(this, "volume.txt");
        userVolume = Float.parseFloat(retrievedVolume);

        if (mSharedPrefs != null) {
            mProgress = mSharedPrefs.getInt("mMySeekBarProgress", 0);
        }

        hi = MediaPlayer.create(getApplicationContext(), R.raw.song1);
        hi.setLooping(true);
        hi.setVolume(userVolume, userVolume);
        hi.start();

        mVolumeControl = findViewById(R.id.volume_control);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mVolumeControl.setMax(MAX_VOLUME);

        if (mProgress == 0) {
            mVolumeControl.setProgress(MAX_VOLUME * mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC) / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        } else {
            mVolumeControl.setProgress(mProgress);
        }
        mVolumeControl.setOnSeekBarChangeListener(mVolumeControlChangeListener);

        mutestatus = fileIo.readFromFile(this, "voiceMute.txt");
        vibstatus = fileIo.readFromFile(this, "vibration.txt");

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
    }

    private SeekBar.OnSeekBarChangeListener mVolumeControlChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            volume = (float) (1 - (Math.log(MAX_VOLUME - progress) / Math.log(MAX_VOLUME)));
            hi.setVolume(volume, volume);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
            mEditor = mSharedPrefs.edit();
            int mProgress = mVolumeControl.getProgress();
            mEditor.putInt("mMySeekBarProgress", mProgress).apply();
            fileIo.writeFile("" + volume, "volume.txt", context);
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
                mainMenu.mediaPlayerMain.stop();
                hi.stop();
                Toast.makeText(settingsPage.this, "YOU LEFT YOUR APP", Toast.LENGTH_SHORT).show();
            }
        }
    }
}