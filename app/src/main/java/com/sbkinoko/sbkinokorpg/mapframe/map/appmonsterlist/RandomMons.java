package com.sbkinoko.sbkinokorpg.mapframe.map.appmonsterlist;

import com.sbkinoko.sbkinokorpg.mylibrary.ArrayToProb;

public class RandomMons implements AppMonster {
    ArrayToProb
            monsNum,
            monsID;

    public RandomMons(ArrayToProb monsNum, ArrayToProb monsID) {
        this.monsNum = monsNum;
        this.monsID = monsID;

    }

    @Override
    public int[] getMonsterIDs() {
        int _monsNum = monsNum.getDatum();
        int[] monsIDs = new int[_monsNum];
        for (int i = 0; i < _monsNum; i++) {
            monsIDs[i] = monsID.getDatum();
        }

        return monsIDs;
    }
}
