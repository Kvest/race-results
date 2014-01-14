package com.kvest.race_results.network;

import com.kvest.race_results.datamodel.RaceResult;
import com.kvest.race_results.datamodel.RunnerResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: roman
 * Date: 1/14/14
 * Time: 10:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class TxtRaceResultsParser implements RaceResultsParser {
    private static final Pattern RESULT_PATTERN = Pattern.compile("([a-zA-Z]+)\\s+(\\d+)\\s+(\\d+)");
    private static final int NAME_GROUP = 1;
    private static final int TIME_GROUP = 2;
    private static final int AGE_GROUP = 3;

    public RaceResult parse(String rawData) {
        RaceResult result = new RaceResult();

        Matcher matcher = RESULT_PATTERN.matcher(rawData);
        while (matcher.find()) {
            RunnerResult runnerResult = new RunnerResult();
            runnerResult.Name = matcher.group(NAME_GROUP);
            runnerResult.Time = Integer.parseInt(matcher.group(TIME_GROUP));
            runnerResult.Age = Integer.parseInt(matcher.group(AGE_GROUP));

            result.Runners.add(runnerResult);
        }

        return result;
    }
}
