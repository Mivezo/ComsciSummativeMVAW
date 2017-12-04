package com.comsci.michelaustin.comscisummative;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

public class mainMenu extends AppCompatActivity {

    ImageButton playBuoy, resetButton;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


        playBuoy = findViewById(R.id.playbuoy);
        resetButton = findViewById(R.id.resetButton);

        Animation shake = AnimationUtils.loadAnimation(this, R.anim.turn);
        playBuoy.startAnimation(shake);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.beachwaves);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        playBuoy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startClass(menuopening.class);
                mediaPlayer.stop();
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

}

