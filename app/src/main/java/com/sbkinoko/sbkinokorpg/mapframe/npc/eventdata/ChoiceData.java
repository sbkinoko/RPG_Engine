package com.sbkinoko.sbkinokorpg.mapframe.npc.eventdata;

public class ChoiceData {
    final int afterID;
    final String txt;

    public ChoiceData(int afterID, String txt) {
        this.afterID = afterID;
        this.txt = txt;
    }

    public int getAfterID() {
        return afterID;
    }

    public String getTxt() {
        return txt;
    }
}
