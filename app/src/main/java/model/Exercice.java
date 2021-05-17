package model;

import java.util.ArrayList;

public abstract class Exercice {

    protected static int count;
    protected int id;
    protected String titre;
    protected Niveau niveau;
    protected Matiere matiere;
    protected ArrayList<Utilisateur> vainqueurs;

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

