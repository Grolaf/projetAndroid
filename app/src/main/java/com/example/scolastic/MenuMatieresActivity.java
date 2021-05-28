package com.example.scolastic;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;

import adapters.MatieresAdapter;
import model.Matiere;

public class MenuMatieresActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_matieres);

        Matiere maths = new Matiere("Maths", "");
        Matiere français = new Matiere("Français", "@drawable/book_icon_transparent_9");
        Matiere anglais = new Matiere("Anglais", "");
        Matiere histoire = new Matiere("History", "");
        Matiere cultureG = new Matiere("Culture G", "");

        ArrayList<Matiere> listeMatieres = new ArrayList<>();
        listeMatieres.add(maths);
        listeMatieres.add(français);
        listeMatieres.add(anglais);
        listeMatieres.add(histoire);
        listeMatieres.add(cultureG);


        GridView gL = (GridView) findViewById(R.id.gridMatieres);
        gL.setAdapter(new MatieresAdapter(this, R.layout.matiere_adapter_view, listeMatieres));

    }
}
