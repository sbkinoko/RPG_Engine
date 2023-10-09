package com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.shopping;

import android.content.Context;
import android.util.Log;

import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.GroupOfWindows;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.amount.AmountBuy;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.StrategyForList;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.shopping.for_sell.sell_item.ShoppingItemData;
import com.sbkinoko.sbkinokorpg.application.MyEntryPoints;
import com.sbkinoko.sbkinokorpg.repository.bagrepository.BagRepository;

import dagger.hilt.EntryPoints;

public class StrategyForBuy extends StrategyForList {

    BagRepository bagRepository;

    public StrategyForBuy(Context context) {
        MyEntryPoints myEntryPoints =
                EntryPoints.get(context.getApplicationContext(), MyEntryPoints.class);
        bagRepository = myEntryPoints.bagRepository();
    }

    @Override
    public void setGroupOfWindows(GroupOfWindows groupOfWindows) {
        super.setGroupOfWindows(groupOfWindows);

        groupOfWindows.setWindowAmount(new AmountBuy(groupOfWindows));
    }

    @Override
    public void useBtB_Detail() {
        groupOfWindows.closeShoppingWindow();
    }

    @Override
    protected void playerTap_SpecialProcess(int viewID) {

    }

    @Override
    public void tapDetailItem(int i) {
        groupOfWindows.tapMerchandiseWindow(i);
    }

    private ShoppingItemData getShoppingItemData(int itemID) {
        if (itemID < 1000) {
            return ShoppingItemData.getShoppingItem(0, groupOfWindows.getPlayer());
        }
        return ShoppingItemData.getShoppingItem(1, groupOfWindows.getPlayer());

    }

    @Override
    public void use_Detail() {
        int itemID = getSelectedItemId();

        int itemPrice = getShoppingItemData(itemID).getBuyPrice(itemID);

        if (bagRepository.isMany(itemID)) {
            groupOfWindows.getMapFrame().getMapTextBoxWindow().openMenu(new String[]{"もう十分もってるんじゃないか?"});
        } else if (itemPrice <= groupOfWindows.getPlayer().getMoney()) {
            groupOfWindows.getWindowAmount().openMenu(itemID);
        } else {
            groupOfWindows.getMapFrame().getMapTextBoxWindow().openMenu(new String[]{"お金が足りないよ"});
        }
        Log.d("msg", "Shopping money = " + groupOfWindows.getPlayer().getMoney());
    }

    @Override
    public String _getText(int position) {
        int itemID = nowList[position];

        return getShoppingItemData(itemID).getMerchandiseText(itemID);
    }

    @Override
    public int[] getNowList() {
        return nowList;
    }

    public void setItemData(int[] itemData) {
        this.nowList = itemData;
    }

    @Override
    public void useBtM() {
        groupOfWindows.getWindowDetail().useBtB();
    }
}
