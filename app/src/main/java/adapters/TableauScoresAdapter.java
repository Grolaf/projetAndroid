package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import model.Utilisateur;
import com.example.scolastic.R;

public class TableauScoresAdapter extends ArrayAdapter<Utilisateur> {

    private Context context;
    private int ressource;


    public TableauScoresAdapter(@NonNull Context context, int resource, @NonNull List<Utilisateur> objects) {
        super(context, resource, objects);
        this.context = context;
        this.ressource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Utilisateur u = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(this.context);
        convertView = inflater.inflate(ressource, parent, false);

        LinearLayout layout = (LinearLayout) convertView.findViewById(R.id.linearLayout);

        ImageView image = (ImageView) layout.findViewById(R.id.imageEleve);
        image.setImageResource(context.getResources().getIdentifier(getItem(position).getAvatar(), "drawable", context.getPackageName()));

        TextView prenom = (TextView) layout.findViewById(R.id.prenom);
        prenom.setText(u.getPrenom());

        TextView nom = (TextView) layout.findViewById(R.id.nom);
        nom.setText(u.getNom());

        TextView score = (TextView) layout.findViewById(R.id.scoreTabScore);
        score.setText(Integer.toString(u.getScore()));

        return convertView;
    }
}
