package com.sbkinoko.sbkinokorpg.mapframe.window.window_set.amount;

import android.util.Log;

import com.sbkinoko.sbkinokorpg.dataList.List_Equipment;
import com.sbkinoko.sbkinokorpg.dataList.item.List_Tool;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.GroupOfWindows;

public class AmountBuy extends WindowAmount {
    public AmountBuy(GroupOfWindows groupOfWindows) {
        super(groupOfWindows);
    }

    public void openMenu(int itemID) {
        this.itemID = itemID;
        groupOfWindows.getWindowDetail().setIsClosed();
        setSelectedTv(menuTV.length - 1);
        resetAmount();
        super.openMenu();
    }

    @Override
    protected void correctAmount() {
        //所持金より安いのでそのまま購入
        if (getTotalPrice() <= groupOfWindows.getPlayer().getMoney()) {
            return;
        }

        Log.d("msg", "Amount totalPrice:" + getTotalPrice());
        final int tmpAmount = (int) Math.floor((float) groupOfWindows.getPlayer().getMoney() / getItemPrice());
        final int amount_10_NUM = (int) Math.floor((float) tmpAmount / 10);
        final String amount_10_Str = amount_10_NUM + "";
        final int amount_1_NUM = tmpAmount - amount_10_NUM * 10;
        final String amount_1_Str = amount_1_NUM + "";

        menuTV[amount_10].setText(amount_10_Str);
        menuTV[amount_1].setText(amount_1_Str);
    }

    @Override
    protected int getTotalPrice() {
        return getAmount() * getItemPrice();
    }

    private int getItemPrice() {
        if (itemID < 1000) {
            return new List_Tool().getPrice(itemID);
        } else {
            return List_Equipment.getPrice(itemID - 1000);
        }
    }

    @Override
    public void useBtA() {
        if (selectedTV != menuTV.length - 1) {
            setSelectedTv(menuTV.length - 1);
            return;
        }

        Log.d("msg", "Amount before money = " + groupOfWindows.getPlayer().getMoney());

        if (itemID < 1000) {
            groupOfWindows.getPlayer().addItem(itemID, getAmount());
        } else {
            groupOfWindows.getPlayer().addEQP(itemID - 1000, getAmount());
        }

        groupOfWindows.getPlayer().decMoney(getTotalPrice());
        groupOfWindows.showMoney();

        Log.d("msg", "Amount after money = " + groupOfWindows.getPlayer().getMoney());
        mapFrame.getMapTextBoxWindow().openMenu(new String[]{"まいどあり"});
        closeMenu();
    }
}
