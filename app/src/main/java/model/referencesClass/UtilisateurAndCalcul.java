package model.referencesClass;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import model.Calcul;
import model.Exercice;
import model.Utilisateur;

public class UtilisateurAndCalcul extends UtilisateurAndExercice{
    @Embedded
    public Utilisateur utilisateur;
    @Relation(
            parentColumn = "prenom",
            entityColumn = "nomPrenomVainqueur"
    )
    public List<Calcul> calculs;
}
