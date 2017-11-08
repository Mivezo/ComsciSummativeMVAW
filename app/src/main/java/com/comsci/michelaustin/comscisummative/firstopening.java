package com.comsci.michelaustin.comscisummative;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class firstopening extends AppCompatActivity {
    ImageButton buoyButton, infoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstopening);

        buoyButton = findViewById(R.id.lgbuoy);
        infoButton = findViewById(R.id.info);

        buoyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "lgbuoy", Toast.LENGTH_SHORT).show();
            }
        });

        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "info", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
