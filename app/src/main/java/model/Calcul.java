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
        Iterator it = this.lignes.iterator();
        int erreur = 0;

        for(double rep : reponses)
        {
            if(it.hasNext())
            {
                LigneCalcul l = (LigneCalcul)it.next();
                if(rep == l.getSolution())
                {
                    erreur++;
                }
            }
        }
        return erreur;
    }

}

