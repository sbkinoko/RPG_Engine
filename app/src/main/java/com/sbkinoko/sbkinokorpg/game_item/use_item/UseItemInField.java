package com.sbkinoko.sbkinokorpg.game_item.use_item;

import android.util.Log;

import com.sbkinoko.sbkinokorpg.GameParams;
import com.sbkinoko.sbkinokorpg.battleframe.status.PlayerStatus;
import com.sbkinoko.sbkinokorpg.battleframe.status.Status;
import com.sbkinoko.sbkinokorpg.game_item.action_item.item.ActionItem;
import com.sbkinoko.sbkinokorpg.mapframe.event.MapEvent;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.GroupOfWindows;

public class UseItemInField {
    /**
     * 袋から使う可能性があるのでplayerBattlesのchooseAlyが使えない
     *
     * @param target 対象
     */
    static public void useInField(GroupOfWindows groupOfWindows,
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
            UseItem.doCure(_fromPlayer,
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

        actionItem.doAfterProcess(
                groupOfWindows.getFromPlayerStatus(),
                groupOfWindows.getPlayer(),
                false,
                groupOfWindows.getSelectedItemPosition());
    }

    public static boolean canUseInField(ActionItem actionItem) {
        int whereCanUse = actionItem.getWhereCanUse();

        return whereCanUse == GameParams.canInBoth ||//フィールドとバトルで使える
                whereCanUse == GameParams.canInField;//フィールドで使える
    }

    static public boolean canHaveTargetInField(int effectType) {
        return effectType == GameParams.EFFECT_TYPE_REVIVE
                || effectType == GameParams.EFFECT_TYPE_HEAL;
    }

    public static int[] getTarget(ActionItem actionItem) {
        if (actionItem.getTargetNum() == 0) {
            return new int[]{-1};
        }
        return null;
    }
}
