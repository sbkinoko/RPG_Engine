package com.sbkinoko.sbkinokorpg.battleframe.window.window_chose_target;

import android.content.Context;

import com.sbkinoko.sbkinokorpg.battleframe.window.BattleGameWindow;
import com.sbkinoko.sbkinokorpg.battleframe.BattleSystem;

abstract public class Window_ChooseTarget extends BattleGameWindow {
    public Window_ChooseTarget(Context context, BattleSystem battleSystem){
        super(context,battleSystem);
    }

    @Override
    public void useBtUp() {

    }

    @Override
    public void useBtDown() {

    }
}
