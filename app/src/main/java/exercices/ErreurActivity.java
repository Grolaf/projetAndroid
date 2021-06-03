package exercices;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.scolastic.MainActivity;
import com.example.scolastic.R;

public class ErreurActivity extends AppCompatActivity {

    public static final String NOMBRE_ERREURS = "nombre_erreurs";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int erreurs = getIntent().getIntExtra(NOMBRE_ERREURS, 1);


        setContentView(R.layout.activity_erreur);
        TextView tV = (TextView) findViewById(R.id.nombre_erreurs);

        tV.setText("Il y a " + erreurs + " erreurs.");

    }

    public void correction(View v)
    {
        finish();
    }

    public void menu(View v)
    {
        Intent it = new Intent(this, MainActivity.class);
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(it);
    }
}
