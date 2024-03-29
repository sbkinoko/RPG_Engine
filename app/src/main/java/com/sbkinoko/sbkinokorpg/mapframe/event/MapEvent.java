package com.sbkinoko.sbkinokorpg.mapframe.event;

import android.util.Log;

import com.sbkinoko.sbkinokorpg.OptionConst;
import com.sbkinoko.sbkinokorpg.battleframe.status.PlayerStatus;
import com.sbkinoko.sbkinokorpg.dataList.item.List_Tool;
import com.sbkinoko.sbkinokorpg.game_item.action_item.use_item.UseItemInField;
import com.sbkinoko.sbkinokorpg.gameparams.Axis;
import com.sbkinoko.sbkinokorpg.gameparams.EscapeFlag;
import com.sbkinoko.sbkinokorpg.gameparams.EventBattleFlag;
import com.sbkinoko.sbkinokorpg.gameparams.GameParams;
import com.sbkinoko.sbkinokorpg.gameparams.MoveState;
import com.sbkinoko.sbkinokorpg.mapframe.MapBackgroundCell;
import com.sbkinoko.sbkinokorpg.mapframe.MapFrame;
import com.sbkinoko.sbkinokorpg.mapframe.map.mapdata.MapChangeDataList;
import com.sbkinoko.sbkinokorpg.mapframe.map.mapdata.MapData;
import com.sbkinoko.sbkinokorpg.mapframe.npc.NPC;
import com.sbkinoko.sbkinokorpg.mapframe.npc.eventdata.EventBattle;
import com.sbkinoko.sbkinokorpg.mapframe.npc.eventdata.EventChoice;
import com.sbkinoko.sbkinokorpg.mapframe.npc.eventdata.EventData;
import com.sbkinoko.sbkinokorpg.mapframe.npc.eventdata.EventItemGet;
import com.sbkinoko.sbkinokorpg.mapframe.npc.eventdata.EventShopping;
import com.sbkinoko.sbkinokorpg.mapframe.npc.eventdata.EventTalk;
import com.sbkinoko.sbkinokorpg.mapframe.player.Player;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.GroupOfWindows;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.WindowDetail;

public class MapEvent {
    MapFrame mapFrame;
    Player player;

    public MapEvent(MapFrame mapFrame) {
        this.mapFrame = mapFrame;
        this.player = mapFrame.player;
    }

    public void autoAction(AutoActionList autoAction) {
        switch (autoAction) {
            case non:
                return;
            case mapChange:
                int mapX = mapFrame.getBgcMatrix().getPlayerMapXY().getX();
                int mapY = mapFrame.getBgcMatrix().getPlayerMapXY().getY();
                loadMap(mapFrame.getNowMap().getCellType(mapY, mapX));
                break;
            case startBattle:
                mapFrame.startBattle(0, 3,
                        EscapeFlag.CanNot, EventBattleFlag.NotEvent);
                break;
            case setGround:
                mapFrame.setPlayerMoveState(MoveState.MoveState_Ground);
                break;
            case setWater:
                mapFrame.setPlayerMoveState(MoveState.MoveState_Water);
                break;
        }
    }

    public void nextEvent(NPC npc) {
        EventData eventData = npc.getActiveEvent();

        switch (eventData.getEventType()) {
            case TALK_EVENT:
                mapFrame.getMapTextBoxWindow().openMenu(eventData.getTxt());
                if (((EventTalk) eventData).isNextTalk()) {
                    player.setNextEventFlag(true);
                }
                break;
            case TALK_AND_HEAL:
                for(PlayerStatus playerStatus:mapFrame.battleSystem.getPlayers()){
                    playerStatus.allRecover();
                }
                mapFrame.getMapTextBoxWindow().openMenu(eventData.getTxt());
                if (((EventTalk) eventData).isNextTalk()) {
                    player.setNextEventFlag(true);
                }
                break;

            case ITEM_EVENT:
                EventItemGet eventItemGet = (EventItemGet) eventData;
                int itemID = eventItemGet.getItemID();
                player.addItem(itemID, eventItemGet.getItemNum());
                String[] txt = new String[]{new List_Tool().getName(itemID) + "を手に入れた"};
                mapFrame.getMapTextBoxWindow().openMenu(txt);
                if (eventItemGet.isNextTalk()) {
                    player.setNextEventFlag(true);
                }
                break;

            case BATTLE_EVENT_NOT_ESCAPE: {
                int battleID = ((EventBattle) eventData).getBattleID();
                mapFrame.startBattle(0, battleID,
                        EscapeFlag.CanNot, EventBattleFlag.Event);
                player.setNowEventFlag(npc.getUsingFlagID());
                break;
            }

            case BATTLE_EVENT_CAN_ESCAPE: {
                int battleID = ((EventBattle) eventData).getBattleID();
                mapFrame.startBattle(0, battleID,
                        EscapeFlag.Can, EventBattleFlag.Event);
                player.setNowEventFlag(npc.getUsingFlagID());
                break;
            }

            case OPEN_CHOICE:
                mapFrame.getMapTextBoxWindow().openMenu();//最後に表示していた文章をもう一回表示する
                mapFrame.getMapTextBoxWindow().changeTargetWindow();
                mapFrame.getMapWindow_choice().openMenu((EventChoice) eventData,
                        npc.getUsingFlagID());
                break;

            case SHOPPING_EVENT:
                WindowDetail shoppingList = mapFrame.getMapWindow_list_detail();
                shoppingList.openMenuWithSetting((EventShopping) eventData);
                break;

            case SELL_EVENT:
                mapFrame.getSelectSellItemKind().openMenu();
                break;

            case JOB_EVENT:
                mapFrame.getWindowGroupInfo().openJob();

                break;

        }

        player.setEventFlag(new int[]{
                npc.getUsingFlagID(),
                eventData.getAfterStep()
        });
    }

    public void doEvent() {
        String[] texts = null;
        MapData nowMap = mapFrame.getNowMap();
        MapBackgroundCell actionBGC = mapFrame.getBgcMatrix().getBGC(player.getActionCell());
        switch (player.getActionType()) {
            case MapACTION_TREASURE_BOX:
                int itemID;
                int boxID = nowMap.getTreasureBoxId(
                        actionBGC.getMapPoint()
                );

                if (nowMap.getTreasureBoxes()[boxID] > 0) {
                    texts = new String[2];
                    texts[0] = "宝箱を開けた";
                    itemID = nowMap.getTreasureBoxes()[boxID];
                    texts[1] = new List_Tool().getName(itemID);
                    texts[1] += "を手に入れた";

                    player.addItem(itemID, 1);
                    nowMap.getTreasureBoxes()[boxID] = 0;
                    actionBGC.setData(nowMap);
                }
                break;

            case MapACTION_GO_WATER:
                mapFrame.setPlayerMoveState(MoveState.MoveState_Water);
                moveToOtherArea();
                texts = new String[]{"水上に出た"};
                break;

            case MapACTION_GO_GROUND:
                mapFrame.setPlayerMoveState(MoveState.MoveState_Ground);
                moveToOtherArea();
                texts = new String[]{"陸に戻った"};
                break;

            case MapAction_talk:
                int npcId = player.getTalkNPC();
                NPC[] npc = mapFrame.getMapViewModel().getNpcList();
                texts = npc[npcId].getActiveEvent().getTxt();
                player.setNextEventFlag(npc[npcId].isNextEventFlag());
                break;

            case MapAction_Switch:
                player.setEventFlag(new int[]{9, (player.getEventFlag(9) + 1) % 4});
                texts = new String[]{"スイッチを押した" + player.getEventFlag(9)};
                mapFrame.getBgcMatrix().setBGImage();
                break;
        }

        mapFrame.getMapTextBoxWindow().openMenu(texts);
    }

    public void doWarp(int townID, GroupOfWindows groupOfWindows) {
        groupOfWindows.getWindowDetail().closeMenu();
        loadMap(townID);

        mapFrame.getMapTextBoxWindow().openMenu(new String[]{"ワープした"});
        UseItemInField.useInField(
                groupOfWindows,
                null,
                this);
    }

    public void openWarpMenu() {
        String name, itemName;
        PlayerStatus _fromPlayer = mapFrame.getWindowGroupInfo().getFromPlayerStatus();
        if (_fromPlayer == null) {
            name = "袋";
        } else {
            name = _fromPlayer.getName();
        }
        itemName = mapFrame.getWindowGroupInfo().getActionItem().getName();

        mapFrame.getMapWindow_list_detail().openWithWarpData();
        mapFrame.window_explanation.setText(0, name);
        mapFrame.window_explanation.setText(1, itemName);
    }

    public void loadMap(int cellType) {
        mapFrame.moveMap(MapChangeDataList.getMapChangeData(cellType));
    }

    public void goSky() {
        mapFrame.setPlayerMoveState(MoveState.MoveState_Sky);
        mapFrame.getBgcMatrix().setBGImage();
        Log.d("msg", "とんだよ");
    }

    public void goGround() {
        String[] txt;
        player.setV(new int[]{0, 0});
        mapFrame.setPlayerMoveState(MoveState.MoveState_Ground);
        mapFrame.checkAfterPosition();
        if (player.getCanMoveDir()[Axis.X.id] && player.getCanMoveDir()[Axis.Y.id]) {
            txt = new String[]{"地上に降りた"};
            mapFrame.getBgcMatrix().setBGImage();
        } else {
            txt = new String[]{"地上に降りられないよ"};
            mapFrame.setPlayerMoveState(MoveState.MoveState_Sky);
        }
        mapFrame.getMapTextBoxWindow().openMenu(txt);
    }

    private int getDirAxis() {
        switch (player.getDir()) {
            case Left://左右を向いている時
            case Right:
                return Axis.Y.id;
            case Up://上下を向いている時
            case Down:
                return Axis.X.id;
        }
        throw new RuntimeException();
    }

    private void moveToOtherArea() {
        int[] goal = {0, 0};
        int[] d = {0, 0};

        getGoal_d(goal, d);

        if (canNotGoToGoal(goal[Axis.X.id], goal[Axis.Y.id])) {
            int axis = getDirAxis();

            goal[axis] = player.getPlayerSize();
            if (canNotGoToGoal(goal[Axis.X.id], goal[Axis.Y.id])) {
                goal[axis] *= -1;
            }
            d[axis] = goal[axis];
        }

        int[] goalPoint = decideGoal(goal[Axis.X.id], d[Axis.X.id], goal[Axis.Y.id], d[Axis.Y.id]);
        double dr = Math.sqrt(Math.pow(goalPoint[Axis.X.id], 2) + Math.pow(goalPoint[Axis.Y.id], 2));

        int[] tmpV = new int[2];
        int v_value = OptionConst.getActualV();
        tmpV[Axis.X.id] = (int) ((goalPoint[Axis.X.id] * v_value) / dr);
        tmpV[Axis.Y.id] = (int) ((goalPoint[Axis.Y.id] * v_value) / dr);
        player.setV(tmpV);
        player.setDistanceToGoal(goalPoint);
    }

    private void getGoal_d(int[] goal, int[] d) {
        int dist = (int) (player.getPlayerSize() * (1 + GameParams.actionOffset));
        switch (player.getDir()) {
            case Right:
                goal[Axis.X.id] = dist;
                d[Axis.X.id] = goal[Axis.X.id];
                break;
            case Down:
                goal[Axis.Y.id] = dist;
                d[Axis.Y.id] = goal[Axis.Y.id];
                break;
            case Left:
                goal[Axis.X.id] = -dist;
                d[Axis.X.id] = goal[Axis.X.id];
                break;
            case Up:
                goal[Axis.Y.id] = -dist;
                d[Axis.Y.id] = goal[Axis.Y.id];
                break;
        }
    }

    private int[] decideGoal(int _goalX, int dX, int _goalY, int dY) {
        int binarySearchTime = 4;
        int goalX = _goalX;
        int goalY = _goalY;
        for (int i = 0; i < binarySearchTime; i++) {
            dX /= 2;
            dY /= 2;
            if (!canNotGoToGoal(goalX, goalY - dY)) {
                //動けたらそこに行って良い
                goalY -= dY;
            }
            if (!canNotGoToGoal(goalX - dX, goalY)) {
                //動けたらそこには行って良い
                goalX -= dX;
            }
        }
        int[] tmpAP = new int[2];
        tmpAP[Axis.X.id] = goalX;
        tmpAP[Axis.Y.id] = goalY;
        return tmpAP;
    }

    private boolean canNotGoToGoal(int dX, int dY) {
        int[] tmpAP = new int[2];
        tmpAP[Axis.X.id] = dX;
        tmpAP[Axis.Y.id] = dY;
        player.setCollisionPoints(tmpAP);

        return mapFrame.getBgcMatrix().checkCanNotGoToGoal();
    }

}
