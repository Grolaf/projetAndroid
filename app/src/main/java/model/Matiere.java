
package model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import database.DAO.ExerciceDAO;
import database.DAO.MatiereDAO;
import database.DAO.UtilisateurExerciceCrossRefDAO;
import model.referencesClass.MatiereAndExercice;
import model.referencesClass.UtilisateurExerciceCrossReference;

@Entity
public class Matiere implements Parcelable {

    @PrimaryKey(autoGenerate = false)
    @NonNull private String nom;
    private String image;
    @Ignore
    private HashMap<Niveau, ArrayList<Exercice>> exercices;

    ///////////////////////////////////////////////////////////////////////////
    // Constructors


    public Matiere(String nom, String image)
    {
        this.nom = nom;
        this.image = image;
        this.exercices = new HashMap<>();
    }
    @Ignore
    public Matiere(String nom, String image, HashMap<Niveau, ArrayList<Exercice>> exercices)
    {
        this.nom = nom;
        this.image = image;
        this.exercices = exercices;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Getters

    protected Matiere(Parcel in) {
        nom = in.readString();
        image = in.readString();
        exercices = in.readHashMap(HashMap.class.getClassLoader());
    }

    public static final Creator<Matiere> CREATOR = new Creator<Matiere>() {
        @Override
        public Matiere createFromParcel(Parcel in) {
            return new Matiere(in);
        }

        @Override
        public Matiere[] newArray(int size) {
            return new Matiere[size];
        }
    };

    public String getNom()
    {
        return this.nom;
    }

    public String getImage()
    {
        return this.image;
    }

    public HashMap<Niveau, ArrayList<Exercice>> getExercices()
    {
        return this.exercices;
    }

    public ArrayList<Exercice> getExercices(Niveau n)
    {
        return this.exercices.get(n);
    }

    public Exercice getRandomExercice(Niveau n)
    {
        Random random = new Random();
        int nbrRandom = random.nextInt(this.exercices.size());
        return this.exercices.get(n).get(nbrRandom);
    }

    public Exercice getRandomExercice(Niveau n, Utilisateur u)
    {
        ArrayList<Exercice> exercicesDispo = this.exercices.get(n);

        for(Exercice e : exercicesDispo)
        {
            if(e.isWinner(u))
            {
                exercicesDispo.remove(e);
            }
        }

        // Si l'utilisateur a terminé tous les exercices du niveau, on lui en remet un qu'il a déjà réussi
        if(exercicesDispo.size() == 0)
            return getRandomExercice(n);


        Random random = new Random();
        int nbrRandom = random.nextInt(exercicesDispo.size());

        return exercicesDispo.get(nbrRandom);
    }

    // Retourne un arrayList des niveaux proposés dans la matière
    public ArrayList<Niveau> getNiveaux()
    {
        boolean accompli = false;

        if(this.exercices.keySet().size() == 0)
        {
            return null;
        }
        ArrayList<Niveau> n = new ArrayList<>();

        for(Niveau niv : this.exercices.keySet())
        {
              n.add(niv);
        }
        Collections.sort(n);
        return n;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Setters

    public void setNom(String nom)
    {
        this.nom =nom;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public void setExercices(HashMap<Niveau, ArrayList<Exercice>> e)
    {
        this.exercices = e;
    }

    // La matière n'a pas forcément des exercices dans tous les niveaux. Cette méthode se charge de l'ajout du niveau s'il n'existe pas
    public void addExercice(Exercice e)
    {
        if(!(this.exercices.get(e.getNiveau()) != null && this.exercices.get(e.getNiveau()).contains(e))) { // Cette condition s'assure que l'exercice n'est pas déjà inséré
            if (this.exercices.get(e.getNiveau()) != null) {
                this.exercices.get(e.getNiveau()).add(e);
            } else {
                this.exercices.put(e.getNiveau(), new ArrayList<>());
                this.exercices.get(e.getNiveau()).add(e);
            }
            if(e.getMatiere() != this) {
                e.setMatiere(this);
            }
        }
    }


    ///////////////////////////////////////////////////////////////////////////
    // Methods

    public boolean equals(Matiere other)
    {
        return this.nom.equals(other.nom);
    }

    public void getElementsFromDataBase(MatiereDAO matiereDAO, UtilisateurExerciceCrossRefDAO utilisateurExerciceCrossRefDAO, ExerciceDAO exerciceDAO)
    {
        MatiereAndExercice matiereExercices = matiereDAO.getExerciceAndMatiere(this.nom);

        if(matiereExercices != null) {
            ArrayList<Exercice> exercices = (ArrayList) matiereExercices.exercices;

            for (Exercice e : exercices) {
                e.getElementsFromDatabase(utilisateurExerciceCrossRefDAO, exerciceDAO, matiereDAO);
                this.addExercice(e);
            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nom);
        dest.writeString(image);
        dest.writeMap(exercices);
    }
}
