package com.kvest.race_results.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.kvest.race_results.R;
import com.kvest.race_results.contentprovider.ResultsProviderMetadata;

public class ResultsActivity extends ActionBarActivity {
    private static final int SETTINGS_MENU_ID = 0;
    private static final int REFRESH_MENU_ID = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuItemCompat.setShowAsAction(menu.add(0, SETTINGS_MENU_ID, 0, getString(R.string.settings))
                      .setIcon(R.drawable.settings), MenuItem.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setShowAsAction(menu.add(0, REFRESH_MENU_ID, 0, getString(R.string.refresh))
                      .setIcon(R.drawable.refresh), MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case SETTINGS_MENU_ID :
                showSettings();
                return true;
            case REFRESH_MENU_ID :
                refreshResults();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void refreshResults() {
        //clear old results
        //getContentResolver().delete(ResultsProviderMetadata.RESULTS_URI, null, null);

        //load new results
        //TODO
    }

    private void showSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
