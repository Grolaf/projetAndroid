package model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

public enum Niveau implements Comparable<Niveau>{

    FACILE("FACILE", 1),
    MOYEN("MOYEN", 2),
    DIFFICILE("DIFFICILE", 3);

    private final int valeur;
    private final String nom;


    Niveau(String nom, int valeur)
    {
        this.nom = nom;
        this.valeur = valeur;
    }

    public String getNom() {
        return this.nom;
    }

    public int getValeur()
    {
        return this.valeur;
    }

    public boolean equals(Niveau other)
    {
        return this.valeur == other.valeur;
    }

}
