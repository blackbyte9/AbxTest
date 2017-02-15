package com.abraxy.abxtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class CharActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("CharActivity", "onCreate");
        setContentView(R.layout.activity_char);
    }

    public void onProfileButton(View view) {
        Log.v("CharActivity", "onProfileButton");
    }

    public void onFighterButton(View view) {
        Log.v("CharActivity", "onFighterButton");
    }

    public void onSettingsButton(View view) {
        Log.v("CharActivity", "onSettingsButton");
    }

    public void onHelpButton(View view) {
        Log.v("CharActivity", "onHelpButton");
    }
}
