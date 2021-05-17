package model;

public class LigneCalcul {

    private String enonce;
    private double solution;

    //////////////////////////////////////////////////////////////////////////

    public LigneCalcul(String enonce, double solution)
    {
        this.enonce = enonce;
        this.solution = solution;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Getters

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
