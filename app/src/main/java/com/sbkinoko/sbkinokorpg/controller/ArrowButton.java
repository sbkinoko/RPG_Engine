package com.sbkinoko.sbkinokorpg.controller;

import android.content.Context;
import android.content.res.Configuration;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.sbkinoko.sbkinokorpg.OptionConst;
import com.sbkinoko.sbkinokorpg.gameparams.GameParams;

public class ArrowButton {

    private final Button btLeft, btRight, btUp, btDown;

    ArrowButton(Context context) {
        btLeft = new Button(context);
        btUp = new Button(context);
        btRight = new Button(context);
        btDown = new Button(context);
    }

    public Button getBtDown() {
        return btDown;
    }

    public Button getBtUp() {
        return btUp;
    }

    public Button getBtLeft() {
        return btLeft;
    }

    public Button getBtRight() {
        return btRight;
    }

    void setBtVisibility(int btVisibility) {
        btLeft.setVisibility(btVisibility);
        btRight.setVisibility(btVisibility);
        btUp.setVisibility(btVisibility);
        btDown.setVisibility(btVisibility);
    }

    boolean isArrowButton(View view) {
        return view == btRight ||
                view == btLeft ||
                view == btUp ||
                view == btDown;
    }

    void setArrowButton(Configuration config,
                        int btSize, FrameLayout controllerFL,
                        ControllerFrame.ButtonTouchListener buttonTouchListener) {
        btUp.setText("⇑");
        btUp.setId(GameParams.ID_btUP);
        btUp.setOnTouchListener(buttonTouchListener);
        btUp.setLayoutParams(new ViewGroup.LayoutParams(
                btSize,
                btSize
        ));
        controllerFL.addView(btUp);

        btRight.setText("⇒");
        btRight.setId(GameParams.ID_btRIGHT);
        btRight.setOnTouchListener(buttonTouchListener);

        btLeft.setText("⇐");
        btLeft.setId(GameParams.ID_btLEFT);
        btLeft.setOnTouchListener(buttonTouchListener);
        ViewGroup.LayoutParams layoutParams;

        if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutParams = new ViewGroup.LayoutParams(
                    btSize,
                    btSize
            );
        } else {
            layoutParams = new ViewGroup.LayoutParams(
                    btSize / 2,
                    btSize
            );
        }

        btRight.setLayoutParams(layoutParams);
        btLeft.setLayoutParams(layoutParams);

        controllerFL.addView(btRight);
        controllerFL.addView(btLeft);

        btDown.setText("⇓");
        btDown.setId(GameParams.ID_btDOWN);
        btDown.setOnTouchListener(buttonTouchListener);
        btDown.setLayoutParams(new ViewGroup.LayoutParams(
                btSize,
                btSize
        ));
        controllerFL.addView(btDown);
    }


    void setArrowButtonPosition(Configuration config,
                                int controllerFrameWidth, int controllerFrameHeight,
                                int btSize, int btH_3) {

        float leftX, upDownX, rightX;
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            float tmpx;
            if (OptionConst.isCommandButtonLeft()) {
                tmpx = (float) (controllerFrameWidth - (controllerFrameWidth - controllerFrameHeight) / 4 - btSize / 2);
            } else {
                tmpx = (float) ((controllerFrameWidth - controllerFrameHeight) / 4 - btSize / 2);
            }
            leftX = tmpx;
            upDownX = tmpx;
            rightX = tmpx + btSize - btH_3 / 2;
        } else {
            if (OptionConst.isCommandButtonLeft()) {
                rightX = (controllerFrameWidth - btH_3);
                upDownX = (controllerFrameWidth - 2 * btH_3);
                leftX = (controllerFrameWidth - 3 * btH_3);
            } else {
                leftX = ((float) (btH_3 - btSize) / 2);
                rightX = ((float) (btH_3 * 5 - btSize) / 2);
                upDownX = ((float) (btH_3 * 3 - btSize) / 2);
            }
        }

        btUp.setX(upDownX);
        btLeft.setX(leftX);
        btRight.setX(rightX);
        btDown.setX(upDownX);

        btUp.setY((float) (btH_3 - btSize) / 2);
        btLeft.setY((float) (btH_3 * 3 - btSize) / 2);
        btRight.setY((float) (btH_3 * 3 - btSize) / 2);
        btDown.setY((float) (btH_3 * 5 - btSize) / 2);

    }
}
