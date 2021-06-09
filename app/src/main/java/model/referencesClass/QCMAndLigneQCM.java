package model.referencesClass;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;
import model.LigneQCM;
import model.QCM;

public class QCMAndLigneQCM {

    @Embedded
    public QCM qcm;
    @Relation(
            parentColumn = "exerciceId",
            entityColumn = "exerciceId"
    )
    public List<LigneQCM> lignes;
}
