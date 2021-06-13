package com.example.scolastic;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import adapters.TableauScoresAdapter;
import database.DatabaseClient;
import model.Utilisateur;

public class TableauScoresActivity extends AppCompatActivity {

    public static final String UTILISATEUR = "utilisateur";

    protected DatabaseClient mDb;
    protected TableauScoresAdapter adapter;
    protected Utilisateur utilisateur;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tableau_scores);
        mDb = DatabaseClient.getInstance(this);

        utilisateur = getIntent().getParcelableExtra(UTILISATEUR);

        ListView lV = (ListView) findViewById(R.id.listViewAdapter);

        // Lier l'adapter au gridView
        this.adapter = new TableauScoresAdapter(this, R.layout.tableau_score_adapter_view, new ArrayList<Utilisateur>());
        lV.setAdapter(this.adapter);
    }

    private void getUtilisateurs() {
        ///////////////////////
        // Classe asynchrone permettant de récupérer des taches et de mettre à jour le listView de l'activité
        class GetUtilisateurs extends AsyncTask<Void, Void, List<Utilisateur>> {

            @Override
            protected List<Utilisateur> doInBackground(Void... voids) {

                ArrayList<Utilisateur> utilisateurs = (ArrayList)mDb.getAppDatabase().utilisateurDAO().getAll();

                for(Utilisateur u : utilisateurs)
                {
                    u.fetchElementsFromDatabase(mDb.getAppDatabase().utilisateurExerciceCrossRefDAO(), mDb.getAppDatabase().exerciceDAO(), mDb.getAppDatabase().matiereDAO());

                }

               return utilisateurs;

            }

            @Override
            protected void onPostExecute(List<Utilisateur> utilisateurs) {
                super.onPostExecute(utilisateurs);


                // Mettre à jour l'adapter avec la liste de matieres
                adapter.clear();

                adapter.addAll(utilisateurs);

                // Now, notify the adapter of the change in source
                adapter.notifyDataSetChanged();
            }
        }

        GetUtilisateurs gU = new GetUtilisateurs();
        gU.execute();
    }
    @Override
    protected void onStart() {
        super.onStart();

        // Mise à jour des matieres
        getUtilisateurs();
    }


}
