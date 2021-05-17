package model;

import androidx.core.content.res.TypedArrayUtils;

import java.util.ArrayList;
import java.util.Iterator;


public class Calcul extends Exercice{

    private ArrayList<LigneCalcul> lignes;

    ///////////////////////////////////////////////////////////////////////////

    public Calcul(String titre, Niveau niveau, Matiere matiere, ArrayList<LigneCalcul> lignes)
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

}

