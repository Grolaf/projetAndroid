package com.example.scolastic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import tests.ExercicesTests;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ExercicesTests e = new ExercicesTests();
        setContentView(R.layout.activity_main);
    }
}