package com.sbkinoko.sbkinokorpg.dataList.player_status;

public class StatusDataBuilder {
    private int[] _skills;

    StatusDataBuilder setSkills(int[] skills) {
        _skills = skills;
        return this;
    }

    private int _hp;

    StatusDataBuilder setHP(int hp) {
        _hp = hp;
        return this;
    }

    private int _mp;

    StatusDataBuilder setMp(int mp) {
        _mp = mp;
        return this;
    }

    private int _atk;

    StatusDataBuilder setAtk(int atk) {
        _atk = atk;
        return this;
    }

    private int _def;

    StatusDataBuilder setDef(int def) {
        _def = def;
        return this;
    }

    private int _healMp;

    StatusDataBuilder setHealMp(int healMp) {
        _healMp = healMp;
        return this;
    }

    private int _speed;

    StatusDataBuilder setSpeed(int speed) {
        _speed = speed;
        return this;
    }

    private int _exp;

    StatusDataBuilder setExp(int exp) {
        _exp = exp;
        return this;
    }

    StatusData build() {
        return new StatusData(_hp, _mp, _atk, _def, _healMp, _speed, _skills, _exp);
    }
}
