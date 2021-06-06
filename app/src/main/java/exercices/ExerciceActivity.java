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
import tests.ExercicesTests;

public abstract class ExerciceActivity extends AppCompatActivity {

    public static final String UTILISATEUR = "utilisateur";
    public static final String EXERCICE_A_FAIRE = "exercice_a_faire";

    protected DatabaseClient mDb;
    protected ArrayAdapter adapter;
    protected Utilisateur utilisateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDb = DatabaseClient.getInstance(this);

        setContentView(R.layout.activity_exercice);
        utilisateur = getIntent().getParcelableExtra(UTILISATEUR);
    }

    protected void reussirExercice(Exercice e){
        e.addVainqueur(utilisateur);
    }

}
