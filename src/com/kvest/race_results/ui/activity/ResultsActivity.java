package com.kvest.race_results.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;
import com.kvest.race_results.R;
import com.kvest.race_results.contentprovider.ResultsProviderMetadata;
import com.kvest.race_results.service.NetworkWorkerService;
import com.kvest.race_results.utility.SettingsPreferenceHelper;
import com.kvest.race_results.utility.Utils;

public class ResultsActivity extends ActionBarActivity {
    private static final int SETTINGS_MENU_ID = 0;
    private static final int REFRESH_MENU_ID = 1;

    private ServiceStateBroadcastReceiver serviceStateBroadcastReceiver = new ServiceStateBroadcastReceiver();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.results);
    }

    @Override
    public void onStart() {
        super.onStart();

        //show progress if service is running
        setSupportProgressBarIndeterminateVisibility(Utils.isServiceRunning(this, NetworkWorkerService.class));

        //register receiver from network worker service
        LocalBroadcastManager.getInstance(this).registerReceiver(serviceStateBroadcastReceiver, new IntentFilter(NetworkWorkerService.START_ACTION));
        LocalBroadcastManager.getInstance(this).registerReceiver(serviceStateBroadcastReceiver, new IntentFilter(NetworkWorkerService.STOP_ACTION));
        LocalBroadcastManager.getInstance(this).registerReceiver(serviceStateBroadcastReceiver, new IntentFilter(NetworkWorkerService.MESSAGE_ACTION));
    }

    @Override
    public void onStop() {
        super.onStop();

        //unregister receiver from network worker service
        LocalBroadcastManager.getInstance(this).unregisterReceiver(serviceStateBroadcastReceiver);

        //hide progress
        setSupportProgressBarIndeterminateVisibility(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //add "settings" and "update" menu items
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
        getContentResolver().delete(ResultsProviderMetadata.RESULTS_URI, null, null);

        //"ask" service to load new race results
        Intent intent = new Intent(this, NetworkWorkerService.class);
        intent.putExtra(NetworkWorkerService.ACTION_CODE_EXTRA, NetworkWorkerService.LOAD_RESULTS_ACTION_CODE);
        intent.putExtra(NetworkWorkerService.DATA_FORMAT_EXTRA, SettingsPreferenceHelper.getLoadFormat(this));
        startService(intent);
    }

    private void showSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private class ServiceStateBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (NetworkWorkerService.START_ACTION.equals(action)) {
                setSupportProgressBarIndeterminateVisibility(true);
            } else if (NetworkWorkerService.STOP_ACTION.equals(action)) {
                setSupportProgressBarIndeterminateVisibility(false);
            } else if (NetworkWorkerService.MESSAGE_ACTION.equals(action)) {
                //just show toast with message
                Toast.makeText(ResultsActivity.this, intent.getStringExtra(NetworkWorkerService.MESSAGE_TEXT_EXTRA),
                               Toast.LENGTH_SHORT).show();
            }
        }
    }
}
