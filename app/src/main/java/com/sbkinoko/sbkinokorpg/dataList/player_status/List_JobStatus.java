package com.sbkinoko.sbkinokorpg.dataList.player_status;


import com.sbkinoko.sbkinokorpg.battleframe.status.PlayerStatus;
import com.sbkinoko.sbkinokorpg.dataList.ResistanceDataList;

public abstract class List_JobStatus {

    String name;

    public String getName() {
        return name;
    }

    public int[][][] STATUS_LIST;

    public int getHP(int lv) {
        return STATUS_LIST[lv - 1][1][0];
    }

    public int getMP(int lv) {
        return STATUS_LIST[lv - 1][1][1];
    }

    public int getATK(int lv) {
        return STATUS_LIST[lv - 1][1][2];
    }

    public int getDEF(int lv) {
        return STATUS_LIST[lv - 1][1][3];
    }

    public int getHealMP(int lv) {
        return STATUS_LIST[lv - 1][1][4];
    }

    public int getSpeed(int lv) {
        return STATUS_LIST[lv - 1][1][5];
    }

    public int getNeedEXP(int lv) {
        if (STATUS_LIST.length <= lv - 1) {
            return Integer.MAX_VALUE;
        }
        return STATUS_LIST[lv - 1][0][0];
    }

    public int[] getSkill(int LV) {
        return STATUS_LIST[LV - 1][2];
    }

    public int[] getConditionResistance(int lv) {
        return ResistanceDataList.defaultConditionResistance;
    }

    public static final int JOB_NUM = 5;

    public static List_JobStatus getStatusList(int i) {
        switch (i) {
            case 1:
                return new Status_1();
            case 2:
                return new Status_2();
            case 3:
                return new Status_3();
            case 4:
                return new Status_4();
            case 5:
                return new Status_5();
        }
        return new Status_1();
    }

    public boolean canChangeJob(PlayerStatus playerStatus) {
        return true;
    }


}

