package com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy;

import com.sbkinoko.sbkinokorpg.battleframe.status.PlayerStatus;
import com.sbkinoko.sbkinokorpg.dataList.player_status.JobStatus;
import com.sbkinoko.sbkinokorpg.dataList.player_status.List_JobStatus;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.GroupOfWindows;

public class StrategyForJob extends StrategyForList {

    @Override
    public int[] getNowList() {
        int jobNUM = 0;
        PlayerStatus status = groupOfWindows.getSelectedIdPlayerStatus();
        nowList = new int[List_JobStatus.JOB_NUM];
        for (int i = 1; i <= List_JobStatus.JOB_NUM; i++) {
            if (List_JobStatus.getStatusList(i).canChangeJob(status)) {
                nowList[jobNUM] = i;
                jobNUM++;
            }
        }
        return nowList;
    }

    @Override
    protected String _getText(int position) {
        return List_JobStatus.getStatusList(nowList[position]).getName();
    }

    @Override
    public void use_Detail() {
        JobStatus job = List_JobStatus.getStatusList(getSelectedItemId());
        groupOfWindows.getSelectedIdPlayerStatus().changeJob(job);
        groupOfWindows.getWindowText().openMenu(new String[]{job.getName() + "に転職した"});
    }

    @Override
    protected void playerTap_SpecialProcess(int viewID) {

    }

    @Override
    public void useBtM() {
        groupOfWindows.getWindowDetail().useBtB();
    }

    @Override
    public void useBtB_Player() {
        groupOfWindows.getPlayer().setNextEventFlag(true);
        groupOfWindows.getPlayer().setEventFlag(new int[]{2, 5});

        groupOfWindows.getWindowPlayer().closeMenu();
        groupOfWindows.getMapFrame().checkNextEvent();
    }

    @Override
    public void setGroupOfWindows(GroupOfWindows groupOfWindows) {
        groupOfWindows.setSelectedPlayerID(0);
        super.setGroupOfWindows(groupOfWindows);
        getNowList();
    }
}
