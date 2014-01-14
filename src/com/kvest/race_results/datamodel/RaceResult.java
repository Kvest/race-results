package com.kvest.race_results.datamodel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Kvest
 * Date: 12.01.14
 * Time: 23:28
 * To change this template use File | Settings | File Templates.
 */
public class RaceResult {
    public String Name;
    public List<RunnerResult> Runners;

    public RaceResult() {
        Name = "";
        Runners = new ArrayList<RunnerResult>();
    }
}
