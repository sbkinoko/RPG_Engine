package com.sbkinoko.sbkinokorpg.mapframe.npc;

import static com.sbkinoko.sbkinokorpg.gameparams.GameParams.X_axis;
import static com.sbkinoko.sbkinokorpg.gameparams.GameParams.Y_axis;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.sbkinoko.sbkinokorpg.MainGame;
import com.sbkinoko.sbkinokorpg.OptionConst;
import com.sbkinoko.sbkinokorpg.R;
import com.sbkinoko.sbkinokorpg.gameparams.GameParams;
import com.sbkinoko.sbkinokorpg.mapframe.MapPoint;
import com.sbkinoko.sbkinokorpg.mapframe.collisionview.CollisionView;
import com.sbkinoko.sbkinokorpg.mapframe.map.bgcell.MapObjectEventData;
import com.sbkinoko.sbkinokorpg.mapframe.npc.eventdata.EventData;
import com.sbkinoko.sbkinokorpg.mapframe.player.Player;

public class NPC {
    private String name;
    private boolean nextEventFlag = false;
    private int nowEventID;
    private int usingFlagID;
    private int npcSize;
    private final Player player;
    private final FrameLayout frameLayout;
    private final Context context;
    private int
            frontImage1,
            frontImage2,
            frontImage3,
            frontImage4,
            backImage1,
            backImage2,
            backImage3,
            backImage4,
            leftImage1,
            leftImage2,
            leftImage3,
            leftImage4,
            rightImage1,
            rightImage2,
            rightImage3,
            rightImage4;

    private CollisionView cv;
    private ImageView iv;

    public NPC(Player player1, FrameLayout frameLayout1, Context context1) {
        player = player1;
        frameLayout = frameLayout1;
        context = context1;
    }

    public void addNPCImage(double npcSize_d) {
        iv = new ImageView(context);
        npcSize = (int) (MainGame.cellLength * GameParams.playerSize * npcSize_d);
        iv.setImageResource(R.drawable.npc_1_1_1);
        iv.setLayoutParams(new ViewGroup.LayoutParams(
                npcSize,
                npcSize
        ));
        frameLayout.addView(iv);

        cv = new CollisionView(context, MapObjectEventData.WallCollision, player);
        cv.setLayoutParams(
                npcSize
        );
        frameLayout.addView(cv);
    }

    NPCData npcData;
    EventData[] eventData;
    private int eventNum;

    public void setData(NPCData npcData, MapPoint mapPoint) {
        //name = (String) npcData[0][1][0];
        usingFlagID = npcData.getFlagID();
        eventNum = npcData.getEventData().length;
        eventData = npcData.getEventData();
        this.npcData = npcData;

        int[] point = new int[2];
        point[X_axis] = getNPCPoint(npcData.getPoint()[X_axis], mapPoint.getX());
        point[Y_axis] = getNPCPoint(npcData.getPoint()[Y_axis], mapPoint.getY());
        setPoint(point);
    }

    public EventData getActiveEvent() {
        Log.d("msg", "NPC eventFlag : ID " + usingFlagID + " step " + (player.getEventFlag(usingFlagID)));
        if (eventNum == 1) {
            return eventData[0];
        }

        int eventStep = player.getEventFlag(usingFlagID);
        for (int i = 0; i < eventNum - 1; i++) {
            if (eventData[i].getKeyStep() <= eventStep
                    && eventStep < eventData[i + 1].getKeyStep()) {
                if (eventStep == eventData[i].getKeyStep()) {
                    nextEventFlag = true;
                    nowEventID = i;
                }
                return eventData[i];
            }
        }
        if (eventStep == eventData[eventNum - 1].getKeyStep()) {
            nextEventFlag = true;
            nowEventID = eventNum - 1;
        }

        return eventData[eventNum - 1];
    }

    public int getUsingFlagID() {
        return usingFlagID;
    }

    public void remove() {
        if (isMove()) {
            npcData.getNpcMove().reset();
        }
        removeNPCImage();
    }

    public void removeNPCImage() {
        if (iv != null) {
            frameLayout.removeView(iv);
            frameLayout.removeView(cv);
        }
    }

    private void setPoint(int[] point) {
        iv.setX(point[X_axis]);
        cv.setX(point[X_axis]);
        iv.setY(point[Y_axis]);
        cv.setY(point[Y_axis]);
    }

    public void moveAccompaniedByPlayer(int[] v) {
        int[] point = new int[2];
        point[X_axis] = (int) iv.getX() - v[X_axis];
        point[Y_axis] = (int) iv.getY() - v[Y_axis];
        setPoint(point);
    }

    public CollisionView getCv() {
        return this.cv;
    }

    private int getNPCPoint(double relativePoint, int cellPoint) {
        int offset = (GameParams.visibleCellNum - 1) / 2;
        return (int) ((relativePoint - cellPoint + offset) * MainGame.cellLength - this.npcSize / 2);
    }

    public boolean isNextEventFlag() {
        if (nextEventFlag) {
            nextEventFlag = false;
            return true;
        }
        return false;
    }

    public boolean isMove() {
        return npcData.isMove();
    }

    public void move() {
        int[] point = new int[2];
        point[X_axis] = (int) iv.getX() + npcData.getNpcMove().getV()[X_axis];
        point[Y_axis] = (int) iv.getY() + npcData.getNpcMove().getV()[Y_axis];
        setPoint(point);
    }

    public boolean willBeColliding() {
        cv.setX((int) iv.getX() + npcData.getNpcMove().getV()[X_axis]);
        cv.setY((int) iv.getY() + npcData.getNpcMove().getV()[Y_axis]);
        boolean flag = cv.isColliding();
        cv.setX(iv.getX());
        cv.setY(iv.getY());
        return flag;
    }

    public boolean isColliding() {
        return cv.isColliding();
    }

    public NPC_Move getNpcMove() {
        return npcData.getNpcMove();
    }

    public void reDraw() {
        if (OptionConst.collisionDrawFlag) {
            cv.setVisibility(View.VISIBLE);
        } else {
            cv.setVisibility(View.GONE);
        }
    }
}
