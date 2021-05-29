package model.referencesClass;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import model.Calcul;
import model.Exercice;
import model.Matiere;


public class MatiereAndCalcul extends MatiereAndExercice{

    @Embedded
    public Matiere matiere;
    @Relation(
            parentColumn = "nom",
            entityColumn = "nomMatiere"
    )
    public List<Calcul> calculs;
}
