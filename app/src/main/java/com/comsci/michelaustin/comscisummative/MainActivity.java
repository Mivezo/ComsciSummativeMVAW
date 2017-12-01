package com.comsci.michelaustin.comscisummative;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    SharedPreferences prefs = null;
    public static String userName;
    Intent startMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences("com.comsci.michelaustin.comscisummative", MODE_PRIVATE);

        userName = fileIo.readFromFile(this, "lifeguardname.txt");

        if (prefs.getBoolean("firstrun", true) || userName == null) {
            prefs.edit().putBoolean("firstrun", false).apply();
            startMenu = new Intent(this, firstopening.class);
            startActivity(startMenu);
        }
        else{
            startMenu = new Intent(this, mainMenu.class);
            startActivity(startMenu);
        }
    }
}
