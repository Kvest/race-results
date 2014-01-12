package com.kvest.race_results.network;

import com.google.gson.Gson;
import com.kvest.race_results.datamodel.RaceResult;

/**
 * Created with IntelliJ IDEA.
 * User: Kvest
 * Date: 12.01.14
 * Time: 23:40
 * To change this template use File | Settings | File Templates.
 */
public class JsonRaceResultsParser implements RaceResultsParser {
    @Override
    public RaceResult parse(String rawData) {
        Gson gson = new Gson();
        return gson.fromJson(rawData, RaceResult.class);
    }
}
