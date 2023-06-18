package com.sbkinoko.sbkinokorpg.battleframe.window.window_action_type;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sbkinoko.sbkinokorpg.MainGame;
import com.sbkinoko.sbkinokorpg.battleframe.BattleConst;
import com.sbkinoko.sbkinokorpg.battleframe.BattleSystem;
import com.sbkinoko.sbkinokorpg.battleframe.action_choice.NonPlayerActionList;
import com.sbkinoko.sbkinokorpg.battleframe.status.PlayerStatus;
import com.sbkinoko.sbkinokorpg.battleframe.window.BattleGameWindow;

public class BattleWindow_ChooseAction extends BattleGameWindow {
    private static final int nameID = 4;


    public BattleWindow_ChooseAction(Context context, BattleSystem battleSystem) {
        super(context, battleSystem);

        menuTV = new TextView[BattleConst.BattleActionMenuNum];
        setMenuTvs();

        setSelectedTv(0);

        frameLayout.setOnTouchListener(new FrameTouchListener());
    }

    @Override
    public void setTvTouch(int viewID) {
        menuTV[viewID].setOnTouchListener(new tvTouch());
    }

    @Override
    public void setMenuTv(int i) {
        menuTV[i].setLayoutParams(new LinearLayout.LayoutParams(
                MainGame.playWindowSize / 2,
                MainGame.playWindowSize / 9
        ));

        if (i < nameID) {
            menuTV[i].setText(getChoice(i).getTxt());
        } else {
            String name = battleSystem.getActionSelectPlayer().getName();
            menuTV[i].setText(name);
        }

        setY(i);

        setX(i);
    }

    private void setY(int id) {
        switch (id) {
            case 0:
            case 1:
                menuTV[id].setY((float) MainGame.playWindowSize / 9 * 1);
                break;
            case 2:
            case 3:
                menuTV[id].setY((float) MainGame.playWindowSize / 9 * 2);
                break;
            case 4:
                menuTV[id].setY(0);
                break;
        }
    }

    private void setX(int id) {
        if (id % 2 == 0) {
            menuTV[id].setX(0);
        } else {
            menuTV[id].setX((float) MainGame.playWindowSize / 2);
        }
    }

    @Override
    public void openMenu() {
        if (battleSystem.isAllPlayerActionSelected()) {
            closeMenu();
            battleSystem.startBattleStep();
            return;
        }

        PlayerStatus nowStatus = battleSystem.getActionSelectPlayer();

        //行動不能の人がいたときの処理
        if (!nowStatus.isAlive()) {
            nowStatus.setActionList(new NonPlayerActionList());
            battleSystem.incWhoseActionSelect();
            openMenu();
            return;
        }

        menuTV[nameID].setText(nowStatus.getName());
        if (nowStatus.getActionType() == BattleConst.Action_NON) {
            setSelectedTv(0);
        } else {
            setSelectedTv(nowStatus.getActionType());
        }

        super.openMenu();
    }

    private Choice getChoice(int id) {
        switch (id) {
            case BattleConst.Action_NormalATK:
                return new Choice_NormalAtk();
            case BattleConst.Action_Tool:
                return new Choice_Tool();
            case BattleConst.Action_Skill:
                return new Choice_Skill();
            case BattleConst.Action_Status:
                return new Choice_Status();
        }
        throw new RuntimeException();
    }

    @Override
    public void useBtA() {
        PlayerStatus status = battleSystem.getActionSelectPlayer();
        Choice choice = getChoice(selectedTV);
        choice.gotoNextWindow(status, battleSystem);
        closeMenu();
    }

    @Override
    public void useBtB() {
        if (battleSystem.getWhoseActionSelect() == 0) {
            battleFrame.battleMenuWindow.openMenu();
        } else {
            battleSystem.decWhoseActionSelect();
            battleFrame.battleChooseEnmWindow.openMenu(
                    battleSystem.getActionSelectPlayer());
        }
        this.closeMenu();
    }

    private class tvTouch implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (event.getAction() != MotionEvent.ACTION_DOWN) {
                return true;
            }
            int viewID = getViewID(view);

            if (nameID == viewID) {
                return true;
            }

            tapTv(viewID);
            return true;
        }
    }


    private static class FrameTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent e) {
            return true;
        }
    }
}


