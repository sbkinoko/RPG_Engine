package com.sbkinoko.sbkinokorpg.game_item.eqp_item;

import com.sbkinoko.sbkinokorpg.battleframe.status.battle_params.BattleParam;

public class EQPItemData {
    String name;
    int where, type, price;
    BattleParam[] upStatusList;

    public EQPItemData(String name, int where, int type, int price, BattleParam[] upStatusList) {
        this.name = name;
        this.type = type;
        this.where = where;
        this.price = price;
        this.upStatusList = upStatusList;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public int getWhere() {
        return where;
    }

    public int getPrice() {
        return price;
    }

    public BattleParam[] getUpStatusList() {
        return upStatusList;
    }

}
