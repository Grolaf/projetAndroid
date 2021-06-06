package model.referencesClass;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(primaryKeys = {"utilisateurID", "exerciceId"})
public class UtilisateurExerciceCrossReference {

    @NonNull
    public int utilisateurID;
    @NonNull
    public int exerciceId;

    public UtilisateurExerciceCrossReference(int utilisateurID, int exerciceId)
    {
        this.utilisateurID = utilisateurID;
        this.exerciceId = exerciceId;
    }
}
