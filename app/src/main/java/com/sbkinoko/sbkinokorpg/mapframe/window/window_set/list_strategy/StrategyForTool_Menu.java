package com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy;

import com.sbkinoko.sbkinokorpg.dataList.item.List_Tool;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.item_list.StrategyForBagTool;

public class StrategyForTool_Menu extends StrategyForBagTool {


    @Override
    public void use_Detail() {
        if (getSelectedItemId() == 0) {
            return;
        }
        groupOfWindows.getWindowDetail().saveSelectedItem();
        groupOfWindows.getWindowExplanation().setUseOrGive();
    }

    @Override
    protected void playerTap_SpecialProcess(int viewID) {

    }

    @Override
    public String _getText(int position) {
        if (groupOfWindows.isIdBag()) {
            return super._getText(position);
        }
        int itemID = nowList[position];
        return new List_Tool().getName(itemID);
    }

    @Override
    public int[] getNowList() {
        if (groupOfWindows.isIdBag()) {
            return super.getNowList();
        }

        nowList = groupOfWindows.getSelectedIdPlayerStatus().getAllTool();
        return nowList;
    }
}
