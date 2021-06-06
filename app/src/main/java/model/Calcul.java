package model;

import androidx.room.Entity;
import androidx.room.Ignore;

import java.util.ArrayList;
import java.util.Iterator;

import database.DAO.ExerciceDAO;
import database.DAO.MatiereDAO;
import database.DAO.UtilisateurExerciceCrossRefDAO;
import model.referencesClass.CalculAndLigneCalcul;

@Entity
public class Calcul extends Exercice{

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

    // Retourne le nombre d'erreurs (si 0 est retourné, alors c'est un sans faute)
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
    public void getElementsFromDatabase(UtilisateurExerciceCrossRefDAO utilisateurExerciceCrossRefDAO, ExerciceDAO exerciceDAO, MatiereDAO matiereDAO)
    {
        // Récupération des vainqueurss
        super.getElementsFromDatabase(utilisateurExerciceCrossRefDAO, exerciceDAO, matiereDAO);

        CalculAndLigneCalcul lignes = exerciceDAO.getCalculAndLigneCalcul(this.exerciceId);

        if(lignes != null) {
            this.lignes = (ArrayList) lignes.lignes;
        }

    }



}

