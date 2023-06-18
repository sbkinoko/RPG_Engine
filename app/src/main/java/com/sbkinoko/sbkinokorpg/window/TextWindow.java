package com.sbkinoko.sbkinokorpg.window;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

abstract public class TextWindow extends GameWindow {
    public TextWindow(Context context) {
        super(context);
    }

    public void setTvTouch(int viewID) {
        this.menuTV[viewID].setOnTouchListener(new tvTouch());
    }

    protected class tvTouch implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent event) {

            useBtA();

            return true;
        }
    }

    @Override
    public void useBtM() {
        useBtA();
    }

    @Override
    public void useBtB() {
        useBtA();
    }

    @Override
    public void useBtUp() {

    }

    @Override
    public void useBtDown() {

    }

    @Override
    public void useBtRight() {

    }

    @Override
    public void useBtLeft() {

    }
}
