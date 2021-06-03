package database.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import model.Calcul;
import model.Exercice;

@Dao
public interface CalculDAO{

    @Query("SELECT * FROM calcul")
    List<Calcul> getAll();

    @Insert
    void insert(Calcul calcul);

    @Insert
    long[] insertAll(Calcul... calcul);

    @Delete
    void delete(Calcul calcul);

    @Update
    void update(Calcul calcul);

}
