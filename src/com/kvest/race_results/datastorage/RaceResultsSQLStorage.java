package com.kvest.race_results.datastorage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.kvest.race_results.datastorage.table.ResultsTable;

/**
 * Created with IntelliJ IDEA.
 * User: Kvest
 * Date: 11.01.14
 * Time: 23:00
 * To change this template use File | Settings | File Templates.
 */
public class RaceResultsSQLStorage extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "race_results";
    private static final int DATABASE_VERSION = 1;

    public RaceResultsSQLStorage(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create DB structure
        ResultsTable.onDBCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Nothing to do yet
    }
}
