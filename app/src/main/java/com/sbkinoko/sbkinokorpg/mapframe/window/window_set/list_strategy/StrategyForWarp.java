package com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy;

import com.sbkinoko.sbkinokorpg.game_item.action_item.item.ActionItem;
import com.sbkinoko.sbkinokorpg.game_item.action_item.item.WarpItem;
import com.sbkinoko.sbkinokorpg.mapframe.event.MapEvent;
import com.sbkinoko.sbkinokorpg.mapframe.map.mapdata.MapChangeDataList;

//todo warp中にMを押したら全部閉じるのをメッセージを出す
public class StrategyForWarp extends StrategyForList {

    @Override
    public int[] getNowList() {
        return nowList;
    }

    ActionItem warpItem;

    public void setWarpItem(ActionItem warpItem) {
        this.warpItem = warpItem;
        nowList = ((WarpItem) warpItem).getTownId();
    }

    public ActionItem getWarpItem() {
        return warpItem;
    }

    @Override
    public String _getText(int position) {
        int id = getNowList()[position];
        if (id == -1) {
            return "キャンセル";
        }
        return MapChangeDataList.getMapChangeData(id).getName();
    }

    @Override
    public void useBtB_Detail() {
        closeWarpMenu();
    }

    @Override
    protected void playerTap_SpecialProcess(int viewID) {

    }

    private void closeWarpMenu() {
        groupOfWindows.getMapFrame().closeAllMenu();
        groupOfWindows.getMapFrame().getMapTextBoxWindow().openMenu(new String[]{"ワープを止めた"});
    }

    @Override
    public void use_Detail() {
        int townID = getSelectedItemId();
        if (townID == -1) {
            closeWarpMenu();
            return;
        }

        new MapEvent(groupOfWindows.getMapFrame()).doWarp(townID, groupOfWindows);
    }
}
