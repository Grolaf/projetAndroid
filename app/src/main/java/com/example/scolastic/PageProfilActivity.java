package com.example.scolastic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

import adapters.NiveauAdapter;
import database.DatabaseClient;
import model.Niveau;
import model.Utilisateur;

public class PageProfilActivity extends AppCompatActivity {

    public static final String UTILISATEUR = "utilisteur";

    private DatabaseClient mDb;

    private Utilisateur utilisateur;
    private LinearLayout achievements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activty_profile_page);
        achievements = (LinearLayout) findViewById(R.id.achievements);
        mDb = DatabaseClient.getInstance(this);


        utilisateur = getIntent().getParcelableExtra(UTILISATEUR);

        TextView nomPrenom = (TextView) findViewById(R.id.textProfil);
        nomPrenom.setText(utilisateur.getPrenom() + " " + utilisateur.getNom());

        ImageView image = (ImageView) findViewById(R.id.imageProfil);
        image.setImageResource(this.getResources().getIdentifier(utilisateur.getAvatar(), "drawable", this.getPackageName()));
    }

    public void seDesinscrire(View view){

        // Classe asynchrone permettant de récupérer des taches et de mettre à jour le listView de l'activité
        class DeleteUtilisateur extends AsyncTask<Void, Void, Void > {

            @Override
            protected Void doInBackground(Void... voids) {
                mDb.getAppDatabase()
                        .utilisateurDAO()
                        .delete(utilisateur);
                return null;
            }

        }

        DeleteUtilisateur dU = new DeleteUtilisateur();
        dU.execute();

        Toast.makeText(this, "Votre profil a correctement été supprimé. A bientot !", Toast.LENGTH_SHORT).show();
        Intent it = new Intent(this, InscriptionActivity.class);
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(it);
        finish();

    }


    private void getAchievements()
    {
        class GetUtilisateurAchievements extends AsyncTask<Void, Void, Utilisateur> {

            @Override
            protected Utilisateur doInBackground(Void... voids) {
                utilisateur.fetchElementsFromDatabase(mDb.getAppDatabase().utilisateurExerciceCrossRefDAO(), mDb.getAppDatabase().exerciceDAO(), mDb.getAppDatabase().matiereDAO());
                return utilisateur;
            }

            @Override
            protected void onPostExecute(Utilisateur utilisateur) {
                super.onPostExecute(utilisateur);

                TextView score = (TextView) achievements.findViewById(R.id.scoreProfilPage);
                score.setText("Score  =  " + Integer.toString(utilisateur.getScore()));

                for(String matiere: utilisateur.getExercicesResolus().keySet())
                {
                    LinearLayout tmpMatiere = (LinearLayout) getLayoutInflater().inflate(R.layout.profil_matiere_inflater_view, null);

                    TextView nomMatiere = (TextView) tmpMatiere.findViewById(R.id.matiereProfilPage);
                    nomMatiere.setText(matiere + " : ");

                    LinearLayout niveaux = (LinearLayout)  tmpMatiere.findViewById(R.id.matiereLinearLayoutProfilPage);

                    for(Niveau n : utilisateur.getExercicesResolus().get(matiere).keySet())
                    {
                        LinearLayout tmpNiveau = (LinearLayout) getLayoutInflater().inflate(R.layout.profil_niveau_inflater_view, null);
                        int nbExercicesResolus = utilisateur.getExercicesResolus(n, matiere).size();

                        TextView niveauEtExercicesResolus = (TextView) tmpNiveau.findViewById(R.id.niveauProfilPage);

                        if(nbExercicesResolus == 1)
                            niveauEtExercicesResolus.setText("Niveau " + n.getNom() + " : " + Integer.toString(utilisateur.getExercicesResolus(n, matiere).size()) + " exercice réussi");
                        else
                            niveauEtExercicesResolus.setText("Niveau " + n.getNom() + " : " + Integer.toString(utilisateur.getExercicesResolus(n, matiere).size()) + " exercices réussis");

                        niveaux.addView(tmpNiveau);
                    }

                    achievements.addView(tmpMatiere);
                }
            }
        }

        GetUtilisateurAchievements achievements = new GetUtilisateurAchievements();
        achievements.execute();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Mise à jour des matieres
        getAchievements();
    }


    public void retour(View view)
    {
        finish();
    }

    public void menu(View view)
    {
        Intent it = new Intent(this, MenuMatieresActivity.class);
        it.putExtra(MenuMatieresActivity.UTILISATEUR, utilisateur);
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(it);
        finish();
    }

    public void pageProfil(View view)
    {
    }

    public void scores(View v)
    {
        Intent it = new Intent(this, TableauScoresActivity.class);
        it.putExtra(TableauScoresActivity.UTILISATEUR, utilisateur);
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(it);
        finish();
    }
}