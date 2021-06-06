package model.referencesClass;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

import model.Exercice;
import model.Utilisateur;

public class UtilisateurWithExercice {

    @Embedded public Utilisateur utilisateur;
    @Relation(
            parentColumn = "utilisateurID",
            entityColumn = "exerciceId",
            associateBy = @Junction(UtilisateurExerciceCrossReference.class)
    )
    public List<Exercice> exercices;
}
