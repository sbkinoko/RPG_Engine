package com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.shopping.for_sell;

import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.GroupOfWindows;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.amount.AmountSell;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.item_list.StrategyForBagTool;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.shopping.for_sell.sell_item.ShoppingItemData;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.shopping.for_sell.sell_item.ShoppingToolData;

public class StrategyForSellTool extends StrategyForBagTool
        implements ISellStrategy {
    ShoppingItemData shoppingItemData;

    @Override
    public void setGroupOfWindows(GroupOfWindows groupOfWindows) {
        super.setGroupOfWindows(groupOfWindows);

        groupOfWindows.setWindowAmount(new AmountSell(groupOfWindows));
        shoppingItemData = new ShoppingToolData(groupOfWindows.getPlayer());
    }

    @Override
    public String _getText(int position) {
        int itemID = nowList[position];

        return shoppingItemData.getSellPrice(itemID) + ":" + super._getText(position);
    }

    @Override
    public void use_Detail() {
        groupOfWindows.getWindowAmount().openMenu(getSelectedItemId());
    }

    @Override
    public void useBtB_Detail() {
        groupOfWindows.goToSelectSellItem();
    }

    @Override
    public void playerTap_SpecialProcess(int viewID) {
        throw new RuntimeException("買い物中にプレイヤーのタップは起きない");
    }

    @Override
    public void tapDetailItem(int i) {
        groupOfWindows.tapMerchandiseWindow(i);
    }

    @Override
    public void decItem(int itemID, int itemNum) {
        groupOfWindows.getPlayer().decItem(itemID, itemNum);
    }

    @Override
    public int getPrice(int itemID) {
        return shoppingItemData.getSellPrice(itemID);
    }

    @Override
    public void useBtM() {
        groupOfWindows.getWindowDetail().useBtB();
    }
}
