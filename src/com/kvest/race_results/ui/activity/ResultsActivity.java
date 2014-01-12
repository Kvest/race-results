package com.kvest.race_results.ui.activity;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import com.kvest.race_results.R;
import com.kvest.race_results.contentprovider.ResultsProviderMetadata;
import com.kvest.race_results.datastorage.table.ResultsTable;
import com.kvest.race_results.utility.GroupHelper;

public class ResultsActivity extends FragmentActivity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);
    }
}
