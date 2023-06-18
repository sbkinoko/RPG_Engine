package com.sbkinoko.sbkinokorpg.mapframe.window.window_main.menu.use_detail;

import com.sbkinoko.sbkinokorpg.mapframe.window.window_main.menu.MainMenuItem;

public abstract class NeedDetailMenuItem extends MainMenuItem {

    protected abstract int getMenuID();

    @Override
    public void useBtA() {
        mapFrame.window_player.openMenu(
                getMenuID());
        mapFrame.getMapWindow_list_detail().setNowList();
    }
}
