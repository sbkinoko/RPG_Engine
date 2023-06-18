package com.sbkinoko.sbkinokorpg.battleframe.window.menu;

import com.sbkinoko.sbkinokorpg.battleframe.BattleFrame;

public class Menu_Escape extends MenuItem {
    @Override
    String getName() {
        return "にげる";
    }

    @Override
    void useBtA(BattleWindow_Menu battleWindow_menu, BattleFrame battleFrame) {
        battleFrame.battleEscapeWindow.openMenu();
        battleWindow_menu.closeMenu();
    }
}
