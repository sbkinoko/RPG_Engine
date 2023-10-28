package com.sbkinoko.sbkinokorpg.mapframe.npc;

import static com.sbkinoko.sbkinokorpg.gameparams.GameParams.X_axis;

import com.sbkinoko.sbkinokorpg.gameparams.Axis;
import com.sbkinoko.sbkinokorpg.mapframe.npc.eventdata.EventData;

public class NPCData {
    private final double[] point = new double[2];
    private final double size;
    private final int flagID;
    private final EventData[] eventData;
    private final boolean isMove;
    private final NPC_Move npcMove;


    public NPCData(double pointY, double pointX, double size, int flagID, EventData[] eventData) {
        this.point[X_axis] = pointX;
        this.point[Axis.Y.id] = pointY;
        npcMove = null;
        this.size = size;
        this.flagID = flagID;
        this.eventData = eventData;
        this.isMove = false;
    }

    public NPCData(double pointY, double pointX, double size, int flagID, NPC_Move npcMove,
                   EventData[] eventData) {
        this.point[X_axis] = pointX;
        this.point[Axis.Y.id] = pointY;

        this.size = size;
        this.flagID = flagID;
        this.eventData = eventData;
        isMove = true;
        this.npcMove = npcMove;
    }

    public double getSize() {
        return size;
    }

    public double[] getPoint() {
        return point;
    }

    public int getFlagID() {
        return flagID;
    }

    public EventData[] getEventData() {
        return eventData;
    }

    public boolean isMove() {
        return isMove;
    }

    public NPC_Move getNpcMove() {
        return npcMove;
    }
}
