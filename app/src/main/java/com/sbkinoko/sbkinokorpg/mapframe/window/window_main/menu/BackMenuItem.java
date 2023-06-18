package com.sbkinoko.sbkinokorpg.mapframe.window.window_main.menu;

public class BackMenuItem extends MainMenuItem {
    @Override
    public void useBtA() {
        mapFrame.mapMainMenuWindow.useBtB();
    }

    @Override
    public String getTxt() {
        return "戻る";
    }
}
