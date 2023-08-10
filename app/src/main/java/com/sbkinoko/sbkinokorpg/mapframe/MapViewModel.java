package com.sbkinoko.sbkinokorpg.mapframe;

import android.content.Context;
import android.widget.FrameLayout;

import com.sbkinoko.sbkinokorpg.mapframe.npc.NPC;
import com.sbkinoko.sbkinokorpg.mapframe.npc.NPCData;
import com.sbkinoko.sbkinokorpg.mapframe.npc.NPCMatrix;
import com.sbkinoko.sbkinokorpg.repository.MyEntryPoints;

import dagger.hilt.EntryPoints;

public class MapViewModel {

    private final Context context;

    private final NPCMatrix npcMatrix;

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

    void resetNPC(NPCData[] npcData,
                  int[] playerMapPoint) {
        npcMatrix.remove();

        if (npcData == null) {
            return;
        }

        npcMatrix.setNpcList(
                npcData,
                playerMapPoint
        );
    }
}
