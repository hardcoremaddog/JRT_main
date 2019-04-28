package com.javarush.task.task39.task3913;

import java.nio.file.Paths;
import java.util.Date;

public class Solution {
    public static void main(String[] args) {
        LogParser logParser = new LogParser(Paths.get("d:/logs/"));
        System.out.println(logParser.getNumberOfUniqueIPs(null, null));
        System.out.println(logParser.getIPsForUser("Dima Dmitrievich Morozko", null, null));
        System.out.println(logParser.getIPsForStatus(Status.ERROR, null, new Date()));
        System.out.println(logParser.getIPsForEvent(Event.DOWNLOAD_PLUGIN, null, null));
    }
}