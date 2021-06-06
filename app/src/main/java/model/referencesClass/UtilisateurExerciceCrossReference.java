package model.referencesClass;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(primaryKeys = {"utilisateurID", "exerciceId"})
public class UtilisateurExerciceCrossReference {

    @NonNull
    public int utilisateurID;
    @NonNull
    public int exerciceId;
}
