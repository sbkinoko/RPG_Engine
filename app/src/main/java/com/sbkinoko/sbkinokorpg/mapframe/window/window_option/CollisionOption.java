package com.sbkinoko.sbkinokorpg.mapframe.window.window_option;

import com.sbkinoko.sbkinokorpg.OptionConst;
import com.sbkinoko.sbkinokorpg.mapframe.MapFrame;

class CollisionOption extends Option {
    String txt = "当たり判定";

    MapFrame mapFrame;

    public CollisionOption(MapFrame mapFrame) {
        this.mapFrame = mapFrame;
    }

    @Override
    String getTxt() {
        if (!OptionConst.collisionDrawFlag) {
            return (txt + ":OFF");
        }
        return (txt + ":ON");
    }

    @Override
    void useBtA() {
        OptionConst.changeCollision();
        mapFrame.getBgcMatrix().reDraw();
        mapFrame.reDrawPlayer();
        mapFrame.getMapViewModel().getNpcMatrix().reDraw();
    }
}
