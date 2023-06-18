package com.sbkinoko.sbkinokorpg.mapframe.map.appmonsterlist;

public class FixMons implements AppMonster {
    int[] monsIDs;

    public FixMons(int[] monsIDs) {
        this.monsIDs = monsIDs;
    }

    @Override
    public int[] getMonsterIDs() {
        return monsIDs;
    }
}
