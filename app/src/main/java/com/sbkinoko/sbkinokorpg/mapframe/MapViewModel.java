package com.sbkinoko.sbkinokorpg.mapframe;

import android.content.Context;
import android.widget.FrameLayout;

import com.sbkinoko.sbkinokorpg.mapframe.map.mapdata.MapData;
import com.sbkinoko.sbkinokorpg.mapframe.map.mapdata.MapId;
import com.sbkinoko.sbkinokorpg.mapframe.npc.NPC;
import com.sbkinoko.sbkinokorpg.mapframe.npc.NPCData;
import com.sbkinoko.sbkinokorpg.mapframe.npc.NPCMatrix;
import com.sbkinoko.sbkinokorpg.mapframe.player.Player;
import com.sbkinoko.sbkinokorpg.application.MyEntryPoints;

import dagger.hilt.EntryPoints;

public class MapViewModel {

    private final Context context;

    private final NPCMatrix npcMatrix;

    private MapData nowMap;

    MapViewModel(Context context,
                 Player player,
                 FrameLayout frameLayout) {
        this.context = context;
        MyEntryPoints myEntryPoints = EntryPoints.get(
                context.getApplicationContext(),
                MyEntryPoints.class
        );

        npcMatrix = new NPCMatrix(player, context, frameLayout);

    }

    public NPCMatrix getNpcMatrix() {
        return npcMatrix;
    }

    public NPC[] getNpcList() {
        return npcMatrix.getNpcList();
    }

    public void checkNPCPosition() {
        npcMatrix.avoidPlayer();
    }

    void resetNPC(int[] playerMapPoint) {
        npcMatrix.remove();
        NPCData[] npcData = nowMap.getNpcData();

        if (npcData == null) {
            return;
        }

        npcMatrix.setNpcList(
                npcData,
                playerMapPoint
        );
    }

    public int getMapWidth() {
        return nowMap.getWidth();
    }

    public int getMapHeight() {
        return nowMap.getHeight();
    }

    public MapId getMapID() {
        return nowMap.getMapId();
    }

    void setNowMap(MapData mapData) {
        nowMap = mapData;
    }

    MapData getNowMap() {
        return nowMap;
    }
}
