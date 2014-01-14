package com.kvest.race_results.datamodel;

import com.kvest.race_results.utility.AgeGroupHelper;

/**
 * Created with IntelliJ IDEA.
 * User: Kvest
 * Date: 12.01.14
 * Time: 23:29
 * To change this template use File | Settings | File Templates.
 */
public class RunnerResult {
    public String Name;
    public int Time;
    public int Age;

    public RunnerResult() {
        Name = "";
        Time = -1;
        Age = -1;
    }

    public int getAgeGroup() {
        return AgeGroupHelper.ageToGroup(Age);
    }
}
