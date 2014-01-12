package com.kvest.race_results.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.RadioGroup;
import com.kvest.race_results.R;
import com.kvest.race_results.network.NetworkRequestHelper;
import com.kvest.race_results.utility.SettingsPreferenceHelper;

/**
 * Created with IntelliJ IDEA.
 * User: Kvest
 * Date: 12.01.14
 * Time: 22:00
 * To change this template use File | Settings | File Templates.
 */
public class SettingsActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        init();
    }

    private void init() {
        //format of the data loading
        RadioGroup loadingFormatGroup = ((RadioGroup)findViewById(R.id.loading_format));
        switch (SettingsPreferenceHelper.getLoadFormat(this)) {
            case NetworkRequestHelper.LOAD_FORMAT_JSON :
                loadingFormatGroup.check(R.id.json_format);
                break;
            case NetworkRequestHelper.LOAD_FORMAT_XML :
                loadingFormatGroup.check(R.id.xml_format);
                break;
            case NetworkRequestHelper.LOAD_FORMAT_TXT :
                loadingFormatGroup.check(R.id.txt_format);
                break;
        }

        loadingFormatGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.json_format :
                        SettingsPreferenceHelper.setLoadFormat(SettingsActivity.this, NetworkRequestHelper.LOAD_FORMAT_JSON);
                        break;
                    case R.id.xml_format :
                        SettingsPreferenceHelper.setLoadFormat(SettingsActivity.this, NetworkRequestHelper.LOAD_FORMAT_XML);
                        break;
                    case R.id.txt_format :
                        SettingsPreferenceHelper.setLoadFormat(SettingsActivity.this, NetworkRequestHelper.LOAD_FORMAT_TXT);
                        break;
                }
            }
        });
    }
}
