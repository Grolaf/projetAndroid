package exercices;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.scolastic.R;

import java.util.ArrayList;

import adapters.LigneCalculAdapter;
import model.Calcul;
import model.LigneCalcul;
import model.Niveau;
import tests.ExercicesTests;

public class ExerciceCalculActivity extends AppCompatActivity {

    private Calcul c;
    private LigneCalculAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ExercicesTests e = new ExercicesTests();
        setContentView(R.layout.activity_exercice_calcul);
        ListView listView = (ListView) findViewById(R.id.listView);

        LigneCalcul l1 = new LigneCalcul("3 + 3", 6);
        LigneCalcul l2 = new LigneCalcul("3 + 2", 5);
        LigneCalcul l3 = new LigneCalcul("3 + 1", 4);


        ArrayList<LigneCalcul> l = new ArrayList<>();
        l.add(l1);
        l.add(l2);
        l.add(l3);

        Calcul c = new Calcul("Additions", Niveau.FACILE, l);
        this.c = c;

        this.adapter = new LigneCalculAdapter(this, R.layout.calcul_adapter_view, c.getLignes());
        listView.setAdapter(adapter);
    }

    public void calculer(View v)
    {
                                                                // Récupération des réponses
        ArrayList<Double> answers = new ArrayList<>();

        for(int i = 0; i < this.adapter.getCount(); i++)
        {
            EditText e = (EditText) findViewById(i);

            try{
                answers.add(Double.parseDouble(e.getText().toString()));
            }catch(Exception ex){
                answers.add(-1.0);
            }
        }
                                                                // Evaluation des erreurs et redirection
        int erreurs = c.resultat(answers);
        if(erreurs == 0)
        {
            Intent it = new Intent(this, FelicitationsActivity.class);
            startActivity(it);
        }
        else
        {
            Intent it = new Intent(this, ErreurActivity.class);
            it.putExtra(ErreurActivity.NOMBRE_ERREURS, erreurs);
            startActivity(it);

        }
    }
}
