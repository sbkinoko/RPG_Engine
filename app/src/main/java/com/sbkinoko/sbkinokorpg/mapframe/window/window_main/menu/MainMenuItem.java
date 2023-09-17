package com.sbkinoko.sbkinokorpg.mapframe.window.window_main.menu;

import com.sbkinoko.sbkinokorpg.mapframe.MapFrame;

public abstract class MainMenuItem {

    public abstract void useBtA();

    public abstract String getTxt();

    protected MapFrame mapFrame;

    public void setMapFrame(MapFrame mapFrame) {
        this.mapFrame = mapFrame;
    }

}
