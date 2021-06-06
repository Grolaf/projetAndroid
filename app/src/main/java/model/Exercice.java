package model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.ArrayList;

import database.DAO.ExerciceDAO;
import database.DAO.MatiereDAO;
import database.DAO.UtilisateurExerciceCrossRefDAO;
import model.Converters.NiveauConverter;
import model.referencesClass.ExerciceAndUtilisateur;
import model.referencesClass.MatiereAndExercice;


// Cette classe devrait être abstraite, Room n'est pas d'accord. On se plie aux règles de la machine...
@Entity
public class Exercice implements Parcelable {

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
        this.vainqueurs = new ArrayList<>();
        this.nomMatiere = nomMatiere;
        this.vainqueurs = new ArrayList<>();
    }

    @Ignore
    public Exercice(String titre, Niveau niveau, Matiere matiere)
    {
        this.titre = titre;
        this.niveau = niveau;
        matiere.addExercice(this);
        this.matiere = matiere;
        if(matiere == null)
        {
            this.nomMatiere = "";
        }
        else
        {
            this.nomMatiere = matiere.getNom();
        }
        this.vainqueurs = new ArrayList<>();
    }

    ///////////////////////////////////////////////////////////////////////////
    // Getters

    protected Exercice(Parcel in) {
        exerciceId = in.readInt();
        titre = in.readString();
        nomMatiere = in.readString();
        vainqueurs = in.createTypedArrayList(Utilisateur.CREATOR);
        matiere = in.readParcelable(Matiere.class.getClassLoader());
    }

    public static final Creator<Exercice> CREATOR = new Creator<Exercice>() {
        @Override
        public Exercice createFromParcel(Parcel in) {
            return new Exercice(in);
        }

        @Override
        public Exercice[] newArray(int size) {
            return new Exercice[size];
        }
    };

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

    public String getNomMatiere()
    {
        return this.nomMatiere;
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

    public void setNomMatiere(String nomMatiere)
    {
        this.nomMatiere = nomMatiere;
    }

    public void addVainqueur(Utilisateur u)
    {
        if(!this.vainqueurs.contains(u))
        {
            this.vainqueurs.add(u);
        }
        u.addExerciceResolu(this);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Methods

    public boolean isWinner(Utilisateur u)
    {
        for(Utilisateur vainqueur: this.vainqueurs)
        {
            if(vainqueur.equals(u))
                return true;
        }
        return false;
    }

    public boolean equals(Exercice other)
    {
        return this.titre.equals(other.titre);
    }

    public void getElementsFromDatabase(UtilisateurExerciceCrossRefDAO utilisateurExerciceCrossRefDAO, ExerciceDAO exerciceDAO, MatiereDAO matiereDAO)
    {
        this.matiere = matiereDAO.getMatiereWithID(this.nomMatiere);

        ExerciceAndUtilisateur utilisateurs = utilisateurExerciceCrossRefDAO.getExerciceWithUtilisateur(this.exerciceId);
        if(utilisateurs != null) {
            this.vainqueurs = (ArrayList) utilisateurs.utilisateurs;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(exerciceId);
        dest.writeString(titre);
        dest.writeString(nomMatiere);
        dest.writeTypedList(vainqueurs);
        dest.writeParcelable(matiere, PARCELABLE_WRITE_RETURN_VALUE);
    }
}

