package com.example.batkhuu.swisslottobuddy;

import android.content.Context;
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
    private static final String NUMBER_1 = "number1";
    private static final String NUMBER_2 = "number2";
    private static final String NUMBER_3 = "number3";
    private static final String NUMBER_4 = "number4";
    private static final String NUMBER_5 = "number5";
    private static final String NUMBER_6 = "number6";
    private static final String L_NUMBER = "lucky_number";
    private static final String R_NUMBER = "replay_number";

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
    private static final String create_Table_Draws = "CREATE TABLE "+DRAWS_TABLE+" ("+
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
    private static final String create_Table_Tips = "CREATE TABLE "+TIPS_TABLE+" ("+
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
}
