package database;

import java.util.Random;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.scolastic.MainActivity;

import java.util.concurrent.Executors;

import model.Converters.NiveauConverter;
import model.Matiere;
import model.Niveau;
import model.Utilisateur;

public class DatabaseClient {

    // Instance unique permettant de faire le lien avec la base de données
    private static DatabaseClient instance;

    // Objet représentant la base de données de l'application
    private AppDatabase appDatabase;

    // Constructeur
    private DatabaseClient(final Context context) {

        // Créer l'objet représentant la base de données de l'application
        // à l'aide du "Room database builder"
        //appDatabase = Room.databaseBuilder(context, AppDatabase.class, "appDatabase").build();

        ////////// REMPLIR LA BD à la première création à l'aide de l'objet roomDatabaseCallback
        // Ajout de la méthode addCallback permettant de populate (remplir) la base de données à sa création
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "appDatabase").addCallback(roomDatabaseCallback).build();
    }

    // Méthode statique

    // Retourne l'instance de l'objet DatabaseClient
    public static synchronized DatabaseClient getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseClient(context);
        }
        return instance;
    }

    // Retourne l'objet représentant la base de données de votre application
    public AppDatabase getAppDatabase() {
        return appDatabase;
    }

    // Objet permettant de populate (remplir) la base de données à sa création
    RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {

        // Called when the database is created for the first time.
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            //Matieres
            db.execSQL("INSERT INTO matiere VALUES(\"Maths\", \"@drawable/math\");");
            db.execSQL("INSERT INTO matiere VALUES(\"Français\", \"@drawable/math\");");
            db.execSQL("INSERT INTO matiere VALUES(\"Histoire\", \"@drawable/math\");");
            db.execSQL("INSERT INTO matiere VALUES(\"Culture G\", \"@drawable/math\");");
            db.execSQL("INSERT INTO matiere VALUES(\"Anglais\", \"@drawable/math\");");


            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            // Calculs + lignecalcul

            NiveauConverter nv = new NiveauConverter();
            Random rand = new Random();
            int idLigne = 1;

            // Niveau Facile
            for(int i = 1; i <= 10; i++)
            {
                db.execSQL("INSERT INTO calcul VALUES(" + i + ", \"Calcul\", " + nv.fromNiveauToJson(Niveau.FACILE) +", \"Maths\");");
                for(int j = 1; j <= 3; j++)
                {
                    db.execSQL("INSERT INTO lignecalcul VALUES(" + idLigne++ + ", " +(rand.nextInt(10) + 1)  + ", \"+\", " + (rand.nextInt(10) + 1)  +", " + i + ");");
                }
            }

            // Niveau Moyen
            for(int i = 11; i <= 20; i++)
            {
                db.execSQL("INSERT INTO calcul VALUES(" + i + ", \"Calcul\", " + nv.fromNiveauToJson(Niveau.MOYEN) +", \"Maths\");");
                for(int j = 1; j <= 3; j++)
                {
                    switch(rand.nextInt(3) + 1)
                    {
                        case 1:
                            db.execSQL("INSERT INTO lignecalcul VALUES(" + idLigne++ + ", " +(rand.nextInt(100) + 1)  + ", \"+\", " + (rand.nextInt(100) + 1)  +", " + i + ");");
                            break;
                        case 2:
                            db.execSQL("INSERT INTO lignecalcul VALUES(" + idLigne++ + ", " +(rand.nextInt(100) + 1)  + ", \"-\", " + (rand.nextInt(100) + 1)  +", " + i + ");");
                            break;
                        case 3 :
                            db.execSQL("INSERT INTO lignecalcul VALUES(" + idLigne++ + ", " +(rand.nextInt(10) + 1)  + ", \"*\", " + (rand.nextInt(10) + 1)  +", " + i + ");");
                            break;
                    }

                }
            }

            // Niveau Difficile
            for(int i = 21; i <= 30; i++)
            {
                db.execSQL("INSERT INTO calcul VALUES(" + i + ", \"Calcul\", " + nv.fromNiveauToJson(Niveau.DIFFICILE) +", \"Maths\");");
                for(int j = 1; j <= 3; j++)
                {
                    switch(rand.nextInt(4) + 1)
                    {
                        case 1:
                            db.execSQL("INSERT INTO lignecalcul VALUES(" + idLigne++ + ", " +(rand.nextInt(1000) + 1)  + ", \"+\", " + (rand.nextInt(100) + 1)  +", " + i + ");");
                            break;
                        case 2:
                            db.execSQL("INSERT INTO lignecalcul VALUES(" + idLigne++ + ", " +(rand.nextInt(1000) + 1)  + ", \"-\", " + (rand.nextInt(100) + 1)  +", " + i + ");");
                            break;
                        case 3 :
                            db.execSQL("INSERT INTO lignecalcul VALUES(" + idLigne++ + ", " +(rand.nextInt(100) + 1)  + ", \"*\", " + (rand.nextInt(100) + 1)  +", " + i + ");");
                            break;
                        case 4 :
                            db.execSQL("INSERT INTO lignecalcul VALUES(" + idLigne++ + ", " +(rand.nextInt(100) + 1)  + ", \"/\", " + (rand.nextInt(10) + 1)  +", " + i + ");");
                            break;
                    }

                }
            }

            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            // QCM + LigneQCM
            idLigne = 0;

            db.execSQL("INSERT INTO qcm VALUES(" + 1 + ", \"Questions de calculs\", " + nv.fromNiveauToJson(Niveau.MOYEN) +", \"Maths\");");
                db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"100 : 10 = ?\", \" 1000\", \" 100\", \" 10\", null,\"B\", " + 1 + ");");
                db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Je pars de chez moi à 9 h 00 je mets 15 mn pour aller à l'école, à quelle heure je serais arrivé ?\", \" 8 h 45\", \" 9 h 00\", \" 9 h 15\", null, \"C\", " + 1 + ");");
                db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Wilfried avait 100 bonbons . Il en donne 70. Combien a-t-il de bonbons maintenant ?\", \" 170\", \" 30\", \" 17\",null, \"B\", " + 1 + ");");

            db.execSQL("INSERT INTO qcm VALUES(" + 2 + ", \"Questions de calculs\", " + nv.fromNiveauToJson(Niveau.MOYEN) +", \"Maths\");");
                db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Eric avait 99 crayons . Il en gagne 49. Combien a-t-il de crayons maintenant ?\", \" 148\", \" 138\", \" 128\",null, \"A\", " + 2 + ");");
                db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Arthur avait 50 crayons . Il en perd 5. Combien a-t-il de crayons maintenant ?\", \" 55\", \" 50\", \" 45\", null,\"B\", " + 2 + ");");
                db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Sébastien avait 85 petites voitures . On lui en donne 45. Combien a-t-il de petites voitures maintenant ?\", \" 45\", \" 85\", \" 130\",null, \"C\", " + 2+ ");");


            db.execSQL("INSERT INTO qcm VALUES(" + 3 + ", \"Questions de cours\", " + nv.fromNiveauToJson(Niveau.DIFFICILE) +", \"Maths\");");
                db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Lorsqu'il est écrit, lequel de ces nombres est mal orthographié ?\", \" Soixante-dix\", \" Quatre-vingt\", \" Deux cents\",\"Cinquante et un\", \"C\", " + 3 + ");");
                db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Laquelle de ces propositions est juste ?\", \" 23 > 45\", \" 777 < 385\", \" 879 > 999\", \"576 > 394\", \"D\", " + 3 + ");");
                db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Chaque nombre que l'on additionne s'appelle...\", \" Un produit\", \"  Un article\", \"  Un terme\",\"Une somme\", \"C\", " + 3 + ");");


            db.execSQL("INSERT INTO qcm VALUES(" + 4 + ", \"Questions de cours\", " + nv.fromNiveauToJson(Niveau.DIFFICILE) +", \"Maths\");");
                db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"En posant la soustraction '376 - 48', tu auras besoin d'ajouter une dizaine au chiffre 6 et au chiffre...\", \" 4\", \" 8\", \" 3\", \"7\",\"A\", " + 4 + ");");
                db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Comment s'appelle le résultat d'une multiplication ?\", \"La somme\", \"  Le produit\", \" L'article\",\"Le multiple\", \"B\", " + 4 + ");");
                db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Quel est l'autre nom de la règle qui te sert à tracer des lignes ?\", \" Le centimètrier\", \" Le double-mètre\", \" Le double-décimètre\", \"La lignette\",\"C\", " + 4 + ");");

            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        }
    };
}

