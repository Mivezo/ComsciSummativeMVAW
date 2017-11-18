package com.comsci.michelaustin.comscisummative;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class menuopening extends AppCompatActivity {

    String name = MainActivity.userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuopening);
        fileIo.writeFile(name,this);

        TextView nameshow = (TextView) findViewById(R.id.menuname);
        nameshow.setText(name);

    }
}
