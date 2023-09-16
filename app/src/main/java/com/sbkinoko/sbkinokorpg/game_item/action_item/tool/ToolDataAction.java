package com.sbkinoko.sbkinokorpg.game_item.action_item.tool;

import com.sbkinoko.sbkinokorpg.game_item.action_item.item.ActionItemData;
import com.sbkinoko.sbkinokorpg.gameparams.EffectType;
import com.sbkinoko.sbkinokorpg.gameparams.WhereCanUse;

public class ToolDataAction extends ActionItemData {

    boolean vanish;
    int price;

    public ToolDataAction(String name, WhereCanUse where, EffectType effect, int attribute, int power,
                          int targetNum, boolean vanish) {
        super(name, where, effect, attribute, power, targetNum);
        this.vanish = vanish;
        this.price = -1;
    }

    public ToolDataAction(String name, WhereCanUse where, EffectType effect, int attribute, int power,
                          int targetNum, boolean vanish, int price) {
        super(name, where, effect, attribute, power, targetNum);
        this.vanish = vanish;
        this.price = price;
    }

    public boolean isVanish() {
        return vanish;
    }

    public int getPrice() {
        return price;
    }
}
