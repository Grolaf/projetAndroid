package database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import database.DAO.CalculDAO;
import database.DAO.LigneCalculDAO;
import database.DAO.MatiereDAO;
import database.DAO.UtilisateurDAO;
import model.Calcul;
import model.LigneCalcul;
import model.Matiere;
import model.Utilisateur;
import model.referencesClass.MatiereAndCalcul;
import model.referencesClass.MatiereAndExercice;
import model.referencesClass.UtilisateurAndCalcul;
import model.referencesClass.UtilisateurAndExercice;

@Database(entities = {
        Utilisateur.class,
        LigneCalcul.class,
        Calcul.class,
        Matiere.class,
        }, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract LigneCalculDAO ligneCalculDao();
    public abstract MatiereDAO matiereDAO();
    public abstract CalculDAO calculDAO();
    public abstract UtilisateurDAO utilisateurDAO();
}