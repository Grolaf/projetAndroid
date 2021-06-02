package database.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import model.LigneCalcul;

@Dao
public interface LigneCalculDAO {

    @Query("SELECT * FROM lignecalcul")
    List<LigneCalcul> getAll();

    @Insert
    void insert(LigneCalcul ligneCalcul);

    @Insert
    long[] insertAll(LigneCalcul... ligneCalcul);

    @Delete
    void delete(LigneCalcul ligneCalcul);

    @Update
    void update(LigneCalcul ligneCalcul);
}
