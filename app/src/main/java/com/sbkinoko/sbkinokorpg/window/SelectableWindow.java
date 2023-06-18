package com.sbkinoko.sbkinokorpg.window;


import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.res.ResourcesCompat;

import com.sbkinoko.sbkinokorpg.R;

abstract public class SelectableWindow extends GameWindow {
    public SelectableWindow(Context context) {
        super(context);
    }

    protected int selectedTV = 0;

    public int getSelectedTV() {
        return selectedTV;
    }

    public void setTvTouch(int viewID) {
        this.menuTV[viewID].setOnTouchListener(new tvTouch());
    }

    protected void setSelectedTv(int ID) {
        selectedTV = ID;
        for (int i = 0; i < this.menuTV.length; i++) {
            if (i != this.selectedTV) {
                this.menuTV[i].setBackground(null);
            } else {
                this.menuTV[i].setBackground(ResourcesCompat.getDrawable(res, R.drawable.selected_frame, null));
            }
        }
    }

    protected int getViewID(View view) {
        for (int i = 0; i < menuTV.length; i++) {
            if (menuTV[i] == view) {
                return i;
            }
        }
        return -1;//どのviewも選ばれていない
    }

    protected class tvTouch implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (event.getAction() != MotionEvent.ACTION_DOWN) {
                return true;
            }

            tapTv(getViewID(view));

            return true;
        }
    }

    protected void tapTv(int id) {
        if (selectedTV == id) {
            useBtA();
        } else {
            setSelectedTv(id);
        }
    }

}
