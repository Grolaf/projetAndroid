package com.example.scolastic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;

import adapters.LigneCalculAdapter;
import model.Calcul;
import model.LigneCalcul;
import model.Niveau;
import tests.ExercicesTests;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ExercicesTests e = new ExercicesTests();
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.listView);

    }

    public void toCalculer(View v)
    {
        Intent it = new Intent(this, ExerciceCalculActivity.class);
        startActivity(it);
    }
}