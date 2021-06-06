package model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import database.DAO.MatiereDAO;
import model.referencesClass.MatiereAndExercice;

@Entity
public class Matiere {

    @PrimaryKey(autoGenerate = false)
    @NonNull private String nom;
    private String image;
    @Ignore
    private HashMap<Niveau, ArrayList<Exercice>> exercices;

    ///////////////////////////////////////////////////////////////////////////
    // Constructors


    public Matiere(String nom, String image)
    {
        this.nom = nom;
        this.image = image;
        this.exercices = new HashMap<>();
    }
    @Ignore
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

    public HashMap<Niveau, ArrayList<Exercice>> getExercices()
    {
        return this.exercices;
    }

    public ArrayList<Exercice> getExercices(Niveau n)
    {
        return this.exercices.get(n);
    }

    // Retourne un arrayList des niveaux proposés dans la matière
    public ArrayList<Niveau> getNiveaux()
    {
        if(this.exercices.keySet().size() == 0)
        {
            return null;
        }
        ArrayList<Niveau> n = new ArrayList<>();

        for(Niveau niv : this.exercices.keySet())
        {
            n.add(niv);
        }
        Collections.sort(n);
        return n;
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
        if(!(this.exercices.get(e.getNiveau()) != null && this.exercices.get(e.getNiveau()).contains(e))) { // Cette condition s'assure que l'exercice n'est pas déjà inséré
            if (this.exercices.get(e.getNiveau()) != null) {
                this.exercices.get(e.getNiveau()).add(e);
            } else {
                this.exercices.put(e.getNiveau(), new ArrayList<>());
                this.exercices.get(e.getNiveau()).add(e);
            }
            if(e.getMatiere() != this) {
                e.setMatiere(this);
            }
        }
    }


    ///////////////////////////////////////////////////////////////////////////
    // Methods

    public boolean equals(Matiere other)
    {
        return this.nom.equals(other.nom);
    }

    public void getElementsFromDataBase(MatiereDAO matiereDAO)
    {
        MatiereAndExercice matiereExercices = matiereDAO.getExerciceAndMatiere(this.nom);

        if(matiereExercices != null) {
            ArrayList<Exercice> exercices = (ArrayList) matiereExercices.exercices;

            for (Exercice e : exercices) {
                this.addExercice(e);
            }
        }
    }
}
