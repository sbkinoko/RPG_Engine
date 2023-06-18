package com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.use_status;

import com.sbkinoko.sbkinokorpg.battleframe.status.PlayerStatus;
import com.sbkinoko.sbkinokorpg.dataList.List_Equipment;
import com.sbkinoko.sbkinokorpg.mapframe.UseUpInfo;

public class StrategyForEQPTo extends StrategyForNeedStatus {

    @Override
    protected void playerTap_SpecialProcess(int viewID) {

    }

    @Override
    public void _useBtA_Player() {

        groupOfWindows.setToPlayer();

        int itemID = getSelectedItemId();
        int where = List_Equipment.getWhere(itemID);

        PlayerStatus tmpStatus = groupOfWindows.getPlayerStatuses()[groupOfWindows.getToPlayer()];

        //今の装備を収納
        groupOfWindows.getPlayer().addEQP(tmpStatus.getEQP(where), 1);
        //次の装備を取り出す
        groupOfWindows.getPlayer().decEQP(itemID, 1);
        //装備を装着
        tmpStatus.changeEQP(itemID, where);

        groupOfWindows.getMapFrame().getMapTextBoxWindow().openMenu(
                new UseUpInfo(List_Equipment.getNameID(itemID) + "を装備した", true));
    }

    @Override
    public void _useBtB_Player() {
        groupOfWindows.getWindowPlayer().goToEQPList();
    }

    @Override
    public void _openMenu_Player() {
        groupOfWindows.getWindowPlayer().openWithComment("着ける");
    }

    @Override
    public int[] _getNowList() {
        setStatusList();

        nowList = new int[List_Equipment.getListLength()];
        for (int i = 1; i < groupOfWindows.getPlayer().getHaveEQP().length; i++) {
            nowList[i - 1] = groupOfWindows.getPlayer().getHaveEQP()[i][0];
        }

        return nowList;
    }
}