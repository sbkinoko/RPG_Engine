package com.sbkinoko.sbkinokorpg.mapframe.window.window_set.amount;

import android.util.Log;

import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.GroupOfWindows;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.shopping.for_sell.ISellStrategy;

public class AmountSell extends WindowAmount {
    public AmountSell(GroupOfWindows groupOfWindows) {
        super(groupOfWindows);
    }

    int maxNum = 0;

    public void openMenu(int itemID) {
        this.itemID = itemID;
        maxNum = groupOfWindows.getPlayer().getHaveItem()[
                groupOfWindows.getSelectedItemPosition()][1];
        groupOfWindows.getWindowDetail().setIsClosed();
        setSelectedTv(menuTV.length - 1);
        resetAmount();
        super.openMenu();
    }

    @Override
    protected void correctAmount() {
        if (maxNum <= getAmount()) {
            final int tmpAmount = maxNum;
            final int amount_10_NUM = (int) Math.floor((float) tmpAmount / 10);
            final String amount_10_Str = amount_10_NUM + "";
            final int amount_1_NUM = tmpAmount - amount_10_NUM * 10;
            final String amount_1_Str = amount_1_NUM + "";

            menuTV[amount_10].setText(amount_10_Str);
            menuTV[amount_1].setText(amount_1_Str);
        }
    }

    @Override
    protected int getTotalPrice() {
        return (getAmount() * ((ISellStrategy) groupOfWindows.getStrategyForList()).getPrice(itemID));

    }

    @Override
    public void useBtA() {
        if (selectedTV != menuTV.length - 1) {
            setSelectedTv(menuTV.length - 1);
            return;
        }

        Log.d("msg", "Amount before money = " + groupOfWindows.getPlayer().getMoney());

        groupOfWindows.getPlayer().addMoney(getTotalPrice());
        groupOfWindows.showMoney();

        ((ISellStrategy) groupOfWindows.getStrategyForList()).decItem(
                itemID,
                getAmount());

        groupOfWindows.getWindowDetail().reloadMenu();


        Log.d("msg", "Amount after money = " + groupOfWindows.getPlayer().getMoney());
        mapFrame.getMapTextBoxWindow().openMenu(new String[]{"まいどあり"});
        closeMenu();
    }
}


