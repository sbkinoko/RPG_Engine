package com.sbkinoko.sbkinokorpg.mapframe.window.window_option;

import com.sbkinoko.sbkinokorpg.OptionConst;

class EscapeOption extends Option {
    String txt = "必ず逃げる";

    @Override
    String getTxt() {

        if (OptionConst.escapeFlag == 1) {
            return (txt + ":ON");
        }
        return (txt + ":OFF");
    }

    @Override
    void useBtA() {
        OptionConst.changeEscapeFlag();
    }
}
