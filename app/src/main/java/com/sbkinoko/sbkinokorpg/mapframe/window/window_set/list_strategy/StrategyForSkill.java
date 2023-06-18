package com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy;

import com.sbkinoko.sbkinokorpg.battleframe.status.PlayerStatus;
import com.sbkinoko.sbkinokorpg.dataList.item.List_Skill;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.GroupOfWindows;

public class StrategyForSkill extends StrategyForList {
    public StrategyForSkill(GroupOfWindows groupOfWindows) {
        this.groupOfWindows = groupOfWindows;
    }

    @Override
    public void use_Detail() {
        if (getSelectedItemId() == 0) {
            return;
        }

        groupOfWindows.getWindowDetail().saveSelectedItem();
        groupOfWindows.getWindowExplanation().setUse();
    }

    @Override
    public void playerTap_SpecialProcess(int viewID) {

    }

    @Override
    public String _getText(int position) {
        String txt = "";
        int itemID = nowList[position];
        if (itemID == 0) {
            return txt;
        }
        txt = new List_Skill().getName(itemID);
        return txt;
    }

    @Override
    public int[] getNowList() {
        PlayerStatus nowPlayer = groupOfWindows.getIdStatus();
        return nowList = nowPlayer.getSkills();
    }
}
