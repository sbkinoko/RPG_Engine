package com.sbkinoko.sbkinokorpg.battleframe.window.menu;

import com.sbkinoko.sbkinokorpg.battleframe.BattleFrame;

public class Menu_ATK extends MenuItem {
    @Override
    String getName() {
        return "たたかう";
    }

    @Override
    void useBtA(BattleWindow_Menu battleWindow_menu, BattleFrame battleFrame) {
        battleFrame.battleWindow_chooseAction.openMenu();
        battleWindow_menu.closeMenu();
    }
}
