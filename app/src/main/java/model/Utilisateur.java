package model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.ArrayList;
import java.util.HashMap;

@Entity
public class Utilisateur {

    @PrimaryKey(autoGenerate = true)
    private int utilisateurID;
    @NonNull private String prenom;
    @NonNull private String nom;
    private String avatar;
    @Ignore
    private HashMap<Matiere, HashMap<Niveau, ArrayList<Exercice>>> exercicesResolus;

    ///////////////////////////////////////////////////////////////////////////
    // Constructors

    public Utilisateur(String prenom, String nom, String avatar)
    {
        this.prenom = prenom;
        this.nom = nom;
        this.avatar = avatar;
        this.exercicesResolus = new HashMap<>();
    }

    @Ignore
    public Utilisateur(String login, String password, String avatar, HashMap<Matiere, HashMap<Niveau, ArrayList<Exercice>>> exercicesResolus)
    {
        this.prenom = login;
        this.nom = password;

        this.avatar = avatar;
        this.exercicesResolus = exercicesResolus;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Getters

    public int getUtilisateurID()
    {
        return this.utilisateurID;
    }

    public String getPrenom()
    {
        return prenom;
    }

    public String getNom()
    {
        return nom;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public HashMap<Matiere, HashMap<Niveau, ArrayList<Exercice>>> getExercicesResolus()
    {
        return this.exercicesResolus;
    }

    public ArrayList<Exercice> getExercicesResolus(Niveau n, Matiere m)
    {
        if(this.exercicesResolus.get(m) == null)
        {
            return null;
        }
        return this.exercicesResolus.get(m).get(n);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Setters

    public void setUtilisateurID(int id)
    {
        this.utilisateurID = id;
    }
    public void setPrenom(String prenom)
    {
        this.prenom = prenom;
    }
    public void setNom(String nom)
    {
        this.nom = nom;
    }
    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }
    public void setExercicesResolus(HashMap<Matiere, HashMap<Niveau, ArrayList<Exercice>>> exercicesResolus)
    {
        this.exercicesResolus = exercicesResolus;
    }

    // L'utilistaeur n'a pas forcément réussi des exercices dans tous les niveaux et/ou toutes les matières
    // Cette méthode se charge de l'ajout s'il n'existe pas
    public void addExerciceResolu(Exercice e)
    {
        // Cette (grosse) condition s'assure que l'exercice n'a pas déjà été inséré
        if(!(this.exercicesResolus.get(e.getMatiere()) != null && this.exercicesResolus.get(e.getMatiere()).get(e.getNiveau()) != null && this.exercicesResolus.get(e.getMatiere()).get(e.getNiveau()).contains(e))) {
            if (this.exercicesResolus.get(e.getMatiere()) != null) {
                if (this.exercicesResolus.get(e.getMatiere()).get(e.getNiveau()) != null) {
                    this.exercicesResolus.get(e.getMatiere()).get(e.getNiveau()).add(e);
                } else {
                    this.exercicesResolus.get(e.getMatiere()).put(e.getNiveau(), new ArrayList<>());
                    this.exercicesResolus.get(e.getMatiere()).get(e.getNiveau()).add(e);
                }
            } else {
                this.exercicesResolus.put(e.getMatiere(), new HashMap<>());
                this.exercicesResolus.get(e.getMatiere()).put(e.getNiveau(), new ArrayList<>());
                this.exercicesResolus.get(e.getMatiere()).get(e.getNiveau()).add(e);
            }
            e.addVainqueur(this);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Methods
}

