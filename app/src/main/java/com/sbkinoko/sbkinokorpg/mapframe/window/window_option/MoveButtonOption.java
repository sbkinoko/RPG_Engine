package com.sbkinoko.sbkinokorpg.mapframe.window.window_option;

import com.sbkinoko.sbkinokorpg.OptionConst;
import com.sbkinoko.sbkinokorpg.mapframe.MapFrame;

class MoveButtonOption extends Option {
    private final MapFrame mapFrame;

    public MoveButtonOption(MapFrame mapFrame) {
        this.mapFrame = mapFrame;
    }

    @Override
    String getTxt() {
        String txt = "移動ボタン";
        if (OptionConst.moveButtonType == 2) {
            return txt + ":スティック";
        } else {
            return txt + ":十字";
        }
    }

    @Override
    void useBtA() {
        OptionConst.changeMoveButtonType();
        mapFrame.controllerFrame.resetButtonPlace();
    }
}
