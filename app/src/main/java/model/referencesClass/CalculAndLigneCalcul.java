package model.referencesClass;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import model.Calcul;
import model.LigneCalcul;

public class CalculAndLigneCalcul {

    @Embedded public Calcul calcul;
    @Relation(
            parentColumn = "exerciceId",
            entityColumn = "exerciceId"
    )
    public List<LigneCalcul> lignes;
}
