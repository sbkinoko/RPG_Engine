package com.sbkinoko.sbkinokorpg.mapframe.window;

import android.content.Context;

import androidx.core.content.res.ResourcesCompat;

import com.sbkinoko.sbkinokorpg.MainGame;
import com.sbkinoko.sbkinokorpg.R;
import com.sbkinoko.sbkinokorpg.mapframe.MapFrame;
import com.sbkinoko.sbkinokorpg.window.SelectableWindow;

public abstract class MapGameWindow extends SelectableWindow {
    protected MapFrame mapFrame;


    public MapGameWindow(Context context, MapFrame mapFrame) {
        super(context);

        this.mapFrame = mapFrame;
        this.parent = mapFrame.getFrameLayout();

        viewHeight = MainGame.playWindowSize / 10;
        viewWidth = MainGame.playWindowSize / 2;
        frameLayout.setBackground(ResourcesCompat.getDrawable(res, R.drawable.character_frame, null));
        this.setFramePosition();
    }

    protected MapGameWindow(Context context, MapFrame mapFrame, String windowName) {
        super(context);

        this.mapFrame = mapFrame;
        this.parent = mapFrame.getFrameLayout();

        viewHeight = MainGame.playWindowSize / 10;
        viewWidth = MainGame.playWindowSize / 2;
        frameLayout.setBackground(ResourcesCompat.getDrawable(res, R.drawable.character_frame, null));
        this.setFramePosition();
    }

    @Override
    public void useBtA() {

    }

    @Override
    public void useBtM() {
        mapFrame.closeAllMenu();
    }

    @Override
    public void useBtB() {
        closeMenu();
        mapFrame.mapMainMenuWindow.openMenu();
    }

    @Override
    public void useBtUp() {
        selectedTV--;
        correctSelectedTV();
        setSelectedTv(selectedTV);
    }

    @Override
    public void useBtDown() {
        selectedTV++;
        correctSelectedTV();
        setSelectedTv(selectedTV);
    }


    public void setIsClosed() {
        isOpen = false;
    }

    @Override
    public void useBtRight() {
        setSelectedTv(menuTV.length - 1);
    }

    @Override
    public void useBtLeft() {
        setSelectedTv(0);
    }

    protected void correctSelectedTV() {
        if (menuTV.length <= selectedTV) {
            selectedTV -= menuTV.length;
        } else if (selectedTV < 0) {
            selectedTV += menuTV.length;
        }
    }
}
