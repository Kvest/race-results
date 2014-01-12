package com.kvest.race_results.datastorage.table;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * Created with IntelliJ IDEA.
 * User: Kvest
 * Date: 11.01.14
 * Time: 23:05
 * To change this template use File | Settings | File Templates.
 */
public class ResultsTable implements BaseColumns {
    public static final String TABLE_NAME = "results";

    public static final String NAME_COLUMN = "name";
    public static final String TIME_COLUMN = "time";
    public static final String AGE_COLUMN = "age";
    public static final String RANKING_COLUMN = "ranking";
    public static final String GROUP_COLUMN = "group";

    public static final String[] FULL_PROJECTION = {_ID, NAME_COLUMN, TIME_COLUMN, AGE_COLUMN,
                                                    RANKING_COLUMN, GROUP_COLUMN};

    public static final String CREATE_TABLE_SQL = "CREATE TABLE \"" + TABLE_NAME + "\" (\"" +
            _ID + "\" INTEGER PRIMARY KEY, \"" +
            NAME_COLUMN + "\" TEXT DEFAULT \"\", \"" +
            TIME_COLUMN + "\" INTEGER NOT NULL, \"" +
            AGE_COLUMN + "\" INTEGER DEFAULT 0, \"" +
            RANKING_COLUMN + "\" INTEGER DEFAULT 0, \"" +
            GROUP_COLUMN +"\" INTEGER NOT NULL);";
    public static final String CREATE_TIME_INDEX_SQL = "CREATE INDEX IF NOT EXISTS \"time_index\" ON \"" + TABLE_NAME +
                                                    "\"(\"" + TIME_COLUMN + "\");";
    public static final String CREATE_AFTER_INSERT_TRIGGER_SQL = "CREATE TRIGGER IF NOT EXISTS \"after_result_insert\"" +
            " AFTER INSERT ON \"" + TABLE_NAME + "\" " +
            "BEGIN " +
            " UPDATE \"" + TABLE_NAME + "\" SET \"" + RANKING_COLUMN +
            "\" = coalesce((SELECT count(*) + 1 FROM \"" + TABLE_NAME + "\" WHERE \"" + TIME_COLUMN +
            "\" < NEW.\"" + TIME_COLUMN +"\" AND \"" + GROUP_COLUMN + "\" =  NEW.\"" + GROUP_COLUMN +
            "\"), 0) WHERE \"" + _ID + "\" = NEW.\"" + _ID + "\";" +
            " UPDATE \"" + TABLE_NAME + "\" SET \"" + RANKING_COLUMN +
            "\"=\"" + RANKING_COLUMN + "\" + 1 WHERE \"" + TIME_COLUMN +
            "\" > NEW.\"" + TIME_COLUMN +"\" AND \"" + GROUP_COLUMN + "\" =  NEW.\"" + GROUP_COLUMN + "\"" +
            " END;";
    public static final String CREATE_AFTER_DELETE_TRIGGER_SQL = "CREATE TRIGGER IF NOT EXISTS \"after_result_delete\"" +
            " AFTER DELETE ON \"" + TABLE_NAME + "\" " +
            "BEGIN " +
            " UPDATE \"" + TABLE_NAME + "\" SET \"" + RANKING_COLUMN +
            "\"=\"" + RANKING_COLUMN + "\" - 1 WHERE \"" + TIME_COLUMN +
            "\" > OLD.\"" + TIME_COLUMN +"\" AND \"" + GROUP_COLUMN + "\" =  OLD.\"" + GROUP_COLUMN + "\"" +
            " END;";
    public static final String DROP_TABLE_SQL = "DROP TABLE IF EXISTS \"" + TABLE_NAME + "\";";

    public static void onDBCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);
        db.execSQL(CREATE_TIME_INDEX_SQL);
        db.execSQL(CREATE_AFTER_INSERT_TRIGGER_SQL);
        db.execSQL(CREATE_AFTER_DELETE_TRIGGER_SQL);
    }
}
