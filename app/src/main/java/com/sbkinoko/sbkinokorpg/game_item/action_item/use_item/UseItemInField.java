package com.sbkinoko.sbkinokorpg.game_item.action_item.use_item;

import static com.sbkinoko.sbkinokorpg.gameparams.EffectType.EFFECT_TYPE_HEAL;
import static com.sbkinoko.sbkinokorpg.gameparams.EffectType.EFFECT_TYPE_REVIVE;

import android.util.Log;

import com.sbkinoko.sbkinokorpg.battleframe.status.PlayerStatus;
import com.sbkinoko.sbkinokorpg.battleframe.status.Status;
import com.sbkinoko.sbkinokorpg.game_item.action_item.item.ActionItem;
import com.sbkinoko.sbkinokorpg.gameparams.EffectType;
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
            _fromPlayer = new PlayerStatus("", 0, groupOfWindows.getContext());
        }
        _fromPlayer.setChooseAly(target);
        ActionItem actionItem = groupOfWindows.getActionItem();

        EffectType itemEffectType = actionItem.getEffect();

        //回復アイテムだったので回復を呼び出し
        if (canHaveTargetInField(itemEffectType)) {
            UseItem.doCure(_fromPlayer,
                    target,
                    groupOfWindows.getPlayerStatuses(),
                    actionItem);
        } else {
            switch (itemEffectType) {
                case EFFECT_TYPE_MOVE:
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

    static public boolean canHaveTargetInField(EffectType effectType) {
        return effectType == EFFECT_TYPE_REVIVE
                || effectType == EFFECT_TYPE_HEAL;
    }

    public static int[] getTarget(ActionItem actionItem) {
        if (actionItem.getTargetNum() == 0) {
            return new int[]{-1};
        }
        return null;
    }
}
