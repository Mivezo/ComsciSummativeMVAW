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

    private ImageButton reset, name; //Initializing all the variables to be used in the class
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
    private PowerManager pm; //checks the devices power setting

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);

        pm = (PowerManager) getSystemService(Context.POWER_SERVICE); //specifically checks device power service

        progressBar = 0;

        marker = 0; //markers that are just used as temp variables
        marker2 = 0;

        reset = findViewById(R.id.resetbutton);
        name = findViewById(R.id.changename);
        voice = findViewById(R.id.voice);
        vib = findViewById(R.id.vibration);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //reset for the scores of the module menu
                LayoutInflater li = LayoutInflater.from(context); //layout used to make the popup
                View promptsView = li.inflate(R.layout.restart_module_prompt, null); //layout inflated to the restartmodulepromp

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context); //where the layout is actually stored then showed, in alertdialog

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                TextView tv = promptsView.findViewById(R.id.textView1);
                tv.setText("Are you sure?");
                tv.setTextColor(Color.BLACK);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK", //if user presses "Ok"
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        fileIo.writeFile("0;","testScores.txt", getApplicationContext()); //write that the test scores are 0
                                        dialog.dismiss();
                                        Toast.makeText(settingsPage.this, "SCORES RESET!", Toast.LENGTH_SHORT).show(); //give a toast that it has been done
                                    }
                                })
                        .setNegativeButton("Cancel", //if user presses cancel, do nothing
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel(); //close dialog
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

        progressBar = Integer.parseInt(fileIo.readFromFile(this, "seekbar.txt")); //take the string seekbar and turn into float to be set the seekbar - to keep users volume preference

        hi = MediaPlayer.create(getApplicationContext(), R.raw.song1);//sample song to be played when seekbar is held/moved
        hi.setLooping(true);//loop the music
        hi.setVolume(userVolume, userVolume); //set volume to stored volume

        mVolumeControl = findViewById(R.id.volume_control);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mVolumeControl.setMax(MAX_VOLUME);
        //mVolumeControl.setProgress(MAX_VOLUME * mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC) / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));

        mVolumeControl.setProgress(progressBar);

        mVolumeControl.setOnSeekBarChangeListener(mVolumeControlChangeListener);

        mutestatus = fileIo.readFromFile(this, "voiceMute.txt"); //read if mutestatus is turned on
        vibstatus = fileIo.readFromFile(this, "vibration.txt"); //read if vibration setting is turned on

        if (mutestatus.equals("muted")) {
            voice.setChecked(true); //if voice is muted, set the button to muted
        }

        if (vibstatus.equals("off")) {
            vib.setChecked(true); //if vibrations are turned off, set button to off
        }

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //when change name is clicked
                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.name_enter_popup, null); //same process as reset scores

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

                                        String test = userInput.getText().toString();

                                        if (!(test.equals(""))){ //does not let user enter nothing as their name
                                            MainActivity.userName = userInput.getText().toString();
                                            writenew(MainActivity.userName, "lifeguardname.txt");
                                        }
                                        else{
                                            Toast.makeText(settingsPage.this, "You must Enter a Name", Toast.LENGTH_SHORT).show(); //if user enters nothing, tell them
                                        }

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
            public void onClick(View v) { //if the user presses the voice

                if (voice.isChecked()) {
                    writenew("muted", "voiceMute.txt");//if it is checked, set status to voice muted
                    Toast.makeText(settingsPage.this, "Voice Assist MUTED", Toast.LENGTH_SHORT).show();
                } else {
                    writenew("notmuted", "voiceMute.txt"); //if status is not checked, set to unmuted
                    Toast.makeText(settingsPage.this, "Voice Assist UnMuted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        vib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //vibration button listener

                if (vib.isChecked()) {
                    writenew("off", "vibration.txt"); //if it is checked, turn vibration status to off
                    Toast.makeText(settingsPage.this, "Vibrations turned OFF", Toast.LENGTH_SHORT).show();
                } else {
                    writenew("on", "vibration.txt"); //if it is not checked, turn vibrations to on
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
        public void onStartTrackingTouch(SeekBar seekBar) { //when seekbar starts moving, turn on sample music
            hi.start();
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) { //when seekbar stops moving, paush sample music and set progress & volume preference
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
    protected void onPause() { //on pause of activitt

        if (this.isFinishing()) { //if the song is finishing, stop it
            hi.stop();
        }
        super.onPause(); //call super of onpause
        Context context = getApplicationContext(); //check context of app, if homebutton is beingpressed
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE); //check activity of homebutton being pressed
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        if (!taskInfo.isEmpty()) {
            ComponentName topActivity = taskInfo.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) { //if the homebutton is actually being pressed
                mainMenu.mediaPlayerMain.pause(); //pause seagull music
                hi.stop(); //stop sample music player
                marker2 = 1; //set marker to 1
            }
        }
    }

    @Override
    protected void onResume(){
        if (marker2==1){ //On reopening app from recents, if the marker is 1, then start the seagull music again
            mainMenu.mediaPlayerMain.start();
        }
        super.onResume();
    }

    @Override
    protected void onStop(){
        if(!(pm.isInteractive())){ //if the phone has been turned off
            if (mainMenu.mediaPlayerMain.isPlaying()){ //if seagull is playing
                mainMenu.mediaPlayerMain.pause(); //turn off
                marker = 1;
            }
        }
        super.onStop();
    }

    @Override
    protected void onRestart(){
        if (marker == 1){ //check marker if phone has been stoped
            mainMenu.mediaPlayerMain.start(); //restart the seagull
        }
        super.onRestart();
    }
}