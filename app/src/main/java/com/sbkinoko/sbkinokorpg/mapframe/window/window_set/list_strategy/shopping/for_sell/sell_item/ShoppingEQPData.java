package com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.shopping.for_sell.sell_item;

import static com.sbkinoko.sbkinokorpg.mapframe.window.WindowIdList.SELL_EQP;

import com.sbkinoko.sbkinokorpg.dataList.List_Equipment;
import com.sbkinoko.sbkinokorpg.mapframe.Player;

public class ShoppingEQPData extends ShoppingItemData {
    public ShoppingEQPData(Player player) {
        super("装備", player);
    }


    @Override
    public String getItemName(int id) {
        if (1000 < id) {
            id -= 1000;
        }
        return List_Equipment.getNameID(id);
    }

    @Override
    public int getBuyPrice(int id) {
        if (1000 < id) {
            id -= 1000;
        }
        return List_Equipment.getPrice(id);
    }

    @Override
    public int getSellWindowType() {
        return SELL_EQP;
    }
}
