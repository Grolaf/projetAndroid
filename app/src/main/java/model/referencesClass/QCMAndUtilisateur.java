package model.referencesClass;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;
import model.QCM;
import model.Utilisateur;

public class QCMAndUtilisateur extends ExerciceAndUtilisateur{

    @Embedded
    public QCM qcm;
    @Relation(
            parentColumn = "exerciceId",
            entityColumn = "utilisateurID",
            associateBy = @Junction(UtilisateurExerciceCrossReference.class)
    )
    public List<Utilisateur> utilisateurs;
}
