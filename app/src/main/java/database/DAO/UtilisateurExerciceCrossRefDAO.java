package database.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.ArrayList;

import model.Exercice;
import model.referencesClass.CalculAndUtilisateur;
import model.referencesClass.ExerciceAndUtilisateur;
import model.referencesClass.UtilisateurAndCalcul;
import model.referencesClass.UtilisateurAndExercice;
import model.referencesClass.UtilisateurExerciceCrossReference;

@Dao
public abstract class UtilisateurExerciceCrossRefDAO {

    @Insert
    public abstract void insert(UtilisateurExerciceCrossReference utilisateurExerciceCrossReference);

    @Insert
    public abstract long[] insertAll(UtilisateurExerciceCrossReference... utilisateurExerciceCrossReference);

    @Delete
    public abstract void delete(UtilisateurExerciceCrossReference utilisateurExerciceCrossReference);

    @Update
    public abstract void update(UtilisateurExerciceCrossReference utilisateurExerciceCrossReference);

    // Partie Utilistaurs -> Exercices //

    @Transaction
    public UtilisateurAndExercice getUtilisateurWithExercice(String nom, String prenom)
    {
        UtilisateurAndExercice retour = new UtilisateurAndExercice();
        retour.exercices = new ArrayList<>();

        UtilisateurAndCalcul calculs = getUtilisteurAndCalcul(nom, prenom);
        if(calculs != null)
        {
            retour.exercices.addAll(calculs.calculs);
        }

        return retour;
    }

    @Transaction
    @Query("SELECT * FROM utilisateur WHERE nom = :nom AND prenom = :prenom")
    abstract UtilisateurAndCalcul getUtilisteurAndCalcul(String nom, String prenom);


    // Partie Exercices -> Utilisateurs //

    @Transaction
    public ExerciceAndUtilisateur getExerciceWithUtilisateur(int exerciceId)
    {
        ExerciceAndUtilisateur retour = new ExerciceAndUtilisateur();
        retour.utilisateurs = new ArrayList<>();

        CalculAndUtilisateur calculsUtilisateurs = getCalculWithUtilisateur(exerciceId);

        if(calculsUtilisateurs != null)
        {
            retour.utilisateurs.addAll(calculsUtilisateurs.utilisateurs);
        }

        return retour;
    }

    @Query("SELECT * FROM calcul WHERE exerciceId = :exerciceId")
    abstract CalculAndUtilisateur getCalculWithUtilisateur(int exerciceId);

}
