package com.sbkinoko.sbkinokorpg.mapframe.npc;

import static com.sbkinoko.sbkinokorpg.gameparams.GameParams.X_axis;

import com.sbkinoko.sbkinokorpg.OptionConst;
import com.sbkinoko.sbkinokorpg.gameparams.Axis;
import com.sbkinoko.sbkinokorpg.mapframe.npc.movedata.MoveData;

public class NPC_Move {
    private int frameCount = 0;
    private int movePattern = 0;
    private final int[][] vList;
    private final int[] frames;
    private final int MOVE_PATTERN_NUM;

    public NPC_Move(MoveData[] moveData) {
        MOVE_PATTERN_NUM = moveData.length;
        vList = new int[MOVE_PATTERN_NUM][2];
        frames = new int[MOVE_PATTERN_NUM];

        for (int i = 0; i < MOVE_PATTERN_NUM; i++) {
            vList[i][X_axis] = (int) (moveData[i].getV()[X_axis] * OptionConst.V * 0.8);
            vList[i][Axis.Y.id] = (int) (moveData[i].getV()[Axis.Y.id] * OptionConst.V * 0.8);
            frames[i] = moveData[i].getFrame();
        }
    }

    public void reset() {
        movePattern = 0;
        frameCount = 0;
    }

    public void incCount() {
        frameCount++;
        if (frames[movePattern] <= frameCount) {
            movePattern++;
            if (MOVE_PATTERN_NUM <= movePattern) {
                movePattern = 0;
            }
            frameCount = 0;
        }
    }

    public int[] getV() {
        return vList[movePattern];
    }

}
