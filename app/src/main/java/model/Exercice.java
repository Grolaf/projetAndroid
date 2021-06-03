package model;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.ArrayList;

import model.Converters.NiveauConverter;



// Cette classe devrait être abstraite, Room n'est pas d'accord. On se plie aux règles de la machine...
@Entity
public class Exercice {

    @PrimaryKey(autoGenerate = true)
    @NonNull public int exerciceId;
    public String titre;
    @TypeConverters(NiveauConverter.class)
    public Niveau niveau;
    public String nomMatiere;
    @Ignore
    public Matiere matiere;
    @Ignore
    public ArrayList<Utilisateur> vainqueurs;

    ///////////////////////////////////////////////////////////////////////////
    // Constructor

    public Exercice(String titre, Niveau niveau, String nomMatiere)
    {
        this.titre = titre;
        this.niveau = niveau;
        this.nomMatiere = matiere.getNom();
        this.vainqueurs = new ArrayList<>();
    }

    @Ignore
    public Exercice(String titre, Niveau niveau, Matiere matiere)
    {
        this.titre = titre;
        this.niveau = niveau;
        this.matiere = matiere;
        this.nomMatiere = matiere.getNom();
        this.vainqueurs = new ArrayList<>();
    }

    ///////////////////////////////////////////////////////////////////////////
    // Getters

    public String getTitre()
    {
        return this.titre;
    }

    public int getExerciceId()
    {
        return this.exerciceId;
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

    public void setExerciceId(int id)
    {
        this.exerciceId = id;
    }
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

