
package tests;

import android.content.Context;

import androidx.annotation.ArrayRes;

import java.util.ArrayList;
import database.DAO.CalculDAO;
import database.DAO.LigneCalculDAO;
import database.DAO.MatiereDAO;
import database.DAO.UtilisateurDAO;
import database.DatabaseClient;
import model.Calcul;
import model.LigneCalcul;
import model.Matiere;
import model.Niveau;
import model.Utilisateur;
import model.referencesClass.CalculAndLigneCalcul;

public class DataBaseTests {

    private DatabaseClient db;
    private UtilisateurDAO utilisateurDAO;
    private MatiereDAO matiereDao;
    private CalculDAO calculDAO;
    private LigneCalculDAO ligneCalculDAO;

    public DataBaseTests (Context context){

        db = DatabaseClient.getInstance(context.getApplicationContext());

        // ATTENTION : RESET DE TOUTES LES TABLES POUR LES TESTS
        try {
            db.getAppDatabase().clearAllTables();
        }catch(Exception e ){}

        utilisateurDAO = this.db.getAppDatabase().utilisateurDAO();
        matiereDao = this.db.getAppDatabase().matiereDAO();
        calculDAO = this.db.getAppDatabase().calculDAO();
        ligneCalculDAO = this.db.getAppDatabase().ligneCalculDao();

        testUtilisateur();
        testMatiere();
        testLigneCalcul();
        testCalcul();
        testCalculEtLigneCalcul();

        try {
            db.getAppDatabase().clearAllTables();

        }catch(Exception e ){}

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////

    // Tests basiques des classes avec la BDD

    private final void testUtilisateur()
    {
        try {
            db.getAppDatabase().clearAllTables();

        }catch(Exception e ){}

        Utilisateur a = new Utilisateur("testprenom", "testnom", "");
        Utilisateur b = new Utilisateur("testprenom2", "testnom2", "");

        utilisateurDAO = this.db.getAppDatabase().utilisateurDAO();
        utilisateurDAO.insert(a);
        utilisateurDAO.insert(b);

        Utilisateur aDB = utilisateurDAO.getUtilisateur("testprenom", "testnom");
        assert (aDB.equals(a));
        assert(aDB.getAvatar().equals(""));
        assert(aDB.getNom().equals("testnom"));
        assert(aDB.getPrenom().equals("testprenom"));
        Utilisateur bDB = utilisateurDAO.getUtilisateur("testprenom2", "testnom2");
        assert (bDB.equals(b));
        assert(bDB.getAvatar().equals(""));
        assert(bDB.getNom().equals("testnom2"));
        assert(bDB.getPrenom().equals("testprenom2"));

        assert(aDB.getExercicesResolus().size() == 0);
        assert(bDB.getExercicesResolus().size() == 0);

        try {
            utilisateurDAO.insert(a);
        }catch(Exception e){}

        aDB = utilisateurDAO.getUtilisateur("testprenom", "testnom");
        assert (aDB.equals(a));
    }

    private final void testMatiere()
    {
        try {
            db.getAppDatabase().clearAllTables();

        }catch(Exception e ){}

        Matiere a = new Matiere("Maths", "");
        Matiere b = new Matiere("Français", "");

        matiereDao.insert(a);
        matiereDao.insert(b);

        Matiere aDb = matiereDao.getMatiereWithID("Maths");
        assert(aDb.equals(a));
        assert(aDb.getNom().equals("Maths"));
        assert(aDb.getExercices().size() == 0);
        assert(aDb.getImage().equals(""));

        Matiere bDb = matiereDao.getMatiereWithID("Français");
        assert(bDb.equals(b));
        assert(bDb.equals(a));
        assert(bDb.getNom().equals("Français"));
        assert(bDb.getExercices().size() == 0);
        assert(bDb.getImage().equals(""));

        try {
            matiereDao.insert(aDb);
        }catch(Exception e){}

        aDb = matiereDao.getMatiereWithID("Maths");
        assert(aDb.equals(a));
    }

    private final void testLigneCalcul()
    {
        try {
            db.getAppDatabase().clearAllTables();

        }catch(Exception e ){}

        LigneCalcul a = new LigneCalcul(3, "+", 2);
        LigneCalcul b = new LigneCalcul(5, "*", 5);

        ligneCalculDAO.insert(a);
        ligneCalculDAO.insert(b);

        ArrayList<LigneCalcul> lignes = (ArrayList)ligneCalculDAO.getAll();
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
        try {
            db.getAppDatabase().clearAllTables();

        }catch(Exception e ){}

        Matiere maths = new Matiere("Maths", "");

        matiereDao = this.db.getAppDatabase().matiereDAO();
        matiereDao.insert(maths);

        Calcul a = new Calcul("Calcul1", Niveau.FACILE, maths);
        Calcul b = new Calcul("Calcul2", Niveau.DIFFICILE, maths);

        calculDAO = this.db.getAppDatabase().calculDAO();
        calculDAO.insert(a);
        calculDAO.insert(b);

        ArrayList<Calcul> calculs = (ArrayList)calculDAO.getAll();
        Calcul aDb = calculs.get(0);
        assert(aDb.equals(a));
        assert(aDb.getTitre().equals("Calcul1"));
        assert(aDb.getMatiere().equals(maths));
        assert(aDb.getLignes().size() == 0);
        assert(aDb.getVainqueurs().size() == 0);

        Calcul bDb = calculs.get(1);
        assert(bDb.equals(b));
        assert(bDb.getTitre().equals("Calcul2"));
        assert(bDb.getMatiere().equals(maths));
        assert(bDb.getLignes().size() == 0);
        assert(bDb.getVainqueurs().size() == 0);


        try {
            calculDAO.insert(aDb);
        }catch(Exception e){}

        ArrayList<Calcul> c = (ArrayList)calculDAO.getAll();
        assert(c.size() == 2);
        assert(aDb.equals(a));
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////

    // Tests de relation double entre les classes

    public final void testCalculEtLigneCalcul()
    {
        try {
            db.getAppDatabase().clearAllTables();

        }catch(Exception e ){}

        Matiere maths = new Matiere("Maths", "");

        matiereDao.insert(maths);

        Calcul a = new Calcul("Calcul1", Niveau.FACILE, maths);
        Calcul b = new Calcul("Calcul2", Niveau.DIFFICILE, maths);

        LigneCalcul la = new LigneCalcul(3, "+", 2);
        LigneCalcul lb = new LigneCalcul(5, "*", 5);

        ArrayList<LigneCalcul> lignesA  = new ArrayList<>();
        lignesA.add(la);

        ArrayList<LigneCalcul> lignesB  = new ArrayList<>();
        lignesB.add(lb);

        a.setLignes(lignesA);
        b.setLignes(lignesB);

        //////
        ligneCalculDAO.insert(la);
        ligneCalculDAO.insert(lb);
        calculDAO.insert(a);
        calculDAO.insert(b);


        ///// Premier calcul //////////
        ArrayList<Calcul> calculs = (ArrayList)calculDAO.getAll();
        Calcul aDb = calculs.get(0);
        CalculAndLigneCalcul cAndL = calculDAO.getCalculAndLigneCalcul(aDb.exerciceId);

        ArrayList<LigneCalcul> lignes = new ArrayList<>();
        lignes.addAll(cAndL.lignes);

        assert(lignes.size() == 1);
        assert(lignes.get(0).equals(la));

        LigneCalcul laDb = ligneCalculDAO.getLigneCalculByID(lignes.get(0).getLigneId());

        assert(laDb.calculID == aDb.exerciceId);

        ///// Second calcul //////////
        Calcul bDb = calculs.get(0);
        cAndL = calculDAO.getCalculAndLigneCalcul(bDb.exerciceId);

        lignes = new ArrayList<>();
        lignes.addAll(cAndL.lignes);

        assert(lignes.size() == 1);
        assert(lignes.get(0).equals(lb));

        LigneCalcul lBDb = ligneCalculDAO.getLigneCalculByID(lignes.get(0).getLigneId());

        assert(lBDb.calculID == bDb.exerciceId);
    }



    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////

    // Tests complets du modèle avec la BDD
}
