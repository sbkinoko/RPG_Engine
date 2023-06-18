package com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy;

import static com.sbkinoko.sbkinokorpg.mapframe.window.WindowIdList.EQP_FROM_EQP_LIST;
import static com.sbkinoko.sbkinokorpg.mapframe.window.WindowIdList.EQP_FROM_STATUS;
import static com.sbkinoko.sbkinokorpg.mapframe.window.WindowIdList.NUM_MapMenu_EQP_LIST;
import static com.sbkinoko.sbkinokorpg.mapframe.window.WindowIdList.NUM_MapMenu_STATUS;

import com.sbkinoko.sbkinokorpg.battleframe.status.PlayerStatus;
import com.sbkinoko.sbkinokorpg.dataList.List_Equipment;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.GroupOfWindows;

public class StrategyForEQP extends StrategyForList {
    int[][] equipmentList;


    @Override
    public void setGroupOfWindows(GroupOfWindows groupOfWindows) {
        super.setGroupOfWindows(groupOfWindows);
        getNowList();
    }

    @Override
    public void use_Detail() {
        if (!groupOfWindows.canUse()) {
            return;
        }

        int where = groupOfWindows.getEQPPosition();
        PlayerStatus tmpStatus = groupOfWindows.getFromPlayerStatus();

        //今の装備を収納
        groupOfWindows.getPlayer().addEQP(tmpStatus.getEQP(where), 1);
        //次の装備を取り出す
        groupOfWindows.getPlayer().decEQP(getSelectedItemId(), 1);
        //装備を装着
        tmpStatus.changeEQP(getSelectedItemId(), where);

        useBtB_Detail();
    }

    @Override
    public String _getText(int position) {
        int data = nowList[position];
        String txt = "";
        if (data == 0) {
            return txt;
        }
        txt = List_Equipment.getNameID(data);
        if (data != 1) {
            txt += "×" + equipmentList[position][1];
        }
        return txt;
    }

    @Override
    public int[] getNowList() {
        equipmentList = groupOfWindows.getPlayer().getHaveEQP();
        return nowList = List_Equipment.getVariableEQPList(
                equipmentList,
                groupOfWindows.getEQPPosition());
    }

    @Override
    public void useBtB_Detail() {
        switch (groupOfWindows.getWindowDetail().getWindowType()) {
            case EQP_FROM_STATUS:
                groupOfWindows.setWindowType(NUM_MapMenu_STATUS);
                break;
            case EQP_FROM_EQP_LIST:
                groupOfWindows.setWindowType(NUM_MapMenu_EQP_LIST);
                break;
        }

        groupOfWindows.getWindowDetail().openMenu();
    }

    @Override
    public void playerTap_SpecialProcess(int viewID) {
        //装備画面を開いていたらステータス画面に戻す
        switch (groupOfWindows.getWindowDetail().getWindowType()) {
            case EQP_FROM_STATUS:
                groupOfWindows.setWindowType(NUM_MapMenu_STATUS);
                break;
            case EQP_FROM_EQP_LIST:
                groupOfWindows.setWindowType(NUM_MapMenu_EQP_LIST);
                break;
        }

        groupOfWindows.getWindowPlayer().setSelectedTv(viewID);

    }
}
