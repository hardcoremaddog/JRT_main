package com.javarush.task.task39.task3913;

import java.nio.file.Paths;
import java.sql.Date;

public class Solution {
    public static void main(String[] args) {
        LogParser logParser = new LogParser(Paths.get("d:/logs/"));

        //IPQUeryTest
//        System.out.println(logParser.getNumberOfUniqueIPs(null, null));
//        System.out.println(logParser.getIPsForUser("Dima Dmitrievich Morozko", null, null));
//        System.out.println(logParser.getIPsForStatus(Status.ERROR, null, new Date()));
//        System.out.println(logParser.getIPsForEvent(Event.DONE_TASK, null, null));

        //UserQueryTest
//        System.out.println(logParser.getAllUsers());
//        System.out.println(logParser.getNumberOfUsers(null, null));
//        System.out.println(logParser.getNumberOfUserEvents("Dima Dmitrievich Morozko", null, null));
//        System.out.println(logParser.getUsersForIP("146.34.15.5", null, null));
//        System.out.println(logParser.getLoggedUsers(null, null));
//        System.out.println(logParser.getSolvedTaskUsers(null, null, 1));

        //DataQueryTest
//        System.out.println(logParser.getDatesForUserAndEvent("Vasya Pupkin", Event.SOLVE_TASK, null, null));
//        System.out.println(logParser.getDatesWhenSomethingFailed(null, null));
//        System.out.println(logParser.getDateWhenUserLoggedFirstTime("Peduard Petrovich Morozko", null, null));
//        System.out.println(logParser.getDateWhenUserSolvedTask("Peduard Petrovich Morozko", 24, null, null));
//        System.out.println(logParser.getDateWhenUserDoneTask("Peduard Petrovich Morozko", 24, null, null));
//        System.out.println(logParser.getDatesWhenUserWroteMessage("Peduard Petrovich Morozko",null, null));

        //QLQueryTest
    }
}