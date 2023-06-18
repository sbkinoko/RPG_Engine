package com.sbkinoko.sbkinokorpg.battleframe.status.resistance;

public class ResistanceList {
    Resistance[] resistances;

    public ResistanceList(int[] resistList) {
        int length = resistList.length;
        resistances = new Resistance[length];
        for (int i = 0; i < length; i++) {
            resistances[i] = new Resistance(resistList[i]);
        }
    }

    public Resistance getResistance(int id) {
        return resistances[id];
    }
}
