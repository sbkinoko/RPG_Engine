package com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.shopping.for_sell;

import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.GroupOfWindows;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.amount.AmountSell;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.item_list.StrategyForBagEQP;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.shopping.for_sell.sell_item.ShoppingEQPData;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.shopping.for_sell.sell_item.ShoppingItemData;

public class StrategyForSellEQP extends StrategyForBagEQP
        implements ISellStrategy {
    ShoppingItemData shoppingItemData;


    @Override
    public void setGroupOfWindows(GroupOfWindows groupOfWindows) {
        super.setGroupOfWindows(groupOfWindows);

        groupOfWindows.setWindowAmount(new AmountSell(groupOfWindows));
        shoppingItemData = new ShoppingEQPData(groupOfWindows.getPlayer());
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
    protected void playerTap_SpecialProcess(int viewID) {

    }

    @Override
    public void tapDetailItem(int i) {
        groupOfWindows.tapMerchandiseWindow(i);
    }

    @Override
    public void decItem(int itemID, int itemNum) {
        groupOfWindows.getPlayer().decEQP(itemID, itemNum);
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
