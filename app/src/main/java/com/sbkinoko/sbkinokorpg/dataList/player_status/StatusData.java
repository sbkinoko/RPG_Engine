package com.sbkinoko.sbkinokorpg.dataList.player_status;

public class StatusData {
    private final int[] _skills;

    int[] getSkills() {
        return _skills;
    }

    private final int _hp;

    int getHp() {
        return _hp;
    }

    private final int _mp;

    int getMp() {
        return _mp;
    }

    private final int _atk;

    int getAtk() {
        return _atk;
    }

    private final int _def;

    int getDef() {
        return _def;
    }


    private final int _healMp;

    int getHealMp() {
        return _healMp;
    }


    private final int _speed;

    int getSpeed() {
        return _speed;
    }

    private final int _exp;

    int getExp() {
        return _exp;
    }

    StatusData(int hp, int mp,
               int atk, int def,
               int healMp, int speed,
               int[] skills, int exp) {
        _hp = hp;
        _mp = mp;
        _atk = atk;
        _def = def;
        _healMp = healMp;
        _speed = speed;
        _skills = skills;
        _exp = exp;
    }
}
