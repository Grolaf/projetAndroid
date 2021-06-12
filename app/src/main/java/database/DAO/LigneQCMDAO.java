package database.DAO;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import model.LigneCalcul;
import model.LigneQCM;

public interface LigneQCMDAO {

    @Query("SELECT * FROM ligneqcm")
    List<LigneCalcul> getAll();

    @Query("SELECT * FROM ligneqcm WHERE ligneId = :id")
    LigneCalcul getLigneCalculByID(int id);

    @Insert
    void insert(LigneQCM ligneQCM);

    @Insert
    long[] insertAll(LigneQCM... ligneQCM);

    @Delete
    void delete(LigneQCM ligneQCM);

    @Update
    void update(LigneQCM ligneQCM);
}
