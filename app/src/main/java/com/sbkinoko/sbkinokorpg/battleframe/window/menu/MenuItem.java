package com.sbkinoko.sbkinokorpg.battleframe.window.menu;

import com.sbkinoko.sbkinokorpg.battleframe.BattleFrame;

public abstract class MenuItem {
    abstract String getName();

    abstract void useBtA(BattleWindow_Menu battleWindow_menu, BattleFrame battleFrame);
}
