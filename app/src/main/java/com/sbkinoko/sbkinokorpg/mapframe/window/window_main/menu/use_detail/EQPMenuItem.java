package com.sbkinoko.sbkinokorpg.mapframe.window.window_main.menu.use_detail;

import static com.sbkinoko.sbkinokorpg.mapframe.window.WindowIdList.NUM_MapMenu_EQP_LIST;

public class EQPMenuItem extends NeedDetailMenuItem {
    @Override
    public String getTxt() {
        return "装備";
    }

    @Override
    protected int getMenuID() {
        return NUM_MapMenu_EQP_LIST;
    }
}
