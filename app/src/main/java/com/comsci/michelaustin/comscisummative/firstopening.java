package com.comsci.michelaustin.comscisummative;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;

public class firstopening extends AppCompatActivity {
    ImageButton buoyButton;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstopening);

        buoyButton = findViewById(R.id.lgbuoy);

        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        buoyButton.startAnimation(shake);

        buoyButton.setOnClickListener(new View.OnClickListener() {

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

                                        if (!MainActivity.userName.isEmpty()){
                                            mainMenu(menuopening.class);
                                            finish();
                                        }
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


    }

    public void changelayout(View view){
        Intent infoPage = new Intent (this, info.class);
        startActivity(infoPage);
    }

    public void mainMenu (Class c){
        Intent mainMenu = new Intent (this, c);
        startActivity(mainMenu);
    }
}
