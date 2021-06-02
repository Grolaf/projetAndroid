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
            int idExercice = 1;

            // Niveau Facile
            for(int i = 1; i <= 10; i++)
            {
                db.execSQL("INSERT INTO calcul VALUES(" + i + ", \"Calcul\", " + nv.fromNiveauToJson(Niveau.FACILE) +", \"Maths\", \"\" );");
                for(int j = 1; j <= 3; j++)
                {
                    db.execSQL("INSERT INTO lignecalcul VALUES(" + idExercice++ + ", " +(rand.nextInt(10) + 1)  + ", \"+\", " + (rand.nextInt(10) + 1)  +", " + i + ");");
                }
            }

            // Niveau Moyen
            for(int i = 11; i <= 20; i++)
            {
                db.execSQL("INSERT INTO calcul VALUES(" + i + ", \"Calcul\", " + nv.fromNiveauToJson(Niveau.MOYEN) +", \"Maths\", \"\" );");
                for(int j = 1; j <= 3; j++)
                {
                    switch(rand.nextInt(3) + 1)
                    {
                        case 1:
                            db.execSQL("INSERT INTO lignecalcul VALUES(" + idExercice++ + ", " +(rand.nextInt(100) + 1)  + ", \"+\", " + (rand.nextInt(100) + 1)  +", " + i + ");");
                            break;
                        case 2:
                            db.execSQL("INSERT INTO lignecalcul VALUES(" + idExercice++ + ", " +(rand.nextInt(100) + 1)  + ", \"-\", " + (rand.nextInt(100) + 1)  +", " + i + ");");
                            break;
                        case 3 :
                            db.execSQL("INSERT INTO lignecalcul VALUES(" + idExercice++ + ", " +(rand.nextInt(10) + 1)  + ", \"*\", " + (rand.nextInt(10) + 1)  +", " + i + ");");
                            break;
                    }

                }
            }

            // Niveau Difficile
            for(int i = 21; i <= 30; i++)
            {
                db.execSQL("INSERT INTO calcul VALUES(" + i + ", \"Calcul\", " + nv.fromNiveauToJson(Niveau.DIFFICILE) +", \"Maths\", \"\" );");
                for(int j = 1; j <= 3; j++)
                {
                    switch(rand.nextInt(4) + 1)
                    {
                        case 1:
                            db.execSQL("INSERT INTO lignecalcul VALUES(" + idExercice++ + ", " +(rand.nextInt(1000) + 1)  + ", \"+\", " + (rand.nextInt(100) + 1)  +", " + i + ");");
                            break;
                        case 2:
                            db.execSQL("INSERT INTO lignecalcul VALUES(" + idExercice++ + ", " +(rand.nextInt(1000) + 1)  + ", \"-\", " + (rand.nextInt(100) + 1)  +", " + i + ");");
                            break;
                        case 3 :
                            db.execSQL("INSERT INTO lignecalcul VALUES(" + idExercice++ + ", " +(rand.nextInt(100) + 1)  + ", \"*\", " + (rand.nextInt(100) + 1)  +", " + i + ");");
                            break;
                        case 4 :
                            db.execSQL("INSERT INTO lignecalcul VALUES(" + idExercice++ + ", " +(rand.nextInt(100) + 1)  + ", \"/\", " + (rand.nextInt(10) + 1)  +", " + i + ");");
                            break;
                    }

                }
            }

            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        }
    };
}
