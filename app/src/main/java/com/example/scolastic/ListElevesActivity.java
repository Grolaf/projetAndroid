package com.example.scolastic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import adapters.UtilisateurAdapter;
import database.DatabaseClient;
import model.Utilisateur;

public class ListElevesActivity extends AppCompatActivity {

    private DatabaseClient mDb;
    private ArrayAdapter adapter;
    private HashMap<Integer, Utilisateur> mapUtilisateurs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_eleves);

        this.mDb = DatabaseClient.getInstance(getApplicationContext());

        // Récupérer les vues
        ListView gL = (ListView) findViewById(R.id.listViewAdapter);

        // Lier l'adapter au gridView
        this.adapter = new UtilisateurAdapter(this, R.layout.utilisateur_adapter_view, new ArrayList<>());
        gL.setAdapter(this.adapter);

        mapUtilisateurs = new HashMap<>();
        getUtilisateurs();
    }

    private void getUtilisateurs() {
        ///////////////////////
        // Classe asynchrone permettant de récupérer des taches et de mettre à jour le listView de l'activité
        class GetUtilisateurs extends AsyncTask<Void, Void, List<Utilisateur>> {

            @Override
            protected List<Utilisateur> doInBackground(Void... voids) {
                List<Utilisateur> utilisateurs = mDb.getAppDatabase()
                        .utilisateurDAO()
                        .getAll();

                return utilisateurs;
            }

            @Override
            protected void onPostExecute(List<Utilisateur> utilisateurs) {
                super.onPostExecute(utilisateurs);

                for(Utilisateur u : utilisateurs)
                {
                    mapUtilisateurs.put(u.getUtilisateurID(), u);
                }
                // Mettre à jour l'adapter avec la liste de matieres
                adapter.clear();
                adapter.addAll(utilisateurs);

                // Now, notify the adapter of the change in source
                adapter.notifyDataSetChanged();


            }
        }

        GetUtilisateurs gt = new GetUtilisateurs();
        gt.execute();
    }

    public void seConnecter(View view)
    {
        Utilisateur choix = mapUtilisateurs.get(view.getId());
        Intent intent = new Intent(this, MenuMatieresActivity.class);
        intent.putExtra(MenuMatieresActivity.UTILISATEUR, choix);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), "Connecté en tant que " + choix.getPrenom() + " " + choix.getNom() ,  Toast.LENGTH_LONG).show();
    }

    public void inscription(View view)
    {
        Intent intent = new Intent(this, InscriptionActivity.class);
        startActivity(intent);
        finish();
    }
}