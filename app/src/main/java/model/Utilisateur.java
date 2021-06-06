package model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.HashMap;

import database.DAO.ExerciceDAO;
import database.DAO.MatiereDAO;
import database.DAO.UtilisateurExerciceCrossRefDAO;
import model.referencesClass.UtilisateurAndExercice;

@Entity
public class Utilisateur {

    @PrimaryKey(autoGenerate = true)
    private int utilisateurID;
    @NonNull private String prenom;
    @NonNull private String nom;
    private String avatar;
    @Ignore
    private HashMap<String, HashMap<Niveau, ArrayList<Exercice>>> exercicesResolus;

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
    public Utilisateur(String login, String password, String avatar, HashMap<String, HashMap<Niveau, ArrayList<Exercice>>> exercicesResolus)
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

    public HashMap<String, HashMap<Niveau, ArrayList<Exercice>>> getExercicesResolus()
    {
        return this.exercicesResolus;
    }

    public ArrayList<Exercice> getExercicesResolus(Niveau n, Matiere m)
    {
        if(this.exercicesResolus.get(m.getNom()) == null)
        {
            return null;
        }
        return this.exercicesResolus.get(m.getNom()).get(n);
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
    public void setExercicesResolus(HashMap<String, HashMap<Niveau, ArrayList<Exercice>>> exercicesResolus)
    {
        this.exercicesResolus = exercicesResolus;
    }

    // L'utilistaeur n'a pas forcément réussi des exercices dans tous les niveaux et/ou toutes les matières
    // Cette méthode se charge de l'ajout s'il n'existe pas
    public void addExerciceResolu(Exercice e)
    {
        // Cette (grosse) condition s'assure que l'exercice n'a pas déjà été inséré
        if(!(this.exercicesResolus.get(e.getMatiere().getNom()) != null && this.exercicesResolus.get(e.getMatiere().getNom()).get(e.getNiveau()) != null && this.exercicesResolus.get(e.getMatiere().getNom()).get(e.getNiveau()).contains(e))) {
            if (this.exercicesResolus.get(e.getMatiere().getNom()) != null) {
                if (this.exercicesResolus.get(e.getMatiere().getNom()).get(e.getNiveau()) != null) {
                    this.exercicesResolus.get(e.getMatiere().getNom()).get(e.getNiveau()).add(e);
                } else {
                    this.exercicesResolus.get(e.getMatiere().getNom()).put(e.getNiveau(), new ArrayList<>());
                    this.exercicesResolus.get(e.getMatiere().getNom()).get(e.getNiveau()).add(e);
                }
            } else {
                this.exercicesResolus.put(e.getMatiere().getNom(), new HashMap<>());
                this.exercicesResolus.get(e.getMatiere().getNom()).put(e.getNiveau(), new ArrayList<>());
                this.exercicesResolus.get(e.getMatiere().getNom()).get(e.getNiveau()).add(e);
            }
            if(!e.getVainqueurs().contains(this))
            {
                e.addVainqueur(this);
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Methods

    public boolean equals(Utilisateur other){
        return this.prenom.equals(other.prenom) && this.nom.equals(other.nom) && this.avatar.equals(other.avatar);
    }

    public void getElementsFromDataBase(UtilisateurExerciceCrossRefDAO utilisateurExerciceCrossRefDAO, ExerciceDAO exerciceDAO, MatiereDAO matiereDAO)
    {
        UtilisateurAndExercice exercices = utilisateurExerciceCrossRefDAO.getUtilisateurWithExercice(this.nom, this.prenom);

        if(exercices != null) {
            ArrayList<Exercice> exercicesResolus = (ArrayList) exercices.exercices;

            for (Exercice e : exercicesResolus) {
                e.getElementsFromDatabase(utilisateurExerciceCrossRefDAO, exerciceDAO, matiereDAO);
                addExerciceResolu(e);
            }
        }
    }
}

