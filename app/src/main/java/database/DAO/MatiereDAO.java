package database.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import model.Matiere;
import model.Niveau;
import model.referencesClass.MatiereAndCalcul;
import model.referencesClass.MatiereAndExercice;

@Dao
public interface MatiereDAO {

    @Query("SELECT * FROM matiere")
    List<Matiere> getAll();

    @Query("SELECT distinct(niveau) FROM matiere m, calcul c WHERE m.nom = :nomMatiere AND m.nom = c.nomMatiere")
    List<String> getNiveaux(String nomMatiere);

    @Insert
    void insert(Matiere matiere);

    @Insert
    long[] insertAll(Matiere... matiere);

    @Delete
    void delete(Matiere matiere);

    @Update
    void update(Matiere matiere);

    @Transaction
    @Query("SELECT * FROM matiere")
    public List<MatiereAndCalcul> getMatiereAndCalcul();
}
