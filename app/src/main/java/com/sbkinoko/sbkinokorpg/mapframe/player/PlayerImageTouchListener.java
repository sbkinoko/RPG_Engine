package com.sbkinoko.sbkinokorpg.mapframe.player;

import android.view.MotionEvent;
import android.view.View;

import com.sbkinoko.sbkinokorpg.controller.ControllerFrame;
import com.sbkinoko.sbkinokorpg.mapframe.MapFrame;

public class PlayerImageTouchListener implements View.OnTouchListener {
    private final Player player;

    private final MapFrame mapFrame;

    private final ControllerFrame controllerFrame;

    public PlayerImageTouchListener(Player player,
                                    MapFrame mapFrame,
                                    ControllerFrame controllerFrame) {
        this.player = player;
        this.mapFrame = mapFrame;
        this.controllerFrame = controllerFrame;
    }

    @Override
    public boolean onTouch(View v, MotionEvent e) {

        if (!mapFrame.isAllMenuClosed()) {
            return false;
        }

        if (e.getAction() != MotionEvent.ACTION_DOWN) {
            return false;
        }

        if (player.canAction()) {
            controllerFrame.useBtA(e);
        } else {
            controllerFrame.useBtM(e);
        }

        return true;
    }

}
