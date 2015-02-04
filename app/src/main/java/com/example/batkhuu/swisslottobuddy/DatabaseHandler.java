package com.example.batkhuu.swisslottobuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseHandler extends SQLiteOpenHelper {

    // DB Informationen
    private static final String DATABASE_NAME = "swisslottobuddy.db";
    private static final int DATABASE_VERSION = 1;

    // Tabelle DRAWS Information
    private static final String DRAWS_TABLE = "DRAWS";

    // Spalten von DRAWS
    private static final String D_ID = "_id";
    private static final String DD = "draw_date";
    private static final String NDD = "next_draw_date";
    private static final String JACKPOT = "jackpot";
    private static final String NUMBER0 = "number0";
    private static final String NUMBER1 = "number1";
    private static final String NUMBER2 = "number2";
    private static final String NUMBER3 = "number3";
    private static final String NUMBER4 = "number4";
    private static final String NUMBER5 = "number5";
    private static final String L_NUMBER = "luckynumber";
    private static final String R_NUMBER = "replay_number";
    // evt. add Win Rank
    private static final String WCI0 = "win_class_index0"; // Win Class Index 0: 6 + 1
    private static final String WCI1 = "win_class_index1"; // Win Class Index 0: 6
    private static final String WCI2 = "win_class_index2"; // Win Class Index 0: 5 + 1
    private static final String WCI3 = "win_class_index3"; // Win Class Index 0: 5
    private static final String WCI4 = "win_class_index4"; // Win Class Index 0: 4 + 1
    private static final String WCI5 = "win_class_index5"; // Win Class Index 0: 4
    private static final String WCI6 = "win_class_index6"; // Win Class Index 0: 3 + 1
    private static final String WCI7 = "win_class_index7"; // Win Class Index 0: 3

    // Tabelle TIPS Information
    private static final String TIPS_TABLE = "TIPS";

    // Spalten von TIPS
    private static final String T_ID = "_id";
    private static final String T_DD = "draw_date";
    private static final String T_NUMBER1 = "number1";
    private static final String T_NUMBER2 = "number2";
    private static final String T_NUMBER3 = "number3";
    private static final String T_NUMBER4 = "number4";
    private static final String T_NUMBER5 = "number5";
    private static final String T_NUMBER6 = "number6";
    private static final String T_L_NUMBER = "lucky_number";
    private static final String T_WINNING = "winning";

    // Query um die Tabelle zu kreieren
    private static final String create_Table_Draws = "CREATE TABLE "+DRAWS_TABLE+"("+
            D_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            DD+" INTEGER, "+
            NDD+" INTEGER, "+
            JACKPOT+" INTEGER, "+
            NUMBER0 +" INTEGER, "+
            NUMBER1 +" INTEGER, "+
            NUMBER2 +" INTEGER, "+
            NUMBER3 +" INTEGER, "+
            NUMBER4 +" INTEGER, "+
            NUMBER5 +" INTEGER, "+
            L_NUMBER+" INTEGER, "+
            R_NUMBER+" INTEGER, "+
            WCI0+" INTEGER, "+
            WCI1+" INTEGER, "+
            WCI2+" INTEGER, "+
            WCI3+" INTEGER, "+
            WCI4+" INTEGER, "+
            WCI5+" INTEGER, "+
            WCI6+" INTEGER, "+
            WCI7+" INTEGER);";
    private static final String create_Table_Tips = "CREATE TABLE "+TIPS_TABLE+"("+
            T_ID+" INTEGER PRIMARY KEY, "+
            T_DD+" INTEGER, "+
            T_NUMBER1 +" INTEGER, "+
            T_NUMBER2 +" INTEGER, "+
            T_NUMBER3 +" INTEGER, "+
            T_NUMBER4 +" INTEGER, "+
            T_NUMBER5 +" INTEGER, "+
            T_NUMBER6 +" INTEGER, "+
            T_L_NUMBER+" INTEGER, "+
            T_WINNING+" INTEGER);";
    private static final String[] D_COLUMNS = {D_ID, DD, NDD, JACKPOT, NUMBER0, NUMBER1, NUMBER2, NUMBER3, NUMBER4, NUMBER5, L_NUMBER, R_NUMBER, WCI0, WCI1, WCI2, WCI3, WCI4, WCI5, WCI6, WCI7};
    private static final String[] T_COLUMNS = {T_ID, T_DD, T_NUMBER1, T_NUMBER2, T_NUMBER3, T_NUMBER4, T_NUMBER5, T_NUMBER6, T_L_NUMBER, T_WINNING};

    // Queries
    private static final String lastDraw = "SELECT * FROM "+DRAWS_TABLE+" ORDER BY "+D_ID+" DESC LIMIT 1;"; // Query für letzte Ziehungsdaten


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_Table_Draws);
        db.execSQL(create_Table_Tips);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DRAWS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+TIPS_TABLE);

        onCreate(db);
    }

    public void addDraw(ContentValues values){
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
//        ContentValues values = new ContentValues();
//        values.put(DD, "24.01.2015");
//        values.put(NDD, "28.01.2015");
//        values.put(JACKPOT, "2200000");
//        values.put(NUMBER1, "3");
//        values.put(NUMBER2, "11");
//        values.put(NUMBER3, "15");
//        values.put(NUMBER4, "20");
//        values.put(NUMBER5, "25");
//        values.put(NUMBER0, "41");
//        values.put(L_NUMBER, "4");
//        values.put(R_NUMBER, "9");

        // 3. insert
        long i = db.insert(DRAWS_TABLE, null, values);
        Log.v("SLB", "Result: " + i);


        // 4. close
        db.close();
    }

    public void addTip(){
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(T_DD, "28.01.2015");
        values.put(T_NUMBER1, "4");
        values.put(T_NUMBER2, "13");
        values.put(T_NUMBER3, "15");
        values.put(T_NUMBER4, "23");
        values.put(T_NUMBER5, "29");
        values.put(T_NUMBER6, "40");
        values.put(T_L_NUMBER, "2");
        values.put(T_WINNING, "");

        // 3. insert
        db.insert(TIPS_TABLE, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public Cursor getLastDraw(){
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor = db.rawQuery(lastDraw, null);

        // 3. return book
        if (cursor != null) {
            return cursor;
        } else {
            return null;
        }
    }
}
