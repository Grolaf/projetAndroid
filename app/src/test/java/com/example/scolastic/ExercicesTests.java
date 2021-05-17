package com.example.scolastic;

import java.util.ArrayList;

import model.*;

public class ExercicesTests {

    /////////////////////////////////////////////////////////////////
    // Tests Ligne de calcul

    public static void main() {

        LigneCalcul l1 = new LigneCalcul("3 + 2 = ", 5);
        assert(l1.getEnonce() == "3 + 2 = ");
        assert (l1.getSolution() == 5);
        assert (l1.getSolution() == 5.000);

        /////////////////////////////////////////////////////////////////
        // Tests Calcul

        LigneCalcul l2 = new LigneCalcul("4 x 2 = ", 8);
        LigneCalcul l3 = new LigneCalcul("4 / 2 = ", 2);

        ArrayList<LigneCalcul> e = new ArrayList<>();
        e.add(l1);
        e.add(l2);
        e.add(l3);

        Calcul c = new Calcul("Calculs basiques", Niveau.FACILE, new Matiere("Maths", ""), e);
        Utilisateur u = new Utilisateur("u", "", "");

        assert(c.isWinner(u) == false);
        assert(c.getLignes() == e);
        assert(c.getMatiere().getNom() == "Maths");
        assert(c.getMatiere() == null);

        ArrayList<Double> answ = new ArrayList<>();
        answ.add(5.0);
        answ.add(8.0);
        &answ.add()

        


    }
}
