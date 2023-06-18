package com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.shopping.for_sell;

public interface ISellStrategy {
    void decItem(int itemID, int itemNum);

    int getPrice(int itemID);
}
