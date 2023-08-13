package com.sbkinoko.sbkinokorpg.mapframe.npc;

import android.content.Context;
import android.widget.FrameLayout;

import com.sbkinoko.sbkinokorpg.mapframe.collisionview.CollisionView;
import com.sbkinoko.sbkinokorpg.mapframe.event.MapEventID;
import com.sbkinoko.sbkinokorpg.mapframe.player.Player;

public class NPCMatrix {
    private NPC[] npcList;
    private boolean npcFlag;
    private final Player player;
    private final FrameLayout frameLayout;
    private final Context context;

    public NPCMatrix(Player player, Context context, FrameLayout frameLayout) {
        this.player = player;
        this.context = context;
        this.frameLayout = frameLayout;
    }

    public void setNpcList(NPCData[] npcData, int[] mapPoint) {
        int length = npcData.length;
        npcList = new NPC[length];
        npcFlag = true;

        for (int i = 0; i < length; i++) {
            npcList[i] = new NPC(player, frameLayout, context);
            npcList[i].addNPCImage(npcData[i].getSize());
            npcList[i].setData(npcData[i],
                    mapPoint
            );
        }
    }

    public void avoidPlayer() {
        if (!npcFlag) {
            return;
        }

        player.setCollisionPoints(new int[]{0, 0});

        for (NPC npc : npcList) {
            if (!npc.isMove()) {
                continue;
            }

            while (npc.isColliding()) {
                npc.move();
                npc.getNpcMove().incCount();
            }
        }
    }

    public NPC[] getNpcList() {
        return npcList;
    }

    public void remove() {
        npcFlag = false;
        if (npcList == null) {
            return;
        }

        for (NPC tmpNPC : npcList) {
            tmpNPC.remove();
        }
        npcList = null;
    }

    public void moveNPC(boolean isTextBoxOpen) {
        if (!npcFlag) {
            return;
        }

        for (int i = 0; i < npcList.length; i++) {
            if (!npcList[i].isMove()) {
                continue;
            }

            if (i == player.getTalkNPC()
                    && isTextBoxOpen) {
                continue;
            }

            player.setCollisionPoints(new int[]{0, 0});
            if (npcList[i].willBeColliding()) {
                continue;
            }

            npcList[i].move();
            npcList[i].getNpcMove().incCount();
        }
    }

    public void scrollNPC(boolean[] scroll) {
        if (!npcFlag) {//npcがいないので修了
            return;
        }
        int[] tmpV = {0, 0};
        for (int axis = 0; axis < 2; axis++) {
            if (player.getCanMoveDir()[axis] && scroll[axis]) {
                tmpV[axis] = player.getV()[axis];
            }
        }

        for (NPC npc : npcList) {
            npc.moveAccompaniedByPlayer(tmpV);
        }
    }

    public boolean isColliding(int id) {
        return npcList[id].getCv().isColliding();
    }

    /**
     * NPCを全部調べて衝突をチェック
     * 斜めには移動できないが、XYそれぞれに移動できる位置のNPCがいればそれの番号を返す
     *
     * @return 再検査するNPC_ID
     */
    public int getNPCCollision() {
        if (!npcFlag) {//NPCが表示されていない
            return -1;//誰とも再検査しなくてよい
        }
        for (int i = 0; i < npcList.length; i++) {
            CollisionView tmpCV = npcList[i].getCv();
            if (!tmpCV.isColliding()) {
                continue;//ぶつかっていないので次へ
            }

            if (npcList[i].getCv().cantMove()) {
                return i;//i番目と再検査
            }
        }
        return -1;//誰とも再検査しなくてよい
    }


    public boolean canAction() {
        if (!npcFlag) {
            return false;
        }

        for (int i = 0; i < npcList.length; i++) {
            if (npcList[i].getCv().isColliding()) {
                player.setActionType(MapEventID.MapAction_talk);
                player.setTalkNPC(i);
                return true;
            }
        }
        return false;
    }

    public NPC getTalkingNPC() {
        return npcList[player.getTalkNPC()];
    }

    public void reDraw() {
        if (npcList == null) {
            return;
        }

        for (NPC npc : npcList) {
            npc.reDraw();
        }

    }
}
