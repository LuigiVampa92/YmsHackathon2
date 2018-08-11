package com.luigivampa92.hackathon2uncompressed;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends Activity {

    //SharedPreferences sharedPreferences;
    String currentLayout;
    Button normalGameBtn, hardGameBtn;
    ImageButton settingsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constants.SCREEN_WIDTH = dm.widthPixels;
        Constants.SCREEN_HEIGHT = dm.heightPixels;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (currentLayout == null) {
            launchScreen();
        }

    }

    public void launchScreen() {
        currentLayout = "splashScreen";
        setContentView(R.layout.activity_main);

        normalGameBtn = (Button)findViewById(R.id.normalGameBtn);
        normalGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchNormalGame();
            }
        });

        hardGameBtn = (Button)findViewById(R.id.hardGameBtn);
        hardGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchHardGame();
            }
        });

        settingsBtn = (ImageButton)findViewById(R.id.settingsBtn);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchSettings();
            }
        });
    }

    public void launchNormalGame() {
        currentLayout = "normalGame";
        start();
    }

    public void launchHardGame() {
        currentLayout = "hardGame";
        start();
    }

    private void start() {
        Intent normal = new Intent(MainActivity.this, GameActivity.class);
        startActivity(normal.putExtra(GameActivity.LVL, currentLayout));
    }

    public void launchSettings() {
        Intent activity = new Intent(this, PreferencesActivity.class);
        startActivity(activity);
    }
}
