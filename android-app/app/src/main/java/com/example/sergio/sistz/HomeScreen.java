package com.example.sergio.sistz;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class HomeScreen extends AppCompatActivity {
    private static boolean homescreen = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        int secondsDelayed = 5;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                finish();
            }
        }, secondsDelayed * 500);

    }
}
