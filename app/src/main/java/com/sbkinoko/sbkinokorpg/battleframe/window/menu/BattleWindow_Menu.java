package com.sbkinoko.sbkinokorpg.battleframe.window.menu;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sbkinoko.sbkinokorpg.MainGame;
import com.sbkinoko.sbkinokorpg.battleframe.BattleConst;
import com.sbkinoko.sbkinokorpg.battleframe.BattleSystem;
import com.sbkinoko.sbkinokorpg.battleframe.window.BattleGameWindow;

public class BattleWindow_Menu extends BattleGameWindow {

    static final int BATTLE_TV = 0,
            OPTION_TV = 1,
            STRATEGY_TV = 2,
            ESCAPE_TV = 3;

    private MenuItem getItem(int id) {
        switch (id) {
            case BATTLE_TV:
                return new Menu_ATK();
            case OPTION_TV:
                return new Menu_Strategy();
            case STRATEGY_TV:
                return new Menu_Option();
            case ESCAPE_TV:
                return new Menu_Escape();
        }
        throw new RuntimeException();
    }

    public BattleWindow_Menu(Context context, BattleSystem battleSystem) {
        super(context, battleSystem);

        menuTV = new TextView[BattleConst.BattleMenuNum];
        setMenuTvs();
        setSelectedTv(0);
    }

    @Override
    public void useBtA() {
        getItem(selectedTV).useBtA(this, battleFrame);
    }

    @Override
    public void useBtB() {
        setSelectedTv(ESCAPE_TV);
    }

    @Override
    public void setMenuTv(int id) {
        menuTV[id].setLayoutParams(new LinearLayout.LayoutParams(
                MainGame.playWindowSize / 2,
                MainGame.playWindowSize / 6
        ));
        menuTV[id].setText(getItem(id).getName());

        menuTV[id].setY(((int) (id / 2.0)) * (float) MainGame.playWindowSize / 6);
        menuTV[id].setX((id % 2) * (float) MainGame.playWindowSize / 2);
    }
}