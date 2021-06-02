package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.scolastic.R;

import java.util.List;

import model.Matiere;
import model.Niveau;

public class NiveauAdapter extends ArrayAdapter<Niveau> {

    private Context context;
    private int ressource;


    public NiveauAdapter(@NonNull Context context, int resource, @NonNull List<Niveau> objects) {
        super(context, resource, objects);
        this.context = context;
        this.ressource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String nom = getItem(position).getNom();

        LayoutInflater inflater = LayoutInflater.from(this.context);
        convertView = inflater.inflate(ressource, parent, false);

        Button b = (Button) convertView.findViewById(R.id.nomNiveau);
        b.setText(nom);

        return convertView;
    }
}
