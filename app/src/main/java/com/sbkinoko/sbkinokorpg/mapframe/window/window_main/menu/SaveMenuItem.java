package com.sbkinoko.sbkinokorpg.mapframe.window.window_main.menu;

public class SaveMenuItem extends MainMenuItem {
    @Override
    public void useBtA() {
        mapFrame.mapSaveWindow.openMenu();
    }

    @Override
    public String getTxt() {
        return "セーブ";
    }
}
