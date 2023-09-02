package com.sbkinoko.sbkinokorpg.game_item.action_item.n_atk;

import com.sbkinoko.sbkinokorpg.game_item.action_item.item.ActionItemData;
import com.sbkinoko.sbkinokorpg.gameparams.WhereCanUse;

public class N_ATKData extends ActionItemData {
    public N_ATKData(String name, WhereCanUse where, int effect, int attribute, int power,
                     int targetNum) {
        super(name, where, effect, attribute, power, targetNum);
    }
}
