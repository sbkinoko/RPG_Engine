package com.sbkinoko.sbkinokorpg.mapframe.window.window_option;

import com.sbkinoko.sbkinokorpg.OptionConst;

class MoveSpeedOption extends Option {
    @Override
    String getTxt() {
        return "移動速度" + ":" + OptionConst.V_mul;
    }

    @Override
    void useBtA() {
        OptionConst.changeSpeed();
    }
}
