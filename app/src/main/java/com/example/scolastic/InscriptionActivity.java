package com.example.scolastic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import database.DatabaseClient;
import model.Utilisateur;

public class InscriptionActivity extends AppCompatActivity {

    private DatabaseClient mDb;

    private EditText editTextprenom;
    private EditText editTextnom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_connect_page);
        editTextprenom = findViewById(R.id.prenom);
        editTextnom = findViewById(R.id.nom);
        mDb = DatabaseClient.getInstance(getApplicationContext());
    }

    public void saveUser(View v) {

        // Récupérer les informations contenues dans les vues
        final String prenom = editTextprenom.getText().toString().trim();
        final String nom = editTextnom.getText().toString().trim();

        // Vérifier les informations fournies par l'utilisateur
        if (prenom.isEmpty()) {
            editTextprenom.setError("Il faut entrer ton prénom");
            editTextprenom.requestFocus();
            return;
        }

        if (nom.isEmpty()) {
            editTextnom.setError("Il faut entrer ton nom");
            editTextnom.requestFocus();
            return;
        }

        /**
         * Création d'une classe asynchrone pour sauvegarder la tache donnée par l'utilisateur
         */
        class SaveTask extends AsyncTask<Void, Void, Utilisateur> {

            @Override
            protected Utilisateur doInBackground(Void... voids) {

                Utilisateur utilisateur = new Utilisateur(prenom, nom, "@drawable/user");

                mDb.getAppDatabase()
                        .utilisateurDAO()
                        .insert(utilisateur);

                return utilisateur;
            }

            @Override
            protected void onPostExecute(Utilisateur utilisateur) {
                super.onPostExecute(utilisateur);

                // Quand la tache est créée, on arrête l'activité AddTaskActivity (on l'enleve de la pile d'activités)
                setResult(RESULT_OK);
                Intent intent = new Intent(InscriptionActivity.this, PageProfilActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(getApplicationContext(), "Inscription réussie", Toast.LENGTH_LONG).show();
            }
        }

        //////////////////////////
        // IMPORTANT bien penser à executer la demande asynchrone
        SaveTask st = new SaveTask();
        st.execute();
    }

    public void seConnecter(View v)
    {
        Intent intent = new Intent(InscriptionActivity.this, PageProfilActivity.class);
        startActivity(intent);
        finish();
    }


}