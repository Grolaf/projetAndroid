package model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class LigneQCM implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int ligneId;
    @NonNull private String enonce;
    @NonNull private String reponseA;
    @NonNull private String reponseB;
    private String reponseC;
    private String reponseD;
    @NonNull private String bonneReponse;
    public int exerciceId;


    //////////////////////////////////////////////////////////////////////////

    public LigneQCM(String enonce, String reponseA, String reponseB, String reponseC, String reponseD, String bonneReponse)
    {
        this.enonce = enonce;
        this.reponseA = "A. " +reponseA;
        this.reponseB = "B. " +reponseB;
        this.reponseC = reponseC == null ? null : "C. " +reponseC;
        this.reponseD = reponseD == null ? null : "D. " +reponseD;
        this.bonneReponse = bonneReponse;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Getters

    protected LigneQCM(Parcel in) {
        ligneId = in.readInt();
        enonce = in.readString();
        reponseA = in.readString();
        reponseB = in.readString();
        reponseC = in.readString();
        reponseD = in.readString();
        bonneReponse = in.readString();
        exerciceId = in.readInt();
    }

    public static final Parcelable.Creator<LigneQCM> CREATOR = new Parcelable.Creator<LigneQCM>() {
        @Override
        public LigneQCM createFromParcel(Parcel in) {
            return new LigneQCM(in);
        }

        @Override
        public LigneQCM[] newArray(int size) {
            return new LigneQCM[size];
        }
    };

    public int getLigneId() {return this.ligneId;}

    public String getEnonce() {
        return enonce;
    }

    public int getExerciceId()
    {
        return this.exerciceId;
    }

    public String getReponseA() {
        return reponseA;
    }

    public String getReponseB() {
        return reponseB;
    }

    public String getReponseC() {
        return reponseC;
    }

    public String getReponseD() {
        return reponseD;
    }

    public String getBonneReponse() {
        return bonneReponse;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Setters

    public void setLigneId(int ligneId) { this.ligneId = ligneId;}



    public void setEnonce(String enonce) {
        this.enonce = enonce;
    }


    public void setReponseA(String reponseA) {
        this.reponseA = reponseA;
    }


    public void setReponseB(String reponseB) {
        this.reponseB = reponseB;
    }


    public void setReponseC(String reponseC) {
        this.reponseC = reponseC;
    }


    public void setReponseD(String reponseD) {
        this.reponseD = reponseD;
    }

    public void setBonneReponse(String bonneReponse) {
        this.bonneReponse = bonneReponse;
    }
///////////////////////////////////////////////////////////////////////////
    // Methods

    public boolean equals(LigneQCM other)
    {
        return this.enonce == other.enonce && this.reponseA.equals(other.reponseA) && this.reponseB.equals(other.reponseB) && this.reponseC.equals(other.reponseC) && this.reponseD.equals(other.reponseD)  &&this.bonneReponse.equals(other.bonneReponse);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ligneId);
        dest.writeString(enonce);
        dest.writeString(reponseA);
        dest.writeString(reponseB);
        dest.writeString(reponseC);
        dest.writeString(reponseD);
        dest.writeString(bonneReponse);
        dest.writeInt(exerciceId);
    }
}
