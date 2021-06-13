package exercices;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.scolastic.R;

import java.util.ArrayList;
import java.util.List;

import adapters.LigneCalculAdapter;
import database.DatabaseClient;
import model.Calcul;
import model.Exercice;
import model.LigneCalcul;
import model.Matiere;
import model.Niveau;
import model.Utilisateur;
import model.referencesClass.UtilisateurExerciceCrossReference;
import tests.ExercicesTests;

public abstract class ExerciceActivity extends AppCompatActivity {

    public static final String UTILISATEUR = "utilisateur";
    public static final String EXERCICE_A_FAIRE = "exercice_a_faire";
    public static final String EXERCICE_REUSSI = "exercice_reussi";

    protected DatabaseClient mDb;
    protected ArrayAdapter adapter;
    protected Utilisateur utilisateur;
    protected boolean exerciceReussi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDb = DatabaseClient.getInstance(this);

        setContentView(R.layout.activity_exercice);

        utilisateur = getIntent().getParcelableExtra(UTILISATEUR);
        exerciceReussi = getIntent().getBooleanExtra(EXERCICE_REUSSI, false);
    }

    protected void reussirExercice(Exercice e){

        class ReussirExercice extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                utilisateur.fetchElementsFromDatabase(mDb.getAppDatabase().utilisateurExerciceCrossRefDAO(), mDb.getAppDatabase().exerciceDAO(), mDb.getAppDatabase().matiereDAO());
                e.fetchElementsFromDatabase(mDb.getAppDatabase().utilisateurExerciceCrossRefDAO(), mDb.getAppDatabase().exerciceDAO(), mDb.getAppDatabase().matiereDAO());
                utilisateur.addExerciceResolu(e);
                UtilisateurExerciceCrossReference ref = new UtilisateurExerciceCrossReference(mDb.getAppDatabase().utilisateurDAO().getUtilisateurID(utilisateur.getPrenom(), utilisateur.getNom()),  e.exerciceId);
                mDb.getAppDatabase().utilisateurExerciceCrossRefDAO().insert(ref);
                mDb.getAppDatabase().exerciceDAO().update(e);
                mDb.getAppDatabase().utilisateurDAO().update(utilisateur);

                return null;
            }

        }

        ReussirExercice reussir = new ReussirExercice();
        reussir.execute();
    }

    protected void validerExercice(int erreurs, Exercice exercice) {
        if (erreurs == 0) {
            if(!exerciceReussi)
                reussirExercice(exercice);
            Intent it = new Intent(this, FelicitationsActivity.class);
            it.putExtra(FelicitationsActivity.EXERCICE_REUSSI, exerciceReussi);
            it.putExtra(FelicitationsActivity.UTILISATEUR, utilisateur);
            startActivity(it);
        } else {
            Intent it = new Intent(this, ErreurActivity.class);
            it.putExtra(ErreurActivity.NOMBRE_ERREURS, erreurs);
            it.putExtra(ErreurActivity.UTILISATEUR, utilisateur);
            startActivity(it);

        }
    }


}

