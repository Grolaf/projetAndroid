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
public class QCM extends Exercice{

    @Ignore
    private ArrayList<LigneQCM> lignes;

    ///////////////////////////////////////////////////////////////////////////

    public QCM(String titre, Niveau niveau, String nomMatiere)
    {
        super(titre, niveau, nomMatiere);
        this.lignes = new ArrayList<>();
    }

    @Ignore
    public QCM(String titre, Niveau niveau, Matiere matiere)
    {
        super(titre, niveau, matiere);
        this.lignes = new ArrayList<>();
    }


    @Ignore
    public QCM(String titre, Niveau niveau, Matiere matiere,  ArrayList<LigneQCM> lignes)
    {
        super(titre, niveau, matiere);
        this.lignes = lignes;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Getters

    public ArrayList<LigneQCM> getLignes()
    {
        return this.lignes;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Setters

    public void setLignes(ArrayList<LigneQCM> lignes)
    {
        this.lignes = lignes;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Methods

    // Retourne le nombre d'erreurs (si 0 est retourné, alors c'est un sans faute)
    public int resultat(ArrayList<String> reponses)
    {
        int erreur = 0;

        Iterator it = reponses.iterator();
        for(LigneQCM l : this.lignes)
        {
            if(it.hasNext())
            {
                String rep = (String) it.next();
                if(!rep.equals(l.getBonneReponse()))
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
        // Récupération des vainqueurss
        super.fetchElementsFromDatabase(utilisateurExerciceCrossRefDAO, exerciceDAO, matiereDAO);

    }

    public void fetchLignesFromDatabase(ExerciceDAO exerciceDao)
    {
        CalculAndLigneCalcul lignes = exerciceDao.getCalculAndLigneCalcul(this.exerciceId);

        if(lignes != null) {
            this.lignes = (ArrayList) lignes.lignes;
        }
    }

    protected QCM(Parcel in) {
        super(in);
        lignes = new ArrayList<>();
    }

    public static final Parcelable.Creator<QCM> CREATOR = new Parcelable.Creator<QCM>() {
        @Override
        public QCM createFromParcel(Parcel in) {
            return new QCM(in);
        }

        @Override
        public QCM[] newArray(int size) {
            return new QCM[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

}
