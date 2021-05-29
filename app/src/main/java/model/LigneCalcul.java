package model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class LigneCalcul {

    @PrimaryKey(autoGenerate = true)
    private int ligneId;
    @NonNull private String enonce;
    private double solution;

    //////////////////////////////////////////////////////////////////////////

    public LigneCalcul(String enonce, double solution)
    {
        this.enonce = enonce;
        this.solution = solution;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Getters

    public int getLigneId() {return this.ligneId;}
    public String getEnonce()
    {
        return this.enonce;
    }
    public double getSolution()
    {
        return this.solution;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Setters

    public void setLigneId(int ligneId) { this.ligneId = ligneId;}
    public void setEnonce(String enonce)
    {
        this.enonce = enonce;
    }
    public void setSolution(double solution)
    {
        this.solution = solution;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Methods
}
