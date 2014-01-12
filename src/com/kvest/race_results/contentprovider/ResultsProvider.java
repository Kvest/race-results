package com.kvest.race_results.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import com.kvest.race_results.datastorage.RaceResultsSQLStorage;
import com.kvest.race_results.datastorage.table.ResultsTable;

/**
 * Created with IntelliJ IDEA.
 * User: Kvest
 * Date: 11.01.14
 * Time: 23:58
 * To change this template use File | Settings | File Templates.
 */
public class ResultsProvider extends ContentProvider {
    private RaceResultsSQLStorage sqlStorage;

    private static final int RESULTS_URI_INDICATOR = 1;
    private static final int RESULT_URI_INDICATOR = 2;

    private static final UriMatcher uriMatcher;
    static
    {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(ResultsProviderMetadata.AUTHORITY, ResultsProviderMetadata.RESULTS_PATH, RESULTS_URI_INDICATOR);
        uriMatcher.addURI(ResultsProviderMetadata.AUTHORITY, ResultsProviderMetadata.RESULTS_PATH + "/#", RESULT_URI_INDICATOR);
    }

    @Override
    public boolean onCreate() {
        sqlStorage = new RaceResultsSQLStorage(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        switch (uriMatcher.match(uri)) {
            case RESULTS_URI_INDICATOR :
                queryBuilder.setTables(ResultsTable.TABLE_NAME);
                break;
            case RESULT_URI_INDICATOR :
                queryBuilder.setTables(ResultsTable.TABLE_NAME);
                queryBuilder.appendWhere(ResultsTable._ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown uri = " + uri);
        }

        //make query
        SQLiteDatabase db = sqlStorage.getReadableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);

        // Make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = sqlStorage.getWritableDatabase();
        long rowId = 0;

        switch (uriMatcher.match(uri)) {
            case RESULTS_URI_INDICATOR :
                //"replace" works as "INSERT OR REPLACE"
                rowId = db.replace(ResultsTable.TABLE_NAME, null, values);
                if (rowId > 0)
                {
                    Uri resultUri = ContentUris.withAppendedId(uri, rowId);
                    getContext().getContentResolver().notifyChange(resultUri, null);
                    return resultUri;
                }
                break;
        }

        throw new IllegalArgumentException("Faild to insert row into " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int rowsDeleted = 0;
        boolean hasSelection = !TextUtils.isEmpty(selection);
        SQLiteDatabase db = sqlStorage.getWritableDatabase();

        switch (uriMatcher.match(uri)) {
            case RESULTS_URI_INDICATOR :
                rowsDeleted = db.delete(ResultsTable.TABLE_NAME, selection, selectionArgs);
                break;
            case RESULT_URI_INDICATOR :
                rowsDeleted = db.delete(ResultsTable.TABLE_NAME, ResultsTable._ID + "=" + uri.getLastPathSegment() +
                        (hasSelection ? (" AND " + selection) : ""), (hasSelection ? selectionArgs : null));
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        if (rowsDeleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        //This method is not used, so we don't implemented it
        throw new IllegalArgumentException("Can't update URI: " + uri);
    }

    @Override
    public String getType(Uri uri) {
        switch(uriMatcher.match(uri))
        {
            case RESULTS_URI_INDICATOR : return ResultsProviderMetadata.CONTENT_TYPE_RESULT_COLLECTION;
            case RESULT_URI_INDICATOR : return ResultsProviderMetadata.CONTENT_TYPE_RESULT_SINGLE;
            default: throw new IllegalArgumentException("Unknown URI" + uri);
        }
    }
}
