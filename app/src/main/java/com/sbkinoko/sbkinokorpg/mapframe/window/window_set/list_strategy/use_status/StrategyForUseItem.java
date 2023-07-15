package com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.use_status;

import com.sbkinoko.sbkinokorpg.game_item.action_item.use_item.UseItem;
import com.sbkinoko.sbkinokorpg.repository.PlayerToolRepository;

public class StrategyForUseItem extends StrategyForNeedStatus {


    @Override
    public void tapDetailItem(int i) {
        groupOfWindows.getWindowPlayer().goToUseOrGive();
    }

    @Override
    public void _useBtB_Player() {
        groupOfWindows.getWindowPlayer().resetAllSelect();
        groupOfWindows.getWindowDetail().reloadMenu();
        groupOfWindows.getWindowPlayer().goToUseOrGive();
    }

    @Override
    public void _useBtA_Player() {
        groupOfWindows.getWindowPlayer().goToUseWindow();
    }

    @Override
    public int[] _getNowList() {
        setStatusList();
        if (groupOfWindows.isFromPlayerBag()) {


            int[][] bagItemList = groupOfWindows.getPlayer().getHaveItem();
            nowList = new int[bagItemList.length];
            for (int i = 0; i < nowList.length; i++) {
                nowList[i] = bagItemList[i][0];
            }
            return nowList;
        }
        
        return PlayerToolRepository.getPlayerToolRepository().getAllItem(
                groupOfWindows.getFromPlayer()
        );
    }

    @Override
    public void _openMenu_Player() {
        groupOfWindows.getWindowPlayer().openWithComment("使う");
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
