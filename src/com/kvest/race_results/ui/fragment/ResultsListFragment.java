package com.kvest.race_results.ui.fragment;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import com.kvest.race_results.R;
import com.kvest.race_results.contentprovider.ResultsProviderMetadata;
import com.kvest.race_results.datastorage.table.ResultsTable;
import com.kvest.race_results.ui.adapter.ResultsListAdapter;

/**
 * Created with IntelliJ IDEA.
 * User: Kvest
 * Date: 12.01.14
 * Time: 13:59
 * To change this template use File | Settings | File Templates.
 */
public class ResultsListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String ORDER = "\"" + ResultsTable.TIME_COLUMN + "\" ASC";
    private static final int LOAD_RESULTS_ID = 0;

    private ResultsListAdapter adapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //don't show click on items
        getListView().setSelector(new ColorDrawable(Color.TRANSPARENT));
        getListView().setCacheColorHint(Color.TRANSPARENT);

        //create and set adapter
        String[] from = {ResultsTable.GROUP_COLUMN, ResultsTable.NAME_COLUMN, ResultsTable.TIME_COLUMN,
                         ResultsTable.AGE_COLUMN, ResultsTable.RANKING_COLUMN};
        int[] to = {R.id.root_container, R.id.name, R.id.time, R.id.age, R.id.ranking};
        adapter = new ResultsListAdapter(getActivity(), R.layout.results_list_item, null, from, to,
                                         ResultsListAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        setListAdapter(adapter);

        //load cursor
        getActivity().getSupportLoaderManager().initLoader(LOAD_RESULTS_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        switch (id) {
            case LOAD_RESULTS_ID : return new CursorLoader(getActivity(), ResultsProviderMetadata.RESULTS_URI,
                                                            ResultsTable.FULL_PROJECTION, null, null, ORDER);
        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        adapter.swapCursor(null);
    }
}
