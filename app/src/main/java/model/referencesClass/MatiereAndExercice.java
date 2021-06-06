package model.referencesClass;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import model.Calcul;
import model.Exercice;
import model.Matiere;

public class MatiereAndExercice {

    @Embedded(prefix = "for_legacy_of_relation_class_")
    public Matiere matiere;
    @Relation(
            parentColumn = "nom",
            entityColumn = "nomMatiere"
    )
    public List<Exercice> exercices;
}
