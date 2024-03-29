package com.sbkinoko.sbkinokorpg.mapframe.player;

import static com.sbkinoko.sbkinokorpg.gameparams.GameParams.whereBattle;
import static com.sbkinoko.sbkinokorpg.gameparams.GameParams.whereMap;

import android.content.Context;
import android.util.Log;

import com.sbkinoko.sbkinokorpg.MainGame;
import com.sbkinoko.sbkinokorpg.application.MyEntryPoints;
import com.sbkinoko.sbkinokorpg.dataList.item.List_Tool;
import com.sbkinoko.sbkinokorpg.gameparams.Axis;
import com.sbkinoko.sbkinokorpg.gameparams.BattleResult;
import com.sbkinoko.sbkinokorpg.gameparams.Dir;
import com.sbkinoko.sbkinokorpg.gameparams.GameParams;
import com.sbkinoko.sbkinokorpg.gameparams.MoveState;
import com.sbkinoko.sbkinokorpg.mapframe.event.MapEventID;
import com.sbkinoko.sbkinokorpg.mapframe.map.mapdata.MapData;
import com.sbkinoko.sbkinokorpg.mapframe.map.mapdata.MapId;
import com.sbkinoko.sbkinokorpg.repository.bagrepository.BagRepository;

import java.util.Arrays;

import dagger.hilt.EntryPoints;

public class Player {
    private final int cellLength;
    final double cvSize;
    final double prm1;
    int movedDistSum = 0;

    private final int playerSize;

    public int getPlayerSize() {
        return playerSize;
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

    BagRepository bagRepository;

    public Player(int cellLength, Context context) {
        this.v[Axis.Y.id] = 0;
        this.v[Axis.X.id] = 0;
        this.playerSize = (int) (cellLength * GameParams.playerSize);


        this.where = GameParams.whereMap;

        this.cellLength = cellLength;

        cvSize = 1 + 2 * GameParams.actionOffset;
        prm1 = (GameParams.actionOffset) / cvSize;

        bagRepository = EntryPoints.get(context.getApplicationContext(), MyEntryPoints.class)
                .bagRepository();

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
        points[Axis.Y.id][0] = (int) (cellLength * (((GameParams.visibleCellNum - 1) / 2) + relativePoint[Axis.Y.id] / cellLength));//Xの位置*cellの長さ+  1/2cellの長さ
        points[Axis.X.id][0] = (int) (cellLength * (((GameParams.visibleCellNum - 1) / 2) + relativePoint[Axis.X.id] / cellLength));
        collisionPoints[Axis.Y.id][0] = points[Axis.Y.id][0];
        collisionPoints[Axis.X.id][0] = points[Axis.X.id][0];
        nextBGC[Axis.Y.id] = (GameParams.visibleCellNum - 1) / 2;
        nextBGC[Axis.X.id] = (GameParams.visibleCellNum - 1) / 2;
        BGC[Axis.Y.id] = (GameParams.visibleCellNum - 1) / 2;
        BGC[Axis.X.id] = (GameParams.visibleCellNum - 1) / 2;

        moveImage();
    }

    public int[][] getPoints() {
        return points;
    }

    public void setCollisionPoints(int[] v) {
        collisionPoints[Axis.Y.id][0] = points[Axis.Y.id][0] + v[Axis.Y.id];
        collisionPoints[Axis.Y.id][1] = collisionPoints[Axis.Y.id][0] + playerSize;
        collisionPoints[Axis.X.id][0] = points[Axis.X.id][0] + v[Axis.X.id];
        collisionPoints[Axis.X.id][1] = collisionPoints[Axis.X.id][0] + playerSize;
    }

    public int[][] getCollisionPoints() {
        return collisionPoints;
    }

    public int[] getCenter() {
        int[] center = new int[2];
        center[Axis.X.id] = points[Axis.X.id][0] + playerSize / 2;
        center[Axis.Y.id] = points[Axis.Y.id][0] + playerSize / 2;
        return center;
    }

    public int getAfterCenterX() {
        return collisionPoints[Axis.X.id][0] + playerSize / 2;
    }

    public int getAfterCenterY() {
        return collisionPoints[Axis.Y.id][0] + playerSize / 2;
    }

    private Dir dir = Dir.Down;

    public void setDir() {
        if (v[Axis.Y.id] >= 0 && Math.abs(v[Axis.X.id]) <= Math.abs(v[Axis.Y.id])) {
            dir = Dir.Down;
        } else if (v[Axis.X.id] >= 0 && Math.abs(v[Axis.X.id]) >= Math.abs(v[Axis.Y.id])) {
            dir = Dir.Right;
        } else if (v[Axis.X.id] <= 0 && Math.abs(v[Axis.X.id]) >= Math.abs(v[Axis.Y.id])) {
            dir = Dir.Left;
        } else if (v[Axis.X.id] <= 0 && Math.abs(v[Axis.X.id]) <= Math.abs(v[Axis.Y.id])) {
            dir = Dir.Up;
        }
    }

    public int[] getActionViewPosition() {
        int[] touchActionViewPosition = new int[2];

        switch (dir) {
            case Right:
                touchActionViewPosition[Axis.X.id] = points[Axis.X.id][0] + playerSize;
                touchActionViewPosition[Axis.Y.id] = points[Axis.Y.id][0];
                break;
            case Down:
                touchActionViewPosition[Axis.X.id] = points[Axis.X.id][0];
                touchActionViewPosition[Axis.Y.id] = points[Axis.Y.id][0] + playerSize;
                break;
            case Left:
                touchActionViewPosition[Axis.X.id] = points[Axis.X.id][0] - playerSize;
                touchActionViewPosition[Axis.Y.id] = points[Axis.Y.id][0];
                break;
            case Up:
                touchActionViewPosition[Axis.X.id] = points[Axis.X.id][0];
                touchActionViewPosition[Axis.Y.id] = points[Axis.Y.id][0] - playerSize;
                break;
        }

        return touchActionViewPosition;
    }

    public Dir getDir() {
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
    private MoveState moveState = MoveState.MoveState_Ground;

    public MoveState getMoveState() {
        return this.moveState;
    }

    public void setMoveState(MoveState moveState) {
        this.moveState = moveState;
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
        if (distanceToGoal[Axis.X.id] == 0 && distanceToGoal[Axis.Y.id] == 0) {
            autoMovingFlag = false;
            canMove = false;
            setCollisionPoints(v);
        }
    }

    public static final int MAX_ITEM_NUM = 99;

    public int[][] getAllItem() {
        return bagRepository.getToolArray();
    }

    public int getToolIdAt(int itemPosition) {
        return bagRepository.getToolIdAt(itemPosition);
    }

    public int getToolNumAt(int itemPosition) {
        return bagRepository.getToolNumAt(itemPosition);
    }

    public void addItem(int ItemNumber, int quantity) {
        bagRepository.addTool(ItemNumber, quantity);
    }

    public boolean haveManyItem(int itemId) {
        return bagRepository.isMany(itemId);
    }

    public void decItem(int itemPosition) {
        bagRepository.decreaseBagTool(itemPosition);
    }

    public void decItem(int itemId, int quantity) {
        bagRepository.decreaseTool(itemId, quantity);
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


    private void moveCellPoint() {
        if (!movedFlag) {
            if (BGC[Axis.X.id] != nextBGC[Axis.X.id]
                    || BGC[Axis.Y.id] != nextBGC[Axis.Y.id]) {
                movedFlag = true;
            }
        }
        System.arraycopy(nextBGC, 0, this.BGC, 0, BGC.length);
    }

    public void moveInMap(int[] moveDist) {
        setCollisionPoints(moveDist);//実際に動いた距離を入れる
        moveImage();
        double y2 =  Math.pow(moveDist[Axis.Y.id], 2);
        movedDistSum += (int) Math.sqrt(Math.pow(moveDist[Axis.X.id], 2) +y2);
    }

    public boolean isEncounterMons() {
        if (movedDistSum - GameParams.MONS_APP_DIST * MainGame.cellLength > 0) {
            movedDistSum -= GameParams.MONS_APP_DIST * MainGame.cellLength;
            return true;
        }
        return false;
    }

    public void moveImage() {
        points[Axis.X.id][0] = collisionPoints[Axis.X.id][0];
        points[Axis.X.id][1] = collisionPoints[Axis.X.id][1];
        points[Axis.Y.id][0] = collisionPoints[Axis.Y.id][0];
        points[Axis.Y.id][1] = collisionPoints[Axis.Y.id][1];
        isAllPartIn = nextAllInFlag;

        moveCellPoint();

        Log.d("msg", "CellY " + BGC[Axis.Y.id] + " :CellX " + BGC[Axis.X.id]);
    }

    public int[] getPlayerPosition() {
        int[] imageViewPosition = new int[2];
        imageViewPosition[Axis.X.id] = points[Axis.X.id][0];
        imageViewPosition[Axis.Y.id] = points[Axis.Y.id][0];
        return imageViewPosition;
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

    public double[] getActionCollision() {
        double[] offset = {0, 0};
        //主人公の向いてる方向で場合分け
        switch (getDir()) {
            case Right:
                offset[Axis.X.id] = GameParams.actionOffset;
                break;
            case Down:
                offset[Axis.Y.id] = GameParams.actionOffset;
                break;
            case Left:
                offset[Axis.X.id] = -GameParams.actionOffset;
                break;
            case Up:
                offset[Axis.Y.id] = -GameParams.actionOffset;
                break;
        }
        return offset;
    }

    private MapId lastTownId;

    public void setLastTownId(MapId lastTownId) {
        this.lastTownId = lastTownId;
    }

    public MapId getLastTownId() {
        return lastTownId;
    }

    private int nowEventFlag;
    public void setNowEventFlag(int nowEventFlag){
        this.nowEventFlag = nowEventFlag;
    }

    public void proceedNowEventFlag(BattleResult battleResult) {
        if (nowEventFlag == -1) return;
        final int  proceedNum;
        switch (battleResult){
            case Win:
                proceedNum = 1;
                break;
            case Lose:
                proceedNum = 2;
                break;
            case Escape:
                proceedNum = 3;
                break;
            default:
                throw new RuntimeException("Battle Result が不正です");
        }

        eventFlag[nowEventFlag] += proceedNum;
    }
}
