package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Matiere {

    private String nom;
    private String image;
    private HashMap<Niveau, ArrayList<Exercice>> exercices;

    ///////////////////////////////////////////////////////////////////////////
    // Constructors

    public Matiere(String nom, String image)
    {
        this.nom = nom;
        this.image = image;
        this.exercices = new HashMap<>();
    }

    public Matiere(String nom, String image, HashMap<Niveau, ArrayList<Exercice>> exercices)
    {
        this.nom = nom;
        this.image = image;
        this.exercices = exercices;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Getters

    public String getNom()
    {
        return this.nom;
    }

    public String getImage()
    {
        return this.image;
    }

    public ArrayList<Exercice> getExercices(Niveau n)
    {
        return this.exercices.get(n);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Setters

    public void setNom(String nom)
    {
        this.nom =nom;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public void setExercices(HashMap<Niveau, ArrayList<Exercice>> e)
    {
        this.exercices = e;
    }

    // La matière n'a pas forcément des exercices dans tous les niveaux. Cette méthode se charge de l'ajout du niveau s'il n'existe pas
    public void addExercice(Exercice e)
    {
        if(this.exercices.get(e.getNiveau()) != null)
        {
            this.exercices.get(e.getNiveau()).add(e);
        }
        else
        {
            this.exercices.put(e.getNiveau(), new ArrayList<>());
            this.exercices.get(e.getNiveau()).add(e);
        }
    }


    ///////////////////////////////////////////////////////////////////////////
    // Methods

}
