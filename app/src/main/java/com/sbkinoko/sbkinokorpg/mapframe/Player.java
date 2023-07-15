package com.sbkinoko.sbkinokorpg.mapframe;

import static com.sbkinoko.sbkinokorpg.GameParams.X_axis;
import static com.sbkinoko.sbkinokorpg.GameParams.Y_axis;
import static com.sbkinoko.sbkinokorpg.GameParams.whereBattle;
import static com.sbkinoko.sbkinokorpg.GameParams.whereMap;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.sbkinoko.sbkinokorpg.GameParams;
import com.sbkinoko.sbkinokorpg.MainGame;
import com.sbkinoko.sbkinokorpg.OptionConst;
import com.sbkinoko.sbkinokorpg.R;
import com.sbkinoko.sbkinokorpg.controller.ControllerFrame;
import com.sbkinoko.sbkinokorpg.dataList.item.List_Tool;
import com.sbkinoko.sbkinokorpg.mapframe.event.MapEventID;
import com.sbkinoko.sbkinokorpg.mapframe.map.mapdata.MapData;
import com.sbkinoko.sbkinokorpg.repository.BagRepository;

import java.util.Arrays;

public class Player {
    private final int cellLength;
    private int imageType;

    final double cvSize;
    final double prm1;
    int movedDistSum = 0;

    private final int
            imageDown1 = R.drawable.player_0100;

    private final Resources res;

    private final int playerSize;

    public int getPlayerSize() {
        return playerSize;
    }

    private final ImageView imageView;

    public ImageView getImageView() {
        return this.imageView;
    }

    private final TextView touchActionView;

    public TextView getTouchActionView() {
        return this.touchActionView;
    }

    private int[][] haveItem;
    private final int[][] EQP = new int[][]{
            {1, 1},
            {2, 1},
            {3, 1},
            {4, 1},
            {5, 1},
            {6, 1},
    };

    public ControllerFrame controllerFrame;

    public void setButtonsFrame(ControllerFrame controllerFrame1) {
        this.controllerFrame = controllerFrame1;
    }

    public MapFrame mapFrame;

    public void setMapFrame(MapFrame mapFrame1) {
        this.mapFrame = mapFrame1;
    }

    public Player(int cellLength, Context context) {
        this.v[Y_axis] = 0;
        this.v[X_axis] = 0;
        imageType = 0;
        this.playerSize = (int) (cellLength * GameParams.playerSize);

        imageView = new ImageView(context);
        imageView.setImageResource(imageDown1);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                playerSize,
                playerSize
        ));
        res = context.getResources();

        imageView.setOnTouchListener(new PlayerImageTouchListener());

        this.where = GameParams.whereMap;

        this.cellLength = cellLength;

        cvSize = 1 + 2 * GameParams.actionOffset;
        prm1 = (GameParams.actionOffset) / cvSize;

        touchActionView = new TextView(context);
        touchActionView.setLayoutParams(new ViewGroup.LayoutParams(
                playerSize,
                playerSize
        ));
        touchActionView.setOnTouchListener(new PlayerImageTouchListener());

    }

    public void reDraw() {
        if (OptionConst.collisionDrawFlag) {
            imageView.setBackground(ResourcesCompat.getDrawable
                    (res, R.drawable.character_frame, null));
        } else {
            imageView.setBackground(null);
        }
    }

    /**
     * 移動をしようとした際に移動できないとfalse
     */
    boolean[] canMoveDir = {true, true};

    public boolean[] getCanMoveDir() {
        return canMoveDir;
    }

    /**
     * trueを入れる
     */
    public void resetCanMoveDir() {
        canMoveDir = new boolean[]{true, true};
    }

    /**
     * @param axis axis方向には移動できない
     */
    public void setCanNotMove_Axis(int axis) {
        canMoveDir[axis] = false;
    }

    /**
     * 次のイベントがあればtrue
     */
    boolean nextEventFlag = false;

    public void setNextEventFlag(boolean flag) {
        nextEventFlag = flag;
    }

    /**
     * 次のイベントがあればtrueを返す。ただしtrueを返したらイベントフラッグを消費する→一回trueを返したらもう返さない
     *
     * @return 次のイベントがあればtrue
     */
    public boolean isNextEventFlag() {
        if (nextEventFlag) {
            nextEventFlag = false;
            return true;
        }
        return false;
    }


    private final int[] eventFlag = new int[MapData.eventFlagNum];

    public void setEventFlag(int[] nextEventInfo) {
        eventFlag[nextEventInfo[0]] = nextEventInfo[1];
    }

    public int getEventFlag(int flagID) {
        return eventFlag[flagID];
    }

    private boolean canAction = false;

    public void setCanAction(boolean flag) {
        this.canAction = flag;
    }

    public boolean canAction() {
        return this.canAction;
    }

    private MapEventID actionType = MapEventID.Non;

    public void setActionType(MapEventID actionType) {
        this.actionType = actionType;
    }

    public MapEventID getActionType() {
        return this.actionType;
    }

    int talkNPC = -1;

    public void setTalkNPC(int npcId) {
        talkNPC = npcId;
    }

    public int getTalkNPC() {
        return talkNPC;
    }

    private int[] v = new int[2];

    public void setV(int[] V) {
        if (!autoMovingFlag) {
            this.v = V;
        }
    }

    public int[] getV() {
        return v;
    }

    /**
     * playerがアクションを起こすセル
     */
    private int[] actionCell = new int[2];

    public void setActionCell(int[] actionCell1) {
        this.actionCell = actionCell1;
    }

    public int[] getActionCell() {
        return actionCell;
    }

    /**
     * playerが斜めに入ったときに衝突するセル番号
     * 最後にもう一度判断するように取っておく
     */
    private int[] reCheckCell = new int[2];

    public void setReCheckCell(int[] reCheckCell1) {
        this.reCheckCell = reCheckCell1;
    }

    public int[] getReCheckCell() {
        return reCheckCell;
    }

    /**
     * 今いるセルに対する相対位置
     */
    private float[] relativePoint;

    public void setRelativePoint(float[] relativePoint) {
        this.relativePoint = relativePoint;
    }

    private final int[][] points = new int[2][2],
            collisionPoints = new int[2][2];

    public void goCenter() {
        canMove = false;
        movedFlag = false;
        points[Y_axis][0] = (int) (cellLength * (((GameParams.visibleCellNum - 1) / 2) + relativePoint[Y_axis] / cellLength));//Xの位置*cellの長さ+  1/2cellの長さ
        points[X_axis][0] = (int) (cellLength * (((GameParams.visibleCellNum - 1) / 2) + relativePoint[X_axis] / cellLength));
        collisionPoints[Y_axis][0] = points[Y_axis][0];
        collisionPoints[X_axis][0] = points[X_axis][0];
        nextBGC[Y_axis] = (GameParams.visibleCellNum - 1) / 2;
        nextBGC[X_axis] = (GameParams.visibleCellNum - 1) / 2;
        BGC[Y_axis] = (GameParams.visibleCellNum - 1) / 2;
        BGC[X_axis] = (GameParams.visibleCellNum - 1) / 2;

        moveImage();
    }

    public int[][] getPoints() {
        return points;
    }

    public void setCollisionPoints(int[] v) {
        collisionPoints[Y_axis][0] = points[Y_axis][0] + v[Y_axis];
        collisionPoints[Y_axis][1] = collisionPoints[Y_axis][0] + playerSize;
        collisionPoints[X_axis][0] = points[X_axis][0] + v[X_axis];
        collisionPoints[X_axis][1] = collisionPoints[X_axis][0] + playerSize;
    }

    public int[][] getCollisionPoints() {
        return collisionPoints;
    }

    public int[] getCenter() {
        int[] center = new int[2];
        center[X_axis] = points[X_axis][0] + playerSize / 2;
        center[Y_axis] = points[Y_axis][0] + playerSize / 2;
        return center;
    }

    public int getAfterCenterX() {
        return collisionPoints[X_axis][0] + playerSize / 2;
    }

    public int getAfterCenterY() {
        return collisionPoints[Y_axis][0] + playerSize / 2;
    }

    private int dir = 0;

    private void setDir() {
        if (v[Y_axis] >= 0 && Math.abs(v[X_axis]) <= Math.abs(v[Y_axis])) {
            dir = GameParams.dir_down;
        } else if (v[X_axis] >= 0 && Math.abs(v[X_axis]) >= Math.abs(v[Y_axis])) {
            dir = GameParams.dir_right;
        } else if (v[X_axis] <= 0 && Math.abs(v[X_axis]) >= Math.abs(v[Y_axis])) {
            dir = GameParams.dir_left;
        } else if (v[X_axis] <= 0 && Math.abs(v[X_axis]) <= Math.abs(v[Y_axis])) {
            dir = GameParams.dir_up;
        }

        if (OptionConst.collisionDrawFlag && canAction) {
            this.touchActionView.setBackgroundResource(R.drawable.character_frame);
        } else {
            this.touchActionView.setBackgroundResource(0);
        }

        switch (dir) {
            case GameParams.dir_right:
                this.touchActionView.setX(points[X_axis][0] + playerSize);
                this.touchActionView.setY(points[Y_axis][0]);
                break;
            case GameParams.dir_down:
                this.touchActionView.setX(points[X_axis][0]);
                this.touchActionView.setY(points[Y_axis][0] + playerSize);
                break;
            case GameParams.dir_left:
                this.touchActionView.setX(points[X_axis][0] - playerSize);
                this.touchActionView.setY(points[Y_axis][0]);
                break;
            case GameParams.dir_up:
                this.touchActionView.setX(points[X_axis][0]);
                this.touchActionView.setY(points[Y_axis][0] - playerSize);
                break;
        }
    }

    public int getDir() {
        return this.dir;
    }

    private boolean isAllPartIn = true,
            nextAllInFlag = true;

    public void setNextAllInFlag(boolean flag) {
        nextAllInFlag = flag;
    }

    public boolean isAllPartIn() {
        return isAllPartIn;
    }

    private boolean canMove = false;

    public boolean canMove() {
        return canMove;
    }

    public void setCanMove(boolean flag) {
        canMove = flag;
    }


    private boolean movedFlag = false;

    public void resetMovedFlag() {
        movedFlag = false;
    }

    public boolean isMoved() {
        return movedFlag;
    }

    private int where;

    public void setInMap() {
        where = whereMap;
        resetMovedFlag();
    }

    public boolean isInMap() {
        return where == whereMap;
    }

    public void setInBattle() {
        where = whereBattle;
        setCanMove(false);
    }

    public boolean isInBattle() {
        return where == GameParams.whereBattle;
    }

    public int getWhere() {
        return this.where;
    }

    private int money;

    public void setMoney(int money) {
        this.money = money;
    }

    public int getMoney() {
        Log.d("msg", "player money" + money);
        return money;
    }

    public void addMoney(int money) {
        this.money += money;
    }

    public void decMoney(int money) {
        this.money -= money;
    }


    /**
     * 移動状況
     */
    private int moveState = GameParams.MoveState_Ground;

    public int getMoveState() {
        return this.moveState;
    }

    public void setMoveState(int moveState) {
        this.moveState = moveState;
        setImageForMoveState();
    }

    int[] distanceToGoal;
    boolean autoMovingFlag;

    public void setDistanceToGoal(int[] distToGaol) {
        this.distanceToGoal = distToGaol;
        Arrays.fill(canMoveDir, true);
        this.autoMovingFlag = true;
    }

    public boolean getAutoMovingFlag() {
        return autoMovingFlag;
    }

    public void decDistToGoal() {
        int tmpDir;
        for (int i = 0; i < 2; i++) {
            tmpDir = Integer.compare(distanceToGoal[i], 0);
            if ((distanceToGoal[i] - v[i]) * tmpDir > 0) {
                distanceToGoal[i] -= v[i];
            } else {
                v[i] = distanceToGoal[i];
                distanceToGoal[i] = 0;
            }
        }

        //goalまでの距離が0になったので移動を停止
        if (distanceToGoal[X_axis] == 0 && distanceToGoal[Y_axis] == 0) {
            autoMovingFlag = false;
            canMove = false;
            setCollisionPoints(v);
        }
    }

    public static final int MAX_ITEM_NUM = 99;

    public void addItem(int ItemNumber, int quantity) {
        BagRepository.getBagRepository().addTool(ItemNumber, quantity);
    }

    public boolean haveManyItem(int itemID) {
        for (int i = 0; i < new List_Tool().getItemNum(); i++) {
            if (haveItem[i][0] == itemID) {
                return MAX_ITEM_NUM <= haveItem[i][1];
            }

            if (haveItem[i][0] == 0) {
                break;
            }
        }
        return false;
    }


    public void decItem(int ItemNumber, int quantity) {
        int i;
        int itemNum = new List_Tool().getItemNum();
        for (i = 0; i < itemNum; i++) {
            if (haveItem[i][0] == ItemNumber) {
                haveItem[i][1] -= quantity;
                break;
            }
        }
        if (haveItem[i][0] != 0) {
            return;
        }

        for (; i < itemNum - 1; i++) {
            haveItem[i][0] = haveItem[i + 1][0];
            haveItem[i][1] = haveItem[i + 1][1];
            if (haveItem[i + 1][0] == 0) {
                return;
            }
        }
        haveItem[itemNum - 1][0] = 0;
        haveItem[itemNum - 1][1] = 0;
    }


    public void addAllItem() {
        for (int i = 0; i < new List_Tool().getItemNum(); i++) {
            addItem(i, 10);
        }
    }

    public void resetItem() {
        for (int[] ints : haveItem) {
            Arrays.fill(ints, 0);
        }
    }

    public int[][] getHaveItem() {
        return haveItem;
    }

    public void addEQP(int EQP_ID, int num) {
        if (EQP_ID == 1) {
            return;
        }

        for (int i = 0; i < EQP.length; i++) {
            if (EQP[i][0] == EQP_ID) {
                EQP[i][1] += num;
                break;
            }

            if (EQP[i][0] == 0) {
                EQP[i][0] = EQP_ID;
                EQP[i][1] = num;
            }
        }
    }

    public void decEQP(int eqpID, int num) {
        if (eqpID == 1) {
            return;
        }

        for (int i = 0; i < EQP.length; i++) {
            if (EQP[i][0] != eqpID) {
                continue;
            }
            EQP[i][1] -= num;
            if (EQP[i][1] == 0) {
                for (int j = i; j < EQP.length - 1; j++) {
                    if (EQP[j][0] == 0) {
                        break;
                    }
                    EQP[j][0] = EQP[j + 1][0];
                    EQP[j][1] = EQP[j + 1][1];
                }
                EQP[EQP.length - 1][0] = 0;
                EQP[EQP.length - 1][1] = 0;
            }
            return;
        }
    }

    public int[][] getHaveEQP() {
        return EQP;
    }


    private void setImageForMoveState() {
        switch (moveState) {
            case GameParams.MoveState_Ground:
                imageView.setBackground(ResourcesCompat.getDrawable
                        (res, R.drawable.character_frame_1, null));
                break;
            case GameParams.MoveState_Water:
                imageView.setBackground(ResourcesCompat.getDrawable
                        (res, R.drawable.character_frame_2, null));
                break;
            case GameParams.MoveState_Sky:
                imageView.setBackground(ResourcesCompat.getDrawable
                        (res, R.drawable.character_frame_3, null));
                break;
        }
    }

    private void moveCellPoint() {
        if (!movedFlag) {
            if (BGC[X_axis] != nextBGC[X_axis] || BGC[Y_axis] != nextBGC[Y_axis]) {
                movedFlag = true;
            }
        }
        System.arraycopy(nextBGC, 0, this.BGC, 0, BGC.length);
    }

    public void moveInMap(int[] moveDist) {
        setCollisionPoints(moveDist);//実際に動いた距離を入れる
        moveImage();

        movedDistSum += (int) Math.sqrt(Math.pow(moveDist[X_axis], 2) + Math.pow(moveDist[Y_axis], 2));
    }

    public boolean isEncounterMons() {
        if (movedDistSum - GameParams.MONS_APP_DIST * MainGame.cellLength > 0) {
            movedDistSum -= GameParams.MONS_APP_DIST * MainGame.cellLength;
            return true;
        }
        return false;
    }

    public void moveImage() {
        points[X_axis][0] = collisionPoints[X_axis][0];
        points[X_axis][1] = collisionPoints[X_axis][1];
        points[Y_axis][0] = collisionPoints[Y_axis][0];
        points[Y_axis][1] = collisionPoints[Y_axis][1];
        isAllPartIn = nextAllInFlag;

        moveCellPoint();

        Log.d("msg", "CellY " + BGC[Y_axis] + " :CellX " + BGC[X_axis]);
        this.imageView.setX(points[X_axis][0]);
        this.imageView.setY(points[Y_axis][0]);

    }

    public void changeImage() {
        int
                imageDown2 = R.drawable.player_0101,
                imageUp1 = R.drawable.player_0102,
                imageUp2 = R.drawable.player_0103,
                imageLeft1 = R.drawable.player_0104,
                imageLeft2 = R.drawable.player_0105,
                imageRight1 = R.drawable.player_0106,
                imageRight2 = R.drawable.player_0107;

        setDir();

        switch (dir) {
            case GameParams.dir_right:
                switch (imageType) {
                    case 0:
                        imageView.setImageResource(imageRight1);
                        imageType = 1;
                        break;
                    case 1:
                        imageView.setImageResource(imageRight2);
                        imageType = 0;
                        break;
                }
                break;
            case GameParams.dir_down:
                switch (imageType) {
                    case 0:
                        imageView.setImageResource(imageDown1);
                        imageType = 1;
                        break;
                    case 1:
                        imageView.setImageResource(imageDown2);
                        imageType = 0;
                        break;
                }
                break;
            case GameParams.dir_left:
                switch (imageType) {
                    case 0:
                        imageView.setImageResource(imageLeft1);
                        imageType = 1;
                        break;
                    case 1:
                        imageView.setImageResource(imageLeft2);
                        imageType = 0;
                        break;
                }
                break;
            case GameParams.dir_up:
                switch (imageType) {
                    case 0:
                        imageView.setImageResource(imageUp1);
                        imageType = 1;
                        break;
                    case 1:
                        imageView.setImageResource(imageUp2);
                        imageType = 0;
                        break;
                }
                break;
        }

    }

    private final int[]
            BGC = new int[2],
            nextBGC = new int[2];

    public void setNextBackGroundCell(int[] nextBackGroundCell) {
        System.arraycopy(nextBackGroundCell, 0, nextBGC, 0, nextBGC.length);
    }

    public int[] getBackGroundCell() {
        return this.BGC;
    }

    private class PlayerImageTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent e) {

            if (!mapFrame.isAllMenuClosed()) {
                return false;
            }

            if (e.getAction() != MotionEvent.ACTION_DOWN) {
                return false;
            }

            if (canAction) {
                controllerFrame.useBtA(e);
            } else {
                controllerFrame.useBtM(e);
            }

            return true;
        }

    }

    public double[] getActionCollision() {
        double[] offset = {0, 0};
        //主人公の向いてる方向で場合分け
        switch (getDir()) {
            case GameParams.dir_right:
                offset[X_axis] = GameParams.actionOffset;
                break;
            case GameParams.dir_down:
                offset[Y_axis] = GameParams.actionOffset;
                break;
            case GameParams.dir_left:
                offset[X_axis] = -GameParams.actionOffset;
                break;
            case GameParams.dir_up:
                offset[Y_axis] = -GameParams.actionOffset;
                break;
        }
        return offset;
    }
}
