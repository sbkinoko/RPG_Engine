package com.sbkinoko.sbkinokorpg.dataList;

import static com.sbkinoko.sbkinokorpg.dataList.ResistanceDataList.defaultAtrResistance;
import static com.sbkinoko.sbkinokorpg.dataList.ResistanceDataList.defaultConditionResistance;
import static com.sbkinoko.sbkinokorpg.dataList.ResistanceDataList.fireResistance;
import static com.sbkinoko.sbkinokorpg.dataList.ResistanceDataList.paralyzeResistance;
import static com.sbkinoko.sbkinokorpg.dataList.ResistanceDataList.poisonResistance;

import com.sbkinoko.sbkinokorpg.R;
import com.sbkinoko.sbkinokorpg.battleframe.status.MonsterData;

public class List_Monster {

    public static final int
            RANDOM = 0,
            ORDER = 1;

    static MonsterData[] statusList = {
            new MonsterData("花", 10, 10, 15, 10, 7,
                    10, 7, new int[]{13, 13, 14}, R.drawable.monster_001_9, ORDER,
                    paralyzeResistance, defaultAtrResistance),
            new MonsterData("イモムシ", 5, 10, 3, 10, 1,
                    3, 3, new int[]{13}, R.drawable.monster_002_3, RANDOM,
                    defaultConditionResistance, defaultAtrResistance),
            new MonsterData("ちょう", 15, 10, 15, 10, 15,
                    15, 10, new int[]{13, 13, 14}, R.drawable.monster_003_1, RANDOM,
                    poisonResistance, fireResistance),
            new MonsterData("からなし", 15, 10, 8, 10, 5,
                    5, 5, new int[]{13, 14}, R.drawable.monster_005, RANDOM,
                    defaultConditionResistance, defaultAtrResistance),
            new MonsterData("かに", 15, 10, 8, 10, 5,
                    5, 5, new int[]{13, 14}, R.drawable.monster_006, RANDOM,
                    defaultConditionResistance, defaultAtrResistance)
    };

    static public boolean isBoss(int number) {
        return false;
    }


    static public MonsterData getDataAt(int monsterID) {
        return statusList[monsterID];
    }

    static public String getName(int monsterID) {
        return statusList[monsterID].getName();
    }

    static public int getHP(int monsterID) {
        return statusList[monsterID].getHP();
    }

    static public int getMP(int monsterID) {
        return statusList[monsterID].getMP();
    }

    static public int getATK(int monsterID) {
        return statusList[monsterID].getATK();
    }

    static public int getDEF(int monsterID) {
        return statusList[monsterID].getDEF();
    }

    static public int getSpeed(int monsterID) {
        return statusList[monsterID].getSPD();
    }

    static public int getExp(int monsterID) {
        return statusList[monsterID].getEXP();
    }

    static public int getPrize(int monsterID) {
        return statusList[monsterID].getPRIZE();
    }

    static public int getImage(int monsterID) {
        return statusList[monsterID].getImageID();
    }

    static public int[] getSkills(int monsterID) {
        return statusList[monsterID].getSkills();
    }

    public static int getSelectAction(int monsterID) {
        return statusList[monsterID].getActionSelectType();
    }

    public static int[] getConResistances(int monsterID) {
        return statusList[monsterID].getConResistances();
    }

    public static int[] getAtrResistances(int monsterID) {
        return statusList[monsterID].getAtrResistances();
    }
}
