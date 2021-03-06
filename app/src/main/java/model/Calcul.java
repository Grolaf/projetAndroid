package model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;

import java.util.ArrayList;
import java.util.Iterator;

import database.DAO.ExerciceDAO;
import database.DAO.MatiereDAO;
import database.DAO.UtilisateurExerciceCrossRefDAO;
import model.referencesClass.CalculAndLigneCalcul;

@Entity
public class Calcul extends Exercice implements Parcelable {

    @Ignore
    private ArrayList<LigneCalcul> lignes;

    ///////////////////////////////////////////////////////////////////////////

    public Calcul(String titre, Niveau niveau, String nomMatiere)
    {
        super(titre, niveau, nomMatiere);
        this.lignes = new ArrayList<>();
    }

    @Ignore
    public Calcul(String titre, Niveau niveau, Matiere matiere)
    {
        super(titre, niveau, matiere);
        this.lignes = new ArrayList<>();
    }


    @Ignore
    public Calcul(String titre, Niveau niveau, Matiere matiere,  ArrayList<LigneCalcul> lignes)
    {
        super(titre, niveau, matiere);
        this.lignes = lignes;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Getters

    public ArrayList<LigneCalcul> getLignes()
    {
        return this.lignes;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Setters

    public void setLignes(ArrayList<LigneCalcul> lignes)
    {
        this.lignes = lignes;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Methods

    // Retourne le nombre d'erreurs (si 0 est retournĂ©, alors c'est un sans faute)
    public int resultat(ArrayList<Double> reponses)
    {
        int erreur = 0;

        Iterator it = reponses.iterator();
        for(LigneCalcul l : this.lignes)
        {
            if(it.hasNext())
            {
                Double rep = (Double)it.next();
                if(rep != l.getSolution())
                {
                    erreur++;
                }
            }
            else
            {
                erreur++;
            }
        }
        return erreur;
    }

    @Override
    public void fetchElementsFromDatabase(UtilisateurExerciceCrossRefDAO utilisateurExerciceCrossRefDAO, ExerciceDAO exerciceDAO, MatiereDAO matiereDAO)
    {
        // RĂ©cupĂ©ration des vainqueurss
        super.fetchElementsFromDatabase(utilisateurExerciceCrossRefDAO, exerciceDAO, matiereDAO);

    }

    public void fetchLignesFromDatabase(ExerciceDAO exerciceDao)
    {
        CalculAndLigneCalcul lignes = exerciceDao.getCalculAndLigneCalcul(this.exerciceId);

        if(lignes != null) {
            this.lignes = (ArrayList) lignes.lignes;
        }
    }

    protected Calcul(Parcel in) {
        super(in);
        lignes = new ArrayList<>();
    }

    public static final Creator<Calcul> CREATOR = new Creator<Calcul>() {
        @Override
        public Calcul createFromParcel(Parcel in) {
            return new Calcul(in);
        }

        @Override
        public Calcul[] newArray(int size) {
            return new Calcul[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }



}

