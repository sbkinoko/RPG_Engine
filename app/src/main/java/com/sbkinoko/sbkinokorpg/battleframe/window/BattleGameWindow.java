package com.sbkinoko.sbkinokorpg.battleframe.window;

import android.content.Context;
import android.widget.FrameLayout;

import androidx.core.content.res.ResourcesCompat;

import com.sbkinoko.sbkinokorpg.MainGame;
import com.sbkinoko.sbkinokorpg.R;
import com.sbkinoko.sbkinokorpg.battleframe.BattleFrame;
import com.sbkinoko.sbkinokorpg.battleframe.BattleSystem;
import com.sbkinoko.sbkinokorpg.window.SelectableWindow;

public abstract class BattleGameWindow extends SelectableWindow {
    protected BattleSystem battleSystem;
    protected BattleFrame battleFrame;

    public BattleGameWindow(Context context, BattleSystem battleSystem) {
        super(context);

        this.battleSystem = battleSystem;
        battleFrame = battleSystem.getBattleFrame();
        this.parent = battleFrame.getBattleFrame();

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

    @Override
    public void useBtM() {

    }

    @Override
    public void useBtUp() {
        moveUpDown();
    }

    @Override
    public void useBtDown() {
        moveUpDown();
    }

    @Override
    public void useBtLeft() {
        moveLeftRight();
    }

    @Override
    public void useBtRight() {
        moveLeftRight();
    }

    void moveUpDown() {
        switch (selectedTV / 2) {
            case 0:
                setSelectedTv(selectedTV + 2);
                break;
            case 1:
                setSelectedTv(selectedTV - 2);
                break;
        }
    }

    protected void moveLeftRight() {
        switch (selectedTV % 2) {
            case 0:
                setSelectedTv(++selectedTV);
                break;
            case 1:
                setSelectedTv(--selectedTV);
                break;
        }
    }
}
