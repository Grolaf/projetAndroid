package Tests;

import java.util.ArrayList;

import model.*;

public class ExercicesTests {

    public ExercicesTests()
    {
        testLigneCalcul();
        testCalcul();
    }

    public static final void testLigneCalcul() {

        LigneCalcul l1 = new LigneCalcul("3 + 2 = ", 5);
        assert(l1.getEnonce() == "3 + 2 = ");
        assert (l1.getSolution() == 5);
        assert (l1.getSolution() == 5.000);


    }

    public static final void testCalcul()
    {
        LigneCalcul l1 = new LigneCalcul("3 + 2 = ", 5);
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
        assert(c.getVainqueurs().size() == 0);

        ArrayList<Double> answ = new ArrayList<>();
        answ.add(5.0);
        answ.add(8.0);
        answ.add(2.0);

        assert(c.resultat(answ) == 0);
        answ = new ArrayList<>();
        answ.add(5.0);
        answ.add(8.0);
        answ.add(3.0); // erreur

        assert(c.resultat(answ) == 1);
        answ.remove(0);
        assert(c.resultat(answ) == 3);
        answ.add(2.0);
        assert(c.resultat(answ) == 2);
    }

    public static final void testMatiere()
    {
        Matiere m = new Matiere();

    }
}
