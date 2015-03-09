package com.example.batkhuu.swisslottobuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


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
    private static final String WCI0 = "win_class_index0"; // Win Class Index 0: 6 + 1
    private static final String WCI1 = "win_class_index1"; // Win Class Index 1: 6
    private static final String WCI2 = "win_class_index2"; // Win Class Index 2: 5 + 1
    private static final String WCI3 = "win_class_index3"; // Win Class Index 3: 5
    private static final String WCI4 = "win_class_index4"; // Win Class Index 4: 4 + 1
    private static final String WCI5 = "win_class_index5"; // Win Class Index 5: 4
    private static final String WCI6 = "win_class_index6"; // Win Class Index 6: 3 + 1
    private static final String WCI7 = "win_class_index7"; // Win Class Index 7: 3

    // Tabelle TIPS Information
    private static final String TIPS_TABLE = "TIPS";

    // Spalten von TIPS
    private static final String T_WINNING = "winning";
    private static final String T_SPENDING = "spending";
    private static final String CREATE_DATE = "created_at";

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
            D_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            DD+" INTEGER, "+
            NUMBER0 +" INTEGER, "+
            NUMBER1 +" INTEGER, "+
            NUMBER2 +" INTEGER, "+
            NUMBER3 +" INTEGER, "+
            NUMBER4 +" INTEGER, "+
            NUMBER5 +" INTEGER, "+
            L_NUMBER+" INTEGER, "+
            T_SPENDING+" INTEGER DEFAULT 0, "+
            T_WINNING+" INTEGER DEFAULT 0, "+
            CREATE_DATE+" DATETIME DEFAULT CURRENT_DATE);";

    /*private static final String[] D_COLUMNS = {D_ID, DD, NDD, JACKPOT, NUMBER0, NUMBER1, NUMBER2,
            NUMBER3, NUMBER4, NUMBER5, L_NUMBER, R_NUMBER, WCI0, WCI1, WCI2, WCI3, WCI4, WCI5,
            WCI6, WCI7};
    private static final String[] T_COLUMNS = {D_ID, DD, NUMBER0, NUMBER1, NUMBER2, NUMBER3,
            NUMBER4, NUMBER5, L_NUMBER, T_SPENDING, T_WINNING};
*/
    // Queries
    private static final String lastDraw = "SELECT * FROM "+DRAWS_TABLE+" ORDER BY "+D_ID+" DESC LIMIT 1;"; // Query für letzte Ziehungsdaten
    private static final String lastDrawID = "SELECT "+D_ID+" FROM "+DRAWS_TABLE+" ORDER BY "+D_ID+" DESC LIMIT 1"; // Query für letzte Ziehungsdaten
    private static final String nextDrawTips = "SELECT * FROM "+TIPS_TABLE+" WHERE "+DD+"=("+lastDrawID+");";
    private static final String query0 = "SELECT SUM("+T_SPENDING+"), SUM("+T_WINNING+") FROM "+TIPS_TABLE+" WHERE "+CREATE_DATE+" BETWEEN datetime('now','-6 days') AND datetime('now','localtime');";
    private static final String query1 = "SELECT SUM("+T_SPENDING+"), SUM("+T_WINNING+") FROM "+TIPS_TABLE+" WHERE "+CREATE_DATE+" BETWEEN datetime('now','-30 days') AND datetime('now','localtime');";
    private static final String query2 = "SELECT SUM("+T_SPENDING+"), SUM("+T_WINNING+") FROM "+TIPS_TABLE+" WHERE "+CREATE_DATE+" BETWEEN datetime('now','-180 days') AND datetime('now','localtime');";
    private static final String query3 = "SELECT SUM("+T_SPENDING+"), SUM("+T_WINNING+") FROM "+TIPS_TABLE+" WHERE "+CREATE_DATE+" BETWEEN datetime('now','-360 days') AND datetime('now','localtime');";
    private static final String query4 = "SELECT SUM("+T_SPENDING+"), SUM("+T_WINNING+") FROM "+TIPS_TABLE+";";


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

        // 2. insert
        db.insert(DRAWS_TABLE, null, values);

        // 3. close
        db.close();
    }

    public void addTip(ContentValues values){
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. insert
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

    public Cursor getNdTips() {
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor = db.rawQuery(nextDrawTips, null);

        // 3. return book
        if (cursor != null) {
            return cursor;
        } else {
            return null;
        }
    }

    public Cursor get0() {
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor = db.rawQuery(query0, null);

        // 3. return book
        if (cursor != null) {
            return cursor;
        } else {
            return null;
        }
    }

    public Cursor get1() {
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor = db.rawQuery(query1, null);

        // 3. return book
        if (cursor != null) {
            return cursor;
        } else {
            return null;
        }
    }

    public Cursor get2() {
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor = db.rawQuery(query2, null);

        // 3. return book
        if (cursor != null) {
            return cursor;
        } else {
            return null;
        }
    }

    public Cursor get3() {
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor = db.rawQuery(query3, null);

        // 3. return book
        if (cursor != null) {
            return cursor;
        } else {
            return null;
        }
    }

    public Cursor get4() {
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor = db.rawQuery(query4, null);

        // 3. return book
        if (cursor != null) {
            return cursor;
        } else {
            return null;
        }
    }

    public void updateTip(String win, String id){
        // 1. set Query
        String updateQuery = "UPDATE "+TIPS_TABLE+" SET "+T_WINNING+"="+win+" WHERE "+D_ID+"="+id+";";
        //Log.v("SLB", updateQuery);

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 3. Update
        db.execSQL(updateQuery);

        // 4. close
        db.close();
    }

    public String getPath(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.getPath();
    }
}
