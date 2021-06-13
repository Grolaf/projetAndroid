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
            db.execSQL("INSERT INTO matiere VALUES(\"Français\", \"@drawable/francais\");");
            db.execSQL("INSERT INTO matiere VALUES(\"Histoire\", \"@drawable/history\");");
            db.execSQL("INSERT INTO matiere VALUES(\"Culture Générale\", \"@drawable/culture\");");
            db.execSQL("INSERT INTO matiere VALUES(\"Anglais\", \"@drawable/anglais\");");


            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            // Calculs + lignecalcul

            NiveauConverter nv = new NiveauConverter();
            Random rand = new Random();
            int idLigne = 1;

            // Niveau Facile
            for(int i = 1; i <= 5; i++)
            {
                db.execSQL("INSERT INTO calcul VALUES(" + i + ", \"Calcul\", " + nv.fromNiveauToJson(Niveau.FACILE) +", \"Maths\");");
                for(int j = 1; j <= 10; j++)
                {
                    db.execSQL("INSERT INTO lignecalcul VALUES(" + idLigne++ + ", " +(rand.nextInt(10) + 1)  + ", \"+\", " + (rand.nextInt(10) + 1)  +", " + i + ");");
                }
            }

            // Niveau Moyen
            for(int i = 11; i <= 20; i++)
            {
                db.execSQL("INSERT INTO calcul VALUES(" + i + ", \"Calcul\", " + nv.fromNiveauToJson(Niveau.MOYEN) +", \"Maths\");");
                for(int j = 1; j <= 5; j++)
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
                for(int j = 1; j <= 5; j++)
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

            // Maths
            idLigne = 0;


            db.execSQL("INSERT INTO qcm VALUES(" + 31 +", \"Vocabulaire\", " + nv.fromNiveauToJson(Niveau.FACILE) + ", \"Anglais\");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Un chat\", \"A dog\", \"A cat\", \"A cow\", null, \"B\", " + 31 + ");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Des pommes\", \"Pears\", \"Strawberries\", \"Apples\", null, \"C\", " + 31 + ");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"This is a girl\", \"C'est un garçon\", \"C'est une fille\", \"C'est une femme\", null, \"B\", " + 31 + ");");



            db.execSQL("INSERT INTO qcm VALUES(" + 32 +", \"Vocabulaire\", " + nv.fromNiveauToJson(Niveau.FACILE) + ", \"Anglais\");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"La maîtresse est heureuse\", \"The teacher is young\", \"The teacher is tall\", \"The teacher is happy\", null, \"C\", " + 32 + ");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"It is hot\", \"Il fait chaud\", \"Il fait froid\", \"Il y a du vent\", null, \"A\", " + 32 + ");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"La cuisine\", \"Bedroom\", \"Kitchen\", \"Garden\", null, \"B\", " + 32 + ");");



            db.execSQL("INSERT INTO qcm VALUES(" + 33 +", \"Vocabulaire\", " + nv.fromNiveauToJson(Niveau.MOYEN) + ", \"Anglais\");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"J'ai huit ans\", \"I'm eight years old\", \"I'm seven years old\", \"I'm nine years old\", null, \"A\", " + 33 + ");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"J'ai un livre vert\", \"I've got a yellow book\", \"I've got a red book\", \"I've got a green book\", null, \"C\", " + 33 + ");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"J'ai une chemise jaune\", \"I have a pink shirt\", \"I have a yellow shirt\", \"I have a blue shirt\", null, \"B\", " + 33 + ");");



            db.execSQL("INSERT INTO qcm VALUES(" + 34 +", \"Vocabulaire\", " + nv.fromNiveauToJson(Niveau.DIFFICILE) + ", \"Anglais\");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Si j'ai le bras long, qu'est-ce qui est long ?\", \"My arm\", \"My leg\", \"My neck\", null, \"A\", " + 34 + ");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Si on me dit de la fermer que dois-je fermer ?\", \"My ear\", \"My eyes\", \"My mouth\", null, \"C\", " + 34 + ");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Si j'ai les chevilles qui gonflent, qu'est-ce qui gonfle ?\", \"My ankles\", \"My breast\", \"My hips\", null, \"A\", " + 34 + ");");



            db.execSQL("INSERT INTO qcm VALUES(" + 35 +", \"Conjugaison\", " + nv.fromNiveauToJson(Niveau.DIFFICILE) + ", \"Anglais\");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Le présent simple sert à parler d'actions habituelles, qui se :\", \"Poursuivent en chantant\", \"Répètent\", \"Pourchassent\", null, \"B\", " + 35 + ");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Le présent be-ing sert à parler d'actions qui se déroulent sous nos yeux, au moment où l'on regarde. Elles sont en train de se produire et ne sont pas forcément :\", \"Terminées\", \"Ecoutées\", \"Habituelles\", null, \"C\", " + 35 + ");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Donnez le pluriel de 'one foot' en anglais :\", \"Two feets\", \"Two foots\", \"Two foot\", null, \"A\", " + 35 + ");");



            db.execSQL("INSERT INTO qcm VALUES(" + 36 +", \"Orthographe\", " + nv.fromNiveauToJson(Niveau.FACILE) + ", \"Français\");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Quel mot est mal écrit ?\", \"Chient\", \"Fenêtre\", \"Viande\", null, \"A\", " + 36 + ");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Comment s'appelle la capitale française ?\", \"Pari\", \"Parie\", \"Paris\", null, \"C\", " + 36 + ");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Quelle phrase est mal écrite ?\", \"Ce cheval\", \"Ces chevaux\", \"C'est chevaux\", null, \"C\", " + 36 + ");");



            db.execSQL("INSERT INTO qcm VALUES(" + 37 +", \"Divers\", " + nv.fromNiveauToJson(Niveau.FACILE) + ", \"Français\");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Donnez le verbe de la phrase 'le chat boit'.\", \"Le\", \"Chat\", \"Boit\", null, \"C\", " + 37 + ");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Donnez le déterminant de la phrase 'le chat boit'.\", \"Le\", \"Chat\", \"Boit\", null, \"A\", " + 37 + ");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Pétale est un mot de genre ---.\", \"Féminin\", \"Masculin\",  null, null, \"B\"," + 37 + ");");



            db.execSQL("INSERT INTO qcm VALUES(" + 38 +", \"Orthographe\", " + nv.fromNiveauToJson(Niveau.MOYEN) + ", \"Français\");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Omoplate est de genre ---.\", \"Féminin\", \"Masculin\",  null, null, \"A\"," + 38 + ");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Ail est de genre ---.\", \"Féminin\", \"Masculin\",  null, null, \"B\"," + 38 + ");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Comment s'écrit le synonyme du mot 'proposition' ?\", \"Réponce\", \"Raiponce\", \"Réponse\", null, \"C\", " + 38 + ");");



            db.execSQL("INSERT INTO qcm VALUES(" + 39 +", \"Divers\", " + nv.fromNiveauToJson(Niveau.MOYEN) + ", \"Français\");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Où ne doit-on PAS mettre un ''Ç'' à la place du ''C'' ?\", \"Nous lancons\", \"Nous avancions\", \"Il remplaca\", \"Nous acquiscâmes\", \"B\", " + 39 + ");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Où ne faut-il PAS ajouter un ''E'' après le ''G'' ?\", \"Nous négligons\", \"J'hébergai\", \"Vous vengâtes\", \"Vous changiez\", \"C\", " + 39 + ");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Verbes en ''YER'' : Quelle forme est correcte ?\", \"Ils balayent\", \"Ils nettoyent\", \"Ils essuyent\", null, \"A\", " + 39 + ");");



            db.execSQL("INSERT INTO qcm VALUES(" + 40 +", \"Divers\", " + nv.fromNiveauToJson(Niveau.DIFFICILE) + ", \"Français\");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Présent de l'indicatif : Quel verbe du 3e groupe conjugué avec JE et TU ne prend pas de ''X'' final mais un ''S'' ?\", \"Pouvoir\", \"Valoir\", \"Mouvoir\", \"Vouloir\", \"C\", " + 40 + ");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Verbes en ''GUER'' : Où faut-il mettre un ''U'' après le ''G'' ? Nous distinguions... Tu narguais... Je fatigue... (une seule solution).\", \"Le premier\", \"Le deuxième\", \"Le troisième\", \"Les trois évidemment !\", \"D\", " + 40 + ");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Futur de l'indicatif : Quel verbe prend un ''E'' muet après le ''I'' ?\", \"Il remplira\", \"Il accomplira\", \"Il déplira\", \"Il assouplira\", \"C\", " + 40 + ");");



            db.execSQL("INSERT INTO qcm VALUES(" + 41 +", \"Divers\", " + nv.fromNiveauToJson(Niveau.DIFFICILE) + ", \"Français\");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Quel verbe n'est pas au futur ?\", \"Il mourra\", \"Il démarra\", \"Il courra\", \"Il conquerra\", \"B\", " + 41 + ");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Quel verbe en ''ELER'' peut doubler le ''L'' ?\", \"Il épèle\", \"Il dégèle\", \"Il martèle\", \"Il pèle\", \"A\", " + 41 + ");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Forme passive (accord du participe passé) : Dans quelle phrase s'est glissée une erreur d'accord ?\", \"Tu as été mordue.\", \"Vous avez été puni.\", \"Nous avons été sondé.\", \"Elles ont été sauvées.\", \"C\", " + 41 + ");");



            db.execSQL("INSERT INTO qcm VALUES(" + 42 +", \"Divers\", " + nv.fromNiveauToJson(Niveau.DIFFICILE) + ", \"Culture Générale\");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Dans cette célèbre comptine, c'est dans la forêt lointaine qu'on entend ces oiseaux :\", \"Caille, tourterelle\", \"Coucou, hibou\", \"Alouette et loriot\", null, \"B\", " + 42 + ");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Quel oiseau voisine avec le renard dans une fable de La Fontaine ?\", \"Le pigeon\", \"Le héron\", \"Le corbeau\", null, \"C\", " + 42 + ");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Dans quelle chanson de Cabrel entend-on 'ça fait marrer les oiseaux qui s'envolent' ?\", \"L'autre côté de toi\", \"Répondez-moi\", \"Encore et encore\", null, \"C\", " + 42 + ");");



            db.execSQL("INSERT INTO qcm VALUES(" + 43 +", \"Divers\", " + nv.fromNiveauToJson(Niveau.DIFFICILE) + ", \"Culture Générale\");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Où rencontre-t-on le hibou Archimède ?\", \"Winnie l'Ourson\", \"Rox et Rouky\", \"Merlin l'enchanteur\", null, \"C\", " + 43 + ");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"À quel oiseau associez-vous Simon and Garfunkel ?\", \"Vautour\", \"Condor\", \"Aigle noir\", null, \"B\", " + 43 + ");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Dans le film 'La Petite Voleuse', que chante Charlotte Gainsbourg ?\", \"À la volette\", \"Alouette, gentille alouette\", \"La P'tite hirondelle\", null, \"C\", " + 43 + ");");



            db.execSQL("INSERT INTO qcm VALUES(" + 44 +", \"Dates\", " + nv.fromNiveauToJson(Niveau.FACILE) + ", \"Histoire\");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"En quelle année Charlemagne fut-il couronné empereur ?\", \"En l'an 600\", \"En l'an 800\", \"En l'an 700\", null, \"B\", " + 44 + ");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Quand eut lieu la prise de la Bastille ?\", \"Le 25 décembre 1789\", \"Le 14 juillet 1789\", \"Le 1er janvier 1789\", null, \"B\", " + 44 + ");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"La guerre de 1870 a opposé les Français aux :\", \"Anglais\", \"Allemands\", \"Italiens\", null, \"B\", " + 44 + ");");



            db.execSQL("INSERT INTO qcm VALUES(" + 45 +", \"Rois de France\", " + nv.fromNiveauToJson(Niveau.FACILE) + ", \"Histoire\");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Quel roi se couvrit de gloire à la bataille de Marignan ?\", \"François Ier\", \"Clovis\", \"Louis XVI\", null, \"A\", " + 45 + ");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Quel roi était surnommé le Roi-Soleil ?\", \"Louis XIII\", \"Louis XIV\", \"Louis XVI\", null, \"B\", " + 45 + ");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Qui a remporté la bataille d'Austerlitz ?\", \"François Ier\", \"Henri IV\", \"Napoléon Ier\", null, \"C\", " + 45 + ");");



            db.execSQL("INSERT INTO qcm VALUES(" + 46 +", \"Guerres\", " + nv.fromNiveauToJson(Niveau.MOYEN) + ", \"Histoire\");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Que célèbre-t-on le 8 mai ?\", \"La capitulation de l'Allemagne\", \"L'armistice de 1918\", \"La fin de la guerre d'Algérie\", null, \"A\", " + 46 + ");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"La guerre de 1870 a opposé les Français aux :\", \"Anglais\", \"Allemands\", \"Italiens\", null, \"B\", " + 46 + ");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Que célèbre-t-on le 11 novembre ?\", \"La fin de la Première Guerre mondiale\", \"La Prise de la Bastille\", \"La naissance de Jésus\", null, \"A\", " + 46 + ");");



            db.execSQL("INSERT INTO qcm VALUES(" + 47 +", \"Exploration\", " + nv.fromNiveauToJson(Niveau.MOYEN) + ", \"Histoire\");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Quel est le nom du pays qui a colonisé le Québec d'aujourd'hui ?\", \"La France\", \"L'Angleterre\", \"L'Europe\", null, \"A\", " + 47 + ");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Quel est le nom du premier explorateur de l'Amérique ?\", \"Jaques Cartier\", \"Christophe Colomb\", \"Samuel de Champlain\", null, \"B\", " + 47 + ");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Quelles étaient les conditions de voyage à bord du navire ?\", \"Il faillit avoir un manque de nourriture\", \"Il faillit avoir une mutinerie\", \"Il faillit y avoir un naufrage\", null, \"B\", " + 47 + ");");



            db.execSQL("INSERT INTO qcm VALUES(" + 48 +", \"Amériques\", " + nv.fromNiveauToJson(Niveau.MOYEN) + ", \"Histoire\");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Qu'est-ce que les blancs et les amérindiens échangeaient ?\", \"Des fourrures\", \"Diverses Babioles\", \"De la viande\", null, \"B\", " + 48 + ");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Quelle coutume amérindienne les blancs ont adoptée ?\", \"Le tabac\", \"La chasse\", \"La pêche\", null, \"A\", " + 48 + ");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Quels sont les buts de l'église catholique en venant en Nouvelle-France ?\", \"Leur apporter un mode de vie sain\", \"Leur enseigner la foi en dieu\", \"Les rapporter comme esclaves\", null, \"B\", " + 48 + ");");



            db.execSQL("INSERT INTO qcm VALUES(" + 49 +", \"Divers\", " + nv.fromNiveauToJson(Niveau.DIFFICILE) + ", \"Histoire\");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"En quoi consiste la traite ?\", \"L'échange de fourrure avec les autochtones\", \"La fabrication de scalpe\",  null, null, \"A\"," + 49 + ");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"Quel est le nom du gouvernement de la Nouvelle-France?\", \"La grc\", \"Le gouvernement du Canada\", \"Le gouvernement royal\", null, \"C\", " + 49 + ");");
            db.execSQL("INSERT INTO ligneqcm VALUES(" + idLigne++ + ", \"À leurs arrivées en Amérique les blancs étaient considérées comme… par les autochtones :\", \"… une menace\", \"… les sauveurs\", \"… des dieux\", \"=\", \"C\", " + 49 + ");");








            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



        }
    };
}

