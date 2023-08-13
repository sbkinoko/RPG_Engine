package com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.item_list;

import com.sbkinoko.sbkinokorpg.dataList.item.List_Tool;
import com.sbkinoko.sbkinokorpg.mapframe.player.Player;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.StrategyForList;

public abstract class StrategyForBagTool extends StrategyForList {


    @Override
    public String _getText(int position) {
        int itemID = nowList[position];
        String txt = new List_Tool().getName(itemID);
        int num = groupOfWindows.getPlayer().getToolNumAt(position);
        if (num <= Player.MAX_ITEM_NUM) {
            txt += "×" + num;
        } else {
            txt += "×" + Player.MAX_ITEM_NUM + "+";
        }
        return txt;
    }

    @Override
    public int[] getNowList() {

        int[][] bagItemList = groupOfWindows.getPlayer().getAllItem();
        nowList = new int[bagItemList.length];
        for (int i = 0; i < nowList.length; i++) {
            nowList[i] = bagItemList[i][0];
        }
        return nowList;
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
