package com.sbkinoko.sbkinokorpg.mapframe.window.window_main.menu.use_detail;

import static com.sbkinoko.sbkinokorpg.mapframe.window.WindowIdList.NUM_MapMenu_SKILL_SEE;

public class SkillMenuItem extends NeedDetailMenuItem {
    @Override
    public String getTxt() {
        return "スキル";
    }

    @Override
    protected int getMenuID() {
        return NUM_MapMenu_SKILL_SEE;
    }
}
