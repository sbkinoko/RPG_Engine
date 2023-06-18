package com.sbkinoko.sbkinokorpg.mapframe.window.window_option;

import com.sbkinoko.sbkinokorpg.OptionConst;
import com.sbkinoko.sbkinokorpg.mapframe.MapFrame;

class ButtonOption extends Option {
    final MapFrame mapFrame;

    public ButtonOption(MapFrame mapFrame) {
        this.mapFrame = mapFrame;
    }

    @Override
    String getTxt() {
        if (OptionConst.isCommandButtonLeft()) {
            return "ボタン配置:左";
        } else {
            return "ボタン配置:右";
        }
    }

    @Override
    void useBtA() {
        OptionConst.changeButtonPattern();
        mapFrame.controllerFrame.resetButtonPlace();
    }
}
