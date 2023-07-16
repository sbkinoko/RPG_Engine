package com.sbkinoko.sbkinokorpg.dataList.player_status;

public class Status_2 extends JobStatus {
    public Status_2() {
        name = "魔法使い";

        STATUS_LIST = new StatusData[]{
                new StatusDataBuilder().setHP(10).setMp(100).setAtk(8).setDef(100)
                        .setHealMp(10).setSpeed(10).setExp(0).
                        setSkills(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,}).
                        build(),
                new StatusDataBuilder().setHP(20).setMp(100).setAtk(12).setDef(100)
                        .setHealMp(10).setSpeed(12).setExp(100).
                        setSkills(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                                11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22}).
                        build(),
                new StatusDataBuilder().setHP(30).setMp(100).setAtk(15).setDef(100)
                        .setHealMp(100).setSpeed(120).setExp(500).
                        setSkills(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                                11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22}).
                        build(),
        };
    }
}
