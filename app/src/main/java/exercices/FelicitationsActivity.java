package exercices;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.scolastic.MainActivity;
import com.example.scolastic.MenuMatieresActivity;
import com.example.scolastic.R;

public class FelicitationsActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_felicitations);

    }

    public void scores(View v)
    {
        // Not set actually

        //Intent it = new Intent(this, MainActivity.class);
        //it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //startActivity(it);
    }

    public void menu(View v)
    {
        Intent it = new Intent(this, MenuMatieresActivity.class);
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(it);
    }
}
