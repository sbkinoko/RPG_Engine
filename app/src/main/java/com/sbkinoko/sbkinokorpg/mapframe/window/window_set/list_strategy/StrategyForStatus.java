package com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy;

import static com.sbkinoko.sbkinokorpg.mapframe.window.WindowIdList.EQP_FROM_STATUS;

import com.sbkinoko.sbkinokorpg.battleframe.status.PlayerStatus;
import com.sbkinoko.sbkinokorpg.battleframe.status.Status;

import java.util.Arrays;

public class StrategyForStatus extends StrategyForList {
    String[] statusList;

    @Override
    public void use_Detail() {
        groupOfWindows.getWindowDetail().saveSelectedItem();

        groupOfWindows.setEQPPosition(
                getSelectedItemPosition() - Status.PARAM_NUM);
        groupOfWindows.setWindowType(EQP_FROM_STATUS);
        groupOfWindows.getWindowDetail().openMenu(groupOfWindows.getSelectedPlayerID());
    }

    @Override
    protected void playerTap_SpecialProcess(int viewID) {

    }

    @Override
    public String _getText(int position) {
        return statusList[position];
    }

    @Override
    protected boolean canGetText(int index) {
        return index < groupOfWindows.getSelectedIdPlayerStatus().getStatuses().length;
    }

    public void setStatusList() {
        PlayerStatus tmpStatus = groupOfWindows.getSelectedIdPlayerStatus();
        statusList = tmpStatus.getStatuses();
    }

    @Override
    public int[] getNowList() {
        setStatusList();
        nowList = new int[statusList.length];
        Arrays.fill(nowList, 1);
        return nowList;
    }

    @Override
    public void openMenu_Detail() {
        groupOfWindows.getWindowDetail().setSelectedTv(Status.PARAM_NUM);
    }

    @Override
    public boolean canSelect(int index) {
        return Status.PARAM_NUM - 1 < index && index < nowList.length;
    }

    @Override
    public void tapDetailItem(int i) {
        groupOfWindows.getWindowDetail().goToStatusWindow(i);
    }
}
