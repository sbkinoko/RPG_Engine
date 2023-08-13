package com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.shopping.for_sell.sell_item;

import com.sbkinoko.sbkinokorpg.mapframe.player.Player;

public abstract class ShoppingItemData {
    protected String name;
    protected Player player;

    ShoppingItemData(String name, Player player) {
        this.name = name;
        this.player = player;
    }

    public String getName() {
        return name;
    }

    public abstract String getItemName(int id);

    public abstract int getBuyPrice(int id);

    public int getSellPrice(int id) {
        return (int) (getBuyPrice(id) * 0.8);
    }

    public String getMerchandiseText(int id) {
        return getItemName(id) + ":" + getBuyPrice(id);
    }

    public abstract int getSellWindowType();

    public static ShoppingItemData getShoppingItem(int selectedTV, Player player) {
        ShoppingItemData shoppingItemData;
        switch (selectedTV) {
            case 0:
                shoppingItemData = new ShoppingToolData(player);
                break;
            case 1:
                shoppingItemData = new ShoppingEQPData(player);
                break;
            default:
                throw new RuntimeException();
        }

        return shoppingItemData;
    }


}
