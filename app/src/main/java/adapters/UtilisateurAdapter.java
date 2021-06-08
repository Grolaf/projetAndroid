
package adapters;

import android.content.Context;
import android.media.Image;
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

import model.LigneCalcul;
import model.Niveau;
import model.Utilisateur;

public class UtilisateurAdapter extends ArrayAdapter<Utilisateur> {

    private Context context;
    private int ressource;


    public UtilisateurAdapter(@NonNull Context context, int resource, @NonNull List<Utilisateur> objects) {
        super(context, resource, objects);
        this.context = context;
        this.ressource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Utilisateur u = getItem(position);
        int utilisateurId = u.getUtilisateurID();

        LayoutInflater inflater = LayoutInflater.from(this.context);
        convertView = inflater.inflate(ressource, parent, false);

        LinearLayout layout = (LinearLayout) convertView.findViewById(R.id.linearLayout);

        ImageView image = (ImageView) layout.findViewById(R.id.imageEleve);
        image.setImageResource(context.getResources().getIdentifier(getItem(position).getAvatar(), "drawable", context.getPackageName()));

        TextView prenom = (TextView) layout.findViewById(R.id.prenom);
        prenom.setText(u.getPrenom());

        TextView nom = (TextView) layout.findViewById(R.id.nom);
        nom.setText(u.getNom());

        layout.setId(utilisateurId);

        return convertView;
    }
}
