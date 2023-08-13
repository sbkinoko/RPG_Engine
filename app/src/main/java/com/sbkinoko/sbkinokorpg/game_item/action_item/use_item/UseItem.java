package com.sbkinoko.sbkinokorpg.game_item.action_item.use_item;

import android.util.Log;

import com.sbkinoko.sbkinokorpg.battleframe.status.Status;
import com.sbkinoko.sbkinokorpg.game_item.action_item.item.ActionItem;
import com.sbkinoko.sbkinokorpg.gameparams.GameParams;

public class UseItem {


    /**
     * 回復を実行 戦闘とフィールドを共用化するため
     *
     * @param fromPlayer  今の行動者
     * @param targetArray ターゲット
     * @param allies      味方の情報
     */
    static void doCure(Status fromPlayer,
                       int[] targetArray,
                       Status[] allies,
                       ActionItem actionItem) {
        for (int chooseID : targetArray) {//選んだ対象を前から攻撃
            if (chooseID < 0) {//対象が設定されていないのでbreak
                break;
            }
            switch (actionItem.getEffect()) {
                case GameParams.EFFECT_TYPE_HEAL://味方のHPを増やす
                case GameParams.EFFECT_TYPE_REVIVE:
                    incHP(fromPlayer, allies[chooseID], actionItem);
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
     * その味方を選択できるかどうか
     */
    static public boolean canSelectALY(int effectType,
                                       Status status) {
        switch (effectType) {
            case GameParams.EFFECT_TYPE_HEAL:
            case GameParams.EFFECT_TYPE_BUFF:
                return status.isAlive();
            case GameParams.EFFECT_TYPE_REVIVE:
                return status.canRevive();
        }
        return false;
    }

    /**
     * 特技が味方を対象にとるかどうか
     */
    public static boolean isTargetAly(int actionEffectType) {
        switch (actionEffectType) {
            case GameParams.EFFECT_TYPE_HEAL:
            case GameParams.EFFECT_TYPE_BUFF:
            case GameParams.EFFECT_TYPE_REVIVE:
                return true;
        }
        return false;
    }
}
