package com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy;

import static com.sbkinoko.sbkinokorpg.mapframe.window.WindowIdList.EQP_FROM_EQP_LIST;

import com.sbkinoko.sbkinokorpg.dataList.List_Equipment;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.item_list.StrategyForBagEQP;

public class StrategyForEQPList extends StrategyForBagEQP {

    @Override
    public int[] getNowList() {
        if (groupOfWindows.isIdBag()) {
            return super.getNowList();
        }

        nowList = new int[3];
        for (int i = 0; i < 3; i++) {
            nowList[i] = groupOfWindows.getSelectedIdPlayerStatus().getEQP(i);
        }
        return nowList;
    }

    @Override
    protected String _getText(int position) {
        if (groupOfWindows.isIdBag()) {
            return super._getText(position);
        }

        return List_Equipment.getNameID(nowList[position]);
    }

    @Override
    public void use_Detail() {
        if (groupOfWindows.isIdBag()) {
            super.use_Detail();
            return;
        }

        groupOfWindows.setEQPPosition(getSelectedItemPosition());
        groupOfWindows.setWindowType(EQP_FROM_EQP_LIST);
        groupOfWindows.getWindowDetail().openMenu(groupOfWindows.getSelectedPlayerID());
    }

    @Override
    protected void playerTap_SpecialProcess(int viewID) {

    }
}
