package model.referencesClass;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;
import model.Matiere;
import model.QCM;

public class MatiereAndQCM extends MatiereAndExercice{

    @Embedded
    public Matiere matiere;
    @Relation(
            parentColumn = "nom",
            entityColumn = "nomMatiere"
    )
    public List<QCM> qcms;
}
