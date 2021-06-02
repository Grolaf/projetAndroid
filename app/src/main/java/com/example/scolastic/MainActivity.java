package com.example.scolastic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import tests.ExercicesTests;

public class  MainActivity extends AppCompatActivity {



    private static int SPLASH_TIME_OUT = 3000;
    Button start_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        ExercicesTests tests = new ExercicesTests();

        start_btn = findViewById(R.id.start_btn);



        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), InscriptionActivity.class));
            }
        });

       /* new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, InscriptionActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIME_OUT); */
    }
}