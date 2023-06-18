package com.sbkinoko.sbkinokorpg.mapframe.window.window_option;

import com.sbkinoko.sbkinokorpg.OptionConst;
import com.sbkinoko.sbkinokorpg.mapframe.MapFrame;

class CellNumOption extends Option {
    private final MapFrame mapFrame;

    public CellNumOption(MapFrame mapFrame) {
        this.mapFrame = mapFrame;
    }

    @Override
    String getTxt() {
        String txt = "背景番号";
        if (OptionConst.visibilityOfMapCellNum) {
            return txt + ":ON";
        } else {
            return txt + ":OFF";
        }
    }

    @Override
    void useBtA() {
        OptionConst.changeCellNum();
        mapFrame.getBgcMatrix().resetBackGroundCellText();
    }
}
