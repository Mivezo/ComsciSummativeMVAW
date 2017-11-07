package com.comsci.michelaustin.comscisummative;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences("com.comsci.michelaustin.comscisummative", MODE_PRIVATE);

        if (prefs.getBoolean("firstrun", true)) {
          Intent startMenu = new Intent(this, firstopening.class);
          startActivity(startMenu);
          prefs.edit().putBoolean("firstrun", false).apply();
        }
        else{
            setContentView(R.layout.activity_main);
        }

    }
}
