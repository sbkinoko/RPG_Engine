package com.sbkinoko.sbkinokorpg.battleframe.window.window_text;

import android.content.Context;
import android.widget.FrameLayout;

import androidx.core.content.res.ResourcesCompat;

import com.sbkinoko.sbkinokorpg.MainGame;
import com.sbkinoko.sbkinokorpg.R;
import com.sbkinoko.sbkinokorpg.battleframe.BattleFrame;
import com.sbkinoko.sbkinokorpg.battleframe.BattleSystem;
import com.sbkinoko.sbkinokorpg.window.TextWindow;

abstract public class BattleTxtWindow extends TextWindow {
    BattleFrame battleFrame;
    BattleSystem battleSystem;

    BattleTxtWindow(Context context, BattleSystem battleSystem) {
        super(context);
        this.battleSystem = battleSystem;
        battleFrame = battleSystem.getBattleFrame();
        parent = battleFrame.getBattleFrame();

        frameLayout.setBackground(ResourcesCompat.getDrawable(res, R.drawable.character_frame, null));

        setFramePosition();
    }


    @Override
    public void setFramePosition() {
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(
                MainGame.playWindowSize,
                MainGame.playWindowSize / 3
        ));
        frameLayout.setY((float) MainGame.playWindowSize * 2 / 3);
    }
}
