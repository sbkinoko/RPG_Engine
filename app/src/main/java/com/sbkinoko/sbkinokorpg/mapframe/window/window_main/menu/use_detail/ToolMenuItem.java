package com.sbkinoko.sbkinokorpg.mapframe.window.window_main.menu.use_detail;

import static com.sbkinoko.sbkinokorpg.mapframe.window.WindowIdList.NUM_MapMenu_ITEM_SEE;

public class ToolMenuItem extends NeedDetailMenuItem {
    @Override
    public String getTxt() {
        return "アイテム";
    }


    @Override
    protected int getMenuID() {
        return NUM_MapMenu_ITEM_SEE;
    }
}
