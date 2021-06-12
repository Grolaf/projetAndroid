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
        setContentView(R.layout.activity_inscription);
        this.editTextprenom = findViewById(R.id.prenom);
        this.editTextnom = findViewById(R.id.nom);
        this.mDb = DatabaseClient.getInstance(getApplicationContext());
    }

    public void saveUser(View v) {

        // Récupérer les informations contenues dans les vues
        final String prenom = this.editTextprenom.getText().toString().trim();
        final String nom = this.editTextnom.getText().toString().trim();

        // Vérifier les informations fournies par l'utilisateur
        if (prenom.isEmpty()) {
            this.editTextprenom.setError("Il faut entrer ton prénom");
            this.editTextprenom.requestFocus();
            return;
        }

        if (nom.isEmpty()) {
            this.editTextnom.setError("Il faut entrer ton nom");
            this.editTextnom.requestFocus();
            return;
        }

        /**
         * Création d'une classe asynchrone pour sauvegarder la tache donnée par l'utilisateur
         */
        class SaveUser extends AsyncTask<Void, Void, Utilisateur> {

            @Override
            protected Utilisateur doInBackground(Void... voids) {

                Utilisateur utilisateur = mDb.getAppDatabase().utilisateurDAO().getUtilisateur(prenom, nom);

                if(utilisateur != null)
                {
                    return null;
                }

                utilisateur = new Utilisateur(prenom, nom, "@drawable/profile");

                mDb.getAppDatabase()
                        .utilisateurDAO()
                        .insert(utilisateur);

                return utilisateur;
            }

            @Override
            protected void onPostExecute(Utilisateur utilisateur) {
                super.onPostExecute(utilisateur);

                if(utilisateur == null)
                {
                    Toast.makeText(getApplicationContext(), "Un utilisateur porte déjà ces identifiants...", Toast.LENGTH_SHORT).show();
                }
                else {

                    // Quand la tache est créée, on arrête l'activité AddTaskActivity (on l'enleve de la pile d'activités)
                    setResult(RESULT_OK);
                    Intent intent = new Intent(InscriptionActivity.this, MenuMatieresActivity.class);
                    intent.putExtra(MenuMatieresActivity.UTILISATEUR, utilisateur);
                    startActivity(intent);
                    finish();
                    Toast.makeText(getApplicationContext(), "Inscription réussie", Toast.LENGTH_LONG).show();
                }
            }
        }

        SaveUser st = new SaveUser();
        st.execute();
    }

    public void seConnecter(View v)
    {
        Intent intent = new Intent(InscriptionActivity.this, ListElevesActivity.class);
        startActivity(intent);
        finish();
    }


}