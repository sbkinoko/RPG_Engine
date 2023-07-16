package com.sbkinoko.sbkinokorpg.dataList.player_status;

import com.sbkinoko.sbkinokorpg.battleframe.status.PlayerStatus;
import com.sbkinoko.sbkinokorpg.dataList.ResistanceDataList;

public abstract class JobStatus {
    String name;

    public String getName() {
        return name;
    }

    public StatusData[] STATUS_LIST;

    private int[] skills;

    public int getHP(int lv) {
        return STATUS_LIST[lv - 1].getHp();
    }

    public int getMP(int lv) {
        return STATUS_LIST[lv - 1].getMp();
    }

    public int getATK(int lv) {
        return STATUS_LIST[lv - 1].getAtk();
    }

    public int getDEF(int lv) {
        return STATUS_LIST[lv - 1].getDef();
    }

    public int getHealMP(int lv) {
        return STATUS_LIST[lv - 1].getHealMp();
    }

    public int getSpeed(int lv) {
        return STATUS_LIST[lv - 1].getSpeed();
    }

    public int[] getSkill(int LV) {
        return STATUS_LIST[LV - 1].getSkills();
    }

    //todo レベル最大のときの処理を考える
    public int getNeedEXP(int lv) {
        if (STATUS_LIST.length <= lv - 1) {
            return Integer.MAX_VALUE;
        }
        return STATUS_LIST[lv - 1].getExp();
    }

    public int[] getConditionResistance(int lv) {
        return ResistanceDataList.defaultConditionResistance;
    }

    public boolean canChangeJob(PlayerStatus playerStatus) {
        return true;
    }

}
