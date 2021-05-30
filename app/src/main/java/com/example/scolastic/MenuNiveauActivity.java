package com.example.scolastic;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import adapters.NiveauAdapter;
import database.DatabaseClient;
import model.Matiere;
import model.Niveau;

public class MenuNiveauActivity extends AppCompatActivity {

    public static final String NOM_MATIERE = "nom_matiere";

    private DatabaseClient mDb;
    private NiveauAdapter adapter;
    private String nomMatiere;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_niveau);

        mDb = DatabaseClient.getInstance(getApplicationContext());
        nomMatiere = getIntent().getStringExtra(NOM_MATIERE);

        // Récupérer les vues
        ListView lV = (ListView) findViewById(R.id.listView);

        // Lier l'adapter au gridView
        adapter = new NiveauAdapter(this, R.layout.niveau_adapter_view, new ArrayList<Niveau>());
        lV.setAdapter(adapter);
    }

    private void getNiveaux() {
        ///////////////////////
        // Classe asynchrone permettant de récupérer des taches et de mettre à jour le listView de l'activité
        class GetNiveaux extends AsyncTask<Void, Void, List<Niveau>> {

            @Override
            protected List<Niveau> doInBackground(Void... voids) {
                List<String> valeurNiveaux = mDb.getAppDatabase()
                        .matiereDAO()
                        .getNiveaux(nomMatiere);
                List<Niveau> niveauxList = new ArrayList<Niveau>();

                for(String s : valeurNiveaux)
                {
                    niveauxList.add(Niveau.valueOf(s));
                }

                // Tri des niveaux pour l'affichage dans l'ordre
                Collections.sort(niveauxList);
                return niveauxList;
            }

            @Override
            protected void onPostExecute(List<Niveau> niveauxList) {
                super.onPostExecute(niveauxList);


                // Mettre à jour l'adapter avec la liste de matieres
                adapter.clear();
                adapter.addAll(niveauxList);

                // Now, notify the adapter of the change in source
                adapter.notifyDataSetChanged();
            }
        }

        GetNiveaux gt = new GetNiveaux();
        gt.execute();
    }

    public void choixNiveau(View view)
    {
        /*
        Intent it = new Intent(this, MenuNiveauActivity.class);
        TextView t = view.findViewById(R.id.nomMatiere);
        it.putExtra(MenuNiveauActivity.NOM_MATIERE, t.getText());
        startActivity(it);

         */
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Mise à jour des matieres
        getNiveaux();
    }
}
