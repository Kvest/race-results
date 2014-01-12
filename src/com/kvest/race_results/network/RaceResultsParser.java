package com.kvest.race_results.network;

import com.kvest.race_results.datamodel.RaceResult;

/**
 * Created with IntelliJ IDEA.
 * User: Kvest
 * Date: 12.01.14
 * Time: 23:26
 * To change this template use File | Settings | File Templates.
 */
public interface RaceResultsParser {
    public RaceResult parse(String rawData);
}
