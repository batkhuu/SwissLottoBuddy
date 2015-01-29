package com.example.batkhuu.swisslottobuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;


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
    private static final String NUMBER_1 = "number1";
    private static final String NUMBER_2 = "number2";
    private static final String NUMBER_3 = "number3";
    private static final String NUMBER_4 = "number4";
    private static final String NUMBER_5 = "number5";
    private static final String NUMBER_6 = "number6";
    private static final String L_NUMBER = "lucky_number";
    private static final String R_NUMBER = "replay_number";
    // evt. add Win Rank
    private static final String WCI_0 = "wci0"; // Win Class Index 0: 6 + 1
    private static final String WCI_1 = "wci1"; // Win Class Index 0: 6
    private static final String WCI_2 = "wci2"; // Win Class Index 0: 5 + 1
    private static final String WCI_3 = "wci3"; // Win Class Index 0: 5
    private static final String WCI_4 = "wci4"; // Win Class Index 0: 4 + 1
    private static final String WCI_5 = "wci5"; // Win Class Index 0: 4
    private static final String WCI_6 = "wci6"; // Win Class Index 0: 3 + 1
    private static final String WCI_7 = "wci7"; // Win Class Index 0: 3

    // Tabelle TIPS Information
    private static final String TIPS_TABLE = "TIPS";

    // Spalten von TIPS
    private static final String T_ID = "_id";
    private static final String T_DD = "draw_date";
    private static final String T_NUMBER_1 = "number1";
    private static final String T_NUMBER_2 = "number2";
    private static final String T_NUMBER_3 = "number3";
    private static final String T_NUMBER_4 = "number4";
    private static final String T_NUMBER_5 = "number5";
    private static final String T_NUMBER_6 = "number6";
    private static final String T_L_NUMBER = "lucky_number";
    private static final String T_WINNING = "winning";
    private static final String create_Table_Draws = "CREATE TABLE "+DRAWS_TABLE+"("+
            D_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            DD+" INTEGER, "+
            NDD+" INTEGER, "+
            JACKPOT+" INTEGER, "+
            NUMBER_1+" INTEGER, "+
            NUMBER_2+" INTEGER, "+
            NUMBER_3+" INTEGER, "+
            NUMBER_4+" INTEGER, "+
            NUMBER_5+" INTEGER, "+
            NUMBER_6+" INTEGER, "+
            L_NUMBER+" INTEGER, "+
            R_NUMBER+" INTEGER);";
    private static final String create_Table_Tips = "CREATE TABLE "+TIPS_TABLE+"("+
            T_ID+" INTEGER PRIMARY KEY, "+
            T_DD+" INTEGER, "+
            T_NUMBER_1+" INTEGER, "+
            T_NUMBER_2+" INTEGER, "+
            T_NUMBER_3+" INTEGER, "+
            T_NUMBER_4+" INTEGER, "+
            T_NUMBER_5+" INTEGER, "+
            T_NUMBER_6+" INTEGER, "+
            T_L_NUMBER+" INTEGER, "+
            T_WINNING+" INTEGER);";
    private static final String[] D_COLUMNS = {D_ID, DD, NDD, JACKPOT, NUMBER_1, NUMBER_2, NUMBER_3, NUMBER_4, NUMBER_5, NUMBER_6, L_NUMBER, R_NUMBER};
    private static final String[] T_COLUMNS = {T_ID, T_DD, T_NUMBER_1, T_NUMBER_2, T_NUMBER_3, T_NUMBER_4, T_NUMBER_5, T_NUMBER_6, T_L_NUMBER, T_WINNING};

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

    public void addDraw(){
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(DD, "24.01.2015");
        values.put(NDD, "28.01.2015");
        values.put(JACKPOT, "2200000");
        values.put(NUMBER_1, "3");
        values.put(NUMBER_2, "11");
        values.put(NUMBER_3, "15");
        values.put(NUMBER_4, "20");
        values.put(NUMBER_5, "25");
        values.put(NUMBER_6, "41");
        values.put(L_NUMBER, "4");
        values.put(R_NUMBER, "9");

        // 3. insert
        db.insert(DRAWS_TABLE, null, values);

        // 4. close
        db.close();
    }

    public void addTip(){
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(T_DD, "28.01.2015");
        values.put(T_NUMBER_1, "4");
        values.put(T_NUMBER_2, "13");
        values.put(T_NUMBER_3, "15");
        values.put(T_NUMBER_4, "23");
        values.put(T_NUMBER_5, "29");
        values.put(T_NUMBER_6, "40");
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
        return cursor;
    }

    public Cursor testo(){
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor = db.rawQuery("Select count(*) from "+DRAWS_TABLE+";", null);

        // 3. return book
        return cursor;
    }
}
