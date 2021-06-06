package com.example.scolastic;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
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
import database.DAO.UtilisateurExerciceCrossRefDAO;
import database.DatabaseClient;
import exercices.ExerciceActivity;
import exercices.ExerciceCalculActivity;
import model.Calcul;
import model.Exercice;
import model.Matiere;
import model.Niveau;
import model.Utilisateur;

public class MenuNiveauActivity extends AppCompatActivity {

    public static final String NOM_MATIERE = "nom_matiere";
    public static final String UTILISATEUR = "utilisateur";

    private DatabaseClient mDb;
    private NiveauAdapter adapter;
    private String nomMatiere;
    private Matiere matiere;
    private Utilisateur utilisateur;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_niveau);

        this.mDb = DatabaseClient.getInstance(getApplicationContext());
        this.nomMatiere = getIntent().getStringExtra(NOM_MATIERE);
        this.utilisateur = getIntent().getParcelableExtra(UTILISATEUR);

        // Récupérer les vues
        ListView lV = (ListView) findViewById(R.id.listView);

        // Lier l'adapter au gridView
        this.adapter = new NiveauAdapter(this, R.layout.niveau_adapter_view, new ArrayList<Niveau>());
        lV.setAdapter(this.adapter);
    }

    private void getMatiereAndNiveaux() {
        ///////////////////////
        // Classe asynchrone permettant de récupérer des taches et de mettre à jour le listView de l'activité
        class GetMatiere extends AsyncTask<Void, Void, Matiere> {

            @Override
            protected Matiere doInBackground(Void... voids) {

                Matiere m = mDb.getAppDatabase().matiereDAO().getMatiereWithID(nomMatiere);
                m.getElementsFromDataBase(mDb.getAppDatabase().matiereDAO(), mDb.getAppDatabase().utilisateurExerciceCrossRefDAO(), mDb.getAppDatabase().exerciceDAO());
               return m;

            }

            @Override
            protected void onPostExecute(Matiere matiereRetour) {
                super.onPostExecute(matiereRetour);


                matiere = matiereRetour;
                // Mettre à jour l'adapter avec la liste de matieres
                adapter.clear();
                adapter.addAll(matiere.getNiveaux());

                // Now, notify the adapter of the change in source
                adapter.notifyDataSetChanged();
            }
        }

        GetMatiere gt = new GetMatiere();
        gt.execute();
    }

    public void choixNiveau(View view)
    {
        Button pressed = (Button)view.findViewById(R.id.nomNiveau);
        Niveau choix =  Niveau.valueOf(pressed.getText().toString().toUpperCase());

        Exercice exerciceAFaire = this.matiere.getRandomExercice(choix, this.utilisateur);

        Intent it = new Intent();

        if(exerciceAFaire.getClass() == Calcul.class) {
            it = new Intent(this, ExerciceCalculActivity.class);
            Calcul exercice = (Calcul) exerciceAFaire;
            it.putExtra(ExerciceActivity.EXERCICE_A_FAIRE, exercice);
        }

        it.putExtra(ExerciceActivity.UTILISATEUR, this.utilisateur);
        startActivity(it);

    }

    public void retour(View view)
    {
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Mise à jour des matieres
        getMatiereAndNiveaux();
    }
}
