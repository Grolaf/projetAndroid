package model;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import database.DAO.ExerciceDAO;
import database.DAO.MatiereDAO;
import database.DAO.UtilisateurExerciceCrossRefDAO;
import model.referencesClass.UtilisateurAndExercice;

@Entity
public class Utilisateur implements Parcelable {

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

    protected Utilisateur(Parcel in) {
        utilisateurID = in.readInt();
        prenom = in.readString();
        nom = in.readString();
        avatar = in.readString();
        this.exercicesResolus = new HashMap<>();
    }

    public static final Creator<Utilisateur> CREATOR = new Creator<Utilisateur>() {
        @Override
        public Utilisateur createFromParcel(Parcel in) {
            return new Utilisateur(in);
        }

        @Override
        public Utilisateur[] newArray(int size) {
            return new Utilisateur[size];
        }
    };

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

    public ArrayList<Exercice> getExercicesResolusArrayList()
    {
        ArrayList<Exercice> exercices = new ArrayList<>();
        for(String s : this.exercicesResolus.keySet())
        {
            for(Niveau n : this.exercicesResolus.get(s).keySet())
            {
                exercices.addAll(this.exercicesResolus.get(s).get(n));
            }
        }
        return exercices;
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

    public void fetchElementsFromDatabase(UtilisateurExerciceCrossRefDAO utilisateurExerciceCrossRefDAO, ExerciceDAO exerciceDAO, MatiereDAO matiereDAO)
    {
        this.exercicesResolus = new HashMap<>();
        UtilisateurAndExercice exercices = utilisateurExerciceCrossRefDAO.getUtilisateurWithExercice(this.nom, this.prenom);

        if(exercices != null) {
            ArrayList<Exercice> exercicesResolus = (ArrayList) exercices.exercices;

            for (Exercice e : exercicesResolus) {
                e.fetchElementsFromDatabase(utilisateurExerciceCrossRefDAO, exerciceDAO, matiereDAO);
                addExerciceResolu(e);
            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(utilisateurID);
        dest.writeString(prenom);
        dest.writeString(nom);
        dest.writeString(avatar);
    }
}

