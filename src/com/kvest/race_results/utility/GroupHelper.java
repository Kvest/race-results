package com.kvest.race_results.utility;

/**
 * Created with IntelliJ IDEA.
 * User: Kvest
 * Date: 12.01.14
 * Time: 10:14
 * To change this template use File | Settings | File Templates.
 */
public abstract class GroupHelper {
    public static final int FIRST_GROUP = 0;
    public static final int SECOND_GROUP = 1;
    public static final int THIRD_GROUP = 2;

    public static final int FIRST_GROUP_MAX_AGE = 15;
    public static final int SECOND_GROUP_MAX_AGE = 29;

    public static int ageToGroup(int age) {
        if (age <= FIRST_GROUP_MAX_AGE) {
            return FIRST_GROUP;
        } else if (age <= SECOND_GROUP_MAX_AGE) {
            return SECOND_GROUP;
        } else {
            return THIRD_GROUP;
        }
    }
}
