package exercices;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.scolastic.R;
import java.util.ArrayList;
import adapters.LigneQCMAdapter;
import model.LigneQCM;
import model.QCM;
import model.referencesClass.QCMAndLigneQCM;

public class ExerciceQCMActivity extends ExerciceActivity{

    protected QCM qcm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.qcm = getIntent().getParcelableExtra(EXERCICE_A_FAIRE);

        TextView nomMatiere = (TextView) findViewById(R.id.matiere);
        nomMatiere.setText(this.qcm.getNomMatiere());

        TextView titreExercice = (TextView) findViewById(R.id.titreExercice);
        titreExercice.setText(this.qcm.getTitre());

        ListView lV = (ListView) findViewById(R.id.listView);
        this.adapter = new LigneQCMAdapter(this, R.layout.qcm_adapter_view, new ArrayList<>());
        lV.setAdapter(adapter);
        fetchLignes();
    }

    private void fetchLignes()
    {
        class FetchLignes extends AsyncTask<Void, Void, ArrayList<LigneQCM>> {

            @Override
            protected ArrayList<LigneQCM> doInBackground(Void... voids) {

                QCMAndLigneQCM qcmAndLigneQCM = mDb.getAppDatabase().exerciceDAO().getQCMAndLigneQCM(qcm.getExerciceId());


                return (ArrayList)qcmAndLigneQCM.lignes;
            }

            @Override
            protected void onPostExecute(ArrayList<LigneQCM> lignes) {
                super.onPostExecute(lignes);


                // Mettre à jour l'adapter avec la liste de matieres
                adapter.clear();
                adapter.addAll(lignes);

                // Now, notify the adapter of the change in source
                adapter.notifyDataSetChanged();
                qcm.setLignes(lignes);
            }

        }

        FetchLignes fetch = new FetchLignes();
        fetch.execute();
    }


    public void valider(View v)
    {
        // Récupération des réponses
        ListView liste = (ListView) findViewById(R.id.listView);
        ArrayList<String> reponses = new ArrayList<>();

        for (int i = 0 ; i < liste.getChildCount() ; i++) {
            View view = liste.getChildAt(i);

            RadioGroup group = (RadioGroup) view.findViewById(R.id.group);
            RadioButton bouton = (RadioButton) view.findViewById(group.getCheckedRadioButtonId());

            if(bouton != null)
              reponses.add(bouton.getTag().toString());
        }


        // Evaluation des erreurs et redirection
        int erreurs = this.qcm.resultat(reponses);

        // Evaluation par la classe mère
        validerExercice(erreurs, qcm);
    }
}
