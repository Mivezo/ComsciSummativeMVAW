package com.comsci.michelaustin.comscisummative;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class firstopening extends AppCompatActivity {
    ImageButton buoyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstopening);

        buoyButton = (ImageButton) findViewById(R.id.imageButton2);

        buoyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Your answer is correct!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
