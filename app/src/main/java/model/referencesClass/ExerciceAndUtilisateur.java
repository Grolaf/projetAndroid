package model.referencesClass;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

import model.Exercice;
import model.Utilisateur;

public class ExerciceAndUtilisateur {

    @Embedded(prefix = "for_legacy_of_relation_class_")
    public Exercice exercice;
    @Relation(
            parentColumn = "exerciceId",
            entityColumn = "utilisateurID",
            associateBy = @Junction(UtilisateurExerciceCrossReference.class)
    )
    public List<Utilisateur> utilisateurs;
}
