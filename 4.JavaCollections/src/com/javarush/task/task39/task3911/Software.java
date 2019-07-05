package com.javarush.task.task39.task3911;

import java.util.*;

public class Software {
    int currentVersion;

    private Map<Integer, String> versionHistoryMap = new LinkedHashMap<>();

    public void addNewVersion(int version, String description) {
        if (version > currentVersion) {
            versionHistoryMap.put(version, description);
            currentVersion = version;
        }
    }

    public int getCurrentVersion() {
        return currentVersion;
    }

    public Map<Integer, String> getVersionHistoryMap() {
        return Collections.unmodifiableMap(versionHistoryMap);
    }

    public boolean rollback(int rollbackVersion) {
        if (versionHistoryMap.containsKey(rollbackVersion)) {
            Iterator<Integer> it = versionHistoryMap.keySet().iterator();
            while (it.hasNext()) {
                if (it.next().equals(rollbackVersion))
                    break;
            }

            while (it.hasNext()) {
                it.next();
                it.remove();
            }

            currentVersion = rollbackVersion;
            return true;
        } else {
            return false;
        }
    }
}
