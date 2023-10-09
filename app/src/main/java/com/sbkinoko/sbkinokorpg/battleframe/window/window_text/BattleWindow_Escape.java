package com.sbkinoko.sbkinokorpg.battleframe.window.window_text;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sbkinoko.sbkinokorpg.OptionConst;
import com.sbkinoko.sbkinokorpg.battleframe.BattleSystem;
import com.sbkinoko.sbkinokorpg.gameparams.BattleResult;
import com.sbkinoko.sbkinokorpg.gameparams.EventBattleFlag;

import java.util.Random;

public class BattleWindow_Escape extends BattleTxtWindow {
    boolean escapeFlag = false;


    public BattleWindow_Escape(Context context, BattleSystem battleSystem) {
        super(context, battleSystem);

        this.menuTV = new TextView[1];
        setMenuTvs();
    }

    @Override
    public void setMenuTv(int i) {
        this.menuTV[i].setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        menuTV[i].setGravity(Gravity.CENTER);
    }


    @Override
    public void useBtA() {
        if (battleSystem.isNotEscapable()) {
            battleFrame.battleEscapeWindow.closeMenu();
            battleFrame.battleMenuWindow.openMenu();
            return;
        }

        if (escapeFlag) {
            battleFrame.closeBattleFrame( EventBattleFlag.NotEvent, BattleResult.Escape);
        }

        battleFrame.battleEscapeWindow.closeMenu();
        battleFrame.battleMenuWindow.openMenu();
    }


    @Override
    public void openMenu() {
        super.openMenu();

        if (battleSystem.isNotEscapable()) {
            this.menuTV[0].setText("この戦いは逃げられない!");
            return;
        }

        if (new Random().nextInt(OptionConst.escapeFlag) < 1) {
            this.menuTV[0].setText("逃げられた");
            escapeFlag = true;
        } else {
            this.menuTV[0].setText("逃げられなかった");
            escapeFlag = false;
        }
    }
}
