package com.sbkinoko.sbkinokorpg.game_item.action_item.item;

import com.sbkinoko.sbkinokorpg.battleframe.condition.ConditionData;

public interface ConditionItemData {
    ConditionData getConditionId();

    int getRestTurn();
}
