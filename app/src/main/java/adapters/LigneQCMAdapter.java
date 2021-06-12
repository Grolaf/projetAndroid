package adapters;

import android.content.Context;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

        RadioGroup group = (RadioGroup) convertView.findViewById(R.id.group);

        RadioButton radioA = new RadioButton(context);
        radioA.setText(ligne.getReponseA());
        radioA.setTag("A");

        RadioButton radioB = new RadioButton(context);
        radioB.setText(ligne.getReponseB());
        radioB.setTag("B");

        group.addView(radioA);
        group.addView(radioB);

        RadioButton radioC = new RadioButton(context);
        if(ligne.getReponseC() != null) {
            radioC.setText(ligne.getReponseC());
            radioC.setTag("C");
            group.addView(radioC);
        }

        RadioButton radioD = new RadioButton(context);
        if(ligne.getReponseD() != null) {
            radioD.setText(ligne.getReponseD());
            radioD.setTag("D");
            group.addView(radioD);
        }


        RadioButton answer = convertView.findViewWithTag(ligne.getBonneReponse());
        answer.setTag("reponse");

        return convertView;
    }
}

