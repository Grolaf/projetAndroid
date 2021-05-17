package tests;

import java.util.ArrayList;

import model.*;

public class ExercicesTests {

    public ExercicesTests()
    {
        testLigneCalcul();
        testCalcul();
        testMatiere();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static final void testLigneCalcul() {

        LigneCalcul l1 = new LigneCalcul("3 + 2 = ", 5);
        assert(l1.getEnonce() == "3 + 2 = ");
        assert (l1.getSolution() == 5);
        assert (l1.getSolution() == 5.000);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static final void testCalcul()
    {
        LigneCalcul l1 = new LigneCalcul("3 + 2 = ", 5);
        LigneCalcul l2 = new LigneCalcul("4 x 2 = ", 8);
        LigneCalcul l3 = new LigneCalcul("4 / 2 = ", 2);

        ArrayList<LigneCalcul> e = new ArrayList<>();
        e.add(l1);
        e.add(l2);
        e.add(l3);

        Calcul c = new Calcul("Calculs basiques", Niveau.FACILE, e);
        Utilisateur u = new Utilisateur("u", "", "");

        assert(c.isWinner(u) == false);
        assert(c.getLignes() == e);
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

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static final void testMatiere()
    {
        Matiere m = new Matiere("Mathématiques", "");
        assert(m.getImage() == "");
        assert(m.getNom() == "Mathématiques");
        assert(m.getNiveaux() == null);
        assert(m.getExercices(Niveau.FACILE) == null);
        assert(m.getExercices(Niveau.MOYEN) == null);
        assert(m.getExercices(Niveau.DIFFICILE) == null);

        LigneCalcul l1 = new LigneCalcul("3 + 2 = ", 5);
        ArrayList<LigneCalcul> e = new ArrayList<>();
        e.add(l1);
        Calcul c = new Calcul("Calculs basiques", Niveau.FACILE,e);

        m.addExercice(c);

        assert(m.getNiveaux().get(0) == Niveau.FACILE);
        assert(m.getExercices(Niveau.FACILE).size() == 1);
        assert(m.getExercices(Niveau.FACILE).get(0) == c);
        assert(c.getMatiere() == m);

        Calcul c2 = new Calcul("Calculs basiques", Niveau.DIFFICILE, e);

        m.addExercice(c2);
        assert(m.getNiveaux().size() == 2);
        assert(m.getNiveaux().get(1) == Niveau.DIFFICILE);
        assert(m.getExercices(Niveau.DIFFICILE).get(0) == c2);
        assert(m.getExercices(Niveau.FACILE).get(0) == c);
        assert(c2.getMatiere() == m);


        // Récupération de tous les exercices de m
        ArrayList<Exercice> ex = new ArrayList<>();

        for(Niveau niv  : m.getNiveaux())
        {
            for(Exercice exe : m.getExercices(niv))
            {
                ex.add(exe);
            }
        }

        assert(ex.size() == 2);
        assert(ex.contains(c));
        assert(ex.contains(c2));
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}

