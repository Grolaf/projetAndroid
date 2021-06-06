
package tests;

import android.content.Context;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.jar.JarException;

import database.DAO.CalculDAO;
import database.DAO.LigneCalculDAO;
import database.DAO.LigneCalculDAO_Impl;
import database.DAO.MatiereDAO;
import database.DAO.UtilisateurDAO;
import database.DatabaseClient;
import model.Calcul;
import model.LigneCalcul;
import model.Matiere;
import model.Utilisateur;

public class DataBaseTests {

    private DatabaseClient db;

    public DataBaseTests (Context context){

        db = DatabaseClient.getInstance(context.getApplicationContext());

        // ATTENTION : RESET DE TOUTES LES TABLES POUR LES TESTS
        db.getAppDatabase().clearAllTables();

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////

    // Tests basiques des classes avec la BDD

    private final void testUtilisateur()
    {
        Utilisateur a = new Utilisateur("testprenom", "testnom", "");
        Utilisateur b = new Utilisateur("testprenom2", "testnom2", "");

        UtilisateurDAO uDAO = this.db.getAppDatabase().utilisateurDAO();
        uDAO.insert(a);
        uDAO.insert(b);

        Utilisateur aDB = uDAO.getUtilisateur("testPrenom", "testnom");
        assert (aDB.equals(a));
        assert(aDB.getAvatar().equals(""));
        assert(aDB.getNom().equals("testnom"));
        assert(aDB.getPrenom().equals("testprenom"));
        Utilisateur bDB = uDAO.getUtilisateur("testPrenom2", "testnom2");
        assert (bDB.equals(b));
        assert(bDB.getAvatar().equals(""));
        assert(bDB.getNom().equals("testnom2"));
        assert(bDB.getPrenom().equals("testprenom2"));

        assert(aDB.getExercicesResolus().size() == 0);
        assert(bDB.getExercicesResolus().size() == 0);

        try {
            uDAO.insert(a);
        }catch(Exception e){}

        aDB = uDAO.getUtilisateur("testPrenom", "testnom");
        assert (aDB.equals(a));
    }

    private final void testMatiere()
    {
        Matiere a = new Matiere("Maths", "");
        Matiere b = new Matiere("Français", "");

        MatiereDAO mDao = this.db.getAppDatabase().matiereDAO();
        mDao.insert(a);
        mDao.insert(b);

        Matiere aDb = mDao.getMatiereWithID("Maths");
        assert(aDb.equals(a));
        assert(aDb.getNom().equals("Maths"));
        assert(aDb.getExercices().size() == 0);
        assert(aDb.getImage().equals(""));

        Matiere bDb = mDao.getMatiereWithID("Français");
        assert(bDb.equals(b));
        assert(bDb.equals(a));
        assert(bDb.getNom().equals("Français"));
        assert(bDb.getExercices().size() == 0);
        assert(bDb.getImage().equals(""));

        try {
            mDao.insert(aDb);
        }catch(Exception e){}

        aDb = mDao.getMatiereWithID("Maths");
        assert(aDb.equals(a));
    }

    private final void testLigneCalcul()
    {
        LigneCalcul a = new LigneCalcul(3, "+", 2);
        LigneCalcul b = new LigneCalcul(5, "*", 5);

        LigneCalculDAO lDao = this.db.getAppDatabase().ligneCalculDao();
        lDao.insert(a);
        lDao.insert(b);

        ArrayList<LigneCalcul> lignes = (ArrayList)lDao.getAll();
        LigneCalcul aDb = lignes.get(0);
        assert(aDb.equals(a));
        assert(aDb.getEnonce().equals("3 + 2 = "));
        assert(aDb.getSolution() == 5);
        LigneCalcul b2Db = lignes.get(1);
        assert(b2Db.equals(b));
        assert(aDb.getEnonce().equals("5 * 5 = "));
        assert(aDb.getSolution() == 25);
    }

    private final void testCalcul()
    {
        Calcul a = new Calcul("Maths", "");
        Calcul b = new Calcul("Français", "");

        CalculDAO mDao = this.db.getAppDatabase().calculDAO();
        mDao.insert(a);
        mDao.insert(b);

        Calcul aDb = mDao.getMatiereWithID("Maths");
        assert(aDb.equals(a));
        assert(aDb.getNom().equals("Maths"));
        assert(aDb.getExercices().size() == 0);
        assert(aDb.getImage().equals(""));

        Calcul bDb = mDao.getMatiereWithID("Français");
        assert(bDb.equals(b));
        assert(bDb.equals(a));
        assert(bDb.getNom().equals("Français"));
        assert(bDb.getExercices().size() == 0);
        assert(bDb.getImage().equals(""));

        try {
            mDao.insert(aDb);
        }catch(Exception e){}

        aDb = mDao.getMatiereWithID("Maths");
        assert(aDb.equals(a));
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////

    // Tests de relation double entre les classes



    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////

    // Tests complets du modèle avec la BDD
}
