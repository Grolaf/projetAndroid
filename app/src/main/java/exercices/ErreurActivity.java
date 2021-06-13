package exercices;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.scolastic.MainActivity;
import com.example.scolastic.MenuMatieresActivity;
import com.example.scolastic.R;

import model.Utilisateur;

public class ErreurActivity extends AppCompatActivity {

    public static final String NOMBRE_ERREURS = "nombre_erreurs";
    public static final String UTILISATEUR = "utilisateur";

    private Utilisateur utilisateur;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int erreurs = getIntent().getIntExtra(NOMBRE_ERREURS, 1);
        utilisateur = getIntent().getParcelableExtra(UTILISATEUR);


        setContentView(R.layout.activity_erreur);
        TextView tV = (TextView) findViewById(R.id.nombre_erreurs);

        if(erreurs == 1)
            tV.setText("Il y a " + erreurs + " erreur.");
        else
            tV.setText("Il y a " + erreurs + " erreurs.");
    }

    public void correction(View v)
    {
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
