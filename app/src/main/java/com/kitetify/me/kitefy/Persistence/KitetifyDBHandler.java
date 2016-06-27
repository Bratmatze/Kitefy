package com.kitetify.me.kitefy.Persistence;


import android.content.ClipData;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.util.Log;

import com.kitetify.me.kitefy.DatabaseModels.User;

import java.util.ArrayList;
import java.util.List;


public class KitetifyDBHandler extends SQLiteOpenHelper {

    private static final String LOG = "DATABASE-HELPER";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "kitetify.db";

    // table names
    private static final String TABLE_BARTOKITE = "barToKite";
    private static final String TABLE_MATERIAL = "material";
    private static final String TABLE_MATERIAL_PLUS = "materialPlus";
    private static final String TABLE_MATERIAL_TO_SURFSESSION = "materialToSurfSession";
    private static final String TABLE_MATERIAL_TYPE = "materialType";
    private static final String TABLE_MBAR = "mBar";
    private static final String TABLE_MKITE = "mKite";
    private static final String TABLE_FORECAST = "forecast";
    private static final String TABLE_SURFSESSION = "surfSession";
    private static final String TABLE_SURFSESSION_CHARACTER = "surfSessionCharacter";
    private static final String TABLE_USER = "user";
    private static final String TABLE_WINDCHARACTER = "windCharacter";
    private static final String TABLE_WINDDIRECTION = "windDirection";


    // Table bar2kite -column names
    private static final String BARTOKITE_BARID = "mBarID";
    private static final String BARTOKITE_KITEID = "mKiteID";

    // Table material - column names
    private static final String MATERIAL_ID = "mID";
    private static final String MATERIAL_NAME = "name";
    private static final String MATERIAL_TYPE = "type";
    private static final String MATERIAL_YEAR = "year";
    private static final String MATERIAL_PRICE = "price";
    private static final String MATERIAL_CONDITION = "condition";
    private static final String MATERIAL_BUYDATE = "buyDate";
    private static final String MATERIAL_USER_ID = "uID";
    private static final String MATERIAL_SELLPRICE = "sellPrice";
    private static final String MATERIAL_SELLDATE = "sellDate";
    private static final String MATERIAL_SELLFLAG = "sellFlag";

    // Table materialPlus - column names
    private static final String MATERIAL_PLUS_MTYPEID = "mTypeID";
    private static final String MATERIAL_PLUS_MID = "mID";
    private static final String MATERIAL_PLUS_MBAR_ID = "mBarID";
    private static final String MATERIAL_PLUS_MKITE_ID = "mKiteID";

    // Table materialToSurfSession - column names
    private static final String MATERIAL_TO_SURFSESSION_SID = "sID";
    private static final String MATERIAL_TO_SURFSESSION_MID = "mID";

    // Table materialType - column names
    private static final String MATERIAL_TYPE_ID = "mTypeID";
    private static final String MATERIAL_TYPE_NAME = "name";

    // Table mBar - column names
    private static final String MBAR_ID = "bID";
    private static final String MBAR_LINES = "lines";
    private static final String MBAR_LENGHT = "length";
    private static final String MBAR_WIDTH = "width";
    private static final String MBAR_TEXT = "text";

    // Table mKite - column names
    private static final String MKITE_ID = "kID";
    private static final String MKITE_LINES = "lines";
    private static final String MKITE_SIZE = "size";
    private static final String MKITE_WINDFROM = "windFrom";
    private static final String MKITE_WINDTILL = "windTill";
    private static final String MKITE_SHAPE = "shape";
    private static final String MKITE_TEXT = "text";

    // Table materialType - column names
    private static final String FORECAST_ID = "fID";
    private static final String FORECAST_NAME = "name";

    // Table surfSession - column names
    private static final String SURFSESSION_ID = "sID";
    private static final String SURFSESSION_WID = "wID";
    private static final String SURFSESSION_FUNID = "funID";
    private static final String SURFSESSION_UID = "uID";
    private static final String SURFSESSION_WCID = "wcID";
    private static final String SURFSESSION_WINDMIN = "windMin";
    private static final String SURFSESSION_WINDMAX = "windMax";
    private static final String SURFSESSION_WINDAVG = "windAvg";
    private static final String SURFSESSION_FORECAST = "forecast";
    private static final String SURFSESSION_DATE = "date";
    private static final String SURFSESSION_DURATION = "duration";
    private static final String SURFSESSION_SPOT = "spot";

    // Table surfSessionCharacter - column name
    private static final String SURFSESSION_CHARACTER_FUNID = "funID";
    private static final String SURFSESSION_CHARACTER_NAME = "name";

    // Table user - column name
    private static final String USER_ID = "uID";
    private static final String USER_NAME = "name";

    // Table windCharacter - column name
    private static final String WIND_CHARACTER_ID = "wcID";
    private static final String WIND_CHARACTER_NAME = "name";

    // Table windDirection
    private static final String WIND_DIRECTION_ID = "windID";
    private static final String WIND_DIRECTION_DIRECTION = "derection";
    private static final String WIND_DIRECTION_DEGREE = "degree";
    private static final String WIND_DIRECTION_NAME = "name";

    // Table create statement
    // barToKite create statement
    private static final String CREATE_TABLE_BARTOKITE = "CREATE TABLE " + TABLE_BARTOKITE + " " +
            "(" + BARTOKITE_BARID + " INTEGER NOT NULL ," +
            " " + BARTOKITE_KITEID + " INTEGER NOT NULL ," +
            " FOREIGN KEY ("+BARTOKITE_BARID+") REFERENCES "+TABLE_MBAR+"("+ MBAR_ID +"),"  +
            " FOREIGN KEY ("+BARTOKITE_KITEID+") REFERENCES "+TABLE_MKITE+"("+ MKITE_ID +"));";


    // material create statement
    private static final String CREATE_TABLE_MATERIAL = "CREATE TABLE "+ TABLE_MATERIAL +" " +
            "(" + MATERIAL_ID + " INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , " +
            " " + MATERIAL_NAME + " VARCHAR," +
            " " + MATERIAL_TYPE + " VARCHAR," +
            " " + MATERIAL_YEAR + " INTEGER," +
            " " + MATERIAL_PRICE + " FLOAT," +
            " " + MATERIAL_CONDITION + " VARCHAR," +
            " " + MATERIAL_BUYDATE + " DATETIME," +
            " " + MATERIAL_USER_ID + " INTEGER, " +
            " " + MATERIAL_SELLPRICE + " FLOAT," +
            " " + MATERIAL_SELLDATE + " DATETIME," +
            " " + MATERIAL_SELLFLAG + " INTEGER DEFAULT 0, " +
            "FOREIGN KEY ("+MATERIAL_USER_ID+") REFERENCES "+TABLE_USER+"("+ USER_ID +") );";

    // materialPlus create statement
    public static final String CREATE_TABLE_MATERIAL_PLUS = "CREATE TABLE " + TABLE_MATERIAL_PLUS + " " +
            "(" + MATERIAL_PLUS_MTYPEID + " INTEGER NOT NULL ," +
            " " + MATERIAL_PLUS_MID + " INTEGER NOT NULL ," +
            " " + MATERIAL_PLUS_MKITE_ID +" INTEGER NOT NULL ," +
            " " + MATERIAL_PLUS_MBAR_ID +" INTEGER NOT NULL ," +
            " FOREIGN KEY ("+MATERIAL_PLUS_MTYPEID+")  REFERENCES "+TABLE_MATERIAL_TYPE+"("+ MATERIAL_TYPE_ID +")," +
            " FOREIGN KEY ("+MATERIAL_PLUS_MKITE_ID+") REFERENCES "+TABLE_MKITE   +"("+ MKITE_ID +"),"  +
            " FOREIGN KEY ("+MATERIAL_PLUS_MID+")      REFERENCES "+TABLE_MATERIAL+"("+ MATERIAL_ID +"),"  +
            " FOREIGN KEY ("+MATERIAL_PLUS_MBAR_ID+")  REFERENCES "+TABLE_MBAR    +"("+ MBAR_ID +"));" ;

    // materialToSurfSession create statement
    private static final String CREATE_TABLE_MATERIAL_TO_SURFSESSION = "CREATE TABLE " + TABLE_MATERIAL_TO_SURFSESSION + " " +
            "(" + MATERIAL_TO_SURFSESSION_SID + " INTEGER NOT NULL ," +
            " " + MATERIAL_TO_SURFSESSION_MID + " INTEGER NOT NULL ," +
            " FOREIGN KEY ("+MATERIAL_TO_SURFSESSION_MID+") REFERENCES "+TABLE_MATERIAL+"("+ MATERIAL_ID +"),"  +
            " FOREIGN KEY ("+MATERIAL_TO_SURFSESSION_SID+") REFERENCES "+TABLE_SURFSESSION+"("+ SURFSESSION_ID +"));";

    // materialType create
    private static final String CREATE_TABLE_MATERIAL_TYPE = "CREATE TABLE " + TABLE_MATERIAL_TYPE + " " +
            "(" + MATERIAL_TYPE_ID + " INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL ," +
            " " + MATERIAL_TYPE_NAME + " VARCHAR);";

    // mBar create statement
    private static final String CREATE_TABLE_MBAR = "CREATE TABLE " + TABLE_MBAR + " " +
            "(" + MBAR_ID + " INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL ," +
            " " + MBAR_LINES + " INTEGER," +
            " " + MBAR_LENGHT + " FLOAT," +
            " " + MBAR_WIDTH + " FLOAT," +
            " " + MBAR_TEXT + " TEXT);";

    // mKite create statement
    private static final String CREATE_TABLE_MKITE = "CREATE TABLE " + TABLE_MKITE + " " +
            "(" + MKITE_ID + " INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL ," +
            " " + MKITE_LINES + " INTEGER," +
            " " + MKITE_SIZE + " FLOAT," +
            " " + MKITE_WINDFROM + " FLOAT," +
            " " + MKITE_WINDTILL + " FLOAT," +
            " " + MKITE_SHAPE + " VARCHAR," +
            " " + MKITE_TEXT +" TEXT);";

    // forecast create
    private static final String CREATE_TABLE_FORECAST= "CREATE TABLE " + TABLE_FORECAST + " " +
            "(" + FORECAST_ID + " INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL ," +
            " " + FORECAST_NAME + " VARCHAR);";

    // SurfSession create statement
    private static final String CREATE_TABLE_SURFSESSION = "CREATE TABLE "+ TABLE_SURFSESSION +" " +
            "("+ SURFSESSION_ID +" INTEGER PRIMARY KEY AUTOINCREMENT  NOT NULL ," +
            " "+ SURFSESSION_WID +" INTEGER," +
            " "+ SURFSESSION_WINDMIN +" DOUBLE," +
            " "+ SURFSESSION_WINDMAX +" DOUBLE," +
            " "+ SURFSESSION_WINDAVG +" DOUBLE," +
            " "+ SURFSESSION_FORECAST +" DOUBLE DEFAULT (null) ," +
            " "+ SURFSESSION_DATE +" DATETIME," +
            " "+ SURFSESSION_DURATION +" FLOAT," +
            " "+ SURFSESSION_SPOT +" VARCHAR," +
            " "+ SURFSESSION_FUNID +" INTEGER," +
            " "+ SURFSESSION_UID +" INTEGER," +
            " "+ SURFSESSION_WCID +" INTEGER," +
            " FOREIGN KEY ("+SURFSESSION_WID  +") REFERENCES "+TABLE_WINDDIRECTION+"("+ WIND_DIRECTION_ID +"),"  +
            " FOREIGN KEY ("+SURFSESSION_FUNID+") REFERENCES "+TABLE_SURFSESSION_CHARACTER+"("+ SURFSESSION_CHARACTER_FUNID +")," +
            " FOREIGN KEY ("+SURFSESSION_UID  +") REFERENCES "+TABLE_USER+"("+ USER_ID +"),"  +
            " FOREIGN KEY ("+SURFSESSION_WCID +") REFERENCES "+TABLE_WINDCHARACTER+"("+ WIND_CHARACTER_ID +"));";

    // SurfSessionCharacter create statement
    private static final String CREATE_TABLE_SURFSESSION_CHARACTER = "CREATE TABLE "+ TABLE_SURFSESSION_CHARACTER +" " +
            "( "+ SURFSESSION_CHARACTER_FUNID +"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," +
            " "+ SURFSESSION_CHARACTER_NAME +" VARCHAR );";

    // user create statement
    private static final String CREATE_TABLE_USER = "CREATE TABLE "+ TABLE_USER +" " +
            "("+ USER_ID +" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL ," +
            " "+ USER_NAME +" VARCHAR);";

    // windCharacter create statement
    private static final String CREATE_TABLE_WINDCHARACTER = "CREATE TABLE "+ TABLE_WINDCHARACTER +" " +
            "("+ WIND_CHARACTER_ID +" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL ," +
            " "+ WIND_CHARACTER_NAME +" VARCHAR);";

    // windDirection create statement
    private static final String CREATE_TABLE_WINDDIRECTION = "CREATE TABLE "+ TABLE_WINDDIRECTION +" " +
            "("+ WIND_DIRECTION_ID +" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL ," +
            " "+ WIND_DIRECTION_DIRECTION +" VARCHAR NOT NULL ," +
            " "+ WIND_DIRECTION_DEGREE +" DOUBLE NOT NULL  ," +
            " "+ WIND_DIRECTION_NAME + " VARCHAR);";


    // fill table
    // material type
    private List<String> COLUMNS_MT = new ArrayList<String>() {{
        add(MATERIAL_TYPE_ID);
        add(MATERIAL_TYPE_NAME);
    }};

    private String[][] VALUES_MT = {
            {"1","sonstiges"},{"2","Kite"},{"3","Bar"},{"4","Wartung"},{"5","Board"},{"6","Noepren"},{"7","Trapez"}
    };

    // forecast
    private List<String> COLUMNS_FC = new ArrayList<String>() {{
        add(FORECAST_ID);
        add(FORECAST_NAME);
    }};

    private String[][] VALUES_FC = {
            {"1","k.a."},{"2","ja"},{"3","nein"},{"4","zum Teil"}
    };

    //surfsession chracter
    private List<String> COLUMNS_SSC = new ArrayList<String>() {{
        add(SURFSESSION_CHARACTER_FUNID);
        add(SURFSESSION_CHARACTER_NAME);
    }};

    private String[][] VALUES_SSC = {
            {"1","sehr gut"},{"2","gut"},{"3","mittel"},{"4","schlecht"},{"5","sehr schlecht"}
    };

    // wind character
    private List<String> COLUMNS_WC = new ArrayList<String>() {{
        add(WIND_CHARACTER_ID);
        add(WIND_CHARACTER_NAME);
    }};

    private String[][] VALUES_WC = {
            {"1","konstant"},
            {"2","nahm zu"},
            {"3","wurde schwächer"},
            {"4","böig"},
            {"5","leichte Böen"},
            {"6", "starke Böen"},
            {"7","zu Begin Flaute"},
            {"8","zum Schluss Flaute"}
    };

    // wind direction
    private List<String> COLUMNS_WD = new ArrayList<String>() {{
        add(WIND_DIRECTION_ID);
        add(WIND_DIRECTION_DIRECTION);
        add(WIND_DIRECTION_DEGREE);
        add(WIND_DIRECTION_NAME);
    }};

    private String[][] VALUES_WD = {
            {"1","N","0","Nord"},
            {"2","NNO","22.5","Nord Nord Ost"},
            {"3","NO","45","Nord Ost"},
            {"4","ONO","67.5","Ost Nord Ost"},
            {"5","O","90","Ost"},
            {"6","OSO","112.5","Ost Süd Ost"},
            {"7","SO","135","Süd Ost"},
            {"8","SSO","157.5","Süd Süd Ost"},
            {"9","S","180","Süd"},
            {"10","SSW","202.5","Süd Süd West"},
            {"11","SW","225","Süd West"},
            {"12","WSW","247.5","West Süd West"},
            {"13","W","270","West"},
            {"14","WNW","292.5","West Nord West"},
            {"15","NW","315","Nord West"},
            {"16","NNW","337.5","Nord Nord west"}
    };

    // ---------------------------------------------------------------------
    // creating dummy data
    // ---------------------------------------------------------------------
    private static List<String> COLUMNS_US = new ArrayList<String>() {{
        add(USER_ID);
        add(USER_NAME);
    }};

    private static String[][] VALUES_US = {
            {"1","MeMatzDB"}
    };


    // dummy data Material
    private static List<String> COLUMNS_MA = new ArrayList<String>() {{
        add(MATERIAL_ID);
        add(MATERIAL_NAME);
        add(MATERIAL_TYPE);
        add(MATERIAL_YEAR);
        add(MATERIAL_PRICE);
        add(MATERIAL_CONDITION);
        add(MATERIAL_BUYDATE);
        add(MATERIAL_USER_ID);
        add(MATERIAL_SELLPRICE);
        add(MATERIAL_SELLDATE);
        add(MATERIAL_SELLFLAG);
    }};

    private static String[][] VALUES_MA = {
            {"1","Cabrinha","Convert","2010","400","gebraucht","12.06.2013","1","","","0"},
            {"2","Mystic","Streamer","2012","110","gebraucht","12.06.2013","1","","","0"},
            {"3","Neopren Schuhe","-","2012","30","gebraucht","12.09.2013","1","","","0"},
            {"4","Kitereperatur","Rebel 7qm","2015","50","gebraucht","20.04.2015","1","","","0"},
            {"5","North","Rebel","2010","450","gebraucht","20.10.2013","1","","","0"},
            {"6","Cabrinha","Powerdrive Bar IDS","2009","0","gebraucht","12.06.2013","1","","","0"},
            {"7","North","5th Element","2010","0","gebraucht","20.10.2013","1","","","0"},
            {"8","North","6th Element","2015","150","gebraucht","20.10.2015","1","70","20.12.2015","1"}
    };

    // dummy DATA Material Plus
    private static List<String> COLUMNS_MP = new ArrayList<String>() {{
        add(MATERIAL_PLUS_MTYPEID);
        add(MATERIAL_PLUS_MID);
        add(MATERIAL_PLUS_MKITE_ID);
        add(MATERIAL_PLUS_MBAR_ID);
    }};

    private static String[][] VALUES_MP = {
            {"2","1","1",""},
            {"2","5","2",""},
            {"6","2","",""},
            {"6","3","",""},
            {"3","6","","1"},
            {"3","7","","2"},
            {"3","8","","3"}
    };

    // dummy data Material to surfsession
    private static List<String> COLUMNS_MTS = new ArrayList<String>() {{
        add(MATERIAL_TO_SURFSESSION_SID);
        add(MATERIAL_TO_SURFSESSION_MID);
    }};

    private static String[][] VALUES_MTS = {
            {"1","1"},
            {"1","2"},
            {"1","3"},
            {"1","6"}
    };

    //dummy data mbar
    private static List<String> COLUMNS_BA = new ArrayList<String>() {{
        add(MBAR_ID);
        add(MBAR_LINES);
        add(MBAR_LENGHT);
        add(MBAR_WIDTH);
        add(MBAR_TEXT);
    }};

    private static String[][] VALUES_BA = {
            {"1","4","24","53","null"},
            {"2","5","27","45","Mal ein Text"},
            {"3","3","23","33","Mal ein Text"}
    };

    // dummy data mkite

    private static List<String> COLUMNS_KI = new ArrayList<String>() {{
        add(MKITE_ID);
        add(MKITE_LINES);
        add(MKITE_SIZE);
        add(MKITE_WINDFROM);
        add(MKITE_WINDTILL);
        add(MKITE_SHAPE);
        add(MKITE_TEXT);
    }};

    private static String[][] VALUES_KI = {
            {"1","4","12","12","20","Bow-Kite","null"},
            {"2","5","7","17","38","Delta-Hybrid","null"}
    };

    // dummy data Bar to Kite
    private static List<String> COLUMNS_BTK = new ArrayList<String>() {{
        add(BARTOKITE_BARID);
        add(BARTOKITE_KITEID);
    }};

    private static String[][] VALUES_BTK = {
            {"1","1"},
            {"2","2"}
    };

    // dummy data surfsession
    private static List<String> COLUMNS_SU = new ArrayList<String>() {{
        add(SURFSESSION_ID);
        add(SURFSESSION_WID);
        add(SURFSESSION_WINDMIN);
        add(SURFSESSION_WINDMAX);
        add(SURFSESSION_WINDAVG);
        add(SURFSESSION_FORECAST);
        add(SURFSESSION_DATE);
        add(SURFSESSION_DURATION);
        add(SURFSESSION_SPOT);
        add(SURFSESSION_FUNID);
        add(SURFSESSION_UID);
        add(SURFSESSION_WCID);
    }};

    private static String[][] VALUES_SU = {
            {"1","9","14","20","16","14","20.08.2015", "2.5", "Suhrendorf", "2", "1", "2"}
    };


    //methods
    public KitetifyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }
    public KitetifyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //new DBHelperMaterial(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.e("....", "CREATING DATABASE ??????");

        // creating required tables
        db.execSQL(CREATE_TABLE_BARTOKITE);
        db.execSQL(CREATE_TABLE_MATERIAL);
        db.execSQL(CREATE_TABLE_MATERIAL_PLUS);
        db.execSQL(CREATE_TABLE_MATERIAL_TO_SURFSESSION);
        db.execSQL(CREATE_TABLE_MATERIAL_TYPE);
        db.execSQL(CREATE_TABLE_MBAR);
        db.execSQL(CREATE_TABLE_MKITE);
        db.execSQL(CREATE_TABLE_FORECAST);
        db.execSQL(CREATE_TABLE_SURFSESSION);
        db.execSQL(CREATE_TABLE_SURFSESSION_CHARACTER);
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_WINDCHARACTER);
        db.execSQL(CREATE_TABLE_WINDDIRECTION);

        // creating required table values
        listInsertIntoDB(db, TABLE_MATERIAL_TYPE, COLUMNS_MT, VALUES_MT);
        listInsertIntoDB(db, TABLE_FORECAST, COLUMNS_FC, VALUES_FC);
        listInsertIntoDB(db, TABLE_SURFSESSION_CHARACTER, COLUMNS_SSC, VALUES_SSC);
        listInsertIntoDB(db, TABLE_WINDCHARACTER, COLUMNS_WC, VALUES_WC);
        listInsertIntoDB(db, TABLE_WINDDIRECTION, COLUMNS_WD, VALUES_WD);

        // dummy VALUES
        listInsertIntoDB(db, TABLE_BARTOKITE, COLUMNS_BTK, VALUES_BTK);
        listInsertIntoDB(db, TABLE_USER, COLUMNS_US, VALUES_US);
        listInsertIntoDB(db, TABLE_MATERIAL, COLUMNS_MA, VALUES_MA);
        listInsertIntoDB(db, TABLE_MATERIAL_PLUS, COLUMNS_MP, VALUES_MP);
        listInsertIntoDB(db, TABLE_MATERIAL_TO_SURFSESSION, COLUMNS_MTS, VALUES_MTS);
        listInsertIntoDB(db, TABLE_MBAR, COLUMNS_BA, VALUES_BA);
        listInsertIntoDB(db, TABLE_MKITE, COLUMNS_KI, VALUES_KI);
        listInsertIntoDB(db, TABLE_SURFSESSION, COLUMNS_SU, VALUES_SU);


        Log.e("....", "DATABASE CREATED!!!!!!!!!!");
        //Log.e("----", surfSession);


    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BARTOKITE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATERIAL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATERIAL_PLUS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATERIAL_TO_SURFSESSION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATERIAL_TYPE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MBAR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MKITE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FORECAST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SURFSESSION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SURFSESSION_CHARACTER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WINDCHARACTER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WINDDIRECTION);

        // create new tables
        onCreate(db);

    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    public void setDBRefresh(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BARTOKITE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATERIAL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATERIAL_PLUS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATERIAL_TO_SURFSESSION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATERIAL_TYPE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MBAR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MKITE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FORECAST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SURFSESSION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SURFSESSION_CHARACTER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WINDCHARACTER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WINDDIRECTION);

        onCreate(db);
    }

    /*
 * get single user
 */
    public User getUser(Integer user_id) {

        SQLiteDatabase db = this.getReadableDatabase();
        User newUser = new User();


        db.beginTransaction();
        try {
            String selectQuery = "SELECT  * FROM " + TABLE_USER + " WHERE "
                    + USER_ID + " = " + user_id;

            Log.e(LOG, selectQuery);

            Cursor c = db.rawQuery(selectQuery, null);

            if (c != null)
                c.moveToFirst();


            newUser.set_uID(c.getInt(c.getColumnIndex(USER_ID)));
            newUser.set_name(c.getString(c.getColumnIndex(USER_NAME)));

            db.setTransactionSuccessful();
        }catch(Exception e){
            // here you can catch all the exceptions
            newUser.set_uID(666);
            newUser.set_name("ErrMem");
            //e.printStack();
        } finally {
            db.endTransaction();
        }

        return newUser;
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    /*
    * Dies ist eine Sicher Hilfsmethode um mehrere Insert INTO von der Datenbank ausfueren zulassen.
    * Dies ist notwendig, da keine Multiplen "INSERT INTOs" von der SQLite Datenbank unter dem
    * Release 3.7.11 ausgefuehrt weden koennen. http://www.sqlite.org/releaselog/3_7_11.html
    * */
    public void listInsertIntoDB(SQLiteDatabase db, String table, List<String> columns, String[][] values){

        if(db != null && db.isOpen()){

            int i = 0;
            int r;

            ContentValues cv = new ContentValues();

            db.beginTransaction();

            for(r=0; r<values.length; r++) {
                for (int c=0; c < values [r].length; c++) {
                   // Log.v("val", columns.get(c) + " " + values [r][c] + " ");
                    cv.put(columns.get(c), values [r][c]);
                }
                db.insertOrThrow(table, null, cv);
                Log.v("Inserted Row :", " - - " + Integer.toString(r));

            }
            db.setTransactionSuccessful();
            db.endTransaction();


        } else {
            Log.v("DB", "DB is close");
        }

    }

    /* -------------------------------------------------------------------------
        GETTTER  + SETTER
       -------------------------------------------------------------------------

     */

    public static String getCreateTableBartokite() {
        return CREATE_TABLE_BARTOKITE;
    }

    public static String getCreateTableMaterial() {
        return CREATE_TABLE_MATERIAL;
    }

    public static String getCreateTableMaterialPlus() {
        return CREATE_TABLE_MATERIAL_PLUS;
    }

    public static String getCreateTableMaterialToSurfsession() {
        return CREATE_TABLE_MATERIAL_TO_SURFSESSION;
    }

    public static String getCreateTableMaterialType() {
        return CREATE_TABLE_MATERIAL_TYPE;
    }

    public static String getCreateTableMbar() {
        return CREATE_TABLE_MBAR;
    }

    public static String getCreateTableMkite() {
        return CREATE_TABLE_MKITE;
    }

    public static String getCreateTableForecast() {
        return CREATE_TABLE_FORECAST;
    }

    public static String getCreateTableSurfsession() {
        return CREATE_TABLE_SURFSESSION;
    }

    public static String getCreateTableSurfsessionCharacter() {
        return CREATE_TABLE_SURFSESSION_CHARACTER;
    }

    public static String getCreateTableUser() {
        return CREATE_TABLE_USER;
    }

    public static String getCreateTableWindcharacter() {
        return CREATE_TABLE_WINDCHARACTER;
    }

    public static String getCreateTableWinddirection() {
        return CREATE_TABLE_WINDDIRECTION;
    }


    public static String getForecastId() {
        return FORECAST_ID;
    }

    public static String getTableBartokite() {
        return TABLE_BARTOKITE;
    }

    public static String getTableForecast() {
        return TABLE_FORECAST;
    }

    public static String getForecastName() {
        return FORECAST_NAME;
    }

    public static String getBartokiteBarid() {
        return BARTOKITE_BARID;
    }

    public static String getBartokiteKiteid() {
        return BARTOKITE_KITEID;
    }

    public static String getMaterialBuydate() {
        return MATERIAL_BUYDATE;
    }

    public static String getMaterialCondition() {
        return MATERIAL_CONDITION;
    }

    public static String getMaterialId() {
        return MATERIAL_ID;
    }

    public static String getMaterialName() {
        return MATERIAL_NAME;
    }

    public static String getMaterialPlusMbarId() {
        return MATERIAL_PLUS_MBAR_ID;
    }

    public static String getMaterialPlusMid() {
        return MATERIAL_PLUS_MID;
    }

    public static String getMaterialPlusMkiteId() {
        return MATERIAL_PLUS_MKITE_ID;
    }

    public static String getMaterialPlusMtypeid() {
        return MATERIAL_PLUS_MTYPEID;
    }

    public static String getMaterialPrice() {
        return MATERIAL_PRICE;
    }

    public static String getMaterialToSurfsessionMid() {
        return MATERIAL_TO_SURFSESSION_MID;
    }

    public static String getMaterialToSurfsessionSid() {
        return MATERIAL_TO_SURFSESSION_SID;
    }

    public static String getMaterialType() {
        return MATERIAL_TYPE;
    }

    public static String getMaterialTypeId() {
        return MATERIAL_TYPE_ID;
    }

    public static String getMaterialTypeName() {
        return MATERIAL_TYPE_NAME;
    }

    public static String getMaterialUserId() {
        return MATERIAL_USER_ID;
    }

    public static String getMaterialYear() {
        return MATERIAL_YEAR;
    }

    public static String getMbarId() {
        return MBAR_ID;
    }

    public static String getMbarLenght() {
        return MBAR_LENGHT;
    }

    public static String getMbarLines() {
        return MBAR_LINES;
    }

    public static String getMbarText() {
        return MBAR_TEXT;
    }

    public static String getMbarWidth() {
        return MBAR_WIDTH;
    }

    public static String getMkiteId() {
        return MKITE_ID;
    }

    public static String getMkiteLines() {
        return MKITE_LINES;
    }

    public static String getMkiteShape() {
        return MKITE_SHAPE;
    }

    public static String getMkiteSize() {
        return MKITE_SIZE;
    }

    public static String getMkiteText() {
        return MKITE_TEXT;
    }

    public static String getMkiteWindfrom() {
        return MKITE_WINDFROM;
    }

    public static String getMkiteWindtill() {
        return MKITE_WINDTILL;
    }

    public static String getSurfsessionCharacterFunid() {
        return SURFSESSION_CHARACTER_FUNID;
    }

    public static String getSurfsessionCharacterName() {
        return SURFSESSION_CHARACTER_NAME;
    }

    public static String getSurfsessionDate() {
        return SURFSESSION_DATE;
    }

    public static String getSurfsessionDuration() {
        return SURFSESSION_DURATION;
    }

    public static String getSurfsessionForecast() {
        return SURFSESSION_FORECAST;
    }

    public static String getSurfsessionFunid() {
        return SURFSESSION_FUNID;
    }

    public static String getSurfsessionId() {
        return SURFSESSION_ID;
    }

    public static String getSurfsessionSpot() {
        return SURFSESSION_SPOT;
    }

    public static String getSurfsessionUid() {
        return SURFSESSION_UID;
    }

    public static String getSurfsessionWcid() {
        return SURFSESSION_WCID;
    }

    public static String getSurfsessionWid() {
        return SURFSESSION_WID;
    }

    public static String getSurfsessionWindavg() {
        return SURFSESSION_WINDAVG;
    }

    public static String getSurfsessionWindmax() {
        return SURFSESSION_WINDMAX;
    }

    public static String getSurfsessionWindmin() {
        return SURFSESSION_WINDMIN;
    }

    public static String getTableMaterial() {
        return TABLE_MATERIAL;
    }

    public static String getTableMaterialPlus() {
        return TABLE_MATERIAL_PLUS;
    }

    public static String getTableMaterialToSurfsession() {
        return TABLE_MATERIAL_TO_SURFSESSION;
    }

    public static String getTableMaterialType() {
        return TABLE_MATERIAL_TYPE;
    }

    public static String getTableMbar() {
        return TABLE_MBAR;
    }

    public static String getTableMkite() {
        return TABLE_MKITE;
    }

    public static String getTableSurfsession() {
        return TABLE_SURFSESSION;
    }

    public static String getTableSurfsessionCharacter() {
        return TABLE_SURFSESSION_CHARACTER;
    }

    public static String getTableUser() {
        return TABLE_USER;
    }

    public static String getTableWindcharacter() {
        return TABLE_WINDCHARACTER;
    }

    public static String getTableWinddirection() {
        return TABLE_WINDDIRECTION;
    }

    public static String getUserId() {
        return USER_ID;
    }

    public static String getUserName() {
        return USER_NAME;
    }


    public static String getWindCharacterId() {
        return WIND_CHARACTER_ID;
    }

    public static String getWindCharacterName() {
        return WIND_CHARACTER_NAME;
    }

    public static String getWindDirectionDegree() {
        return WIND_DIRECTION_DEGREE;
    }

    public static String getWindDirectionDirection() {
        return WIND_DIRECTION_DIRECTION;
    }

    public static String getWindDirectionId() {
        return WIND_DIRECTION_ID;
    }

    public static String getWindDirectionName() {
        return WIND_DIRECTION_NAME;
    }



    public static String getMaterialSelldate() {
        return MATERIAL_SELLDATE;
    }

    public static String getMaterialSellflag() {
        return MATERIAL_SELLFLAG;
    }

    public static String getMaterialSellprice() {
        return MATERIAL_SELLPRICE;
    }


}
