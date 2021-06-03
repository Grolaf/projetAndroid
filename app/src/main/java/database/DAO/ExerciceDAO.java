package database.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import model.Calcul;
import model.Exercice;
import model.referencesClass.ExerciceWithUtilisateur;
import model.referencesClass.UtilisateurWithExercice;

@Dao
public interface ExerciceDAO {

    @Query("SELECT * FROM exercice")
    List<Exercice> getAll();

    @Insert
    void insert(Exercice exercice);

    @Insert
    long[] insertAll(Exercice... exercice);

    @Delete
    void delete(Exercice exercice);

    @Update
    void update(Exercice exercice);

    @Transaction
    @Query("SELECT * FROM exercice")
    public List<ExerciceWithUtilisateur> getExerciceWithUtilisateur();
}
