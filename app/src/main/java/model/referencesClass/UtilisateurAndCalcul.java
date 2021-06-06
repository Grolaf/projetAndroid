package model.referencesClass;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;
import java.util.List;
import model.Calcul;
import model.Utilisateur;

public class UtilisateurAndCalcul extends UtilisateurAndExercice{

    @Embedded
    public Utilisateur utilisateur;
    @Relation(
            parentColumn = "utilisateurID",
            entityColumn = "exerciceId",
            associateBy = @Junction(UtilisateurExerciceCrossReference.class)
    )
    public List<Calcul> calculs;
}
