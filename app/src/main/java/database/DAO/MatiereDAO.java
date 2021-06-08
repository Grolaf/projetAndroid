package database.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import model.Exercice;
import model.Matiere;
import model.Niveau;
import model.Utilisateur;
import model.referencesClass.MatiereAndCalcul;
import model.referencesClass.MatiereAndExercice;
import model.referencesClass.MatiereAndQCM;

@Dao
public abstract class MatiereDAO {

    @Query("SELECT * FROM matiere")
    public abstract List<Matiere> getAll();

    @Query("SELECT * FROM matiere WHERE nom = :nom")
    public abstract Matiere getMatiereWithID(String nom);


    public List<String> getNiveaux(String nomMatiere)
    {
        ArrayList<String> niveaux = new ArrayList<>();

        niveaux.addAll(getNiveauxCalcul(nomMatiere));
        niveaux.addAll(getNiveauxQCM(nomMatiere));

        HashSet<String> hset = new HashSet<>(niveaux);
        niveaux = new ArrayList<>(hset);

        return niveaux;
    }

    @Query("SELECT distinct(niveau) FROM matiere m, calcul c WHERE m.nom = :nomMatiere AND m.nom = c.nomMatiere")
    public abstract List<String> getNiveauxCalcul(String nomMatiere);

    @Query("SELECT distinct(niveau) FROM matiere m, qcm c WHERE m.nom = :nomMatiere AND m.nom = c.nomMatiere")
    public abstract List<String> getNiveauxQCM(String nomMatiere);

    @Insert
    public abstract void insert(Matiere matiere);

    @Insert
    public abstract long[] insertAll(Matiere... matiere);

    @Delete
    public abstract void delete(Matiere matiere);

    @Update
    public abstract void update(Matiere matiere);


    // Doit être changé à chaque ajout d'une nouvelle classe fille
    @Transaction
    public MatiereAndExercice getExerciceAndMatiere(String nomMatiere) {

        MatiereAndExercice retour = new MatiereAndExercice();
        retour.exercices = new ArrayList<>();

        MatiereAndCalcul calculs = getCalculAndMatiere(nomMatiere);
        if(calculs != null) {
            retour.exercices.addAll(calculs.calculs);
        }

        MatiereAndQCM qcms = getQCMAndMatiere(nomMatiere);
        if(qcms != null) {
            retour.exercices.addAll(qcms.qcms);
        }

        return retour;
    }

    // Partie Calculs //

    @Transaction
    @Query("SELECT * FROM matiere WHERE nom = :nomMatiere")
    abstract MatiereAndCalcul getCalculAndMatiere(String nomMatiere);

    @Transaction
    @Query("SELECT * FROM matiere WHERE nom = :nomMatiere")
    abstract MatiereAndQCM getQCMAndMatiere(String nomMatiere);

}