package com.sbkinoko.sbkinokorpg.game_item.action_item.item;

import com.sbkinoko.sbkinokorpg.battleframe.condition.ConditionData;

public interface ConditionItem {
    ConditionData getConditionId();

    int getRestTurn();
}
