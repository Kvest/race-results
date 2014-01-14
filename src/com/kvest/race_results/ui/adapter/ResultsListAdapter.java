package com.kvest.race_results.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import com.kvest.race_results.R;
import com.kvest.race_results.utility.AgeGroupHelper;

/**
 * Created with IntelliJ IDEA.
 * User: Kvest
 * Date: 12.01.14
 * Time: 15:28
 * To change this template use File | Settings | File Templates.
 */
public class ResultsListAdapter extends SimpleCursorAdapter implements SimpleCursorAdapter.ViewBinder {
    public ResultsListAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to);

        setViewBinder(this);
    }

    public ResultsListAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);

        setViewBinder(this);
    }

    @Override
    public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
        if (view.getId() == R.id.root_container) {
            //set background color depending on age group
            switch (cursor.getInt(columnIndex)) {
                case AgeGroupHelper.FIRST_GROUP :
                    view.setBackgroundResource(R.color.first_group_bg_color);
                    break;
                case AgeGroupHelper.SECOND_GROUP :
                    view.setBackgroundResource(R.color.second_group_bg_color);
                    break;
                case AgeGroupHelper.THIRD_GROUP :
                    view.setBackgroundResource(R.color.third_group_bg_color);
                    break;
            }

            return true;
        }

        return false;
    }
}
