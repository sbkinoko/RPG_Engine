package com.sbkinoko.sbkinokorpg.dataList.player_status;

public class Status_1 extends JobStatus {

    public Status_1() {
        name = "戦士";

        STATUS_LIST = new StatusData[]{
                new StatusDataBuilder().setHP(100).setMp(100).setAtk(10).setDef(100)
                        .setHealMp(10).setSpeed(10).setExp(0).
                        setSkills(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                                11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22}).
                        build(),
                new StatusDataBuilder().setHP(200).setMp(100).setAtk(10).setDef(100)
                        .setHealMp(10).setSpeed(10).setExp(100).
                        setSkills(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                                11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22}).
                        build(),
                new StatusDataBuilder().setHP(300).setMp(100).setAtk(10).setDef(100)
                        .setHealMp(10).setSpeed(10).setExp(500).
                        setSkills(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                                11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22}).
                        build(),
        };
    }
}
