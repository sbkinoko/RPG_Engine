package com.sbkinoko.sbkinokorpg.battleframe.action_choice;

import com.sbkinoko.sbkinokorpg.battleframe.BattleConst;
import com.sbkinoko.sbkinokorpg.battleframe.BattleFrame;
import com.sbkinoko.sbkinokorpg.battleframe.BattleSystem;
import com.sbkinoko.sbkinokorpg.battleframe.status.PlayerStatus;
import com.sbkinoko.sbkinokorpg.battleframe.status.Status;
import com.sbkinoko.sbkinokorpg.dataList.item.List_Tool;

public class ToolPlayerActionList extends PlayerActionList {
    @Override
    public int[] getActionItemList(Status nowPlayer) {
        int[] _nowList = new int[((PlayerStatus) nowPlayer).getHaveTool().length];//null回避
        System.arraycopy(((PlayerStatus) nowPlayer).getHaveTool(), 0, _nowList, 0, _nowList.length);
        return _nowList;
    }

    @Override
    public int getThisTurnAction() {
        return BattleConst.Action_Tool;
    }

    @Override
    public int getItemPosition(Status nowPlayer) {
        return nowPlayer.getLastSelectedTool();
    }

    @Override
    public void setThisTurnChoice(Status nowPlayer, int thisTurnChoice) {
        nowPlayer.setLastSelectedTool(thisTurnChoice);
    }

    public void backFromTarget(BattleFrame battleFrame, BattleSystem battleSystem) {
        battleFrame.battleWindow_chooseItem.openMenu(
                battleSystem.getPlayer(battleSystem.getWhoseActionSelect()),
                new List_Tool()
        );
    }
}
