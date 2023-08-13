package com.sbkinoko.sbkinokorpg.window;

import android.content.Context;
import android.content.res.Resources;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.sbkinoko.sbkinokorpg.gameparams.GameParams;

public abstract class GameWindow implements MenuWindowInterface {

    public GameWindow(Context context) {
        this.frameLayout = new FrameLayout(context);
        this.context = context;
        this.res = context.getResources();
        this.isOpen = false;
    }

    protected int viewHeight;
    protected int viewWidth;

    protected FrameLayout frameLayout;
    protected FrameLayout parent = null;

    protected TextView[] menuTV = null;

    protected Context context;
    protected Resources res;
    protected boolean isOpen;

    public boolean isOpen() {
        return isOpen;
    }

    //todo　毎回tvをセットするのはあほくさいのでは？
    protected void setMenuTvs() {
        if (menuTV == null) {
            return;
        }

        removeTVs();

        for (int i = 0; i < menuTV.length; i++) {
            this.menuTV[i] = new TextView(context);
            setTvTouch(i);
            this.menuTV[i].setAutoSizeTextTypeUniformWithConfiguration(10, 200, 10, 0);
            this.menuTV[i].setPadding(5, 5, 5, 5);
            this.setMenuTv(i);
            frameLayout.addView(menuTV[i]);
        }
    }

    protected void removeTVs() {
        for (TextView textView : menuTV) {
            frameLayout.removeView(textView);
        }
    }

    public void openMenu() {
        parent.removeView(this.frameLayout);
        parent.addView(this.frameLayout);
        this.isOpen = true;
    }

    public void closeMenu() {
        parent.removeView(this.frameLayout);
        this.isOpen = false;
    }

    public void OnBtClicked(View view, MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN) {
            return;
        }
        switch (view.getId()) {
            case GameParams.ID_btRIGHT:
                useBtRight();
                break;
            case GameParams.ID_btLEFT:
                useBtLeft();
                break;
            case GameParams.ID_btUP:
                useBtUp();
                break;
            case GameParams.ID_btDOWN:
                useBtDown();
                break;
            case GameParams.ID_btA:
                useBtA();
                break;
            case GameParams.ID_btMenu:
                useBtM();
                break;
            case GameParams.ID_btB:
                useBtB();
                break;
        }
    }
}
