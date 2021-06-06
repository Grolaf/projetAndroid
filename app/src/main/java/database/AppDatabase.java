package database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import database.DAO.ExerciceDAO;
import database.DAO.LigneCalculDAO;
import database.DAO.MatiereDAO;
import database.DAO.UtilisateurDAO;
import database.DAO.UtilisateurExerciceCrossRefDAO;
import model.Calcul;
import model.Exercice;
import model.LigneCalcul;
import model.Matiere;
import model.Utilisateur;
import model.referencesClass.UtilisateurExerciceCrossReference;

@Database(entities = {
        Utilisateur.class,
        LigneCalcul.class,
        Exercice.class,
        Calcul.class,
        UtilisateurExerciceCrossReference.class,
        Matiere.class,
        }, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract LigneCalculDAO ligneCalculDao();
    public abstract MatiereDAO matiereDAO();
    public abstract UtilisateurDAO utilisateurDAO();
    public abstract ExerciceDAO exerciceDAO();
    public abstract UtilisateurExerciceCrossRefDAO utilisateurExerciceCrossRefDAO();
}