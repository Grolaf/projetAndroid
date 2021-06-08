package exercices;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.scolastic.R;

import java.util.ArrayList;
import java.util.List;

import adapters.LigneCalculAdapter;
import model.Calcul;
import model.Exercice;
import model.LigneCalcul;
import model.Matiere;
import model.referencesClass.CalculAndLigneCalcul;
import model.referencesClass.UtilisateurExerciceCrossReference;

public class ExerciceCalculActivity extends ExerciceActivity{


    protected Calcul calcul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.calcul = getIntent().getParcelableExtra(EXERCICE_A_FAIRE);

        ListView lV = (ListView) findViewById(R.id.listView);
        this.adapter = new LigneCalculAdapter(this, R.layout.calcul_adapter_view, new ArrayList<>());
        lV.setAdapter(adapter);
        fetchLignes();
    }

    private void fetchLignes()
    {
        class FetchLignes extends AsyncTask<Void, Void, ArrayList<LigneCalcul>> {

            @Override
            protected ArrayList<LigneCalcul> doInBackground(Void... voids) {

                CalculAndLigneCalcul calculAndLignes = mDb.getAppDatabase().exerciceDAO().getCalculAndLigneCalcul(calcul.getExerciceId());


                return (ArrayList)calculAndLignes.lignes;
            }

            @Override
            protected void onPostExecute(ArrayList<LigneCalcul> lignes) {
                super.onPostExecute(lignes);


                // Mettre à jour l'adapter avec la liste de matieres
                adapter.clear();
                adapter.addAll(lignes);

                // Now, notify the adapter of the change in source
                adapter.notifyDataSetChanged();
                calcul.setLignes(lignes);
            }

        }

        FetchLignes fetch = new FetchLignes();
        fetch.execute();
    }


    public void valider(View v)
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
        int erreurs = this.calcul.resultat(answers);
        if(erreurs == 0)
        {
            reussirExercice(calcul);
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
