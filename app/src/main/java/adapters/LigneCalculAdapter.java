package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.scolastic.R;

import java.util.List;

import model.LigneCalcul;

public class LigneCalculAdapter extends ArrayAdapter<LigneCalcul> {

    private Context context;
    private int ressource;


    public LigneCalculAdapter(@NonNull Context context, int resource, @NonNull List<LigneCalcul> objects) {
        super(context, resource, objects);
        this.context = context;
        this.ressource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String enonce = getItem(position).getEnonce();

        LayoutInflater inflater = LayoutInflater.from(this.context);
        convertView = inflater.inflate(ressource, parent, false);

        TextView e = (TextView) convertView.findViewById(R.id.enonce);
        e.setText(enonce);

        EditText answer = (EditText) convertView.findViewById(R.id.answer);
        answer.setId(position);

        return convertView;
    }
}
