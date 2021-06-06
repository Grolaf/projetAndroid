
package tests;

import android.content.Context;

import java.util.ArrayList;

import database.DAO.ExerciceDAO;
import database.DAO.LigneCalculDAO;
import database.DAO.MatiereDAO;
import database.DAO.UtilisateurDAO;
import database.DAO.UtilisateurExerciceCrossRefDAO;
import database.DatabaseClient;
import model.Calcul;
import model.Exercice;
import model.LigneCalcul;
import model.Matiere;
import model.Niveau;
import model.Utilisateur;
import model.referencesClass.CalculAndLigneCalcul;
import model.referencesClass.UtilisateurExerciceCrossReference;

public class DataBaseTests {

    private DatabaseClient db;
    private UtilisateurDAO utilisateurDAO;
    private MatiereDAO matiereDao;
    private ExerciceDAO exerciceDAO;
    private LigneCalculDAO ligneCalculDAO;
    private UtilisateurExerciceCrossRefDAO utilisateurExerciceCrossRefDAO;

    public DataBaseTests (Context context){

        db = DatabaseClient.getInstance(context.getApplicationContext());

        // ATTENTION : RESET DE TOUTES LES TABLES POUR LES TESTS
        try {
            db.getAppDatabase().clearAllTables();
        }catch(Exception e ){}

        utilisateurDAO = this.db.getAppDatabase().utilisateurDAO();
        matiereDao = this.db.getAppDatabase().matiereDAO();
        exerciceDAO = this.db.getAppDatabase().exerciceDAO();
        ligneCalculDAO = this.db.getAppDatabase().ligneCalculDao();
        utilisateurExerciceCrossRefDAO = this.db.getAppDatabase().utilisateurExerciceCrossRefDAO();

        testUtilisateur();
        testMatiere();
        testLigneCalcul();
        testCalcul();
        testCalculEtLigneCalcul();
        testMatiereEtCalcul();
        testCalculsEtUtilisateur();


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
        LigneCalcul bDb = lignes.get(1);
        assert(bDb.equals(b));
        assert(bDb.getEnonce().equals("5 * 5 = "));
        assert(bDb.getSolution() == 25);
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

        exerciceDAO.insert(a);
        exerciceDAO.insert(b);

        ArrayList<Exercice> calculs = (ArrayList)exerciceDAO.getAllCalculs();
        Calcul aDb = (Calcul)calculs.get(0);
        assert(aDb.equals(a));
        assert(aDb.getTitre().equals("Calcul1"));
        assert(aDb.getNomMatiere().equals(maths.getNom()));
        assert(aDb.getLignes().size() == 0);
        assert(aDb.getVainqueurs().size() == 0);

        Calcul bDb = (Calcul)calculs.get(1);
        assert(bDb.equals(b));
        assert(bDb.getTitre().equals("Calcul2"));
        assert(bDb.getNomMatiere().equals(maths.getNom()));
        assert(bDb.getLignes().size() == 0);
        assert(bDb.getVainqueurs().size() == 0);


        try {
            exerciceDAO.insert(aDb);
        }catch(Exception e){}

        ArrayList<Calcul> c = (ArrayList)exerciceDAO.getAllCalculs();
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
        exerciceDAO.insert(a);
        int idCalcul1 = exerciceDAO.maxId();
        la.setExerciceId(idCalcul1);
        lb.setExerciceId(idCalcul1 + 1);
        exerciceDAO.insert(b);
        ligneCalculDAO.insert(la);
        ligneCalculDAO.insert(lb);


        ///// Premier calcul //////////
        ArrayList<Calcul> calculs = (ArrayList)exerciceDAO.getAllCalculs();
        Calcul aDb = calculs.get(0);
        CalculAndLigneCalcul cAndL = exerciceDAO.getCalculAndLigneCalcul(aDb.exerciceId);

        ArrayList<LigneCalcul> lignes = new ArrayList<>();
        lignes.addAll(cAndL.lignes);

        assert(lignes.size() == 1);
        assert(lignes.get(0).equals(la));

        LigneCalcul laDb = ligneCalculDAO.getLigneCalculByID(lignes.get(0).getLigneId());

        assert(laDb.exerciceId == aDb.exerciceId);

        ///// Second calcul //////////
        Calcul bDb = calculs.get(1);
        cAndL = exerciceDAO.getCalculAndLigneCalcul(bDb.exerciceId);

        lignes = new ArrayList<>();
        lignes.addAll(cAndL.lignes);

        assert(lignes.size() == 1);
        assert(lignes.get(0).equals(lb));

        LigneCalcul lBDb = ligneCalculDAO.getLigneCalculByID(lignes.get(0).getLigneId());

        assert(lBDb.exerciceId == bDb.exerciceId);
    }

    public void testMatiereEtCalcul()
    {
        try {
            db.getAppDatabase().clearAllTables();

        }catch(Exception e ){}

        Matiere maths = new Matiere("Maths", "");
        Matiere francais = new Matiere("Français", "");

        Calcul a = new Calcul("Calcul1", Niveau.FACILE, maths);
        Calcul b = new Calcul("Calcul2", Niveau.DIFFICILE, francais);


        matiereDao.insert(maths);
        matiereDao.insert(francais);
        exerciceDAO.insert(a);
        exerciceDAO.insert(b);

        Matiere mathsDB = matiereDao.getMatiereWithID("Maths");
        Matiere francaisDB = matiereDao.getMatiereWithID("Français");

        mathsDB.getElementsFromDataBase(matiereDao, utilisateurExerciceCrossRefDAO, exerciceDAO);
        francaisDB.getElementsFromDataBase(matiereDao, utilisateurExerciceCrossRefDAO, exerciceDAO);

        assert(mathsDB.getExercices().size() == 1);
        assert(mathsDB.getExercices(Niveau.FACILE).size() == 1);
        assert(mathsDB.getExercices(Niveau.FACILE).get(0).equals(a));

        assert(francaisDB.getExercices().size() == 1);
        assert(francaisDB.getExercices(Niveau.DIFFICILE).size() == 1);
        assert(francaisDB.getExercices(Niveau.DIFFICILE).get(0).equals(b));

        mathsDB.getExercices(Niveau.FACILE).get(0).getElementsFromDatabase(utilisateurExerciceCrossRefDAO, exerciceDAO, matiereDao);
        francaisDB.getExercices(Niveau.DIFFICILE).get(0).getElementsFromDatabase(utilisateurExerciceCrossRefDAO, exerciceDAO, matiereDao);

        assert(mathsDB.getExercices(Niveau.FACILE).get(0).getNomMatiere().equals("Maths"));
        assert(mathsDB.getExercices(Niveau.FACILE).get(0).getTitre().equals("Calcul1"));
        assert(mathsDB.getExercices(Niveau.FACILE).get(0).getNiveau().equals(Niveau.FACILE));
        Calcul calcul = (Calcul)mathsDB.getExercices(Niveau.FACILE).get(0);
        assert(calcul.getLignes().size() == 0);

        assert(francaisDB.getExercices(Niveau.DIFFICILE).get(0).getNomMatiere().equals("Français"));
        assert(francaisDB.getExercices(Niveau.DIFFICILE).get(0).getTitre().equals("Calcul2"));
        assert(francaisDB.getExercices(Niveau.DIFFICILE).get(0).getNiveau().equals(Niveau.DIFFICILE));
        calcul = (Calcul)francaisDB.getExercices(Niveau.DIFFICILE).get(0);
        assert(calcul.getLignes().size() == 0);
    }

    public void testCalculsEtUtilisateur()
    {
        try {
            db.getAppDatabase().clearAllTables();

        }catch(Exception e ){}

        Matiere maths = new Matiere("Maths", "");

        Calcul a = new Calcul("Calcul1", Niveau.FACILE, maths);
        Calcul b = new Calcul("Calcul2", Niveau.DIFFICILE, maths);

        Utilisateur u1 = new Utilisateur("test1", "test1", "");
        Utilisateur u2 = new Utilisateur("test2", "test2", "");

        matiereDao.insert(maths);
        exerciceDAO.insert(a);
        exerciceDAO.insert(b);
        utilisateurDAO.insert(u1);
        utilisateurDAO.insert(u2);

        ArrayList<Calcul> calculs = (ArrayList)exerciceDAO.getAllCalculs();
        Calcul aDb = calculs.get(0);
        aDb.getElementsFromDatabase(utilisateurExerciceCrossRefDAO, exerciceDAO, matiereDao);
        aDb.addVainqueur(u1);
        UtilisateurExerciceCrossReference ref = new UtilisateurExerciceCrossReference(utilisateurDAO.getUtilisateurID(u1.getPrenom(), u1.getNom()), aDb.exerciceId);
        utilisateurExerciceCrossRefDAO.insert(ref);
        exerciceDAO.update(aDb);
        utilisateurDAO.update(u1);

        Calcul bDb = calculs.get(1);
        bDb.getElementsFromDatabase(utilisateurExerciceCrossRefDAO, exerciceDAO, matiereDao);
        bDb.addVainqueur(u2);
        ref = new UtilisateurExerciceCrossReference(utilisateurDAO.getUtilisateurID(u2.getPrenom(), u2.getNom()), bDb.exerciceId);
        utilisateurExerciceCrossRefDAO.insert(ref);
        exerciceDAO.update(bDb);
        utilisateurDAO.update(u2);

        /////  Partie Calcul -> Utilisateur /////

        calculs = (ArrayList)exerciceDAO.getAllCalculs();
        aDb = calculs.get(0);
        aDb.getElementsFromDatabase(utilisateurExerciceCrossRefDAO, exerciceDAO, matiereDao);

        assert(aDb.isWinner(u1));
        assert(!aDb.isWinner(u2));

        bDb = calculs.get(1);
        bDb.getElementsFromDatabase(utilisateurExerciceCrossRefDAO, exerciceDAO, matiereDao);

        assert(bDb.isWinner(u2));
        assert(!bDb.isWinner(u1));

        /////  Partie Utilisateur -> Calcul /////

        u1 = utilisateurDAO.getUtilisateur("test1", "test1");
        u2 = utilisateurDAO.getUtilisateur("test2", "test2");

        u1.getElementsFromDataBase(utilisateurExerciceCrossRefDAO, exerciceDAO, matiereDao);
        u2.getElementsFromDataBase(utilisateurExerciceCrossRefDAO, exerciceDAO, matiereDao);

        assert(u1.getExercicesResolus().size() == 1);
        assert(u1.getExercicesResolus(Niveau.FACILE, maths).get(0).equals(a));

        assert(u2.getExercicesResolus().size() == 1);
        assert(u2.getExercicesResolus(Niveau.DIFFICILE, maths).get(0).equals(b));
    }
}
