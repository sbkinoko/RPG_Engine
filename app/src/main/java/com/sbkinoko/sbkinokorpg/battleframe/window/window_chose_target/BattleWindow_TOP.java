package com.sbkinoko.sbkinokorpg.battleframe.window.window_chose_target;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.sbkinoko.sbkinokorpg.MainGame;
import com.sbkinoko.sbkinokorpg.R;
import com.sbkinoko.sbkinokorpg.battleframe.BattleSystem;
import com.sbkinoko.sbkinokorpg.game_item.action_item.use_item.UseItem;
import com.sbkinoko.sbkinokorpg.gameparams.EffectType;
import com.sbkinoko.sbkinokorpg.gameparams.GameParams;

public class BattleWindow_TOP extends Window_ChooseTarget {
    boolean allSelectFlag = false;
    EffectType effectType = EffectType.EFFECT_TYPE_NULL;
    int LINE_NUM = 4;
    int[] target = {0};

    public BattleWindow_TOP(Context context, BattleSystem battleSystem) {
        super(context, battleSystem);

        this.menuTV = new TextView[GameParams.PLAYER_NUM];
        setMenuTvs();
        setSelectedTv(-1);
        reloadTv();
    }

    @Override
    public void setFramePosition() {
        this.frameLayout.setLayoutParams(new ViewGroup.LayoutParams(
                MainGame.playWindowSize,
                MainGame.playWindowSize / 4
        ));
        parent.addView(frameLayout);
    }

    @Override
    public void setMenuTv(int i) {
        this.menuTV[i].setLayoutParams(new ViewGroup.LayoutParams(
                MainGame.playWindowSize / 4,
                MainGame.playWindowSize / 4
        ));
        this.menuTV[i].setMaxLines(LINE_NUM);
        this.menuTV[i].setX((float) (MainGame.playWindowSize / 4) * i);
    }

    @Override
    public void setTvTouch(int viewID) {
        this.menuTV[viewID].setOnTouchListener(new tvTouch());
    }

    public class tvTouch implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (!battleFrame.battleWindow_Top.isOpen) {
                return true;
            }
            if (event.getAction() != MotionEvent.ACTION_DOWN) {
                return true;
            }
            if (allSelectFlag) {
                return true;
            }

            int viewID = getViewID(view);
            if (selectedTV != viewID) {
                setSelectedTv(viewID);
            } else {
                useBtA();
            }
            return true;
        }
    }

    private void correctSelectedTV() {
        if (GameParams.PLAYER_NUM <= selectedTV) {
            selectedTV -= GameParams.PLAYER_NUM;
        } else if (selectedTV < 0) {
            selectedTV += GameParams.PLAYER_NUM;
        }
    }

    /**
     * これが使えるということは少なくとも一人対象がいる
     *
     * @param dir 移動方向
     */
    private void setCanSelectPlayer(int dir) {
        selectedTV += dir;
        correctSelectedTV();
        while (!UseItem.canSelectALY(effectType, battleSystem.getPlayer(selectedTV))) {
            selectedTV += dir;
            correctSelectedTV();
        }
        setSelectedTv(selectedTV);
    }


    @Override
    public void useBtRight() {
        if (allSelectFlag) {
            return;
        }
        setCanSelectPlayer(1);
    }

    @Override
    public void useBtLeft() {
        if (allSelectFlag) {
            return;
        }
        setCanSelectPlayer(-1);
    }


    @Override
    public void useBtA() {
        battleFrame.battleChooseEnmWindow.useBtA();
    }

    @Override
    public void useBtB() {

    }

    public void reloadTv() {
        String txt;
        for (int i = 0; i < GameParams.PLAYER_NUM; i++) {
            txt = battleSystem.getPlayer(i).getName() +
                    "\nLV : " + battleSystem.getPlayer(i).getLV() +
                    "\nHP : " + battleSystem.getPlayer(i).getHP() +
                    "\nMP : " + battleSystem.getPlayer(i).getMP();
            this.menuTV[i].setText(txt);
        }
    }

    public void openMenu(int[] subject, EffectType effectType) {
        this.isOpen = true;
        this.effectType = effectType;
        if (subject.length == 1) {
            allSelectFlag = false;
            target = new int[1];
        } else {
            allSelectFlag = true;
            target = new int[GameParams.PLAYER_NUM];
        }

        this.selectedTV = subject[0];
        int firstTarget = subject[0];
        boolean canOpen = true;
        while (!UseItem.canSelectALY(effectType, battleSystem.getPlayer(selectedTV))) {
            selectedTV++;
            if (battleSystem.getPlayerNum() <= selectedTV) {
                selectedTV -= battleSystem.getPlayerNum();
            }
            if (selectedTV == firstTarget) {
                canOpen = false;
                break;//一周したからbreak
            }
        }
        if (!canOpen) {
            battleFrame.battleChooseEnmWindow.useBtB();
        }
        //選べる対象が決定するまで選択状態にしない
        setSelectedTv(selectedTV);
    }

    @Override
    public void setSelectedTv(int ID) {
        selectedTV = ID;
        if (allSelectFlag) {
            for (int i = 0; i < this.menuTV.length; i++) {
                if (UseItem.canSelectALY(effectType, battleSystem.getPlayer(i))) {
                    this.menuTV[i].setBackground(ResourcesCompat.getDrawable(res, R.drawable.selected_frame, null));
                }
            }
            return;
        }
        for (int i = 0; i < this.menuTV.length; i++) {
            if (i != this.selectedTV) {
                this.menuTV[i].setBackground(null);
            } else {
                this.menuTV[i].setBackground(ResourcesCompat.getDrawable(res, R.drawable.selected_frame, null));
            }
        }
    }

    @Override
    public void closeMenu() {
        this.isOpen = false;
        this.allSelectFlag = false;
        setSelectedTv(-1);
    }

    public int getSelectedTV() {
        return selectedTV;
    }

}
