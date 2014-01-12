package com.kvest.race_results.contentprovider;

import android.net.Uri;

/**
 * Created with IntelliJ IDEA.
 * User: Kvest
 * Date: 11.01.14
 * Time: 23:57
 * To change this template use File | Settings | File Templates.
 */
public abstract class ResultsProviderMetadata {
    public static final String AUTHORITY = "com.kvest.race_results.contentprovider.ResultsProvider";

    public static final String CONTENT_TYPE_RESULT_COLLECTION = "vnd.android.cursor.dir/vnd.kvest.result";
    public static final String CONTENT_TYPE_RESULT_SINGLE = "vnd.android.cursor.item/vnd.kvest.result";

    public static final String RESULTS_PATH = "results";
    public static final Uri RESULTS_URI = Uri.parse("content://" + AUTHORITY + "/" + RESULTS_PATH);
}
