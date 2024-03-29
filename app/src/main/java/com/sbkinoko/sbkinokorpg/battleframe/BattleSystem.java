package com.sbkinoko.sbkinokorpg.battleframe;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.sbkinoko.sbkinokorpg.battleframe.condition.DefaultCondition;
import com.sbkinoko.sbkinokorpg.battleframe.status.MonsterStatus;
import com.sbkinoko.sbkinokorpg.battleframe.status.PlayerStatus;
import com.sbkinoko.sbkinokorpg.battleframe.status.Status;
import com.sbkinoko.sbkinokorpg.dataList.item.List_Tool;
import com.sbkinoko.sbkinokorpg.game_item.action_item.item.SuccessiveItem;
import com.sbkinoko.sbkinokorpg.game_item.action_item.use_item.UseItemInBattle;
import com.sbkinoko.sbkinokorpg.gameparams.BattleResult;
import com.sbkinoko.sbkinokorpg.gameparams.EffectType;
import com.sbkinoko.sbkinokorpg.gameparams.EscapeFlag;
import com.sbkinoko.sbkinokorpg.gameparams.EventBattleFlag;
import com.sbkinoko.sbkinokorpg.gameparams.GameParams;
import com.sbkinoko.sbkinokorpg.mapframe.MapFrame;
import com.sbkinoko.sbkinokorpg.mapframe.window.MapWindow_Save;

import java.util.Random;

public class BattleSystem {
    Context context;

    private final BattleFrame battleFrame;
    private final MapFrame mapFrame;

    public BattleSystem(MapFrame mapFrame, BattleFrame battleFrame, Context context) {
        players = MapWindow_Save.getStatusData(context);
        this.mapFrame = mapFrame;
        this.battleFrame = battleFrame;
        this.context = context;
    }

    int whoseTurn = 0;
    int whoseActionSelect = 0;

    public void incWhoseActionSelect() {
        whoseActionSelect++;
    }

    public void decWhoseActionSelect() {
        whoseActionSelect--;
    }

    public int getWhoseActionSelect() {
        return whoseActionSelect;
    }

    public boolean isAllPlayerActionSelected() {
        return GameParams.PLAYER_NUM <= whoseActionSelect;
    }

    private boolean isAllCharacterActionFinished() {
        return getStatusNum() <= whoseTurn;
    }

    public PlayerStatus getActionSelectPlayer() {
        return getPlayer(whoseActionSelect);
    }

    boolean battleEndFlag = false;
    BattleResult winFlag = BattleResult.Lose;

    private EscapeFlag canEscape;

    public boolean isNotEscapable() {
        return !canEscape.caeEscapeBattle();
    }

    private PlayerStatus[] players;

    public PlayerStatus[] getPlayers() {
        return players;
    }

    public int getPlayerNum() {
        return players.length;
    }

    public PlayerStatus getPlayer(int id) {
        return players[id];
    }

    private MonsterStatus[] statusMonsters;

    public MonsterStatus getMonster(int id) {
        return statusMonsters[id];
    }

    int monstersNum;

    public int getMonstersNum() {
        return monstersNum;
    }

    public int getStatusNum() {
        return getPlayerNum()
                + getMonstersNum();
    }


    public BattleFrame getBattleFrame() {
        return battleFrame;
    }

    private EventBattleFlag eventBattleFlag;

    /**
     * @param monsters   出現したモンスターのid
     * @param bgImage    背景画像
     * @param escapeFlag 逃走可能かどうか
     */
    public void startBattle(int[] monsters,
                            int bgImage,
                            EscapeFlag escapeFlag,
                            EventBattleFlag eventBattleFlag) {
        this.canEscape = escapeFlag;
        this.eventBattleFlag = eventBattleFlag;
        battleFrame.battleEscapeWindow.setEscapeFlag(eventBattleFlag);
        battleEndFlag = false;
        winFlag = BattleResult.Lose;

        whoseTurn = 0;
        whoseActionSelect = 0;

        battleFrame.setVisibility(View.VISIBLE);
        mapFrame.getFrameLayout().setVisibility(View.GONE);

        mapFrame.player.setInBattle();

        battleFrame.battleWindow_Top.reloadTv();
        battleFrame.battleMenuWindow.openMenu();

        battleFrame.setBackGroundImage(bgImage);

        for (int i = 0; i < GameParams.PLAYER_NUM; i++) {
            players[i].setChooseEnm(new int[]{0});
        }
        monstersNum = monsters.length;
        statusMonsters = new MonsterStatus[monstersNum];
        for (int i = 0; i < monstersNum; i++) {
            statusMonsters[i] = new MonsterStatus(monsters[i], context);
        }

        battleFrame.setMonstersImage(statusMonsters);
    }

    public int getEXP() {
        int EXP = 0;
        for (MonsterStatus statusMonster : statusMonsters) {
            EXP += statusMonster.getExp();
        }
        return EXP;
    }

    public int getPrize() {
        int PRIZE = 0;
        for (MonsterStatus statusMonster : statusMonsters) {
            PRIZE += statusMonster.getPrize();
        }
        return PRIZE;
    }


    private void endBattle() {
        for (PlayerStatus playerStatus : players) {
            playerStatus.resetInfo();
        }

        int[] dropItem = new int[monstersNum];
        for (int i = 0; i < monstersNum; i++) {
            int gotItemID = statusMonsters[i].getDropItem();
            if (gotItemID == 0) {
                continue;
            }
            Log.d("debugMSG", "addItem");
            mapFrame.player.addItem(gotItemID, 1);
            dropItem[i] = gotItemID;
        }

        for (int i = 0; i < GameParams.PLAYER_NUM; i++) {
            getPlayer(i).addExp(getEXP());
        }


        battleFrame.battleEndWindow.openMenu("魔物の群れ", winFlag, eventBattleFlag);
        battleFrame.battleEndWindow.addItem(dropItem);

    }

    public void processForGameOver() {
        players[0].setHP(1);
    }

    public void checkNextStep() {
        if (battleEndFlag) {
            endBattle();
        } else if (isAllCharacterActionFinished()) {
            whoseTurn = 0;
            whoseActionSelect = 0;
            battleFrame.battleMenuWindow.openMenu();
        } else {
            battleStep(actingID, _isWin);
        }
    }

    int[][] speedList;

    DefaultCondition condition;
    boolean isStepChanged;

    public void startBattleStep() {
        setSpeedList();

        Log.d("msg_1", "sort前");
        showSpeedList();

        sortSpeedList();

        Log.d("msg_1", "sort後");
        showSpeedList();

        setMonsterAction();

        stepType = BattleStep.BEFORE;
        isStepChanged = true;
        changeAttacker();
        battleStep(actingID, _isWin);
    }

    private void setMonsterAction() {
        for (MonsterStatus monster : statusMonsters) {
            monster.setAction();
        }
    }

    boolean isPlayer(int statusID) {
        return statusID < getPlayerNum();
    }

    int getMonsterID(int statusID) {
        return statusID - getPlayerNum();
    }

    int getManipulatedSpeed(int speed) {
        Random random = new Random();
        return speed * (90 + random.nextInt(20)) / 100;
    }

    private void setSpeedList() {
        speedList = new int[getStatusNum()][2];
        for (int i = 0; i < speedList.length; i++) {
            speedList[i][0] = i;
            if (isPlayer(i)) {
                speedList[i][1] = getManipulatedSpeed(players[i].getTotalSpeed().getEffValue());
                Log.d("msg", players[i].getName()
                        + ":" + speedList[i][1]);
            } else {
                speedList[i][1] = getManipulatedSpeed(
                        statusMonsters[getMonsterID(i)].getTotalSpeed().getEffValue());
                Log.d("msg", statusMonsters[getMonsterID(i)].getName()
                        + ":" + speedList[i][1]);
            }
        }
    }

    private void showSpeedList() {
        for (int[] ints : speedList) {
            int statusID = ints[0];
            String txt;
            if (isPlayer(statusID)) {
                txt = players[statusID].getName()
                        + ":" + players[statusID].getTotalSpeed();
            } else {
                txt = statusMonsters[getMonsterID(statusID)].getName()
                        + ":" + statusMonsters[getMonsterID(statusID)].getTotalSpeed();
            }
            Log.d("msg_1", txt);
        }
    }

    private void sortSpeedList() {
        for (int i = 0; i < speedList.length; i++) {
            for (int j = 0; j < speedList.length - 1 - i; j++) {
                if (speedList[j][1] < speedList[j + 1][1]) {
                    changeSpeedList(j, j + 1);
                }
            }
        }
    }

    private void changeSpeedList(int i, int j) {
        for (int k = 0; k < 2; k++) {
            int tmp = speedList[i][k];
            speedList[i][k] = speedList[j][k];
            speedList[j][k] = tmp;
        }
    }

    BattleStep stepType;

    //todo getメソッドを利用する
    int actingID;
    BattleResult _isWin;

    private int getActingID() {
        return speedList[whoseTurn][0];
    }

    private boolean getIsWin() {
        return isPlayer(getActingID());
    }

    private void changeAttacker() {
        if (isAllCharacterActionFinished()) {
            return;
        }

        actingID = speedList[whoseTurn][0];
        if (isPlayer(actingID)) {//プレイヤーが攻撃
            _isWin = BattleResult.Win;
        } else {//モンスターが攻撃
            actingID = getMonsterID(actingID);
            _isWin = BattleResult.Lose;
        }
    }

    private void battleStep(int actingID, BattleResult isWin) {
        Status[] atkStatus, defStatus;
        if (isWin == BattleResult.Win) {
            atkStatus = players;
            defStatus = statusMonsters;
        } else {
            atkStatus = statusMonsters;
            defStatus = players;
        }

        Status nowPlayer = atkStatus[actingID];

        if (nowPlayer.canNotAction()) {
            whoseTurn++;
            changeAttacker();
            checkNextStep();
            return;
        }

        if (isStepChanged) {
            isStepChanged = false;
            condition = nowPlayer.getCondition();
        }

        switch (stepType) {
            case BEFORE:
                beforeATKStep(nowPlayer, atkStatus, isWin);
                break;
            case ATK:
                atkStep(actingID, atkStatus, defStatus, isWin);
                break;
            case AFTER:
                afterATKStep(nowPlayer, atkStatus, isWin);
                break;
            case STEEL:
                steelStep((PlayerStatus) nowPlayer);
                break;
        }
    }

    private void steelStep(PlayerStatus nowPlayer) {
        stepType = BattleStep.AFTER;

        if (!useItem.isCanSteel()) {
            battleFrame.battleAttackWindow.openMenu("何も持っていなかった");
            return;
        }

        if (useItem.getSteelItem() == 0) {
            battleFrame.battleAttackWindow.openMenu("何も取れなかった");
            return;
        }

        battleFrame.battleAttackWindow.openMenu(List_Tool.getToolAt(useItem.getSteelItem()).getName()
                + "を手に入れた");

    }

    UseItemInBattle useItem;

    void atkStep(int actingID, Status[] atkStatus, Status[] defStatus, BattleResult isWin) {
        Status nowStatus = atkStatus[actingID];
        battleFrame.battleAttackWindow.openMenu(nowStatus.getActionTxt());
        useItem = new UseItemInBattle();
        Log.d("msg", nowStatus.getName() + "のターン");
        useItem.useInBattle(actingID, atkStatus, defStatus);

        battleFrame.battleWindow_Top.reloadTv();
        chekExtermination(defStatus, isWin);

        if (isSteelItem(nowStatus)) {
            stepType = BattleStep.STEEL;
            return;
        }

        if (!isSuccessiveItem(nowStatus)) {
            incStep();
            return;
        }

        //攻撃された対象
        Status attackedStatus = defStatus[nowStatus.getChooseEnm()[0]];

        if (((SuccessiveItem) nowStatus.getActionItem()).stopAtk(attackedStatus)) {
            incStep();
        }
    }

    private boolean isSuccessiveItem(Status nowStatus) {
        return nowStatus.getActionItem().getEffect() == EffectType.EFFECT_TYPE_CONTINUE_ATK;
    }

    private boolean isSteelItem(Status nowStatus) {
        return nowStatus.getActionItem().getEffect()
                == EffectType.EFFECT_TYPE_STEEL;
    }

    boolean skipATKStep = false;

    private void beforeATKStep(Status nowPlayer, Status[] atkStatus, BattleResult isWin) {
        condition = condition.getNextCondition();
        if (condition == null
                || skipATKStep) {
            incStep();
            if (skipATKStep) {
                incStep();
            }
            skipATKStep = false;
            checkNextStep();
            return;
        }

        if (hasConditionAction(nowPlayer, atkStatus, isWin)) {
            return;
        }

        beforeATKStep(nowPlayer, atkStatus, isWin);
    }

    private void afterATKStep(Status nowPlayer, Status[] atkStatus, BattleResult isWin) {
        condition = condition.getNextCondition();
        if (condition == null) {
            incStep();
            checkNextStep();
            return;
        }

        if (hasConditionAction(nowPlayer, atkStatus, isWin)) {
            return;
        }

        afterATKStep(nowPlayer, atkStatus, isWin);
    }

    private boolean hasConditionAction(Status nowPlayer, Status[] atkStatus, BattleResult isWin) {
        if (!stepType.isNeedStep(condition)) {
            return false;
        }

        int dmg = 0;
        DefaultCondition _condition = condition;
        while (_condition != null) {
            dmg += stepType.stepAction(_condition, nowPlayer);
            _condition = _condition.getSameTypeNextCondition();
        }
        battleFrame.battleWindow_Top.reloadTv();
        chekExtermination(atkStatus, isWin.not());

        if (condition.isSkipATKStep()) {
            skipATKStep = true;
        }

        String txt = condition.getTxt(nowPlayer, dmg);
        if (txt != null) {
            battleFrame.battleAttackWindow.openMenu(txt);
            return true;
        }
        return false;
    }

    private void incStep() {
        stepType = stepType.getNextStep();
        isStepChanged = true;
        if (stepType == BattleStep.END) {
            whoseTurn++;
            changeAttacker();
            stepType = BattleStep.BEFORE;
        }
    }

    //fixme typo
    private void chekExtermination(Status[] checkStatus, BattleResult _batlteResult) {
        battleEndFlag = isExterminated(checkStatus);
        if (battleEndFlag) {
            winFlag = _batlteResult;
        }
    }

    /**
     * @param statuses 全滅を確認するステータス
     * @return 全滅しているかどうか
     */
    public boolean isExterminated(Status[] statuses) {
        for (Status status : statuses) {
            if (status.isAlive()) {
                return false;
            }
        }
        return true;
    }
}
