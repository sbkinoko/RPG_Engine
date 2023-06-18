package com.sbkinoko.sbkinokorpg.mapframe.window.window_option;

import com.sbkinoko.sbkinokorpg.OptionConst;

class MonsterOption extends Option {
    String txt = "モンスター";

    @Override
    String getTxt() {
        if (OptionConst.encounter == 200) {
            return (txt + ":OFF");
        }
        return (txt + ":ON");
    }

    @Override
    void useBtA() {
        OptionConst.changeMonsApp();
    }
}
