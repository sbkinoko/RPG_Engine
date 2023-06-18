package com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy;

import com.sbkinoko.sbkinokorpg.mapframe.MapFrame;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.GroupOfWindows;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.WindowPlayer;

public abstract class StrategyForList {
    protected int[] nowList;

    protected GroupOfWindows groupOfWindows;

    public void setGroupOfWindows(GroupOfWindows groupOfWindows) {
        this.groupOfWindows = groupOfWindows;
    }

    public void setNowList(int[] nowList) {
        this.nowList = nowList;
    }

    protected int getSelectedItemPosition() {
        return groupOfWindows.getSelectedItemPosition();
    }

    protected boolean canGetText(int index) {
        if (0 <= index && index < nowList.length) {
            return nowList[index] != 0;
        }
        return false;
    }

    public boolean canSelect(int index) {
        return canGetText(index);
    }

    abstract public int[] getNowList();

    public String getText(int position) {
        if (canGetText(position)) {
            return _getText(position);
        }
        return "";
    }

    abstract protected String _getText(int position);

    public void openMenu_Detail() {
        groupOfWindows.getWindowDetail().setSelectedTv(0);
    }

    public void openMenu_Player() {
        groupOfWindows.getWindowPlayer().openWithFromPlayer();
    }

    public void tapDetailItem(int i) {
        groupOfWindows.getWindowDetail().goToDetailWindow(i);
    }

    abstract public void use_Detail();

    public void useBtB_Detail() {
        groupOfWindows.setPlayerOpen();
        groupOfWindows.getWindowExplanation().closeMenu();
        groupOfWindows.getWindowDetail().resetSelectedTV();
    }

    public void useBtA_Player() {
        groupOfWindows.getWindowPlayer().goToDetailWindow();
    }

    public void useBtB_Player() {
        groupOfWindows.getWindowPlayer().closeMenu();
        groupOfWindows.getMapFrame().mapMainMenuWindow.openMenu();
    }

    public void tapPlayer(int viewID) {
        WindowPlayer windowPlayer = groupOfWindows.getWindowPlayer();
        if (windowPlayer.getSelectedTV() == viewID) {
            windowPlayer.useBtA();
            return;
        }

        if (windowPlayer.isOpen()) {
            //開いているのでターゲットを変更するだけで良い
            windowPlayer.setSelectedTv(viewID);
            return;
        }

        playerTap_SpecialProcess(viewID);

        MapFrame mapFrame = groupOfWindows.getMapFrame();
        if (!mapFrame.window_explanation.getUseOrGiveFlag() &&
                !mapFrame.window_explanation.isUseFlag()) {
            //openMenu_Player();
            groupOfWindows.setPlayerOpen();
            mapFrame.window_explanation.closeMenu();
            groupOfWindows.getWindowPlayer().setSelectedTv(viewID);
            return;
        }

        groupOfWindows.setPlayerOpen();
        groupOfWindows.getMapFrame().window_explanation.closeMenu();
    }

    /**
     * タップしたときの特別な処理
     *
     * @param viewID
     */
    protected abstract void playerTap_SpecialProcess(int viewID);

    protected int getSelectedItemId() {
        return nowList[getSelectedItemPosition()];
    }


    public void useBtM() {
        groupOfWindows.getMapFrame().closeAllMenu();
    }


}


