package com.sbkinoko.sbkinokorpg.mapframe.window.window_main.menu;

public class OptionMenuItem extends MainMenuItem {
    @Override
    public void useBtA() {
        mapFrame.window_option.openMenu();
    }

    @Override
    public String getTxt() {
        return "オプション";
    }
}
