package com.sbkinoko.sbkinokorpg.battleframe.window.window_text;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sbkinoko.sbkinokorpg.battleframe.BattleSystem;


public class BattleWindow_Attack extends BattleTxtWindow {
    public BattleWindow_Attack(Context context, BattleSystem battleSystem) {
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

    public void openMenu(String txt) {
        super.openMenu();
        this.menuTV[0].setText(txt);
    }

    @Override
    public void useBtA() {
        if (battleFrame.isImageShaving()) {
            return;
        }

        this.closeMenu();
        battleSystem.checkNextStep();
    }
}
