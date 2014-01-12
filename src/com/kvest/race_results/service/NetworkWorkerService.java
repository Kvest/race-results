package com.kvest.race_results.service;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import com.kvest.race_results.R;
import com.kvest.race_results.contentprovider.ResultsProviderMetadata;
import com.kvest.race_results.datamodel.RaceResult;
import com.kvest.race_results.datamodel.RunnerResult;
import com.kvest.race_results.datastorage.table.ResultsTable;
import com.kvest.race_results.network.NetworkRequestHelper;
import com.kvest.race_results.network.RaceResultsParser;
import com.kvest.race_results.network.RaceResultsParserFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Kvest
 * Date: 12.01.14
 * Time: 22:27
 * To change this template use File | Settings | File Templates.
 */
public class NetworkWorkerService extends IntentService {
    public static final String START_ACTION = "com.kvest.race_results.service.NetworkWorkerService.START_ACTION";
    public static final String STOP_ACTION = "com.kvest.race_results.service.NetworkWorkerService.STOP_ACTION";
    public static final String MESSAGE_ACTION = "com.kvest.race_results.service.NetworkWorkerService.MESSAGE_ACTION";
    public static final String MESSAGE_TEXT_EXTRA = "com.kvest.race_results.service.NetworkWorkerService.MESSAGE_TEXT";

    public static final String ACTION_CODE_EXTRA = "com.kvest.race_results.service.NetworkWorkerService.ACTION_CODE";
    public static final String DATA_FORMAT_EXTRA = "com.kvest.race_results.service.NetworkWorkerService.DATA_FORMAT";
    public static final int LOAD_RESULTS_ACTION_CODE = 0;

    public NetworkWorkerService() {
        super("NetworkWorkerService");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //notify that the service started to work
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(START_ACTION));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //notify that the service stopped to work
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(STOP_ACTION));
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        switch (intent.getIntExtra(ACTION_CODE_EXTRA, -1)) {
            case LOAD_RESULTS_ACTION_CODE : onLoadResults(intent); break;
        }
    }

    private void onLoadResults(Intent intent) {
        //extract data from intent
        int dataFormat = intent.getIntExtra(DATA_FORMAT_EXTRA, NetworkRequestHelper.LOAD_FORMAT_JSON);

        //load data
        String rawRaceResults = NetworkRequestHelper.loadRaceResults(dataFormat);
        if (rawRaceResults != null) {
            //get parser
            RaceResultsParser parser = RaceResultsParserFactory.getParser(dataFormat);
            if (parser == null) {
                messageNotification(getString(R.string.error_parsing_results));
                return;
            }

            //parse data
            RaceResult raceResult = parser.parse(rawRaceResults);
            if (raceResult != null) {
                saveRaceResults(raceResult);
            } else {
                messageNotification(getString(R.string.error_parsing_results));
            }
        } else {
            messageNotification(getString(R.string.error_loading_results));
        }
    }

    private void saveRaceResults(RaceResult raceResult) {
        //insert all results into content provider
        for (RunnerResult runnerResult : raceResult.Runners) {
            ContentValues values = new ContentValues(4);
            values.put(ResultsTable.NAME_COLUMN, runnerResult.Name);
            values.put(ResultsTable.TIME_COLUMN, runnerResult.Time);
            values.put(ResultsTable.AGE_COLUMN, runnerResult.Age);
            values.put(ResultsTable.GROUP_COLUMN, runnerResult.getAgeGroup());

            getContentResolver().insert(ResultsProviderMetadata.RESULTS_URI, values);
        }
    }

    private void messageNotification(String text) {
        Intent intent = new Intent(MESSAGE_ACTION);
        intent.putExtra(MESSAGE_TEXT_EXTRA, text);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
