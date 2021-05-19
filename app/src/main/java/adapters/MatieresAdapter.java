package adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.scolastic.R;

import java.util.List;

import model.Matiere;

public class MatieresAdapter extends ArrayAdapter<Matiere> {

    private Context context;
    private int ressource;


    public MatieresAdapter(@NonNull Context context, int resource, @NonNull List<Matiere> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String nom = getItem(position).getNom();
        String image = getItem(position).getImage();

        LayoutInflater inflater = LayoutInflater.from(this.context);
        convertView = inflater.inflate(ressource, parent, false);

        TextView e = (TextView) convertView.findViewById(R.id.nomMatiere);
        e.setText(nom);

        ImageView iView = (ImageView) convertView.findViewById(R.id.imageMatiere);
        iView.setImageURI(Uri.parse("~/Images/ecosia.jpeg"));

        LinearLayout matiereSelectionnee = (LinearLayout) convertView.findViewById(R.id.matiere);
        matiereSelectionnee.setId(position);

        return convertView;
    }
}