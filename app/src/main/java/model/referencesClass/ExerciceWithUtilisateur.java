package model.referencesClass;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

import model.Exercice;
import model.Utilisateur;

public class ExerciceWithUtilisateur {

    @Embedded
    public Exercice exercice;
    @Relation(
            parentColumn = "exerciceId",
            entityColumn = "utilisateurID",
            associateBy = @Junction(UtilisateurExerciceCrossReference.class)
    )
    public List<Utilisateur> utilisateurs;
}
