package model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.text.DecimalFormat;

@Entity
public class LigneCalcul implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int ligneId;
     private int operande1;
     private String operator;
     private int operande2;
    public int exerciceId;


    //////////////////////////////////////////////////////////////////////////

    public LigneCalcul(int operande1, String operator, int operande2)
    {
        this.operande1 = operande1;
        this.operande2 = operande2;
        this.operator = operator;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Getters

    protected LigneCalcul(Parcel in) {
        ligneId = in.readInt();
        operande1 = in.readInt();
        operator = in.readString();
        operande2 = in.readInt();
        exerciceId = in.readInt();
    }

    public static final Creator<LigneCalcul> CREATOR = new Creator<LigneCalcul>() {
        @Override
        public LigneCalcul createFromParcel(Parcel in) {
            return new LigneCalcul(in);
        }

        @Override
        public LigneCalcul[] newArray(int size) {
            return new LigneCalcul[size];
        }
    };

    public int getLigneId() {return this.ligneId;}
    public int getOperande1()
    {
        return this.operande1;
    }
    public int getOperande2()
    {
        return this.operande2;
    }
    public String getOperator()
    {
        return this.operator;
    }
    public String getEnonce()
    {
        return Integer.toString(this.operande1) + " " + this.operator + " " + Integer.toString(this.operande2)  + " = ";
    }
    public double getSolution()
    {
        DecimalFormat formatter = new DecimalFormat(".##");
        switch(this.operator)
        {
            case "+":
                return this.operande1 + this.operande2;
            case "-":
                return this.operande1 - this.operande2;
            case "/":
                return Double.parseDouble(formatter.format((double)this.operande1 / (double)this.operande2));
            case "*":
                return this.operande1 * this.operande2;
            default:
                return 0;
        }
    }
    public int getExerciceId()
    {
        return this.exerciceId;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Setters

    public void setLigneId(int ligneId) { this.ligneId = ligneId;}
    public void setOperande1(int op)
    {
        this.operande1 = op;
    }
    public void setOperande2(int op)
    {
        this.operande2 = op;
    }
    public void setOperator(String op)
    {
        this.operator = op;
    }
    public void setExerciceId(int exerciceId)
    {
        this.exerciceId = exerciceId;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Methods

    public boolean equals(LigneCalcul other)
    {
        return this.operande1 == other.operande1 && this.operande2 == other.operande2 && this.operator.equals(other.operator);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ligneId);
        dest.writeInt(operande1);
        dest.writeString(operator);
        dest.writeInt(operande2);
        dest.writeInt(exerciceId);
    }
}
