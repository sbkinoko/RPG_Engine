package com.sbkinoko.sbkinokorpg.game_item.action_item;

import android.util.Log;

import com.sbkinoko.sbkinokorpg.GameParams;
import com.sbkinoko.sbkinokorpg.battleframe.condition.ConditionData;
import com.sbkinoko.sbkinokorpg.battleframe.status.MonsterStatus;
import com.sbkinoko.sbkinokorpg.battleframe.status.PlayerStatus;
import com.sbkinoko.sbkinokorpg.battleframe.status.Status;
import com.sbkinoko.sbkinokorpg.game_item.action_item.item.ActionItem;
import com.sbkinoko.sbkinokorpg.game_item.action_item.item.ConditionItem;
import com.sbkinoko.sbkinokorpg.game_item.action_item.item.SuccessiveItem;
import com.sbkinoko.sbkinokorpg.mapframe.event.MapEvent;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.GroupOfWindows;

import java.util.Random;

public class UseItem {

    /**
     * 袋から使う可能性があるのでplayerBattlesのchooseAlyが使えない
     *
     * @param target 対象
     */
    static public boolean useInField(GroupOfWindows groupOfWindows,
                                     int[] target,
                                     MapEvent mapEvent) {
        Log.d("msg", groupOfWindows.getFromPlayer() + "が使用");

        Status _fromPlayer = groupOfWindows.getFromPlayerStatus();
        if (_fromPlayer == null) {//fromPlayerが袋
            _fromPlayer = new PlayerStatus("", 0);
        }
        _fromPlayer.setChooseAly(target);
        ActionItem actionItem = groupOfWindows.getActionItem();

        int itemEffectType = actionItem.getEffect();

        //回復アイテムだったので回復を呼び出し
        if (canHaveTargetInField(itemEffectType)) {
            doCure(_fromPlayer,
                    target,
                    groupOfWindows.getPlayerStatuses(),
                    actionItem);
        } else {
            switch (itemEffectType) {
                case GameParams.EFFECT_TYPE_MOVE:
                    mapEvent.goSky();
                    break;
            }
        }

        return actionItem.doAfterProcess(
                groupOfWindows.getFromPlayerStatus(),
                groupOfWindows.getPlayer(),
                groupOfWindows.getSelectedItemPosition());
    }

    public void useInBattle(int nowPlayerID,
                            Status[] allies,
                            Status[] enemies) {
        Status nowPlayer = allies[nowPlayerID];

        doMainProcess(nowPlayer, allies, enemies);

        doAfterProcess(nowPlayer);
    }

    private void doMainProcess(Status nowPlayer,
                               Status[] allies,
                               Status[] enemies) {
        ActionItem actionItem = nowPlayer.getActionItem();
        if (canSelectEnm(nowPlayer.getEffectType())) {
            nowPlayer.correctChooseEnm(enemies);
            doAttack(nowPlayer, enemies);
        } else if (canSelectAly(nowPlayer.getEffectType())) {
            nowPlayer.correctChooseAly(allies);
            doCure(nowPlayer,
                    nowPlayer.getChooseAly(),
                    allies,
                    actionItem);
        } else {
            doEscape(nowPlayer);
        }
    }

    private void doAfterProcess(Status nowPlayer) {
        //　道具を使った時に消す必要があるから
        int itemPosition = nowPlayer.getActionItemPosition();
        nowPlayer.getActionItem().doAfterProcess(
                nowPlayer,
                null,
                itemPosition);
    }

    /**
     * 攻撃の種類で場合分け
     *
     * @param status1 　攻撃者
     * @param enemies 　敵の情報
     */
    private void doAttack(Status status1, Status[] enemies) {
        int chooseID;
        for (int i = 0; i < status1.getChooseEnm().length; i++) {//選んだ対象を前から攻撃
            chooseID = status1.getChooseEnm()[i];
            if (chooseID < 0) {//対象が設定されていないのでbreak
                break;
            }

            switch (status1.getActionItem().getEffect()) {
                case GameParams.EFFECT_TYPE_ATK://敵を対象として行う行動
                    damageCal(status1, enemies[chooseID]);
                    break;
                case GameParams.EFFECT_TYPE_CONDITION:
                    changeCondition(status1, enemies[chooseID]);
                    break;
                case GameParams.EFFECT_TYPE_CONTINUE_ATK://敵を対象として行う行動
                    //todo 全体攻撃を複数回やる時の処理
                    Status enemy =
                            ((SuccessiveItem) status1.getActionItem()).getTarget(enemies, chooseID);
                    damageCal(status1, enemy);
                    return;
                case GameParams.EFFECT_TYPE_STEEL:
                    steelItem((PlayerStatus) status1, (MonsterStatus) enemies[chooseID]);
                    break;
            }
        }
    }

    private boolean canSteel;
    private int steelItem = 0;

    public boolean isCanSteel() {
        return canSteel;
    }

    public int getSteelItem() {
        return steelItem;
    }

    private void steelItem(PlayerStatus attacker, MonsterStatus defender) {
        canSteel = defender.hasItem();
        if (!canSteel) {
            return;
        }

        steelItem = defender.getDropItem();
        if (steelItem == 0) {
            return;
        }

        attacker.addHaveItem(steelItem);

    }

    /**
     * ダメージを与える処理
     *
     * @param status1 　攻撃者
     * @param status  　敵
     */
    private void damageCal(Status status1, Status status) {
        ActionItem actionItem = status1.getActionItem();
        //ダメージの計算式はここ
        double dmg = actionItem.getManipulatedValue(status1.getTotalATK().getEffValue())
                * actionItem.killerMagnification(status)
                * status.getAtrResistance(actionItem.getActionType()) / 100;

        status.decHP((int) dmg);
    }

    /**
     * 状態異常にする処理
     *
     * @param status1 自身
     * @param status  　対象
     */
    private static void changeCondition(Status status1, Status status) {
        //todo ステータス・パラメータによって状態異常のターン数を変えるならここに記述
        ConditionItem actionItem = (ConditionItem) status1.getActionItem();
        ConditionData conditionData = actionItem.getConditionId();

        if (new Random().nextInt(100)
                < status.getConditionResistance(conditionData.getId())) {
            return;
        }
        status.addCondition(conditionData.getCondition(actionItem.getRestTurn()));
    }


    /**
     * 回復を実行 戦闘とフィールドを共用化するため
     *
     * @param fromPlayer  今の行動者
     * @param targetArray ターゲット
     * @param allies      味方の情報
     */
    static private void doCure(Status fromPlayer, int[] targetArray, Status[] allies,
                               ActionItem actionItem) {
        for (int chooseID : targetArray) {//選んだ対象を前から攻撃
            if (chooseID < 0) {//対象が設定されていないのでbreak
                break;
            }
            switch (actionItem.getEffect()) {
                case GameParams.EFFECT_TYPE_HEAL://味方のHPを増やす
                case GameParams.EFFECT_TYPE_REVIVE:
                    incHP(fromPlayer, allies[chooseID], actionItem);
                    return;

                case GameParams.EFFECT_TYPE_BUFF:
                    giveBuff(allies[chooseID], actionItem);
                    break;
            }
        }
    }

    /**
     * 味方のHPを増やす処理
     *
     * @param fromPlayer
     * @param aly
     * @param actionItem
     */
    static private void incHP(Status fromPlayer, Status aly,
                              ActionItem actionItem) {
        int cure = actionItem.getManipulatedValue(
                fromPlayer.getHealMP().getEffValue());
        Log.d("msg", actionItem.getName() + ":" + cure);
        aly.incHP(cure);
    }

    /**
     * 味方にバフを与える処理
     *
     * @param aly
     * @param actionItem
     */
    static private void giveBuff(Status aly, ActionItem actionItem) {
        switch (actionItem.getAtr()) {
            case GameParams.STATUS_ATK:
                aly.getTotalATK().changeRank(1);
                break;
        }
    }

    /**
     * モンスターが逃げる処理
     *
     * @param nowPlayer 逃げたモンスター
     */
    private static void doEscape(Status nowPlayer) {
        ((MonsterStatus) nowPlayer).escape();
    }


    public static int[] getTarget(ActionItem actionItem) {
        if (actionItem.getTargetNum() == 0) {
            return new int[]{-1};
        }
        return null;
    }

    public static boolean canUseInField(ActionItem actionItem) {
        int whereCanUse = actionItem.getWhereCanUse();

        return whereCanUse == GameParams.canInBoth ||//フィールドとバトルで使える
                whereCanUse == GameParams.canInField;//フィールドで使える
    }

    static public boolean canSelectALY(int effectType, Status status) {
        switch (effectType) {
            case GameParams.EFFECT_TYPE_HEAL:
            case GameParams.EFFECT_TYPE_BUFF:
                return status.isAlive();
            case GameParams.EFFECT_TYPE_REVIVE:
                return status.canRevive();
        }
        return false;
    }

    static public boolean canHaveTargetInField(int effectType) {
        return effectType == GameParams.EFFECT_TYPE_REVIVE
                || effectType == GameParams.EFFECT_TYPE_HEAL;
    }

    /**
     * @return 戦闘中に敵を選べればtrueを返す
     */
    public static boolean canSelectEnm(int actionEffectType) {
        switch (actionEffectType) {
            case GameParams.EFFECT_TYPE_ATK:
            case GameParams.EFFECT_TYPE_CONDITION:
            case GameParams.EFFECT_TYPE_CONTINUE_ATK:
            case GameParams.EFFECT_TYPE_STEEL:
                return true;
            default:
                return false;
        }
    }

    public static boolean canSelectAly(int actionEffectType) {
        switch (actionEffectType) {
            case GameParams.EFFECT_TYPE_HEAL:
            case GameParams.EFFECT_TYPE_BUFF:
            case GameParams.EFFECT_TYPE_REVIVE:
                return true;
        }
        return false;
    }
}
