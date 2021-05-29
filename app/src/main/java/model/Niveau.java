package model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

public enum Niveau implements Comparable<Niveau>{

    FACILE("facile", 1),
    MOYEN("moyen", 2),
    DIFFICILE("difficile", 3);

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


}
