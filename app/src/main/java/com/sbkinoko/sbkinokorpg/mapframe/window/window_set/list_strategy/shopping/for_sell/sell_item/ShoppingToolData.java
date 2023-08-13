package com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.shopping.for_sell.sell_item;

import static com.sbkinoko.sbkinokorpg.mapframe.window.WindowIdList.SELL_TOOL;

import com.sbkinoko.sbkinokorpg.dataList.item.List_Tool;
import com.sbkinoko.sbkinokorpg.mapframe.player.Player;

public class ShoppingToolData extends ShoppingItemData {

    public ShoppingToolData(Player player) {
        super("道具", player);
    }

    @Override
    public String getItemName(int id) {
        return List_Tool.getToolAt(id).getName();
    }

    @Override
    public int getBuyPrice(int id) {
        return List_Tool.getToolAt(id).getPrice();
    }

    @Override
    public int getSellWindowType() {
        return SELL_TOOL;
    }
}
