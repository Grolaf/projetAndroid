package com.example.scolastic;

import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import adapters.MatieresAdapter;
import database.DatabaseClient;
import model.Matiere;
import model.Utilisateur;

public class MenuMatieresActivity extends AppCompatActivity {

    public static final String UTILISATEUR = "utilisateur";
    private DatabaseClient mDb;
    private MatieresAdapter adapter;
    private Utilisateur utilisateur;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_matieres);

        this.mDb = DatabaseClient.getInstance(getApplicationContext());
        this.utilisateur = getIntent().getParcelableExtra(UTILISATEUR);

        // Récupérer les vues
        GridView gL = (GridView) findViewById(R.id.gridMatieres);

        // Lier l'adapter au gridView
        this.adapter = new MatieresAdapter(this, R.layout.matiere_adapter_view, new ArrayList<>());
        gL.setAdapter(this.adapter);

    }

    private void getMatieres() {
        ///////////////////////
        // Classe asynchrone permettant de récupérer des taches et de mettre à jour le listView de l'activité
        class GetMatieres extends AsyncTask<Void, Void, List<Matiere>> {

            @Override
            protected List<Matiere> doInBackground(Void... voids) {
                List<Matiere> matieresList = mDb.getAppDatabase()
                        .matiereDAO()
                        .getAll();
                return matieresList;
            }

            @Override
            protected void onPostExecute(List<Matiere> matieresList) {
                super.onPostExecute(matieresList);


                // Mettre à jour l'adapter avec la liste de matieres
                adapter.clear();
                adapter.addAll(matieresList);

                // Now, notify the adapter of the change in source
                adapter.notifyDataSetChanged();
            }
        }

        GetMatieres gt = new GetMatieres();
        gt.execute();
    }

    public void choixMatiere(View view)
    {
        Intent it = new Intent(this, MenuNiveauActivity.class);
        TextView t = view.findViewById(R.id.nomMatiere);
        it.putExtra(MenuNiveauActivity.UTILISATEUR, this.utilisateur);
        it.putExtra(MenuNiveauActivity.NOM_MATIERE, t.getText());
        startActivity(it);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Mise à jour des matieres
        getMatieres();
    }

    public void pageProfil(View view)
    {
        Intent it = new Intent(this, PageProfilActivity.class);
        it.putExtra(PageProfilActivity.UTILISATEUR, utilisateur);
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(it);
    }
}
