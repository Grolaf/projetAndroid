package database.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import model.Utilisateur;
import model.referencesClass.UtilisateurWithExercice;

@Dao
public interface UtilisateurDAO {

    @Query("SELECT * FROM utilisateur;")
        List<Utilisateur> getAll();

    @Query("SELECT * FROM utilisateur WHERE prenom = :prenom AND nom = :nom;")
        Utilisateur getUtilisateur(String prenom, String nom);

    @Insert
    void insert(Utilisateur utilisateur);

    @Insert
    long[] insertAll(Utilisateur... utilisateur);

    @Delete
    void delete(Utilisateur utilisateur);

    @Update
    void update(Utilisateur utilisateur);

    @Transaction
    @Query("SELECT * FROM utilisateur")
    public List<UtilisateurWithExercice> getUtilisateurWithExercice();

}
