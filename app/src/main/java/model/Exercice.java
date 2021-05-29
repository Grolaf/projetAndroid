package model;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.ArrayList;

import model.Converters.NiveauConverter;

@Entity
public abstract class Exercice {

    @PrimaryKey(autoGenerate = false)
    @NonNull public int id;
    public String titre;
    @TypeConverters(NiveauConverter.class)
    public Niveau niveau;
    public String nomMatiere;
    @Ignore
    public Matiere matiere;
    public String nomPrenomVainqueur;
    @Ignore
    public ArrayList<Utilisateur> vainqueurs;

    ///////////////////////////////////////////////////////////////////////////
    // Constructor

    public Exercice(String titre, Niveau niveau)
    {
        this.titre = titre;
        this.niveau = niveau;
        this.vainqueurs = new ArrayList<>();
    }

    ///////////////////////////////////////////////////////////////////////////
    // Getters

    public String getTitre()
    {
        return this.titre;
    }

    public int getId()
    {
        return this.id;
    }

    public Niveau getNiveau()
    {
        return this.niveau;
    }

    public Matiere getMatiere()
    {
        return this.matiere;
    }

    public ArrayList<Utilisateur> getVainqueurs()
    {
        return this.vainqueurs;
    }
    ///////////////////////////////////////////////////////////////////////////
    // Setters

    public void setTitre(String titre)
    {
        this.titre = titre;
    }

    public void setNiveau(Niveau n)
    {
        this.niveau = n;
    }

    public void setMatiere(Matiere m)
    {
        this.matiere = m;
    }

    public void addVainqueur(Utilisateur u)
    {
        this.vainqueurs.add(u);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Methods

    public boolean isWinner(Utilisateur u)
    {
        return this.vainqueurs.contains(u);
    }
}

