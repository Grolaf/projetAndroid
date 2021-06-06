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
import model.referencesClass.CalculAndLigneCalcul;


// Doit être modifié à chaque ajout d'une classe fille
@Dao
public abstract class ExerciceDAO {

    @Query("SELECT * FROM calcul")
    public abstract List<Calcul> getAllCalculs();


    @Query("SELECT max(exerciceId) FROM calcul")
    public abstract int maxId();

    @Insert
    public void insert(Exercice exercice)
    {
        if(exercice.getClass() == Calcul.class)
        {
            insert((Calcul) exercice);
        }
    }

    @Insert
    public long[] insertAll(Exercice... exercice)
    {
        for(Exercice e : exercice) {
            if (e.getClass() == Calcul.class) {
                insert((Calcul) e);
            }
        }

        return null;
    }

    @Delete
    public void delete(Exercice exercice)
    {
        if(exercice.getClass() == Calcul.class)
        {
            delete((Calcul) exercice);
        }
    }

    @Update
    public void update(Exercice exercice)
    {
        if(exercice.getClass() == Calcul.class)
        {
            update((Calcul) exercice);
        }
    }




    // Pour la table calcul //

    @Insert
    abstract void insert(Calcul calcul);

    @Insert
    abstract long[] insertAll(Calcul... calcul);

    @Delete
    abstract void delete(Calcul calcul);

    @Update
    abstract void update(Calcul calcul);

    @Transaction
    @Query("SELECT * FROM calcul WHERE exerciceId = :id")
    public abstract CalculAndLigneCalcul getCalculAndLigneCalcul(int id);

}
