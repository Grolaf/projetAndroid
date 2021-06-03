package com.example.scolastic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import tests.DataBaseTests;
import tests.ExercicesTests;

public class  MainActivity extends AppCompatActivity {



    private static int SPLASH_TIME_OUT = 3000;
    Button start_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        executeTests(this);


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

    private void executeTests(Context context)
    {
        class Tests extends AsyncTask<Void, Void, Void>
        {
            @Override
            protected Void doInBackground(Void... voids)
            {
                if(android.os.Debug.isDebuggerConnected())
                    android.os.Debug.waitForDebugger();

                ExercicesTests tests = new ExercicesTests();
                DataBaseTests testsDB = new DataBaseTests(context);

                return null;
            }
        }

        Tests t = new Tests();
        t.execute();
    }
}