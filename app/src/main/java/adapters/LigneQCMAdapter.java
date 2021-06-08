package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.scolastic.R;
import java.util.List;
import model.LigneQCM;

public class LigneQCMAdapter extends ArrayAdapter<LigneQCM> {

    private Context context;
    private int ressource;


    public LigneQCMAdapter(@NonNull Context context, int resource, @NonNull List<LigneQCM> objects) {
        super(context, resource, objects);
        this.context = context;
        this.ressource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LigneQCM ligne = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(this.context);
        convertView = inflater.inflate(ressource, parent, false);

        // Idée : ajouter ici les radio bouttons et le radiogroup (faire ça en java et non en xml)

        TextView e = (TextView) convertView.findViewById(R.id.enonce);
        e.setText(ligne.getEnonce());
        TextView a = (TextView) convertView.findViewById(R.id.enonceReponseA);
        e.setText(ligne.getReponseA());
        TextView b = (TextView) convertView.findViewById(R.id.enonceReponseB);
        e.setText(ligne.getReponseB());
        if(ligne.getReponseC() == null)
        {
            RadioButton c = (RadioButton) convertView.findViewWithTag("C");
            c.setVisibility(View.GONE);
        }
        else
        {
            TextView c = (TextView) convertView.findViewById(R.id.enonceReponseC);
            e.setText(ligne.getReponseC());
        }

        if(ligne.getReponseD() == null)
        {
            RadioButton d = (RadioButton) convertView.findViewWithTag("D");
            d.setVisibility(View.GONE);
        }
        else
        {
            TextView d = (TextView) convertView.findViewById(R.id.enonceReponseD);
            e.setText(ligne.getReponseD());
        }


        RadioButton answer = (RadioButton) convertView.findViewWithTag(ligne.getBonneReponse());
        answer.setTag("reponse");

        return convertView;
    }
}
