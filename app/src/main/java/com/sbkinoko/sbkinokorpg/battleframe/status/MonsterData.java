package com.sbkinoko.sbkinokorpg.battleframe.status;

import com.sbkinoko.sbkinokorpg.mylibrary.ArrayToProb;

public class MonsterData {
    String name;
    int[] skills;

    int HP;
    int MP;
    int ATK;
    int DEF;
    int SPD;

    int EXP;
    int PRIZE;

    int actionSelectType;

    int imageID;

    int[] conResistances;
    int[] atrResistances;
    ArrayToProb dropItem;

    public MonsterData(String name, int HP, int MP, int ATK, int DEF, int SPD, int EXP, int PRIZE,
                       int[] skills, int imageID, int actionSelectType,
                       int[] conResistances, int[] atrResistances) {
        this.HP = HP;
        this.MP = MP;
        this.ATK = ATK;
        this.DEF = DEF;
        this.SPD = SPD;
        this.EXP = EXP;
        this.PRIZE = PRIZE;
        this.skills = skills;
        this.imageID = imageID;
        this.name = name;
        this.actionSelectType = actionSelectType;
        this.conResistances = conResistances;
        this.atrResistances = atrResistances;
        this.dropItem = new ArrayToProb(new int[][]{
                {50, 1}, {50, 0}
        });
    }

    public MonsterData(String name, int HP, int MP, int ATK, int DEF, int SPD, int EXP, int PRIZE,
                       int[] skills, int imageID, int actionSelectType,
                       int[] conResistances, int[] atrResistances,
                       ArrayToProb dropItem) {
        this.HP = HP;
        this.MP = MP;
        this.ATK = ATK;
        this.DEF = DEF;
        this.SPD = SPD;
        this.EXP = EXP;
        this.PRIZE = PRIZE;
        this.skills = skills;
        this.imageID = imageID;
        this.name = name;
        this.actionSelectType = actionSelectType;
        this.conResistances = conResistances;
        this.atrResistances = atrResistances;
        this.dropItem = dropItem;
    }

    public ArrayToProb getHaveItem() {
        return dropItem;
    }

    public String getName() {
        return name;
    }

    public int getHP() {
        return HP;
    }

    public int getMP() {
        return MP;
    }

    public int getATK() {
        return ATK;
    }

    public int getDEF() {
        return DEF;
    }

    public int getSPD() {
        return SPD;
    }

    public int getEXP() {
        return EXP;
    }

    public int getPRIZE() {
        return PRIZE;
    }

    public int getActionSelectType() {
        return actionSelectType;
    }

    public int getImageID() {
        return imageID;
    }

    public int[] getSkills() {
        return skills;
    }

    public int[] getConResistances() {
        return conResistances;
    }

    public int[] getAtrResistances() {
        return atrResistances;
    }
}
