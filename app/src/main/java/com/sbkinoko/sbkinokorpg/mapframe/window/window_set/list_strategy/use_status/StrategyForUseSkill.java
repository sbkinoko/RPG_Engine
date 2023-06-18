package com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.use_status;

import com.sbkinoko.sbkinokorpg.battleframe.status.PlayerStatus;
import com.sbkinoko.sbkinokorpg.dataList.item.List_Skill;
import com.sbkinoko.sbkinokorpg.game_item.action_item.UseItem;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.StrategyForSkill;

public class StrategyForUseSkill extends StrategyForNeedStatus {

    @Override
    public void _useBtB_Player() {
        groupOfWindows.getWindowPlayer().goToSkillList();
    }

    @Override
    public void _useBtA_Player() {
        PlayerStatus tmpStatus = groupOfWindows.getFromPlayerStatus();
        int skillID = tmpStatus.getSkills()[getSelectedItemPosition()];
        if (tmpStatus.getMP() < new List_Skill().getNeedMP(skillID)) {
            groupOfWindows.getMapFrame().getMapTextBoxWindow().openMenu(new String[]{"MPが足りません"});
            return;
        }
        groupOfWindows.getWindowPlayer().goToUseWindow();
    }

    @Override
    public void _openMenu_Player() {
        groupOfWindows.getWindowPlayer().openWithComment("使う");
    }

    @Override
    public int[] _getNowList() {
        setStatusList();
        nowList = new StrategyForSkill(groupOfWindows).getNowList();
        return nowList;
    }

    @Override
    public void tapPlayer(int viewID) {
        if (UseItem.canSelectALY(
                groupOfWindows.getActionItem().getEffect(),
                groupOfWindows.getPlayerStatuses()[viewID])) {
            super.tapPlayer(viewID);
        }
    }
}
