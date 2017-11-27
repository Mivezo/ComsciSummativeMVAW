package com.comsci.michelaustin.comscisummative;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

public class mainMenu extends AppCompatActivity {

    ImageButton playBuoy;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        playBuoy = findViewById(R.id.playbuoy);

        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        playBuoy.startAnimation(shake);

        playBuoy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mainMenu(menuopening.class);
            }
        });


    }

    public void changelayoutinfo (View view){
        Intent infoPage = new Intent (this, info.class);
        startActivity(infoPage);
    }

    public void mainMenu (Class c){
        Intent mainMenu = new Intent (this, c);
        startActivity(mainMenu);
    }
}

