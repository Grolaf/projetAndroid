package model.referencesClass;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

import model.Calcul;
import model.Utilisateur;

public class CalculAndUtilisateur extends ExerciceAndUtilisateur {

    @Embedded
    public Calcul calcul;
    @Relation(
            parentColumn = "exerciceId",
            entityColumn = "utilisateurID",
            associateBy = @Junction(UtilisateurExerciceCrossReference.class)
    )
    public List<Utilisateur> utilisateurs;
}
