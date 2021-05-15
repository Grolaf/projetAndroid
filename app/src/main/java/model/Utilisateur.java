package model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Utilisateur {

    private String login;
    private String password;
    private String avatar;
    private HashMap<Matiere, HashMap<Niveau, ArrayList<Exercice>>> exercicesResolus;

    ///////////////////////////////////////////////////////////////////////////
    // Constructors

    public Utilisateur(String login, String password, String avatar)
    {
        this.login = login;
        this.password = password;
        this.avatar = avatar;
        this.exercicesResolus = new HashMap<>();
    }

    public Utilisateur(String login, String password, String avatar, HashMap<Matiere, HashMap<Niveau, ArrayList<Exercice>>> exercicesResolus)
    {
        this.login = login;
        this.password = password;

        this.avatar = avatar;
        this.exercicesResolus = exercicesResolus;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Getters

    public String getLogin()
    {
        return login;
    }

    public String getPassword()
    {
        return password;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public ArrayList<Exercice> getExercicesResolus(Niveau n, Matiere m)
    {
        if(this.exercicesResolus.get(n) == null)
        {
            return null;
        }
        return this.exercicesResolus.get(n).get(m);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Setters

    public void setLogin(String login)
    {
        this.login = login;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }
    public void setExercicesResolus(HashMap<Matiere, HashMap<Niveau, ArrayList<Exercice>>> exercicesResolus)
    {
        this.exercicesResolus = exercicesResolus;
    }

    // L'utilistaeur n'a pas forcément réussi des exercices dans tous les niveaux et/ou toutes les matières
    // Cette méthode se charge de l'ajout s'il n'existe pas
    public void addExerciceResolu(Exercice e)
    {
        if(this.exercicesResolus.get(e.getMatiere()) != null)
        {
            if(this.exercicesResolus.get(e.getMatiere()).get(e.getNiveau()) != null)
            {
                this.exercicesResolus.get(e.getMatiere()).get(e.getNiveau()).add(e);
            }
            else
            {
                this.exercicesResolus.get(e.getMatiere()).put(e.getNiveau(), new ArrayList<>());
            }
        }
        else
        {
            this.exercicesResolus.put(e.getMatiere(), new HashMap<>());
            this.exercicesResolus.get(e.getMatiere()).put(e.getNiveau(), new ArrayList<>());
            this.exercicesResolus.get(e.getMatiere()).get(e.getNiveau()).add(e);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Methods
}

