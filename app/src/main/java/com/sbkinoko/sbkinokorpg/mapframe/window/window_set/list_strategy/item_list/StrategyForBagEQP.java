package com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.item_list;

import com.sbkinoko.sbkinokorpg.dataList.List_Equipment;
import com.sbkinoko.sbkinokorpg.mapframe.player.Player;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.StrategyForList;

public abstract class StrategyForBagEQP extends StrategyForList {
    @Override
    public int[] getNowList() {
        nowList = new int[List_Equipment.getListLength()];
        for (int i = 0; i < groupOfWindows.getPlayer().getHaveEQP().length - 1; i++) {
            nowList[i] = groupOfWindows.getPlayer().getHaveEQP()[i + 1][0];
        }
        return nowList;
    }

    @Override
    protected String _getText(int position) {
        String txt = List_Equipment.getNameID(nowList[position]);
        int num = groupOfWindows.getPlayer().getHaveEQP()[position + 1][1];
        if (num <= Player.MAX_ITEM_NUM) {
            txt += "×" + num;
        } else {
            txt += "×" + Player.MAX_ITEM_NUM + "+";
        }
        return txt;
    }

    @Override
    public void use_Detail() {
        if (getSelectedItemId() == 0) {
            return;
        }

        groupOfWindows.getWindowDetail().saveSelectedItem();
        groupOfWindows.getWindowExplanation().setEQP();
    }

    @Override
    public void tapDetailItem(int i) {
        super.tapDetailItem(i);
    }

    @Override
    public void openMenu_Detail() {
        if (nowList[0] == 0) {
            useBtB_Detail();
            return;
        }

        groupOfWindows.getWindowDetail().setSelectedTv(0);
    }
}
