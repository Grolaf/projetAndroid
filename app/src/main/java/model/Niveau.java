package model;

public enum Niveau implements Comparable<Niveau>{

    FACILE("facile", 1),
    MOYEN("moyen", 2),
    DIFFICILE("difficile", 3);

    private final String nom;
    private final int valeur;

    Niveau(String nom, int valeur)
    {
        this.nom = nom;
        this.valeur = valeur;
    }

    public String getNom()
    {
        return this.nom;
    }

    public int getValeur()
    {
        return this.valeur;
    }
}
