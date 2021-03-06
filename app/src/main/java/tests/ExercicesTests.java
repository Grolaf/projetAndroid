package tests;

import java.util.ArrayList;

import model.*;

public class ExercicesTests {

    public ExercicesTests()
    {
        testLigneCalcul();
        testCalcul();
        testMatiere();
        testUtilistaeur();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static final void testLigneCalcul() {

        LigneCalcul l1 = new LigneCalcul(3, "+", 2);
        assert(l1.getEnonce().equals("3 + 2 = "));
        assert (l1.getSolution() == 5);
        assert (l1.getSolution() == 5.000);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static final void testCalcul()
    {
        LigneCalcul l1 = new LigneCalcul(3, "+", 2);
        LigneCalcul l2 = new LigneCalcul(4, "*", 2);
        LigneCalcul l3 = new LigneCalcul(4, "/", 2);

        ArrayList<LigneCalcul> e = new ArrayList<>();
        e.add(l1);
        e.add(l2);
        e.add(l3);

        Matiere maths = new Matiere("Maths", "");

        Calcul c = new Calcul("Calculs basiques", Niveau.FACILE, maths, e);
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
        Matiere m = new Matiere("Math??matiques", "");
        assert(m.getImage() == "");
        assert(m.getNom() == "Math??matiques");
        assert(m.getNiveaux() == null);
        assert(m.getExercices(Niveau.FACILE) == null);
        assert(m.getExercices(Niveau.MOYEN) == null);
        assert(m.getExercices(Niveau.DIFFICILE) == null);

        Matiere maths = new Matiere("Maths", "");
        LigneCalcul l1 = new LigneCalcul(3, "+", 2);
        ArrayList<LigneCalcul> e = new ArrayList<>();
        e.add(l1);
        Calcul c = new Calcul("Calculs basiques", Niveau.FACILE,maths, e);

        assert(c.getMatiere() == maths);
        assert(maths.getNiveaux().get(0) == Niveau.FACILE);
        assert(maths.getExercices(Niveau.FACILE).size() == 1);
        assert(maths.getExercices(Niveau.FACILE).get(0) == c);

        Calcul c2 = new Calcul("Calculs basiques", Niveau.DIFFICILE,maths, e);

        assert(maths.getNiveaux().size() == 2);
        System.out.println(m.getNiveaux());
        assert(maths.getNiveaux().get(1) == Niveau.DIFFICILE);
        assert(maths.getExercices(Niveau.DIFFICILE).get(0) == c2);
        assert(maths.getExercices(Niveau.DIFFICILE).size() == 1);
        assert(maths.getExercices(Niveau.FACILE).get(0) == c);
        assert(maths.getExercices(Niveau.FACILE).size() == 1);


        // Redite du dessus (pour ??viter les doublons)
        maths.addExercice(c2);
        assert(maths.getNiveaux().size() == 2);
        assert(maths.getNiveaux().get(1) == Niveau.DIFFICILE);
        assert(maths.getExercices(Niveau.DIFFICILE).get(0) == c2);
        assert(maths.getExercices(Niveau.DIFFICILE).size() == 1);
        assert(maths.getExercices(Niveau.FACILE).get(0) == c);
        assert(maths.getExercices(Niveau.FACILE).size() == 1);
        assert(c2.getMatiere() == maths);


        // R??cup??ration de tous les exercices de m
        ArrayList<Exercice> ex = new ArrayList<>();

        for(Niveau niv  : maths.getNiveaux())
        {
            for(Exercice exe : maths.getExercices(niv))
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

    public static final void testUtilistaeur()
    {
        Utilisateur u = new Utilisateur("CompteA", "pwdA", "");
        Matiere maths = new Matiere("Maths", "");
        assert(u.getPrenom() == "CompteA");
        assert(u.getAvatar() == "");
        assert(u.getNom() == "pwdA");
        assert(u.getExercicesResolus().size() == 0);

        LigneCalcul l1 = new LigneCalcul(3, "+", 2);
        ArrayList<LigneCalcul> e = new ArrayList<>();
        e.add(l1);
        Calcul c = new Calcul("Calculs basiques", Niveau.FACILE,maths, e);
        Matiere m = new Matiere("Math??matiques", "");

        m.addExercice(c);

        u.addExerciceResolu(c);
        assert(u.getExercicesResolus().size() == 1);
        assert(u.getExercicesResolus(Niveau.FACILE, m).size() == 1);
        assert(u.getExercicesResolus(Niveau.FACILE, m).get(0) == c);
        assert(c.getVainqueurs().size() == 1);
        assert(c.getVainqueurs().contains(u));

        Calcul c2 = new Calcul("Calculs basiques", Niveau.DIFFICILE, maths, e);
        m.addExercice(c2);

        u.addExerciceResolu(c2);

        assert(u.getExercicesResolus().size() == 1);
        assert(u.getExercicesResolus(Niveau.FACILE, m).size() == 1);
        assert(u.getExercicesResolus(Niveau.FACILE, m).get(0) == c);
        assert(u.getExercicesResolus(Niveau.DIFFICILE, m).size() == 1);
        assert(u.getExercicesResolus(Niveau.DIFFICILE, m).get(0) == c2);
        assert(c2.getVainqueurs().size() == 1);
        assert(c2.getVainqueurs().contains(u));

        Calcul c3 = new Calcul("Calculs v3", Niveau.DIFFICILE,maths,  e);
        m.addExercice(c3);

        u.addExerciceResolu(c3);

        assert(u.getExercicesResolus().size() == 1);
        assert(u.getExercicesResolus(Niveau.FACILE, m).size() == 1);
        assert(u.getExercicesResolus(Niveau.FACILE, m).get(0) == c);
        assert(u.getExercicesResolus(Niveau.DIFFICILE, m).size() == 2);
        assert(u.getExercicesResolus(Niveau.DIFFICILE, m).get(1) == c3);
        assert(c3.getVainqueurs().size() == 1);
        assert(c3.getVainqueurs().contains(u));


        // Redite du dessus (pour ??viter les doublons)
        u.addExerciceResolu(c3);
        assert(u.getExercicesResolus().size() == 1);
        assert(u.getExercicesResolus(Niveau.FACILE, m).size() == 1);
        assert(u.getExercicesResolus(Niveau.FACILE, m).get(0) == c);
        assert(u.getExercicesResolus(Niveau.DIFFICILE, m).size() == 2);
        assert(u.getExercicesResolus(Niveau.DIFFICILE, m).get(1) == c3);
        assert(c3.getVainqueurs().size() == 1);
        assert(c3.getVainqueurs().contains(u));


        Utilisateur u2 = new Utilisateur("CompteB", "pwdB", "");

        u2.addExerciceResolu(c);
        assert(c.getVainqueurs().size() == 2);
        assert(c.getVainqueurs().get(1) == u2);
    }
}

