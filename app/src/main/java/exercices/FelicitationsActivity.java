package exercices;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.scolastic.MainActivity;
import com.example.scolastic.MenuMatieresActivity;
import com.example.scolastic.R;
import com.example.scolastic.TableauScoresActivity;

import model.Utilisateur;

public class FelicitationsActivity extends AppCompatActivity {

    public static final String EXERCICE_REUSSI = "exercice_reussi";
    public static final String UTILISATEUR = "utilisateur";

    private Utilisateur utilisateur;
    private boolean exerciceReussi;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_felicitations);

        utilisateur = getIntent().getParcelableExtra(UTILISATEUR);
       exerciceReussi = getIntent().getBooleanExtra(EXERCICE_REUSSI, false);


       if(exerciceReussi) {
           TextView tV = (TextView) findViewById(R.id.laiusExerciceReussi);
           tV.setText("Bien joué, mais tu as déjà réussi cet exercice. Tu ne gagnera donc pas de points...");
       }
    }

    public void scores(View v)
    {
        Intent it = new Intent(this, TableauScoresActivity.class);
        it.putExtra(TableauScoresActivity.UTILISATEUR, utilisateur);
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(it);
        finish();
    }

    public void menu(View v)
    {
        Intent it = new Intent(this, MenuMatieresActivity.class);
        it.putExtra(MenuMatieresActivity.UTILISATEUR, utilisateur);
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(it);
        finish();
    }
}
