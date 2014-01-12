package com.kvest.race_results.network;

/**
 * Created with IntelliJ IDEA.
 * User: Kvest
 * Date: 12.01.14
 * Time: 23:32
 * To change this template use File | Settings | File Templates.
 */
public abstract class RaceResultsParserFactory {
    public static RaceResultsParser getParser(int format) {
        switch (format) {
            case NetworkRequestHelper.LOAD_FORMAT_JSON : return new JsonRaceResultsParser();
        }

        return null;
    }
}
