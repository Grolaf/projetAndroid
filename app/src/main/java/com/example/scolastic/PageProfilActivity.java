package com.example.scolastic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import model.Utilisateur;

public class PageProfilActivity extends AppCompatActivity {

    public static final String UTILISATEUR = "utilisteur";

    private Utilisateur utilisateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activty_profile_page);

        utilisateur = getIntent().getParcelableExtra(UTILISATEUR);
    }
}