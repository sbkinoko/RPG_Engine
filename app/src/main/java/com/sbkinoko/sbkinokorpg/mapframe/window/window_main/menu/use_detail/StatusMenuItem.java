package com.sbkinoko.sbkinokorpg.mapframe.window.window_main.menu.use_detail;

import static com.sbkinoko.sbkinokorpg.mapframe.window.WindowIdList.NUM_MapMenu_STATUS;

public class StatusMenuItem extends NeedDetailMenuItem {
    @Override
    public String getTxt() {
        return "ステータス";
    }

    @Override
    protected int getMenuID() {
        return NUM_MapMenu_STATUS;
    }
}
