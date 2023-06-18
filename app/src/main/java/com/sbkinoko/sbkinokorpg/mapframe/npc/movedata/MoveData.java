package com.sbkinoko.sbkinokorpg.mapframe.npc.movedata;

public class MoveData {
    int[] v;
    int frame;

    MoveData(int[] v, int frame) {
        this.v = v;
        this.frame = frame;
    }

    public int[] getV() {
        return v;
    }

    public int getFrame() {
        return frame;
    }
}
